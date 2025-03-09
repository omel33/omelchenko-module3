<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quest Result</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <h1>{result}</h1>
    <p>Congratulations, you have completed the quest!</p>
    <p>Games played:${sessionScope.gamesPlayed}</p>
    <form action="quest" method="post">
        <input type="submit" value="Play Again">
    </form>
</body>
</html>