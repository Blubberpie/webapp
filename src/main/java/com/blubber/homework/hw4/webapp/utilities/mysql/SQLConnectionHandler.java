package com.blubber.homework.hw4.webapp.utilities.mysql;

import static com.blubber.homework.hw4.webapp.utilities.properties.ConnectionProperties.*;

import java.sql.*;
import java.util.Properties;

public class SQLConnectionHandler {

    private Connection conn;
    private SQLActions sqlActions;

    public SQLConnectionHandler(){
        this.conn = getConnection();
        sqlActions = new SQLActions(conn);
        sqlActions.initializeSchema();
    }

    private static Connection getConnection(){
        try {
            Properties connectionProps = new Properties(){{
                put("user", connUsername);
                put("connPassword", connPassword);
            }};
            return DriverManager.getConnection(connectionString, connectionProps);
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public SQLActions sqlActions() { return sqlActions; }
}
