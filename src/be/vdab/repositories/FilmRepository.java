package be.vdab.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import be.vdab.entities.Film;
import be.vdab.entities.Genre;

public class FilmRepository extends AbstractRepository {
	// SQL statements
	private static final String BASIC_SELECT = "select id, genreid, titel, voorraad, gereserveerd, prijs from films";
	private static final String FIND_ALL_BY_GENRE = 
			"select films.id as filmid, genres.id as genreid, naam as genre, titel, voorraad, gereserveerd, prijs\r\n" + 
			"from films inner join genres\r\n" + 
			"on films.genreid=genres.id\r\n" + 
			"where naam = ?\r\n" + 
			"order by titel";

	public List<Film> findAllByGenre(String genre) {
		List<Film> films = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_GENRE, Statement.RETURN_GENERATED_KEYS)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setString(1, genre);
			try(ResultSet result = statement.executeQuery()){
				while (result.next()) {
					films.add(converteerNaarFilm(result));
				}
			}
			connection.commit();
			return films;
		} catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
	
	private Film converteerNaarFilm(ResultSet result) throws SQLException {
		return new Film(result.getString("filmid"),
				result.getString("voorraad"),
				result.getString("gereserveerd"),
				result.getString("prijs"),
				new Genre(result.getString("genreid"), result.getString("genre")),
				result.getString("titel"));
	}

}
