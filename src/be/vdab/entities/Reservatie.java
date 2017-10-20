package be.vdab.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import be.vdab.util.StringUtils;

public class Reservatie {
	long filmid, klantid;
	LocalDate reservatiedatum;
	
	
	public Reservatie(String filmid, String klantid, String reservatiedatum) {
		setFilmid(filmid);
		setKlantid(klantid);
		setReservatiedatum(reservatiedatum);
	}

	public long getFilmid() {
		return filmid;
	}

	public void setFilmid(String filmid) {
		if (StringUtils.isLong(filmid) && new Long(filmid) > 0 && new Long(filmid) <= Long.MAX_VALUE) {
			this.filmid = new Long(filmid);
		} else
			throw new ReservatieException("ongeldig klantid voor reservatie");
	}

	public long getKlantid() {
		return klantid;
	}

	public void setKlantid(String klantid) {
		if (StringUtils.isLong(klantid) && new Long(klantid) > 0 && new Long(klantid) <= Long.MAX_VALUE) {
			this.klantid = new Long(klantid);
		} else
			throw new KlantException("ongeldig klantid voor reservatie");
	}

	public LocalDate getReservatiedatum() {
		return reservatiedatum;
	}

	public void setReservatiedatum(String reservatiedatum) {
		DateTimeFormatter format = new D;
		this.reservatiedatum = reservatiedatum;
		try {
			LocalDate.parse(reservatiedatum, format);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
}
