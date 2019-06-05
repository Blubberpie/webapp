package com.blubber.homework.hw4.webapp.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.blubber.homework.hw4.webapp.utilities.ConnectionProperties.connSchemaName;
import static com.blubber.homework.hw4.webapp.utilities.SchemaProperties.*;
import static com.blubber.homework.hw4.webapp.utilities.SchemaProperties.tUsername;

public class SQLActions {

    private Connection conn;

    SQLActions(Connection conn){ this.conn = conn; }

    private void executeMySQLUpdate(String sqlCommand){
        if (conn != null) {
            try { conn.createStatement().executeUpdate(sqlCommand); }
            catch(SQLException ex){ ex.printStackTrace(); }
        }
    }

    // todo: move all sql commands to own class for clean code
     void initializeSchema(){
        String sqlCreateSchema = String.format("CREATE SCHEMA IF NOT EXISTS %s;", connSchemaName);
        String sqlSwitchToSchema = String.format("USE %s;", connSchemaName);
        String sqlCreateUser = String.format("CREATE TABLE IF NOT EXISTS %s(" +
                        "%s VARCHAR(30) NOT NULL," +
                        "%s VARCHAR(72) NOT NULL," +
                        "PRIMARY KEY (%s));",
                tUser, tUsername, tPassword, tUsername
        );

        executeMySQLUpdate(sqlCreateSchema);
        executeMySQLUpdate(sqlSwitchToSchema);
        executeMySQLUpdate(sqlCreateUser);
    }

    // todo: check redundant null checkers
    public void updateUserTable(String username, String password){
        String action = String.format("INSERT INTO " +
                        "%s (%s, %s) " +
                        "VALUES (\'%s\', \'%s\');",
                tUser, tUsername, tPassword, username, password);

        executeMySQLUpdate(action);
    }

    public boolean usernameExists(String username){
        String action = String.format("SELECT 1 FROM %s WHERE %s = \'%s\';",
                tUser, tUsername, username);
        if (conn != null) {
            try{
                PreparedStatement preparedStatement = conn.prepareStatement(action);
                ResultSet rs = preparedStatement.executeQuery();
                return rs.next();
            }catch(SQLException ex){ ex.printStackTrace(); }
        }
        return false;
    }

    public ResultSet getPassword(String username){
        String action = String.format("SELECT %s FROM %s WHERE %s = \'%s\';",
                tPassword, tUser, tUsername, username);
        if (conn != null){
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(action);
                return preparedStatement.executeQuery();
            }catch (SQLException ex){ ex.printStackTrace(); }
        }
        return null;
    }
}
