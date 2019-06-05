package com.blubber.homework.hw4.webapp.servlet;

import com.blubber.homework.hw4.webapp.Routable;
import com.blubber.homework.hw4.webapp.service.SecurityService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean authorized = securityService.isAuthorized(request);
        if (authorized){
            String username = (String) request.getSession().getAttribute("connUsername");
            request.setAttribute("connUsername", username);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/home.jsp");
            rd.include(request, response);
        } else {
            response.sendRedirect("/login");
        }
    }

    @Override
    public String getMapping(){ return "/index.jsp"; }

    @Override
    public void setSecurityService(SecurityService securityService){ this.securityService = securityService; }
}
