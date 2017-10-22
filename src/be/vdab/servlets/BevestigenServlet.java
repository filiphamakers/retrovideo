package be.vdab.servlets;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import be.vdab.entities.Klant;
import be.vdab.repositories.KlantRepository;

/**
 * Servlet implementation class BevestigenServlet
 */
@WebServlet("/bevestigen.htm")
public class BevestigenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String MANDJE = SessionFieldStorage.MANDJE.getSessionField();
	private static final String KLANT_ID = SessionFieldStorage.KLANT_ID.getSessionField();
	private static final String VIEW = "/WEB-INF/JSP/bevestigen.jsp";
	private final transient KlantRepository klantRepository = new KlantRepository();

	@Resource(name = KlantRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		klantRepository.setDataSource(dataSource);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Optional<Klant> klant = Optional.ofNullable(null);
		HttpSession session = request.getSession(false);
		if (session != null) {
			@SuppressWarnings("unchecked")
			Set<Long> mandje = (Set<Long>) session.getAttribute(MANDJE);
			if (mandje != null) {
				request.setAttribute("aantalFilms", mandje.size());
				klant = klantRepository.findById(Long.parseLong(request.getParameter("klant")));
			}
		}
		if (klant.isPresent()) {
			request.setAttribute("klant", klant.get());
			request.getSession().setAttribute(KLANT_ID, klant.get().getId());
		}
		request.getRequestDispatcher(VIEW).forward(request, response);

	}
}
