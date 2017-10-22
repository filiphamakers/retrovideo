package be.vdab.servlets;

import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import be.vdab.repositories.FilmRepository;
import be.vdab.repositories.ReservatieRepository;

/**
 * Servlet implementation class RapportServlet
 */
@WebServlet("/rapport.htm")
public class RapportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/rapport.jsp";
	// SESSION FIELDS
	private static final String KLANT_ID = SessionFieldStorage.KLANT_ID.getSessionField();
	private static final String MANDJE = SessionFieldStorage.MANDJE.getSessionField();
	private static final String MISLUKTE_RESERVATIES = SessionFieldStorage.MISLUKTE_RESERVATIES
			.getSessionField();
	// REPOSITORIES
	private final transient ReservatieRepository reservatieRepository = new ReservatieRepository();
	private final transient FilmRepository filmRepository = new FilmRepository();

	@Resource(name = ReservatieRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		reservatieRepository.setDataSource(dataSource);
		filmRepository.setDataSource(dataSource);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Set<Long> mandje = (Set<Long>) request.getSession().getAttribute(MANDJE);
		Long klantid = (Long) request.getSession().getAttribute(KLANT_ID);
		Set<String> mislukteReservaties = new LinkedHashSet<>();
		if (mandje != null && klantid != null) {
			for (Long filmid : mandje) {
				if (!reservatieRepository.addReservatie(klantid, filmid)) {
					filmRepository.findById(filmid).ifPresent(film -> mislukteReservaties.add(film.getTitel()));
				}
			}
			request.getSession().removeAttribute(MANDJE);
			request.getSession().removeAttribute(KLANT_ID);
		}
		request.getSession().setAttribute(MISLUKTE_RESERVATIES, mislukteReservaties);
		response.sendRedirect(request.getRequestURI());
	}

}
