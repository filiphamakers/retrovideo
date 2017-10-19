<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="vdab" uri="http://vdab.be/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<title></title>
</head>
<body>
	<vdab:reservaties />
	<c:forEach var="film" items="${films}">
		<c:url value="/filmdetails.htm" var="filmdetailsURL">
			<c:param name="filmid" value="${film.id}" />
		</c:url>
		<c:choose>
			<c:when test="${filmIdsMetFoto.contains(film.id)}">
				<a href="${filmdetailsURL}"><img alt="${film.titel}"
					src="images/${film.id}.jpg"
					title="${film.aantalGereserveerd<film.aantalInVoorraad?" reservatie mogelijk":"reservatie niet mogelijk"}"></a>
			</c:when>
			<c:otherwise>
				<img alt="Afbeelding niet beschikbaar" src="images/niks.jpg">
			</c:otherwise>
		</c:choose>
	</c:forEach>
</body>
</html>