package be.vdab.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.vdab.repositories.FilmRepository;

/**
 * Servlet implementation class MandjeServlet
 */
@WebServlet("/reserveren/mandje.htm")
public class MandjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String MANDJE = "mandje";
	private static final String VIEW = "/WEB-INF/JSP/mandje.jsp";
	private final transient FilmRepository filmRepository = new FilmRepository();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			List<Long> mandje = (List<Long>) session.getAttribute(MANDJE);
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
		if (request.getParameterValues("filmid") != null) {
			HttpSession session = request.getSession();
			@SuppressWarnings("unchecked")
			Set<Long> mandje = (Set<Long>) session.getAttribute(MANDJE);
			if (mandje == null) {
				mandje = new LinkedHashSet<>();
			}
			mandje.addAll(Arrays.stream(request.getParameterValues("filmid")).map(id -> Long.parseLong(id))
					.collect(Collectors.toSet()));
			session.setAttribute(MANDJE, mandje);
		}
		response.sendRedirect(response.encodeRedirectURL(request.getRequestURI()));
	}

}
