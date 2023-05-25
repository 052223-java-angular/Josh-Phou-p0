package com.revature.app.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static ConnectionFactory instance;
    private Connection connection;

    private ConnectionFactory() throws IOException, ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Properties prop = getProperties();
        connection = DriverManager.getConnection(
                prop.getProperty("url"),
                prop.getProperty("username"),
                prop.getProperty("password"));

    }

    /*----------Methods--------*/

    public static ConnectionFactory getInstance() throws SQLException, IOException, ClassNotFoundException {
        if (instance == null || instance.getConnection().isClosed()){
            instance = new ConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    /*-------Helper Methods-------*/
    private Properties getProperties() throws IOException {
        Properties prop = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null){
                throw new IOException("Unable to find application properties");
            }
            prop.load(input);
        }
        return prop;
    }

}
