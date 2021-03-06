package be.vdab.entities;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import be.vdab.util.StringUtils;

public class Film {
	private long id, aantalInVoorraad, aantalGereserveerd;
	private BigDecimal prijs;
	private Genre genre;
	private String titel;

	public Film(String id, String aantalInVoorraad, String aantalGereserveerd, String prijs, Genre genre,
			String titel) {
		setId(id);
		setAantalInVoorraad(aantalInVoorraad);
		setAantalGereserveerd(aantalGereserveerd);
		setPrijs(prijs);
		setGenre(genre);
		setTitel(titel);
	}

	public long getId() {
		return id;
	}

	public void setId(String id) {
		if (StringUtils.isLong(id) && new Long(id) > 0) {
			this.id = new Long(id);
		} else
			throw new FilmException("ongeldig id voor film");
	}

	public long getAantalInVoorraad() {
		return aantalInVoorraad;
	}

	public void setAantalInVoorraad(String aantalInVoorraad) {
		if (StringUtils.isLong(aantalInVoorraad) && new Long(aantalInVoorraad) >= 0) {
			this.aantalInVoorraad = new Long(aantalInVoorraad);
		} else
			throw new FilmException("ongeldige waarde voor film (aantal in voorraad)");
	}

	public long getAantalGereserveerd() {
		return aantalGereserveerd;
	}

	public void setAantalGereserveerd(String aantalGereserveerd) {
		if (StringUtils.isLong(aantalGereserveerd) && new Long(aantalGereserveerd) >= 0) {
			this.aantalGereserveerd = new Long(aantalGereserveerd);
		} else
			throw new FilmException("ongeldige waarde voor film (aantal gereserveerd)");
	}

	public BigDecimal getPrijs() {
		return prijs;
	}

	public void setPrijs(String prijs) {
		if (StringUtils.isBigDecimal(prijs) && new BigDecimal(prijs).compareTo(BigDecimal.ZERO) >= 0) {
			this.prijs = new BigDecimal(prijs);
		} else
			throw new FilmException("ongeldige prijs voor film");
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		if (genre != null) {
			this.genre = genre;
		} else
			throw new FilmException("ongeldig genre voor film");
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		if (titel != null && !titel.trim().isEmpty()) {
			this.titel = titel;
		} else
			throw new FilmException("ongeldige titel voor film");
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(11, 31).append(id).toHashCode();
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
		Film film = (Film) obj;
		return new EqualsBuilder()
				.append(id, film.id)
				.append(titel, film.titel)
				.isEquals();
	}

	@Override
	public String toString() {
		return String.format("filmid=%s, titel=%s, prijs=%s, aantal gereserveerd=%s, aantal in voorraad=%s"
				, id, titel, prijs.toString(), aantalGereserveerd, aantalInVoorraad);
	}

}
