package com.blubber.homework.hw4.webapp.utilities.mysql;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.blubber.homework.hw4.webapp.utilities.properties.ConnectionProperties.connSchemaName;
import static com.blubber.homework.hw4.webapp.utilities.properties.SchemaProperties.*;
import static com.blubber.homework.hw4.webapp.utilities.properties.SchemaProperties.tUsername;
import static com.blubber.homework.hw4.webapp.utilities.mysql.SQLCommands.*;

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
        executeMySQLUpdate(String.format(sqlCreateSchema, connSchemaName)); // create schema if not exists
        executeMySQLUpdate(String.format(sqlSwitchToSchema, connSchemaName)); // use schema
        executeMySQLUpdate(String.format(sqlCreateUser, // create user table if not exists
                tUser, tUsername, tPassword,
                tFirstName, tLastName, tBirthYear,
                tUsername));
        String firstUser = "whodidnothingwrong";
        if (!usernameExists(firstUser)) {
            updateUserTable(firstUser, BCrypt.hashpw("thanos", BCrypt.gensalt()));
        }
    }

    // todo: check redundant null checkers
    public void updateUserTable(String username, String password){
        executeMySQLUpdate(String.format(sqlUpdateUserTable, tUser, tUsername, tPassword, username, password));
    }

    public boolean usernameExists(String username){
        if (conn != null) {
            try{
                PreparedStatement preparedStatement = conn.prepareStatement(String.format(sqlGetUsername, tUser, tUsername, username));
                ResultSet rs = preparedStatement.executeQuery();
                return rs.next();
            }catch(SQLException ex){ ex.printStackTrace(); }
        }
        return false;
    }

    public ResultSet getPassword(String username){
        if (conn != null){
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(String.format(sqlGetPassword, tPassword, tUser, tUsername, username));
                return preparedStatement.executeQuery();
            }catch (SQLException ex){ ex.printStackTrace(); }
        }
        return null;
    }

    public ResultSet getAllUsers(){
        if (conn != null){
            try{
                PreparedStatement preparedStatement = conn.prepareStatement(String.format(sqlGetUsers, tUsername, tUser));
                return preparedStatement.executeQuery();
            }catch (SQLException ex){ ex.printStackTrace(); }
        }
        return null;
    }

    public ResultSet getUserInfo(String username){
        if (conn != null){
            try{
                PreparedStatement preparedStatement = conn.prepareStatement(String.format(sqlGetUserInfo, tUser, tUsername, username));
                return preparedStatement.executeQuery();
            }catch (SQLException ex) { ex.printStackTrace(); }
        }
        return null;
    }
}
