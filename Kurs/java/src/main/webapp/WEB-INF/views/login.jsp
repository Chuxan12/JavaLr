<%@ include file="/WEB-INF/views/_layout.jspf" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css" />
<div class="main card">
  <h2>Login</h2>
  <form method="post" action="${pageContext.request.contextPath}/login">
      <label>Login<br><input name="login" required></label><br>
      <label>Password<br><input type="password" name="password" required></label><br>
      <button class="btn" type="submit" href="${pageContext.request.contextPath}/main">Enter</button>
  </form>
  <p>No account? <a href="${pageContext.request.contextPath}/register">Register here</a></p>
</div>