package be.vdab.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import be.vdab.repositories.FilmRepository;
import be.vdab.entities.Film;

/**
 * Servlet implementation class MandjeServlet
 */
@WebServlet("/mandje.htm")
public class MandjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String MANDJE = SessionFieldStorage.MANDJE.getSessionField();
	private static final String VIEW = "/WEB-INF/JSP/mandje.jsp";
	private final transient FilmRepository filmRepository = new FilmRepository();

	@Resource(name = FilmRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		filmRepository.setDataSource(dataSource);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			@SuppressWarnings("unchecked")
			Set<Long> mandje = (Set<Long>) session.getAttribute(MANDJE);
			if (mandje != null) {
				Set<Film> filmsInMandje = new LinkedHashSet<>();
				filmsInMandje = mandje.stream().map(id -> filmRepository.findById(id))
						.filter(optionalFilm -> optionalFilm.isPresent())
						.map(optionalFilm -> optionalFilm.get()).collect(Collectors.toSet());
				request.setAttribute("filmsInMandje",filmsInMandje);
				request.setAttribute("totaal", filmsInMandje.stream()
						.map(film -> film.getPrijs())
						.reduce(BigDecimal.ZERO, BigDecimal::add));
			}
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		Set<Long> mandje = (Set<Long>) session.getAttribute(MANDJE);
		if (request.getParameter("filmid") != null) {
			if (mandje == null) {
				mandje = new LinkedHashSet<>();
			}
			mandje.add(Long.parseLong(request.getParameter("filmid")));
		}
		if (request.getParameterValues("verwijder") != null) {
			mandje.removeAll(Arrays.asList(request.getParameterValues("verwijder")).stream()
				.map(id -> Long.parseLong(id))
				.collect(Collectors.toSet()));
			
		}
		session.setAttribute(MANDJE, mandje);
		response.sendRedirect(response.encodeRedirectURL(request.getRequestURI()));
	}

}
