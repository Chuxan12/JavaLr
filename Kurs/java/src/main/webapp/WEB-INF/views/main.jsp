<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/_layout.jspf" %>
<div class="main card" style="text-align:center">
    <h2>Главное меню</h2>
    <a class="btn" href="${pageContext.request.contextPath}/init">Играть</a><br>
    <a class="btn" href="${pageContext.request.contextPath}/leaderboard">Таблица лидеров</a><br>
    <a class="btn" href="${pageContext.request.contextPath}/rules">Правила</a>
</div>