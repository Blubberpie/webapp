<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<h2>Edit Your Profile, ${username}</h2>
<a href="/">Return to home page</a><br/>
<p style="color:firebrick">${errorU}</p>
<form action="/editprofile" method="post">
    <h3>Change Username:</h3>
    <p style="color:green">${usernameSuccess}</p>
    New username:
    <input type="hidden" name="edit_form" value="change_username"/>
    <input type="text" name="changed_username" required/>
    <input type="submit" value="Change username"/>
</form>
<p style="color:firebrick">${errorP}</p>
<form action="/editprofile" method="post">
    <h3>Change Password</h3>
    <p style="color:green">${passwordSuccess}</p>
    New password: <br/>
    <input type="hidden" name="edit_form" value="change_password"/>
    <input type="password" name="changed_password" required>
    <p style="color:firebrick">${pwdUnmatchedError}</p>
    Confirm password: <br/>
    <input type="password" name="password_confirm" required>
    <input type="submit" value="Change password">
</form>
<p style="color:firebrick">${errorFN}</p>
<form action="/editprofile" method="post">
    <h3>Change First Name:</h3>
    <p style="color:green">${firstNameSuccess}</p>
    New first name:
    <input type="hidden" name="edit_form" value="change_first_name"/>
    <input type="text" name="changed_first_name" required/>
    <input type="submit" value="Change first name"/>
</form>
<p style="color:firebrick">${errorLN}</p>
<form action="/editprofile" method="post">
    <h3>Change Last Name:</h3>
    <p style="color:green">${lastNameSuccess}</p>
    New username:
    <input type="hidden" name="edit_form" value="change_last_name"/>
    <input type="text" name="changed_last_name" required/>
    <input type="submit" value="Change last name"/>
</form>
<p style="color:firebrick">${errorBY}</p>
<form action="/editprofile" method="post">
    <h3>Change Birth Year:</h3>
    <p style="color:green">${birthYearSuccess}</p>
    New birth year:
    <input type="hidden" name="edit_form" value="change_birth_year"/>
    <input type="number" min="1865" max="2019" name="changed_birth_year" required/>
    <input type="submit" value="Change birth year"/>
</form>
</body>
</html>
