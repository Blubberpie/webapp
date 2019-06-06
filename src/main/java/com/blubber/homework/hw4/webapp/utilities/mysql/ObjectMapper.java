package com.blubber.homework.hw4.webapp.utilities.mysql;

import com.blubber.homework.hw4.webapp.utilities.builders.UserBuilder;
import com.blubber.homework.hw4.webapp.utilities.datamodels.User;
import static com.blubber.homework.hw4.webapp.utilities.properties.SchemaProperties.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ObjectMapper {

    public static User makeUser(ResultSet rs){
        try{
            if (rs.next()){
                String username = rs.getString(tUsername);
                String password = rs.getString(tPassword);
                String firstName = (rs.getString(tFirstName) == null) ? "[EMPTY]" : rs.getString(tFirstName);
                String lastName = (rs.getString(tLastName) == null) ? "[EMPTY]" : rs.getString(tLastName);
                int birthYear = rs.getInt(tBirthYear);
                return new UserBuilder().setUsername(username)
                        .setPassword(password)
                        .setFirstName(firstName)
                        .setLastname(lastName)
                        .setBirthYear(birthYear).getUser();
            }
        }catch(SQLException ex) { ex.printStackTrace(); }
        return null;
    }
}
