package com.aquariusmaster.elastic;

import java.net.InetAddress;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DockerClientBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.NodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.annotation.Resource;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.aquariusmaster.elastic")
@ComponentScan(basePackages = { "com.aquariusmaster.elastic" })
public class ApplicationConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

    @Resource
    private Environment environment;


    @Bean
    public NodeBuilder nodeBuilder() {
        return new NodeBuilder();
    }

    @Bean
    public Client getNodeClient() {

        dockerStarter();

        Client client = null;
        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(
                            new InetSocketTransportAddress(
                                    InetAddress.getByName(environment.getProperty("elasticsearch.host")),
                                    Integer.parseInt(environment.getProperty("elasticsearch.port"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(getNodeClient());
    }

    private void dockerStarter(){

        LOGGER.info("Connect to Docker");
        DockerClient dockerClient = DockerClientBuilder.getInstance("unix:///var/run/docker.sock").build();
        Info info = dockerClient.infoCmd().exec();
        LOGGER.info("Docker info: " + info);
        LOGGER.info(info.getContainersRunning() + " is already containers run");
        LOGGER.info("Start container");
        ExposedPort tcp9200 = ExposedPort.tcp(9200);
        ExposedPort tcp9300 = ExposedPort.tcp(9300);
        Ports portBindings = new Ports();
        portBindings.bind(tcp9200, new Ports.Binding(environment.getProperty("elasticsearch.host"), "9200"));
        portBindings.bind(tcp9300, new Ports.Binding(environment.getProperty("elasticsearch.host"), "9300"));

        CreateContainerResponse container = dockerClient.createContainerCmd(environment.getProperty("docker.image"))
                .withExposedPorts(tcp9200, tcp9300)
                .withPortBindings(portBindings)
                .exec();

        dockerClient.startContainerCmd(container.getId()).exec();
        LOGGER.info("Container run with ID = " + container.getId());
        LOGGER.info("IP for elasticsearch access: " + environment.getProperty("elasticsearch.host"));
    }

}