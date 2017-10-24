<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix="vdab" uri="http://vdab.be/tags"%>
<!doctype html>
<html lang='nl'>
<head>
<vdab:head title="retrovideo: klant" />
</head>
<body>
	<a href="<c:url value="/index.htm" />">Reserveren</a>
	<a href="<c:url value="/mandje.htm" />">Mandje</a>

	<h1>Klant</h1>
	<form>
		<label>Familienaam bevat: <input type="text"
			name="familienaam" value="${param.familienaam}" autofocus></label> <input
			type="submit" name="bevestigingsknop" value="Zoeken">
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
			<tbody>
				<c:forEach var="klant" items="${klanten}">

					<tr>
						<td><c:url var="bevestigenURL" value="/bevestigen.htm">
								<c:param name="klant" value="${klant.id}"></c:param>
							</c:url> <a href="${bevestigenURL}">${klant.naam}</a></td>
						<td>${klant.straatNummer}</td>
						<td>${klant.postcode}</td>
						<td>${klant.gemeente}</td>
					</tr>

				</c:forEach>
			</tbody>
		</table>
	</c:if>

</body>
</html>