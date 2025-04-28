<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="error.jsp" %>
<!DOCTYPE html>
<html>
<head><title>Попытка угадать</title></head>
<body>
<h3>Моё предположение: <b>${requestScope.guess}</b></h3>

<form action="${pageContext.request.contextPath}/game" method="post">
    <button name="answer" value="LESS">Меньше</button>
    <button name="answer" value="EQUAL">Угадал!</button>
    <button name="answer" value="MORE">Больше</button>
</form>
</body>
</html>
