package com.aquariusmaster.elastic;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represent class that parse json data from a file
 * Created by harkonnen on 15.07.16.
 */
public class UserJSONParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserJSONParser.class);

    /**
     * Parsing json file into a List
     * @param step the number of entries that will return in List
     * @param counter number of entries to skip
     * @param path path to the json file
     * @return List of Users
     */
    public static List<User> parse(int step, long counter, String path){

        LOGGER.info("Counter: " + counter);
        List<User> resultList = new ArrayList<User>(step);

        try {

            JsonFactory jfactory = new JsonFactory();

            /*** READ JSON DATA FROM FILE ***/
            JsonParser jParser = jfactory
                    .createParser(new File(path));

            // skip section
            long skip = counter;
                //Loop until skip already proceeded entries
            while(skip > 0){
                if(jParser.nextToken() == JsonToken.END_OBJECT){
                    skip--;
                }
            }

            while (resultList.size() < step){
                User currentUser = new User();
                // LOOP UNTIL WE READ END OF JSON DATA, INDICATED BY }
                while (jParser.nextToken() != JsonToken.END_OBJECT) {

                    String fieldname = jParser.getCurrentName();

                    // current Token == null indicate to the end of json file
                    if (jParser.currentToken() == null) {
                        return resultList;
                    }

                    if ("id".equals(fieldname)) {
                        // once we get the token name we are interested,
                        // move next to get its value
                        jParser.nextToken();
                        // read the value of id
                        currentUser.setId(jParser.getLongValue());
                    }
                    if ("gender".equals(fieldname)) {
                        jParser.nextToken();
                        currentUser.setGender(jParser.getValueAsString());
                    }
                    if ("first_name".equals(fieldname)) {
                        jParser.nextToken();
                        currentUser.setFirst_name(jParser.getValueAsString());
                    }
                    if ("last_name".equals(fieldname)) {
                        jParser.nextToken();
                        currentUser.setLast_name(jParser.getValueAsString());
                    }
                    if ("email".equals(fieldname)) {
                        jParser.nextToken();
                        currentUser.setEmail(jParser.getValueAsString());
                    }
                    if ("ip_address".equals(fieldname)) {
                        jParser.nextToken();
                        currentUser.setIp_address(jParser.getValueAsString());
                    }

                }
                resultList.add(currentUser);
            }

            jParser.close();

        } catch (JsonGenerationException e) {
            LOGGER.error("JsonGenerationException", e);

        } catch (IOException e) {
            LOGGER.error("IOException while parsing", e);
        }

        LOGGER.info("Parsed " + step + " users from file: " + path);
        LOGGER.info("Parsed result list: " + resultList);
        return resultList;
    }
}
