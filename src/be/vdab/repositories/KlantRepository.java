package be.vdab.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import be.vdab.entities.Klant;

public class KlantRepository extends AbstractRepository {
	// SQL statements
	private static final String BASIC_SELECT = "select id as klantid, familienaam, voornaam, straatNummer, postcode, gemeente from klanten";
	private static final String FIND_BY_LIKE_FAMILIENAAM = String.format("%s %s", BASIC_SELECT, "where familienaam like ?");
	private static final String FIND_BY_ID = String.format("%s %s", BASIC_SELECT, "where id = ?");

	private Klant converteerNaarKlant(ResultSet result) throws SQLException {
		return new Klant(result.getString("klantid"),
				result.getString("familienaam"),
				result.getString("voornaam"),
				result.getString("straatNummer"),
				result.getString("postcode"),
				result.getString("gemeente"));
	}

	public List<Optional<Klant>> findByLikeFamilienaam(String familienaam) {
		List<Optional<Klant>> klanten = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(FIND_BY_LIKE_FAMILIENAAM)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connection.setAutoCommit(false);
			statement.setString(1, "%"+familienaam+"%");
			try (ResultSet result = statement.executeQuery()) {
				while (result.next()) {
					klanten.add(Optional.ofNullable(converteerNaarKlant(result)));
				}
			}
			connection.commit();
			return Collections.unmodifiableList(klanten);
		} catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
	
	public Optional<Klant> findById(long id){
		Optional<Klant> klant = null;
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)){
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			connection.setAutoCommit(false);
			statement.setLong(1, id);
			try (ResultSet result = statement.executeQuery()){
				if (result.next()) {
					klant = Optional.ofNullable(converteerNaarKlant(result));
				}
			}
			connection.commit();
			return klant;
		} catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
}
