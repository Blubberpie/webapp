package com.blubber.homework.hw4.webapp.servlet;

import com.blubber.homework.hw4.webapp.Routable;
import com.blubber.homework.hw4.webapp.service.DatabaseService;
import com.blubber.homework.hw4.webapp.service.SecurityService;
import com.blubber.homework.hw4.webapp.utilities.datamodels.User;
import org.apache.commons.lang.StringUtils;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.LinkedList;
import java.util.List;

import static com.blubber.homework.hw4.webapp.utilities.Messages.*;

@WebServlet("/")
public class HomeServlet extends HttpServlet implements Routable {

    private SecurityService securityService;
    private DatabaseService databaseService;

    private void refreshData(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException{
        String username = (String) request.getSession().getAttribute("username");
        User currentUser = databaseService.getUserInfo(username);
        List<String> userList = databaseService.getUserList();
        request.setAttribute("userData", currentUser); // Keep profile visible
        request.setAttribute("userList", userList); // Keep the list visible
        dispatch(request, response, "username", username);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean authorized = securityService.isAuthorized(request);
        if (authorized){
            refreshData(request, response);
        } else {
            response.sendRedirect("/login");
        }
    }

    private void dispatch(HttpServletRequest request,
                          HttpServletResponse response,
                          String attribute,
                          String value) throws ServletException, IOException{

        request.setAttribute(attribute, value);
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/home.jsp");
        rd.include(request, response);
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("new_username");
        String password = BCrypt.hashpw(request.getParameter("new_password"), BCrypt.gensalt());
        if (BCrypt.checkpw(request.getParameter("password_confirm"), password)) {
            if (!StringUtils.isBlank(username) && !StringUtils.isBlank(password)) {
                if (databaseService.isNewUser(username)) {
                    databaseService.addNewUser(username, password);
                    request.setAttribute("userAdded", msgUserAdded);
                    refreshData(request, response);
                } else dispatch(request, response, "error", errUsernameExists);
            } else dispatch(request, response, "error", errMissingArgs);
        } else dispatch(request, response, "pwdUnmatchedError", errUnmatchedPwd);
    }

    private void removeUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userToRemove = request.getParameter("user_to_remove");
        if (!StringUtils.isBlank(userToRemove)){
            databaseService.removeUser(userToRemove);
            request.setAttribute("userRemoved", msgUserRemoved);
            refreshData(request, response);
        } else {
            dispatch(request, response, "userRemoved", errNoUserGiven);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (securityService.isAuthorized(request)) {
            String addOrRemove = request.getParameter("add_remove_user");
            switch (addOrRemove){
                case "add":
                    addUser(request, response);
                    break;
                case "remove":
                    removeUser(request, response);
                    break;
                default:
                    break;
            }
        } else response.sendRedirect("/login");
    }

    @Override
    public String getMapping(){ return "/index.jsp"; }

    @Override
    public void setSecurityService(SecurityService securityService){ this.securityService = securityService; }

    @Override
    public void setDatabaseService(DatabaseService databaseService){ this.databaseService = databaseService;}
}
