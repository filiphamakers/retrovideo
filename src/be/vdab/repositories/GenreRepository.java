package be.vdab.repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import be.vdab.entities.Genre;

public class GenreRepository extends AbstractRepository {
	// SQL statements
	private static final String BASIC_SELECT = "select id, naam from genres";
	private static final String FIND_ALL = BASIC_SELECT + String.format("%n%s", "order by naam");

	public List<Genre> findAll() {
		try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
			List<Genre> genres = new ArrayList<>();
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			try (ResultSet result = statement.executeQuery(FIND_ALL)) {
				while (result.next()) {
					genres.add(converteerNaarGenre(result));
				}
				connection.commit();
				return Collections.unmodifiableList(genres);
			}
		} catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
	
	private static Genre converteerNaarGenre(ResultSet result) throws SQLException {
		return new Genre(result.getString("id"), result.getString("naam"));
	}
}
