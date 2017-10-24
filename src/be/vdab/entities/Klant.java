package be.vdab.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import be.vdab.util.StringUtils;

public class Klant {
	private long id;
	private String familienaam, voornaam, straatNummer, postcode, gemeente;

	public Klant(String id, String familienaam, String voornaam, String straatNummer, String postcode,
			String gemeente) {
		setId(id);
		setFamilienaam(familienaam);
		setVoornaam(voornaam);
		setStraatNummer(straatNummer);
		setPostcode(postcode);
		setGemeente(gemeente);
	}

	public long getId() {
		return id;
	}

	public void setId(String id) {
		if (StringUtils.isLong(id) && new Long(id) > 0) {
			this.id = new Long(id);
		} else
			throw new KlantException("ongeldig id voor klant");
	}

	public String getFamilienaam() {
		return familienaam;
	}
	
	public void setFamilienaam(String familienaam) {
		if (StringUtils.isWellFormed(familienaam, "[A-Za-z]+( [A-Za-z]+)*")) {
			this.familienaam = familienaam;
		} else
			throw new KlantException("ongeldige familienaam voor klant");

	}

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		if (StringUtils.isWellFormed(voornaam, "[A-Za-z]+")) {
			this.voornaam = voornaam;
		} else
			throw new KlantException("ongeldige voornaam voor klant");
	}
	
	public String getNaam() {
		return String.format("%s %s", voornaam, familienaam);
	}

	public String getStraatNummer() {
		return straatNummer;
	}

	public void setStraatNummer(String straatNummer) {
		if (StringUtils.isWellFormed(straatNummer, "[A-Za-z]+( [A-Za-z]+)*( [0-9]+)*")) {
			this.straatNummer = straatNummer;
		} else
			throw new KlantException("ongeldige woonplaatsgegevens voor klant (straatnummer)");

	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		if (StringUtils.isWellFormed(postcode, "[0-9]{4}")) {
			this.postcode = postcode;
		} else
			throw new KlantException("ongeldige woonplaatsgegevens voor klant (postcode)");
	}

	public String getGemeente() {
		return gemeente;
	}

	public void setGemeente(String gemeente) {
		if (StringUtils.isWellFormed(gemeente, "[A-Za-z]+")) {
			this.gemeente = gemeente;
		} else
			throw new KlantException("ongeldige woonplaatsgegevens voor klant (gemeente)");

	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(21, 53).append(id).toHashCode();
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
		Klant klant = (Klant) obj;
		return new EqualsBuilder()
				.append(id, klant.id)
				.append(voornaam, klant.voornaam)
				.append(familienaam, klant.familienaam)
				.isEquals();
	}

	@Override
	public String toString() {
		return String.format("klantid=%s, naam=%s, straatnummer=%s, gemeente=%s, postcode=%s"
				, id, getNaam(), straatNummer, gemeente, postcode);
	}

}
