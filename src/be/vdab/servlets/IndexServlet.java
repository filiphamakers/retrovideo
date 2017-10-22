package be.vdab.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import be.vdab.entities.Film;
import be.vdab.entities.Genre;
import be.vdab.repositories.FilmRepository;
import be.vdab.repositories.GenreRepository;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index.htm")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/reservaties.jsp";
	private static String filmImgPath;
	private final transient GenreRepository genreRepository = new GenreRepository();
	private final transient FilmRepository filmRepository = new FilmRepository();

	@Resource(name = GenreRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		genreRepository.setDataSource(dataSource);
		filmRepository.setDataSource(dataSource);
	}

	@Override
	public void init() throws ServletException {
		filmImgPath = this.getServletContext().getRealPath("/images");
		System.out.println(filmImgPath);
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// toon alle genres
		List<Genre> genres = genreRepository.findAll();
		request.setAttribute("genres", genres);
		// toon alle films in het aangeklikte genre
		if (request.getParameter("genre") != null) {
			List<Film> films = filmRepository.findAllByGenre(request.getParameter("genre"));
			request.setAttribute("films", films);
			request.setAttribute("filmIdsMetFoto",
					films.stream().filter(film -> Files.exists(Paths.get(filmImgPath, film.getId() + ".jpg")))
							.map(film -> film.getId()).collect(Collectors.toList()));
		}

		request.getRequestDispatcher(VIEW).forward(request, response);
	}

}
