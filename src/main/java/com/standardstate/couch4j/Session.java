package com.standardstate.couch4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Session {

    private String host = null;
    private String database = "";
    private String username = null;
    private String password = null;
    private int port = Constants.DEFAULT_PORT;

    public static Session load(final String filename) {
        try {
            
            final Properties properties = new Properties();
            properties.load(new FileInputStream(filename));
            
            final Session session = new Session();
            session.setHost(properties.getProperty("host"));
            session.setDatabase(properties.getProperty("database"));
            session.setUsername(properties.getProperty("username"));
            session.setPassword(properties.getProperty("password"));
            session.setPort(Integer.valueOf(properties.getProperty("port")));
            
            return session;
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(final String host) {
        this.host = host;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(final String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(final int port) {
        this.port = port;
    }

}
