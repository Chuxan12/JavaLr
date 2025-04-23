<%@ include file="/WEB-INF/views/_layout.jspf" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css" />
<div class="main card">
  <h2>Create account</h2>
  <form method="post" action="${pageContext.request.contextPath}/register">
      <label>Login<br><input name="login" required></label><br>
      <label>Nickname<br><input name="nickname" required></label><br>
      <label>Password<br><input type="password" name="password" required></label><br>
      <button class="btn" type="submit">Register</button>
  </form>
</div>