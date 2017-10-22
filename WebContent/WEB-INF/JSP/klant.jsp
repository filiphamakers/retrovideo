<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<!doctype html>
<html lang='nl'>
<head>
</head>
<body>
	<a href="<c:url value="/index.htm" />">Reserveren</a>
	<a href="<c:url value="/mandje.htm" />">Mandje</a>

	<h1>Klant</h1>
	<form>
		<label>Familienaam bevat: <input type="text"
			name="familienaam" value="${param.familienaam}" autofocus></label>
		<input type="submit" value="Zoeken">
	</form>
	<span>${fout}</span>

	<c:if test="${not empty klanten}">
		<table>
			<thead>
				<tr>
					<td>Naam</td>
					<td>Straat - Huisnummer</td>
					<td>Postcode</td>
					<td>Gemeente</td>
				</tr>
			</thead>
			<c:forEach var="klant" items="${klanten}">
				<tbody>
					<tr>
						<td>
							<c:url var="bevestigenURL" value="/bevestigen.htm">
								<c:param name="klant" value="${klant.id}"></c:param>
							</c:url>
							<a href="${bevestigenURL}">${klant.naam}</a>
						</td>
						<td>${klant.straatNummer}</td>
						<td>${klant.postcode}</td>
						<td>${klant.gemeente}</td>
					</tr>
				</tbody>
			</c:forEach>
		</table>
	</c:if>

</body>
</html>