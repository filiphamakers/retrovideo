package be.vdab.servlets;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import be.vdab.entities.Klant;
import be.vdab.repositories.KlantRepository;

/**
 * Servlet implementation class KlantServlet
 */
@WebServlet("/klant.htm")
public class KlantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/klant.jsp";
	private final transient KlantRepository klantRepository = new KlantRepository();

	@Resource(name = KlantRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		klantRepository.setDataSource(dataSource);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("familienaam") != null && !request.getParameter("familienaam").trim().isEmpty()) {

			Set<Klant> klanten = klantRepository.findByLikeFamilienaam(request.getParameter("familienaam")).stream()
					.filter(optionalKlant -> optionalKlant.isPresent()).map(optionalKlant -> optionalKlant.get())
					.collect(Collectors.toSet());
			request.setAttribute("klanten", klanten);
		} else if (request.getParameter("bevestigingsknop") != null
				&& "Zoeken".equals(request.getParameter("bevestigingsknop"))) {
			request.setAttribute("fout", "Tik minstens één letter");
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

}
