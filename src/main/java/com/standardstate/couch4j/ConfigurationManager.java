package com.standardstate.couch4j;

import com.standardstate.couch4j.util.Utils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ConfigurationManager {

    private static Configuration configuration = null;
    
    private static void loadCondifuration() {

        try {
            
            final Path path = getConfigurationFilePath();
            final List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            final String json = Utils.listToString(lines);
            
            configuration = Utils.readString(json, Configuration.class);
            
        } catch (IOException e) {
            configuration = null;
            throw new RuntimeException(e);
        }
        
    }
    
    public static Path getConfigurationFilePath() {
        final String userHome = System.getProperty("user.home");
        return Paths.get(userHome + File.separator + ".couch4j/config.json");
    }
    
    public static Session getSession() {
        if(configuration == null) {
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

}
