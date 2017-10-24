<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="vdab" uri="http://vdab.be/tags"%>
<!DOCTYPE html>
<html>
<head>
<vdab:head title="retrovideo: bevestig uw reservatie"/>
</head>
<body>
	<a href="<c:url value="/index.htm" />">Reserveren</a>
	<a href="<c:url value="/mandje.htm" />">Mandje</a>
	<a href="<c:url value="/klant.htm" />">Klant</a>

	<h1>Bevestigen</h1>
	<c:if test="${not empty klant and aantalFilms>0}">
		<p>${aantalFilms} ${aantalFilms==1?"film":"films"} voor ${klant.naam}</p>
		<form method="post" action="<c:url value="/rapport.htm"/>">
			<input name="klant" value="${klant.id}" hidden="true">
			<input type="submit" name="bevestigenknop" value="bevestigen">
		</form>
	</c:if>
</body>
</html>