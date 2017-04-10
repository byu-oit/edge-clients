package edu.byu.chartblock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by yoshutch on 4/7/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChartBlockContact implements Serializable {
	private static final long serialVersionUID = 1L;
	private String byuId;
	private String netId;
	private String name;
	private String phoneNumber;
	private String emailAddress;

	public ChartBlockContact() {
	}

	public String getByuId() {
		return byuId;
	}

	public void setByuId(String byuId) {
		this.byuId = byuId;
	}

	public String getNetId() {
		return netId;
	}

	public void setNetId(String netId) {
		this.netId = netId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}
