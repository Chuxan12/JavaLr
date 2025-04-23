<%@ include file="/WEB-INF/views/_layout.jspf" %>
<html lang="ru">
<div class="main card">
 <h2>Toп 20</h2>
 <table class="table">
   <tr><th>#</th><th>Игрок</th><th>Очки</th><th>Дата</th></tr>
   <c:forEach var="entry" items="${entries}" varStatus="st">
     <tr>
       <td>${st.index+1}</td>
       <td>${entry.nickname}</td>
       <td>${entry.score}</td>
       <td><fmt:formatDate value="${entry.playedAt}" type="both"/></td>
     </tr>
   </c:forEach>
 </table>
</div>