<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<!doctype html>
<html lang='nl'>
<head>
</head>
<body>
	<h1>${film.titel}</h1>
	<img alt="${film.titel}" src="images/${film.id}.jpg">
</body>
</html>