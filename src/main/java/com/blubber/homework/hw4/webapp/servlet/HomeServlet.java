package com.blubber.homework.hw4.webapp.servlet;

import com.blubber.homework.hw4.webapp.Routable;
import com.blubber.homework.hw4.webapp.service.DatabaseService;
import com.blubber.homework.hw4.webapp.service.SecurityService;
import com.blubber.homework.hw4.webapp.utilities.datamodels.User;
import com.blubber.homework.hw4.webapp.utilities.mysql.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import static com.blubber.homework.hw4.webapp.utilities.ErrorMessages.*;

@WebServlet("/")
public class HomeServlet extends HttpServlet implements Routable {

    private SecurityService securityService;
    private DatabaseService databaseService;
    private List<String> userList = new LinkedList<>(); //todo: bad for memory
    private User currentUser = null;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean authorized = securityService.isAuthorized(request);
        if (authorized){
            String username = (String) request.getSession().getAttribute("username");
            currentUser = databaseService.getUserInfo(username);
            userList = databaseService.getUserList();
            request.setAttribute("userData", currentUser);
            dispatch(request, response, "username", username);
        } else {
            response.sendRedirect("/login");
        }
    }

    private void dispatch(HttpServletRequest request,
                          HttpServletResponse response,
                          String attribute,
                          String value) throws ServletException, IOException{

        request.setAttribute(attribute, value);
        request.setAttribute("userList", userList); // Keep the list visible
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/home.jsp");
        rd.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (securityService.isAuthorized(request)) {
            String username = request.getParameter("new_username");
            String password = BCrypt.hashpw(request.getParameter("new_password"), BCrypt.gensalt());
            if (BCrypt.checkpw(request.getParameter("password_confirm"), password)) {
                if (!StringUtils.isBlank(username) && !StringUtils.isBlank(password)) {
                    if (databaseService.isNewUser(username)) {
                        databaseService.addNewUser(username, password);
                        userList.add(username);
                        request.setAttribute("userList", userList);
                        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/home.jsp");
                        rd.include(request, response);
                    } else dispatch(request, response, "error", errUsernameExists);
                } else dispatch(request, response, "error", errMissingArgs);
            } else dispatch(request, response, "pwdUnmatchedError", errUnmatchedPwd);
        } else response.sendRedirect("/login");
    }

    @Override
    public String getMapping(){ return "/index.jsp"; }

    @Override
    public void setSecurityService(SecurityService securityService){ this.securityService = securityService; }

    @Override
    public void setDatabaseService(DatabaseService databaseService){ this.databaseService = databaseService;}
}
