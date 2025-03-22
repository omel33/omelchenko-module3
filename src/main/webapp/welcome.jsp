<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome to the Quest</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <h1>Welcome to the Text Quest!</h1>
    <p>Congratulations, you have completed the quest!</p>
    <p>Total games played: ${totalGamesPlayed}</p>
    <form action="quest" method="post">
        <label for="playerId">Enter your name:</label>
        <input type="text" id="playerId" name="playerName" required><br>
        <input type="submit" value="Start the Quest">
    </form>
</body>
</html>