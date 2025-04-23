<%@ include file="/WEB-INF/views/_layout.jspf" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css" />
<div class="main card" style="text-align:center">
    <h2>Main menu</h2>
    <a class="btn" href="${pageContext.request.contextPath}/init">Play</a><br>
    <a class="btn" href="${pageContext.request.contextPath}/leaderboard">Leaderboard</a><br>
    <a class="btn" href="${pageContext.request.contextPath}/rules">Rules</a>
</div>