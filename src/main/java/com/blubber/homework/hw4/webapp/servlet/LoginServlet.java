package com.blubber.homework.hw4.webapp.servlet;

import com.blubber.homework.hw4.webapp.Routable;
import com.blubber.homework.hw4.webapp.service.DatabaseService;
import com.blubber.homework.hw4.webapp.service.SecurityService;
import org.apache.commons.lang.StringUtils;
import static com.blubber.homework.hw4.webapp.utilities.Messages.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet implements Routable {

    private SecurityService securityService;
    private DatabaseService databaseService;

    private void dispatch(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException{

        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/login.jsp");
        rd.include(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean authorized = securityService.isAuthorized(request);
        if (!authorized) {
            dispatch(request, response);
        } else response.sendRedirect("/");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (!StringUtils.isBlank(username) && !StringUtils.isBlank(password)){
            if (securityService.authenticate(username, password, request)) {
                response.sendRedirect("/");
            } else {
                request.setAttribute("error", errWrongUsrPwd);
                dispatch(request, response);
            }
        } else {
            request.setAttribute("error", errMissingArgs);
            dispatch(request, response);
        }
    }

    @Override
    public String getMapping() { return "/login"; }

    @Override
    public void setSecurityService(SecurityService securityService){ this.securityService = securityService; }

    @Override
    public void setDatabaseService(DatabaseService databaseService){ this.databaseService = databaseService;}
}
