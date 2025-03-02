<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quest</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>${quest.currentQuestion}</h1>
    <form action="quest" method="post">
        <c:if test="${not empty quest and not empty quest.answers}">
            <c:forEach items="${quest.answers}" var="answer">
                <input type="radio" name="answer" value="${answer}" required> ${answer}<br>
            </c:forEach>
        </c:if>
        <input type="submit" value="Submit">
    </form>
</body>
</html>