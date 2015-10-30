package com.standardstate.couch4j;

import com.standardstate.couch4j.response.OperationResponse;
import com.standardstate.couch4j.util.Utils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigurationManager {

    private static Configuration configuration = null;

    private static void loadCondifuration() {

        try {

            final Path path = getConfigurationFilePath();
            configuration = Utils.readInputStream(Files.newInputStream(path), Configuration.class);
            attemptAuthenticatedCall(getSession());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static Path getConfigurationFilePath() {
        final String userHome = System.getProperty("user.home");
        return Paths.get(userHome + File.separator + ".couch4j/config.json");
    }

    public static Session getSession() {
        if (configuration == null) {
            loadCondifuration();
        }
        return (isUnitTesting() ? configuration.getTest() : configuration.getCurrent());
    }

    public static void reset() {
        configuration = null;
    }

    private static boolean isUnitTesting() {

        boolean condition1 = System.getProperty("surefire.test.class.path") != null;
        boolean condition2 = System.getProperty("sun.java.command").contains("-testLoaderClass");
        return (condition1 || condition2);

    }

    public static void attemptAuthenticatedCall(final Session session) {

        OperationResponse response = null;
        try {
            response = DatabaseOperations.createDatabase("dixvingtneuftrentehuit");
            if (response.isOk()) {
                response = DatabaseOperations.deleteDatabase("dixvingtneuftrentehuit");
            }
        } catch (Exception e) {
            if(response != null) {
                throw new RuntimeException("An error occured when attempting to delete temporary database \"dixvingtneuftrentehuit\"", e);
            } else {
                throw new RuntimeException("Check couch4j configuration - most likely an authentication issue " + Utils.objectToJSON(session));
            }
        }

    }

}
