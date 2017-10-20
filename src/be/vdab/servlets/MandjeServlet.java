package be.vdab.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
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

/**
 * Servlet implementation class MandjeServlet
 */
@WebServlet("/mandje.htm")
public class MandjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String MANDJE = "mandje";
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
				request.setAttribute("filmsInMandje",
						mandje.stream().map(id -> filmRepository.findById(id))
								.filter(optionalFilm -> optionalFilm.isPresent())
								.map(optionalFilm -> optionalFilm.get()).collect(Collectors.toList()));
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
