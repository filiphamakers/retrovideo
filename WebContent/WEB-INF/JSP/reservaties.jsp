<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="vdab" uri="http://vdab.be/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<vdab:head title="Welkom bij retrovideo"/>
</head>
<body>
	<h1>Reservaties</h1>
	<nav>
		<ul>
			<c:forEach var="genre" items="${genres}">
				<c:url value="/index.htm" var="genreURL">
					<c:param name="genre" value="${genre.naam}" />
				</c:url>
				<li><c:choose>
						<c:when test="${genre.naam == param.genre}">
							<span>${genre.naam}</span>
						</c:when>
						<c:otherwise>
							<a href="${genreURL}"> ${genre.naam}</a>
						</c:otherwise>
					</c:choose></li>
			</c:forEach>
		</ul>
	</nav>
	<c:forEach var="film" items="${films}">
		<c:url value="/filmdetails.htm" var="filmdetailsURL">
			<c:param name="filmid" value="${film.id}" />
		</c:url>
		<a href="${filmdetailsURL}"><img alt="${film.titel}"
			title="${film.aantalGereserveerd<film.aantalInVoorraad?
			"reservatie mogelijk":"reservatie niet mogelijk"}" 
			src="
			<c:choose>
				<c:when test="${filmIdsMetFoto.contains(film.id)}"><c:url value="/images/${film.id}.jpg"/>
				</c:when>
				<c:otherwise><c:url value="/images/niks.jpg"/>
				</c:otherwise>
			</c:choose>">
		</a>
	</c:forEach>
</body>
</html>