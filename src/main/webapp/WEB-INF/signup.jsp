<html>
<body>
<h2>Signup</h2>
<p>${error}</p>
<form action="/signup" method="post">
    Create a username:<br/>
    <input type="text" name="username"/>
    <br/>
    Create a password:<br/>
    <input type="password" name="password">
    <br/>
    <p>${pwdUnmatchedError}</p>
    Confirm password:<br/>
    <input type="password" name="password_confirm">
    <br><br>
    <input type="submit" value="Create your account!">
</form>
</body>
</html>