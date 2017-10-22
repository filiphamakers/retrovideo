package be.vdab.servlets;

import java.io.IOException;
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
	private static final String MISLUKTE_RESERVATIES_IDS = SessionFieldStorage.MISLUKTE_RESERVATIES_IDS
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
		@SuppressWarnings("unchecked")
		Set<Long> mislukteReservatiesIDS = (Set<Long>) request.getSession().getAttribute(MISLUKTE_RESERVATIES_IDS);
		if (mislukteReservatiesIDS != null) {
			Set<String> mislukteReservatiesTitels = new LinkedHashSet<>();
			for (Long filmid : mislukteReservatiesIDS) {
				filmRepository.findById(filmid).ifPresent(film -> mislukteReservatiesTitels.add(film.getTitel()));
			}
			request.setAttribute("mislukteReservatiesTitels", mislukteReservatiesTitels);
			mislukteReservatiesTitels.stream().forEach(titel -> System.out.println(titel));
		}
		request.setAttribute("reservatieStatus", mislukteReservatiesIDS == null ? "De reservatie is OK"
				: "De volgende films konden niet worden gereserveerd:");
		//winkelmandje na bevestiging leegmaken
		if (request.getSession() != null) {
			request.getSession().invalidate();
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(String.format("klantid:%s ", request.getSession().getAttribute(KLANT_ID)));
		@SuppressWarnings("unchecked")
		Set<Long> mandje = (Set<Long>) request.getSession().getAttribute(MANDJE);
		Long klantid = (Long) request.getSession().getAttribute(KLANT_ID);
		if (mandje != null && klantid != null) {
			Set<Long> mislukteReservatiesIDS = new LinkedHashSet<>();
			for (Long filmid : mandje) {
				if (!reservatieRepository.addReservatie(klantid, filmid)) {
					mislukteReservatiesIDS.add(filmid);
				}
			}
			if (!mislukteReservatiesIDS.isEmpty()) {
				request.getSession().setAttribute(MISLUKTE_RESERVATIES_IDS, mislukteReservatiesIDS);
			}
		}
		response.sendRedirect(request.getRequestURI());
	}

}
