package be.vdab.servlets;

/*
 * Bevat de namen van de session fields die in de verschillende servlets worden aangesproken. 
 */
enum SessionFieldStorage {
	KLANT_ID("klantid"), MANDJE("mandje"), MISLUKTE_RESERVATIES("mislukteReservaties");

	private final String sessionField;

	private SessionFieldStorage(String sessionField) {
		this.sessionField = sessionField;
	}

	public String getSessionField() {
		return sessionField;
	}

}
