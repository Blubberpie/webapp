package com.blubber.homework.hw4.webapp.service;

import com.blubber.homework.hw4.webapp.utilities.SQLConnectionHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static com.blubber.homework.hw4.webapp.utilities.SchemaProperties.*;

public class DatabaseService {

    private SQLConnectionHandler sCHand;

    public DatabaseService(SQLConnectionHandler sqlConnectionHandler){
        this.sCHand = sqlConnectionHandler;
    }

    public void addNewUser(String username, String password){
        sCHand.sqlActions().updateUserTable(username, password);
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
}
