package be.vdab.repositories;

import java.lang.reflect.Executable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ReservatieRepository extends AbstractRepository {
	private static final String ADD_RESERVATIE = "insert into reservaties(klantid, filmid, reservatieDatum)\r\n"
			+ "values(?, ?, ?)";
	private static final String UPDATE_FILM = "update films set gereserveerd = gereserveerd + 1\r\n"
			+ "where id = ? and voorraad - gereserveerd > 0";

	public boolean addReservatie(long klantid, long filmid) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement reservatieStatement = connection.prepareStatement(ADD_RESERVATIE);
				PreparedStatement filmStatement = connection.prepareStatement(UPDATE_FILM)) {
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
			reservatieStatement.setLong(1, klantid);
			reservatieStatement.setLong(2, filmid);
			reservatieStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			filmStatement.setLong(1, filmid);
			int reservatieSucces = reservatieStatement.executeUpdate();
			int filmSucces = filmStatement.executeUpdate();

			if (reservatieSucces == 1 && filmSucces == 1) {
				connection.commit();
				return true;
			} else {
				connection.rollback();
				return false;
			}
		} catch (SQLException ex) {
			throw new RepositoryException(ex);
		}
	}
}
