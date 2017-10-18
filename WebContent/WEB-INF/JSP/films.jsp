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
	<ul>
		<c:forEach var="film" items="${films}">
			<li>${film.titel}</li>
		</c:forEach>
	</ul>
</body>
</html>