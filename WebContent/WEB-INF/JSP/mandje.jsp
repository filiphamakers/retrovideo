<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html lang="nl">
<head>
</head>
<body>
	<a href="<c:url value="/index.htm" />">Reserveren</a>
	<a href="<c:url value="/klant.htm" />">Klant</a>

	<h1>Mandje</h1>

	<form method="post">
		<table>
			<thead>
				<tr>
					<th>Film</th>
					<th>Prijs</th>
					<th><input type="submit" value="Verwijderen" />
				</tr>
			</thead>
			<tbody>
				<c:forEach var="film" items="${filmsInMandje}">
					<tr>
						<td>${film.titel}</td>
						<td>&euro;${film.prijs}</td>
						<td><input type="checkbox" name="verwijder" value="${film.id}"></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>

</body>
</html>