<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><title>Ошибка</title></head>
<body>
<h2 style="color:orange">Упс! Произошла ошибка:</h2>
<p>${exception.message}</p>
<a href="javascript:history.back()">Вернуться назад</a>
</body>
</html>
