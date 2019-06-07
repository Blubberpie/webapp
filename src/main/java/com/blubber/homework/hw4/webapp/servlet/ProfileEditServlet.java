package com.blubber.homework.hw4.webapp.servlet;

import com.blubber.homework.hw4.webapp.Routable;
import com.blubber.homework.hw4.webapp.service.DatabaseService;
import com.blubber.homework.hw4.webapp.service.SecurityService;
import org.apache.commons.lang.StringUtils;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.blubber.homework.hw4.webapp.utilities.Messages.*;

@WebServlet("/editprofile")
public class ProfileEditServlet extends HttpServlet implements Routable {

    private SecurityService securityService;
    private DatabaseService databaseService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean authorized = securityService.isAuthorized(request);
        if (authorized) {
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/editprofile.jsp");
            rd.include(request, response);
        }else{
            response.sendRedirect("/login");
        }
    }

    private void dispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/editprofile.jsp");
        rd.include(request, response);
    }

    private void dispatchWithAttribute(HttpServletRequest request, HttpServletResponse response,
                                       String attribute, String value) throws ServletException, IOException {

        request.setAttribute(attribute, value);
        dispatch(request, response);
    }

    private void changeUsername(HttpServletRequest request, HttpServletResponse response,
                                String username) throws ServletException, IOException{

        String newUsername = request.getParameter("changed_username");

        if (!StringUtils.isBlank(newUsername)){
            if (databaseService.isNewUser(newUsername)){
                databaseService.updateUsername(username, newUsername);
                request.getSession().setAttribute("username", newUsername);
                dispatchWithAttribute(request, response, "usernameSuccess", msgUsernameChanged);
            } else dispatchWithAttribute(request, response, "errorU", errUsernameExists);
        } else dispatchWithAttribute(request, response, "errorU", errMissingArgs);
    }

    private void changePassword(HttpServletRequest request, HttpServletResponse response,
                                String username) throws ServletException, IOException{

        String newPassword = BCrypt.hashpw(request.getParameter("changed_password"), BCrypt.gensalt());

        if (BCrypt.checkpw(request.getParameter("password_confirm"), newPassword)) {
            databaseService.updatePassword(username, newPassword);
            dispatchWithAttribute(request, response, "passwordSuccess", msgPasswordChanged);
        } else dispatchWithAttribute(request, response, "pwdUnmatchedError", errUnmatchedPwd);
    }

    private void changeFirstName(HttpServletRequest request, HttpServletResponse response,
                                 String username) throws ServletException, IOException{
        String newFirstName = request.getParameter("changed_first_name");

        databaseService.updateFirstName(username, newFirstName);
        dispatchWithAttribute(request, response,"firstNameSuccess", msgFirstNameChanged);
    }

    private void changeLastName(HttpServletRequest request, HttpServletResponse response,
                                 String username) throws ServletException, IOException{
        String newLastName = request.getParameter("changed_last_name");

        databaseService.updateLastName(username, newLastName);
        dispatchWithAttribute(request, response,"lastNameSuccess", msgLastNameChanged);
    }

    private void changeBirthYear(HttpServletRequest request, HttpServletResponse response,
                                 String username) throws ServletException, IOException{
        int newBirthYear = Integer.parseInt(request.getParameter("changed_birth_year"));

        databaseService.updateBirthYear(username, newBirthYear);
        dispatchWithAttribute(request, response,"birthYearSuccess", msgBirthYearChanged);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        String valueToChange = request.getParameter("edit_form");
        switch(valueToChange){
            case "change_username":
                changeUsername(request, response, username);
                break;
            case "change_password":
                changePassword(request, response, username);
                break;
            case "change_first_name":
                changeFirstName(request, response, username);
                break;
            case "change_last_name":
                changeLastName(request, response, username);
                break;
            case "change_birth_year":
                changeBirthYear(request, response, username);
                break;
            default:
                dispatch(request, response);
                break;
        }
    }

    @Override
    public String getMapping() { return "/editprofile"; }

    @Override
    public void setSecurityService(SecurityService securityService){ this.securityService = securityService; }

    @Override
    public void setDatabaseService(DatabaseService databaseService){ this.databaseService = databaseService;}
}
