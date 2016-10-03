FROM i686/ubuntu
ENV DEBIAN_FRONTEND noninteractive
RUN apt-get update -q && apt-get -y install software-properties-common
RUN add-apt-repository ppa:webupd8team/java && apt-get update -q && echo debconf shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && apt-get -y -q install oracle-java8-installer
RUN wget https://download.elastic.co/elasticsearch/release/org/elasticsearch/distribution/deb/elasticsearch/2.3.4/elasticsearch-2.3.4.deb && dpkg -i elasticsearch-* && echo 'network.host: _non_loopback_' > /etc/elasticsearch/elasticsearch.yml && service elasticsearch restart && rm elasticsearch-*
COPY start.sh /
ENTRYPOINT /start.sh
CMD /start.sh
EXPOSE 9200 9300
