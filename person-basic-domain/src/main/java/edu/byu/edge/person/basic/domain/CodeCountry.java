package edu.byu.edge.person.basic.domain;

/**
 * Created by IntelliJ IDEA.
 * User: thirschi
 * Date: 4/10/13
 * Time: 7:50 PM
 */
public class CodeCountry {

	private String countryCode;
	private String country;
	private String isoCode;
	private String countryPhonePrefix;
	private String isoCode3;
	private String continentCode;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public String getCountryPhonePrefix() {
		return countryPhonePrefix;
	}

	public void setCountryPhonePrefix(String countryPhonePrefix) {
		this.countryPhonePrefix = countryPhonePrefix;
	}

	public String getIsoCode3() {
		return isoCode3;
	}

	public void setIsoCode3(String isoCode3) {
		this.isoCode3 = isoCode3;
	}

	public String getContinentCode() {
		return continentCode;
	}

	public void setContinentCode(String continentCode) {
		this.continentCode = continentCode;
	}
}
