<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<h2>Welcome, ${username}</h2>
<a href="/logout">Log out</a><br/>
<br/>
<h3>Add a New User</h3>
<p style="color:firebrick">${error}</p>
<form action="/" method="post" style="border:2px; border-style:solid; padding: 1em;">
    Create a username:<br/>
    <input type="text" name="new_username"/>
    <br/>
    Create a password:<br/>
    <input type="password" name="new_password">
    <br/>
    <p style="color:firebrick">${pwdUnmatchedError}</p>
    Confirm password:<br/>
    <input type="password" name="password_confirm">
    <br><br>
    <input type="submit" value="Add new user!">
</form>
<br/>
<h3>All users on this server:</h3> <br/>
<table style="border:2px; border-style:solid; padding: 1em;">
    <c:forEach items="${userList}" var="usr">
        <tr>
            <td>${usr}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
