<%@ include file="/WEB-INF/views/_layout.jspf" %>
<div class="main card">
  <c:choose>
     <c:when test="${param.error=='pass'}">
        <div class="alert alert-danger">Пароли не совпадают</div>
     </c:when>
     <c:when test="${param.error=='exists'}">
        <div class="alert alert-danger">Логин уже существует</div>
     </c:when>
     <c:when test="${param.error=='empty'}">
        <div class="alert alert-danger">Пожалуйста, заполните все поля</div>
     </c:when>
  </c:choose>

  <h2>Создать аккаунт</h2>
  <form method="post" action="${pageContext.request.contextPath}/register">
      <label>Логин<br><input name="login" required></label><br>
      <label>Никнеем<br><input name="nickname" required></label><br>
      <label>Пароль<br><input type="password" name="password" required></label><br>
      <label>Подтвердить пароль<br><input type="password" name="password2" required></label><br>
      <button class="btn" type="submit">Зарегистрироваться</button>
  </form>
</div>
</body></html>