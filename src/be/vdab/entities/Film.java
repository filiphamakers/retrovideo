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

	private void valideerLongValue(long var, String getal, String msg) {
		if (StringUtils.isLong(getal)) {
			var = new Long(getal);
		} else
			throw new FilmException(msg);
	}
	
	public long getId() {
		return id;
	}

	public void setId(String id) {
		String msg = "ongeldig id voor film";
		valideerLongValue(this.id, id, msg);
	}

	public long getAantalInVoorraad() {
		return aantalInVoorraad;
	}

	public void setAantalInVoorraad(String aantalInVoorraad) {
		String msg = "ongeldige waarde voor film (aantal in voorraad)";
		valideerLongValue(this.aantalInVoorraad, aantalInVoorraad, msg);
	}

	public long getAantalGereserveerd() {
		return aantalGereserveerd;
	}

	public void setAantalGereserveerd(String aantalGereserveerd) {
		String msg = "ongeldige waarde voor film (aantal gereserveerd)";
		valideerLongValue(this.aantalGereserveerd, aantalGereserveerd, msg);
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
