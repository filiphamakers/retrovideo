<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<!doctype html>
<html lang='nl'>
<head>
</head>
<body>
	<a href="<c:url value="/index.htm"/>">Reserveren</a>
	<h1>${film.titel}</h1>
	<img alt="${film.titel}" src="images/${film.id}.jpg">
	<dl>
		<dt>Prijs</dt>
		<dd>&euro;${film.prijs}</dd>
		<dt>Voorraad</dt>
		<dd>${film.aantalInVoorraad}</dd>
		<dt>Gereserveerd</dt>
		<dd>${film.aantalGereserveerd}</dd>
		<dt>Beschikbaar</dt>
		<dd>${beschikbaar}</dd>
	</dl>
	<c:if test="${beschikbaar>0}">
		<form method="post" action="<c:url value="/mandje.htm"/>"
			id="filmreserverenform">
			<input name="filmid" value="${film.id}" hidden="true"> <input
				type="submit" value="In mandje" name="filmreserverenknop">
		</form>
		<script>
			document.getElementById("filmreserverenform").onsubmit = function() {
				if (!navigator.cookieEnabled) {
					alert("Om films te reserveren moet je cookies toestaan.");
					return false;
				}
				document.getElementByName("filmreserverenknop").disabled = true;
			};
		</script>
	</c:if>
</body>
</html>