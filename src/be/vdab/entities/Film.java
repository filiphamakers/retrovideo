package be.vdab.entities;

import java.math.BigDecimal;

import be.vdab.util.StringUtils;

public class Film {
	private long id, aantalInVoorraad, aantalGereserveerd;
	private BigDecimal prijs;
	private Genre genre;
	private String titel;

	public Film(String id, String aantalInVoorraad, String aantalGereserveerd, String prijs, Genre genre, String titel) {
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
		if (StringUtils.isLong(id)) {
			this.id = new Long(id);
		} else
			throw new FilmException("ongeldig id voor film");
	}

	public long getAantalInVoorraad() {
		return aantalInVoorraad;
	}

	public void setAantalInVoorraad(String aantalInVoorraad) {
		if (StringUtils.isLong(aantalInVoorraad)) {
			this.aantalInVoorraad = new Long(aantalInVoorraad);
		} else
			throw new FilmException("ongeldige waarde voor film (aantal in voorraad)");
	}

	public long getAantalGereserveerd() {
		return aantalGereserveerd;
	}

	public void setAantalGereserveerd(String aantalGereserveerd) {
		if (StringUtils.isLong(aantalGereserveerd)) {
			this.aantalInVoorraad = new Long(aantalGereserveerd);
		} else
			throw new FilmException("ongeldige waarde voor film (aantal gereserveerd)");
	}

	public BigDecimal getPrijs() {
		return prijs;
	}

	public void setPrijs(String prijs) {
		if (StringUtils.isBigDecimal(prijs)) {
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

}
