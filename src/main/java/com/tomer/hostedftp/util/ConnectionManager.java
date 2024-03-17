package com.tomer.hostedftp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.io.InputStream;
import java.io.IOException;

public class ConnectionManager implements ServletContextListener {
	
    private static String url;
    private static String username;
    private static String password;
    private static final Logger logger = Logger.getLogger(ConnectionManager.class.getName());
    
    static {
    	try (InputStream input = ConnectionManager.class.getClassLoader().getResourceAsStream("database.properties")) {
    		Properties prop = new Properties();
    		prop.load(input);
    		url = prop.getProperty("db.url");
    		username = prop.getProperty("db.username");
    		password = prop.getProperty("db.password");
    	} catch (IOException exception) {
    		logger.severe("An IOException occured: " + exception.getMessage());
    	}
    	
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    	} catch (ClassNotFoundException exception) {
    		logger.severe("A ClassNotFoundException occured: " + exception.getMessage());
    	}
    }
    
    public static Connection getConnection() throws SQLException{
    	return DriverManager.getConnection(url, username, password);
    }
    
    public static void closeConnection(Connection connection) {
    	if (connection != null) {
    		try {
    			connection.close();
    		} catch (SQLException exception) {
    			logger.severe("A SQLException occured: " + exception.getMessage());
    		}
    	}
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            DriverManager.deregisterDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException exception) {
        	logger.severe("A SQLException occured: " + exception.getMessage());
        }
    }


}
