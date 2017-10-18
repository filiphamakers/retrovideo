package be.vdab.entities;

import be.vdab.util.StringUtils;

public class Genre {
	private long id;
	private String naam;

	public Genre(String id, String naam) {
		setId(id);
		setNaam(naam);
	}

	public long getId() {
		return id;
	}

	public void setId(String id) {
		if (StringUtils.isLong(id)) {
			this.id = new Long(id);
		} else
			throw new GenreException("ongeldig id voor genre");
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		if (naam != null && !naam.trim().isEmpty()) {
			this.naam = naam;
		} else
			throw new GenreException("ongeldige naam voor genre");
	}

}
