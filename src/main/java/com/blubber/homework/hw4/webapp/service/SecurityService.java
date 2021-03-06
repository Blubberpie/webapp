package com.blubber.homework.hw4.webapp.service;

import com.blubber.homework.hw4.webapp.utilities.mysql.SQLConnectionHandler;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SecurityService {

    private SQLConnectionHandler sCHand;

    public SecurityService(SQLConnectionHandler sqlConnectionHandler){
        this.sCHand = sqlConnectionHandler;
    }

    public boolean isAuthorized(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        return (username != null && sCHand.sqlActions().usernameExists(username));
    }

    public boolean authenticate(String username, String password, HttpServletRequest request){
        if (sCHand.sqlActions().usernameExists(username)){
            ResultSet rs = sCHand.sqlActions().getPassword(username);
            try{
                if (rs.next()){
                    if (BCrypt.checkpw(password, rs.getString(1))){
                        request.getSession().setAttribute("username", username);
                        return true;
                    }else return false;
                }else return false;
            }catch(SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        } else return false;
    }

    public void logout(HttpServletRequest request) { request.getSession().invalidate(); }
}
