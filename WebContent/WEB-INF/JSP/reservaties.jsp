<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<title>Reservaties</title>
</head>
<body>
	<h1>Reservaties</h1>
	<ul>
		<c:forEach var="genre" items="${genres}">
			<c:url value="/films.htm" var="genreURL">
				<c:param name="genre" value="${genre.naam}" />
			</c:url>
			<li><a href="${genreURL}">${genre.naam}</a></li>
		</c:forEach>
	</ul>
</body>
</html>