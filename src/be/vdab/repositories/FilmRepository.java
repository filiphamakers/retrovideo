package be.vdab.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import be.vdab.entities.Film;
import be.vdab.entities.Genre;

public class FilmRepository extends AbstractRepository {
	// SQL statements
	private static final String BASIC_SELECT = 
			"select films.id as filmid, genres.id as genreid, naam as genre, titel, voorraad, gereserveerd, prijs\r\n" + 
			"from films inner join genres\r\n" + 
			"on films.genreid=genres.id\r\n";
	private static final String FIND_BY_ID = String.format("%s %s", BASIC_SELECT,"where films.id=?");
	private static final String FIND_ALL_BY_GENRE = 
			String.format("%s %s", BASIC_SELECT, "where naam = ? order by titel");

	public Optional<Film> findById(long id){
		Film film = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)){
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setLong(1, id);
			try(ResultSet result = statement.executeQuery()){
				if (result.next()) {
					film = converteerNaarFilm(result);
				}
			}
			connection.commit();
			return Optional.ofNullable(film);
		} catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
	
	public List<Film> findAllByGenre(String genre) {
		List<Film> films = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_GENRE)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setString(1, genre);
			try(ResultSet result = statement.executeQuery()){
				while (result.next()) {
					films.add(converteerNaarFilm(result));
				}
			}
			connection.commit();
			return Collections.unmodifiableList(films);
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
