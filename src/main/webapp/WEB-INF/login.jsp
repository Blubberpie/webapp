<html>
<body>
<h2>Login</h2>
<p>${error}</p>
<form action="/login" method="post">
    Username:<br/>
    <input type="text" name="username" required/>
    <br/>
    Password:<br/>
    <input type="password" name="password" required/>
    <br><br>
    <input type="submit" value="Log in">
</form>
</body>
</html>