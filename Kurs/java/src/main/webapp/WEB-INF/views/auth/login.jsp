<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/_layout.jspf" %>
<div class="main card">
  <c:if test="${param.registered=='1'}">
     <div class="alert alert-success">Аккаунт создан. Вы можете зайти.</div>
  </c:if>
  <c:if test="${param.error=='auth'}">
     <div class="alert alert-danger">Неправильный логин или пароль</div>
  </c:if>

  <h2>Вход</h2>
  <form method="post" action="${pageContext.request.contextPath}/login">
      <label>Логин<br><input name="login" required></label><br>
      <label>Пароль<br><input type="password" name="password" required></label><br>
      <button class="btn" type="submit">Войти</button>
  </form>
  <p>Нет аккаунта? <a href="${pageContext.request.contextPath}/register">Зарегистрироваться здесь</a></p>
</div>
</body></html>