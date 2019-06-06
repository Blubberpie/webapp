package com.blubber.homework.hw4.webapp;

import com.blubber.homework.hw4.webapp.service.DatabaseService;
import com.blubber.homework.hw4.webapp.service.SecurityService;
import com.blubber.homework.hw4.webapp.servlet.HomeServlet;
import com.blubber.homework.hw4.webapp.servlet.LoginServlet;
import com.blubber.homework.hw4.webapp.servlet.LogoutServlet;
import com.blubber.homework.hw4.webapp.servlet.ProfileEditServlet;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.http.HttpServlet;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ServletRouter {

    private static final List<Class<? extends Routable>> routables = new ArrayList<>();

    static{
        routables.add(HomeServlet.class);
        routables.add(LoginServlet.class);
        routables.add(LogoutServlet.class);
        routables.add(ProfileEditServlet.class);
    }

    private SecurityService securityService;
    private DatabaseService databaseService;

    public void setSecurityService(SecurityService securityService){ this.securityService = securityService; }
    public void setDatabaseService(DatabaseService databaseService){ this.databaseService = databaseService; }

    public void init(Context ctx){
        for (Class<? extends Routable> routableClass : routables){
            try{
                Routable routable = routableClass.getConstructor().newInstance();
                routable.setSecurityService(securityService);
                routable.setDatabaseService(databaseService);
                String name = routable.getClass().getSimpleName();
                Tomcat.addServlet(ctx, name, (HttpServlet) routable);
                ctx.addServletMappingDecoded(routable.getMapping(), name);
            }catch(InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException ex){
                ex.printStackTrace();
            }
        }
    }
}
