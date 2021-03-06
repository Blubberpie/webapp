package com.blubber.homework.hw4.webapp;

import com.blubber.homework.hw4.webapp.service.DatabaseService;
import com.blubber.homework.hw4.webapp.service.SecurityService;
import com.blubber.homework.hw4.webapp.utilities.mysql.SQLConnectionHandler;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class WebApp {
    public static void main(String[] args) {

        File docBase = new File("src/main/webapp/");
        docBase.mkdirs();
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(80);

        SQLConnectionHandler sqlConnectionHandler = new SQLConnectionHandler();

        SecurityService securityService = new SecurityService(sqlConnectionHandler);
        DatabaseService databaseService = new DatabaseService(sqlConnectionHandler);
        ServletRouter servletRouter = new ServletRouter();
        servletRouter.setSecurityService(securityService);
        servletRouter.setDatabaseService(databaseService);

        Context ctx;
        try{
            ctx = tomcat.addWebapp("", docBase.getAbsolutePath());
            servletRouter.init(ctx);

            tomcat.start();
            tomcat.getServer().await();
        }catch(LifecycleException ex){
            ex.printStackTrace();
        }
    }
}
