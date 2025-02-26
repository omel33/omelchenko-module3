<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome to the Quest</title>
</head>
<body>
    <h1>Welcome to the Text Quest!</h1>
    <form action="quest" method="post">
        <label for="name">Enter your name:</label>
        <input type="text" id="playerId" name="playerName" required><br>
        <input type="submit" value="Start the Quest">
    </form>
</body>
</html>