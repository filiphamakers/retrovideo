package be.vdab.filters;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.sql.DataSource;

import be.vdab.entities.Genre;
import be.vdab.repositories.GenreRepository;

/**
 * Servlet Filter implementation class ReservatiesFilter
 */
@WebFilter("*.htm")
public class ReservatiesFilter implements Filter {
    private final GenreRepository genreRepository = new GenreRepository();

    @Resource(name = GenreRepository.JNDI_NAME)
    void setDataSource(DataSource dataSource) {
        genreRepository.setDataSource(dataSource);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        List<Genre> genres = genreRepository.findAll();
        request.setAttribute("genres", genres);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
    }

}
