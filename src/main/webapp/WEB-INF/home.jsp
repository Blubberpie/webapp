<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<h2>Welcome, ${username}</h2>
<a href="/logout">Log out</a><br/>
<br/>
<h3>Your Profile</h3>
<a href="/editprofile">Edit your profile</a><br/>
<p style="border:2px; border-style:solid; padding: 1em;">
    <strong>Username:</strong> ${userData.getUsername()}<br/>
    <strong>First name:</strong> ${userData.getFirstName()}<br/>
    <strong>Last name:</strong> ${userData.getLastName()}<br/>
    <strong>Birth year:</strong> ${userData.getBirthYear()}<br/>
</p>
<h3>Add a new user</h3>
<p style="color:green">${userAdded}</p>
<p style="color:firebrick">${error}</p>
<form action="/" method="post" style="border:2px; border-style:solid; padding: 1em;">
    Create a username:<br/>
    <input type="hidden" name="add_remove_user" value="add"/>
    <input type="text" name="new_username" required/>
    <br/>
    Create a password:<br/>
    <input type="password" name="new_password" required>
    <br/>
    <p style="color:firebrick">${pwdUnmatchedError}</p>
    Confirm password:<br/>
    <input type="password" name="password_confirm">
    <br><br>
    <input type="submit" value="Add new user!">
</form>
<br/>
<h3>All users on this server:</h3> <br/>
<p style="color:firebrick">${userRemoved}</p>
<table style="border:2px; border-style:solid; padding: 1em;">
    <c:forEach items="${userList}" var="usr">
        <tbody style="vertical-align: center">
            <tr>
                <td>${usr}</td>
                <td>
                    <form action="/" method="post">
                        <input type="hidden" name="add_remove_user" value="remove">
                        <c:choose>
                            <c:when test="${usr != username}">
                                <input type="hidden" name="user_to_remove" value="${usr}"/>
                                <input type="submit" value="Remove user" onclick="{return confirm('Are you sure you want to remove this user?')}"/>
                            </c:when>
                            <c:otherwise>
                                <p style="color:firebrick">Cannot remove self!</p>
                            </c:otherwise>
                        </c:choose>
                    </form>
                </td>
            </tr>
        </tbody>
    </c:forEach>
</table>
</body>
</html>
