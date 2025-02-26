<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="https://jakarta.ee/xml/ns/jakartaee/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quest</title>
</head>
<body>
    <h1>${quest.currentQuestion}</h1>
    <form action="quest" method="post">
    <c:forEach items="${answers}" var="answer">
        <input type="radio" name="answer" value="${answer}" required> ${answer}<br>
    </c:forEach>
        <input type="submit" value="Submit">
    </form>
</body>
</html>