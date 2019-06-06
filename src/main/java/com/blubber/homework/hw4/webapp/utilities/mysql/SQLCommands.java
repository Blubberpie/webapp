package com.blubber.homework.hw4.webapp.utilities.mysql;

public class SQLCommands {

    // INITIALIZE SCHEMA //
    static final String sqlCreateSchema = "CREATE SCHEMA IF NOT EXISTS %s;";
    static final String sqlSwitchToSchema = "USE %s;";
    static final String sqlCreateUser =
            "CREATE TABLE IF NOT EXISTS %s(" +
            "%s VARCHAR(30) NOT NULL," +
            "%s VARCHAR(72) NOT NULL," +
            "%s VARCHAR(35)," +
            "%s VARCHAR(35)," +
            "%s INT(4), " +
            "PRIMARY KEY (%s));";

    // UPDATE //
    static final String sqlUpdateUserTable = "INSERT INTO " +
                                            "%s (%s, %s) " +
                                            "VALUES (\'%s\', \'%s\');";

    // QUERY //
    static final String sqlGetUsername = "SELECT 1 FROM %s WHERE %s = \'%s\';";
    static final String sqlGetPassword = "SELECT %s FROM %s WHERE %s = \'%s\';";
    static final String sqlGetUsers = "SELECT %s FROM %s;";
    static final String sqlGetUserInfo = "SELECT * FROM %s WHERE %s = \'%s\';";
}
