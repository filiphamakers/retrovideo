package be.vdab.servlets;

import java.io.IOException;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import be.vdab.entities.Film;
import be.vdab.repositories.FilmRepository;

/**
 * Servlet implementation class FilmDetailsServlet
 */
@WebServlet("/filmdetails.htm")
public class FilmDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/filmdetails.jsp";
	private final transient FilmRepository filmRepository = new FilmRepository();
	
	@Resource(name = FilmRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		filmRepository.setDataSource(dataSource);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<Film> film = filmRepository.findById(new Long(request.getParameter("filmid")));
		request.setAttribute("film", film.get());
		//nog toe te voegen: alternatieve setAttribute() indien film met betreffend id niet gevonden wordt!
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

}
