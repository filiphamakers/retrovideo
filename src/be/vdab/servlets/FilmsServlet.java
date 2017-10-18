package be.vdab.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

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
	private static String filmImgPath; 
	private final transient FilmRepository filmRepository = new FilmRepository();
	
	@Resource(name = GenreRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		filmRepository.setDataSource(dataSource);
	}
	
	
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		filmImgPath = this.getServletContext().getRealPath("/images");
		System.out.println(filmImgPath);
		super.init();
	}


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Film> films = filmRepository.findAllByGenre(request.getParameter("genre").toString());
		request.setAttribute("films", films);
		request.setAttribute("filmIdsMetFoto",
				films.stream().filter(film -> Files.exists(Paths.get(filmImgPath, film.getId() + ".jpg")))
						.map(film -> film.getId()).collect(Collectors.toList()));
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

}
