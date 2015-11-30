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
    private static volatile Boolean validated = Boolean.FALSE;
    private static final Object monitor = new Object();

    private static void loadCondifuration() {

        try {

            final Path path = getConfigurationFilePath();
            configuration = Utils.readInputStream(Files.newInputStream(path), Configuration.class);

        } catch (IOException e) {
            throw new Couch4JException(e);
        }

    }

    public static Path getConfigurationFilePath() {
        final String userHome = System.getProperty("user.home");
        return Paths.get(userHome + File.separator + ".couch4j/config.json");
    }

    public static Session getSession() {
        synchronized (monitor) {
            if (configuration == null) {
                loadCondifuration();
            }
        }
        final Session test = configuration.getTest();
        final Session current = configuration.getCurrent();
        attemptAuthenticatedCall(isUnitTesting() ? test : current);
        return isUnitTesting() ? test : current;
    }

    public static void reset() {
        configuration = null;
        validated = Boolean.FALSE;
    }

    private static boolean isUnitTesting() {

        boolean condition1 = System.getProperty("surefire.test.class.path") != null;
        boolean condition2 = System.getProperty("sun.java.command").contains("-testLoaderClass");
        return condition1 || condition2;

    }

    public static void attemptAuthenticatedCall(final Session session) {

        synchronized (monitor) {

            validated = Boolean.TRUE;
            OperationResponse response = null;
            try {
                response = DatabaseOperations.createDatabase("dixvingtneuftrentehuit", session);
                if (response.isOk()) {
                    response = DatabaseOperations.deleteDatabase("dixvingtneuftrentehuit", session);
                }
            } catch (Exception e) {
                if (response != null) {
                    throw new Couch4JException("An error occured when attempting to delete temporary database \"dixvingtneuftrentehuit\"", e);
                } else {
                    throw new Couch4JException("Check couch4j configuration - most likely an authentication issue " + Utils.objectToJSON(session));
                }
            }

        }

    }

}
