<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<!doctype html>
<html lang='nl'>
<head>
</head>
<body>
	<a href="<c:url value="/index.htm"/>">Reserveren</a>
	<h1>Rapport</h1>
	<h2>${empty mislukteReservaties?"De reservatie is ok":"De volgende films konden niet worden gereserveerd"}</h2>
	<c:if test="${not empty mislukteReservaties}">
		<ul>
			<c:forEach var="mislukt" items="${mislukteReservaties}">
				<li>${mislukt} (niet meer in voorraad)</li>
			</c:forEach>
		</ul>
	</c:if>
</body>
</html>