<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="https://jakarta.ee/xml/ns/jakartaee/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quest Result</title>
</head>
<body>
<h1>Quest Finished</h1>
<p>Congratulations, you have completed the quest!</p>
<from action="quest" method="post">
    <input type="submit" value="Play Again">
</form>
</body>
</html>