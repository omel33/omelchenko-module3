<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quest Result</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Quest Finished!</h1>
    <p>Congratulations, you have completed the quest!</p>
    <form action="quest" method="post">
        <input type="submit" value="Play Again">
    </form>
</body>
</html>