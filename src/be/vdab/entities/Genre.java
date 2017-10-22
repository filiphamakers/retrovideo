package be.vdab.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
		if (StringUtils.isLong(id) && new Long(id) > 0) {
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

	@Override
	public int hashCode() {
		return new HashCodeBuilder(13, 41).append(id).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		Genre genre = (Genre) obj;
		return new EqualsBuilder()
				.append(id, genre.id)
				.append(naam, genre.naam)
				.isEquals();
	}

	@Override
	public String toString() {
		return String.format("genreid=%s, naam=%s", id, naam);
	}

}
