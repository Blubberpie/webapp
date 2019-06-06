package com.blubber.homework.hw4.webapp.utilities.builders;

import com.blubber.homework.hw4.webapp.utilities.datamodels.User;

public class UserBuilder {

    private User user = new User();

    public UserBuilder setUsername(String username){
        this.user.setUsername(username);
        return this;
    }

    public UserBuilder setPassword(String password){
        this.user.setPassword(password);
        return this;
    }

    public UserBuilder setFirstName(String firstName){
        this.user.setFirstName(firstName);
        return this;
    }
    public UserBuilder setLastname(String lastName){
        this.user.setLastName(lastName);
        return this;
    }
    public UserBuilder setBirthYear(int birthYear){
        this.user.setBirthYear(birthYear);
        return this;
    }

    public User getUser() { return this.user;}
}
