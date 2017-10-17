package be.vdab.repositories;

import javax.sql.DataSource;

public abstract class AbstractRepository {

	public final static String JNDI_NAME = "jdbc/retrovideo";
	protected DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
