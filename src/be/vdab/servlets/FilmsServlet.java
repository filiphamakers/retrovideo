package be.vdab.servlets;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import be.vdab.entities.Film;
import be.vdab.repositories.FilmRepository;
import be.vdab.repositories.GenreRepository;

/**
 * Servlet implementation class FilmsServlet
 */
@WebServlet("/films.htm")
public class FilmsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "WEB-INF/JSP/films.jsp";
	private final transient FilmRepository filmRepository = new FilmRepository();
	
	@Resource(name = GenreRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		filmRepository.setDataSource(dataSource);
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Film> films = filmRepository.findAllByGenre(request.getParameter("genre").toString());
		request.setAttribute("films", films);
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

}
