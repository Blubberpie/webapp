package com.blubber.homework.hw4.webapp.servlet;

import com.blubber.homework.hw4.webapp.Routable;
import com.blubber.homework.hw4.webapp.service.DatabaseService;
import com.blubber.homework.hw4.webapp.service.SecurityService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet implements Routable {

    private SecurityService securityService;
    private DatabaseService databaseService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/home.jsp");
        rd.include(request, response);
        securityService.logout(request);
        response.sendRedirect("/login");
    }

    @Override
    public String getMapping() { return "/logout"; }

    @Override
    public void setSecurityService(SecurityService securityService){ this.securityService = securityService; }

    @Override
    public void setDatabaseService(DatabaseService databaseService){ this.databaseService = databaseService;}
}
