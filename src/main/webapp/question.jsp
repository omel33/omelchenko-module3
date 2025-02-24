<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="https://jakarta.ee/xml/ns/jakartaee/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quest</title>
</head>
<body>
    <h1>${quest.currentQuestion}</h1>
    <form action="quest" method="post">
        <input type="text" name="answer" required>
        <input type="submit" value="Submit">
    </form>
</body>
</html>