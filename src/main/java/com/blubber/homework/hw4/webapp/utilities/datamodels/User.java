package com.blubber.homework.hw4.webapp.utilities.datamodels;

public class User {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private int birthYear;

    public String getFirstName() { return firstName; }
    public int getBirthYear() { return birthYear; }
    public String getLastName() { return lastName; }
    public String getPassword() { return password; }
    public String getUsername() { return username; }

    public void setBirthYear(int birthYear) { this.birthYear = birthYear; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setPassword(String password) { this.password = password; }
    public void setUsername(String username) { this.username = username; }
}