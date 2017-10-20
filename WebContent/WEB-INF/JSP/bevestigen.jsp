<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<a href="<c:url value="/index.htm" />">Reserveren</a>
	<a href="<c:url value="/mandje.htm" />">Mandje</a>
	<a href="<c:url value="/klant.htm" />">Klant</a>

	<h1>Bevestigen</h1>
	<p>${aantalFilms} ${aantalFilms==1?"film":"films"} voor ${klant.naam}</p>
	<form>
		<input type="submit" value="bevestigen">
	</form>
</body>
</html>