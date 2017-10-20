<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
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
		<label>Familienaam bevat: <input type="text" name="familienaam" value="${param.familienaam}" autofocus required></label>
		<input type="submit" value="Zoeken">
	</form>
	<span>${fout}</span>

</body>
</html>