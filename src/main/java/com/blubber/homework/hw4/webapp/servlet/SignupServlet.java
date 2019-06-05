package com.blubber.homework.hw4.webapp.servlet;

import com.blubber.homework.hw4.webapp.Routable;
import com.blubber.homework.hw4.webapp.service.SecurityService;
import static com.blubber.homework.hw4.webapp.utilities.ErrorMessages.*;

import org.apache.commons.lang.StringUtils;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignupServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/signup.jsp");
        rd.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = BCrypt.hashpw(request.getParameter("password"), BCrypt.gensalt());
        if (BCrypt.checkpw(request.getParameter("password_confirm"), password)) {
            if (!StringUtils.isBlank(username) && !StringUtils.isBlank(password)) {
                if (securityService.isNewUser(username)) {
                    securityService.addNewUser(username, password);
                    response.sendRedirect("/");
                } else {
                    request.setAttribute("error", errUsernameExists);
                    RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/signup.jsp");
                    rd.include(request, response);
                }
            } else {
                request.setAttribute("error", errMissingArgs);
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/signup.jsp");
                rd.include(request, response);
            }
        } else {
            request.setAttribute("pwdUnmatchedError", errUnmatchedPwd);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/signup.jsp");
            rd.include(request, response);
        }
    }

    @Override
    public String getMapping() { return "/signup"; }

    @Override
    public void setSecurityService(SecurityService securityService){ this.securityService = securityService; }
}
