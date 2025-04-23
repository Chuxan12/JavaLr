<%@ include file="/WEB-INF/views/_layout.jspf" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/style.css" />
<div class="main card">
 <h2>Top 20</h2>
 <table class="table">
   <tr><th>#</th><th>Player</th><th>Score</th><th>Date</th></tr>
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