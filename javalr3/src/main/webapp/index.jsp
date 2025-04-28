<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="error.jsp" %>
<!DOCTYPE html>
<html>
<head><title>Угадай-ка!</title></head>
<body>
<h2>Загадайте число</h2>
<form action="${pageContext.request.contextPath}/game" method="post">
    <label>Минимум: <input type="number" name="min" required></label><br>
    <label>Максимум: <input type="number" name="max" required></label><br><br>
    <button type="submit">Начать игру</button>
</form>
</body>
</html>
