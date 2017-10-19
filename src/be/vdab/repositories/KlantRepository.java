package be.vdab.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import be.vdab.entities.Klant;

public class KlantRepository extends AbstractRepository {
	// SQL statements
	private static final String BASIC_SELECT = "select id as klantid, familienaam, voornaam, postcode, gemeente from klanten";
	private static final String FIND_BY_LIKE_FAMILIENAAM = String.format("%s %s", "where familienaam like ?");

	private Klant converteerNaarKlant(ResultSet result) throws SQLException {
		return new Klant(result.getString("klantid"),
				result.getString("familienaam"),
				result.getString("voornaam"),
				result.getString("voornaam"),
				result.getString("postcode"),
				result.getString("gemeente"));
	}

	public Optional<Klant> findByLikeFamilienaam(String familienaam) {
		Klant klant = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(FIND_BY_LIKE_FAMILIENAAM)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setString(1, familienaam);
			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					klant = converteerNaarKlant(result);
				}
			}
			connection.commit();
			return Optional.ofNullable(klant);
		} catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
}
