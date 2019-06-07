package com.blubber.homework.hw4.webapp.service;

import com.blubber.homework.hw4.webapp.utilities.datamodels.User;
import com.blubber.homework.hw4.webapp.utilities.mysql.ObjectMapper;
import com.blubber.homework.hw4.webapp.utilities.mysql.SQLConnectionHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static com.blubber.homework.hw4.webapp.utilities.properties.SchemaProperties.*;

public class DatabaseService {

    private SQLConnectionHandler sCHand;

    public DatabaseService(SQLConnectionHandler sqlConnectionHandler){
        this.sCHand = sqlConnectionHandler;
    }

    public void addNewUser(String username, String password){
        sCHand.sqlActions().updateUserTable(username, password);
    }

    public void updateUsername(String oldUsername, String newUsername){
        sCHand.sqlActions().updateUsername(oldUsername, newUsername);
    }

    public void updatePassword(String username, String password){
        sCHand.sqlActions().updatePassword(username, password);
    }

    public void updateFirstName(String username, String newFirstName){
        sCHand.sqlActions().updateFirstName(username, newFirstName);
    }

    public void updateLastName(String username, String newLastName){
        sCHand.sqlActions().updateLastName(username, newLastName);
    }

    public void updateBirthYear(String username, int newBirthYear){
        sCHand.sqlActions().updateBirthYear(username, newBirthYear);
    }

    public void removeUser(String username){
        sCHand.sqlActions().removeUser(username);
    }

    public boolean isNewUser(String username){
        return !sCHand.sqlActions().usernameExists(username);
    }

    public List<String> getUserList(){
        ResultSet rs = sCHand.sqlActions().getAllUsers();
        List<String> ret = new LinkedList<>();
        try{
            while(rs.next()){
                ret.add(rs.getString(tUsername));
            }
        }catch(SQLException ex){ ex.printStackTrace(); }
        return ret;
    }

    public User getUserInfo(String username){
        ResultSet rs = sCHand.sqlActions().getUserInfo(username);
        return ObjectMapper.makeUser(rs);
    }
}
