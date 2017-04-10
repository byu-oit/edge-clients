package edu.byu.chartblock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by yoshutch on 4/7/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChartBlockOperatingUnit implements Serializable{
	private static final long serialVersionUID = 1L;
	@JsonProperty("value")
	private String id;
	private String status;
	@JsonProperty("description")
	private String name;
	private String effectiveDate;
	private ChartBlockContact manager;
	private ChartBlockContact contact;

	public ChartBlockOperatingUnit() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public ChartBlockContact getManager() {
		return manager;
	}

	public void setManager(ChartBlockContact manager) {
		this.manager = manager;
	}

	public ChartBlockContact getContact() {
		return contact;
	}

	public void setContact(ChartBlockContact contact) {
		this.contact = contact;
	}
}