<%@ tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
	<h1>Reservaties</h1>
	<nav>
		<ul>
			<c:forEach var="genre" items="${genres}">
				<c:url value="/films.htm" var="genreURL">
					<c:param name="genre" value="${genre.naam}" />
				</c:url>
				<li><a href="${genreURL}">${genre.naam}</a></li>
			</c:forEach>
		</ul>
	</nav>
</header>
