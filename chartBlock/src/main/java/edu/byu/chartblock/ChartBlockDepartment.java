package edu.byu.chartblock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by yoshutch on 4/7/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChartBlockDepartment implements Serializable{
	private static final long serialVersionUID = 1L;
	@JsonProperty("value")
	private String id;
	@JsonProperty("description")
	private String name;
	private ChartBlockContact manager;
	private ChartBlockContact contact;
	private ChartBlockContact vp;
	private ChartBlockContact dean;
	private ChartBlockContact liaison;
	private ChartBlockContact controller;

	public ChartBlockDepartment() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public ChartBlockContact getVp() {
		return vp;
	}

	public void setVp(ChartBlockContact vp) {
		this.vp = vp;
	}

	public ChartBlockContact getDean() {
		return dean;
	}

	public void setDean(ChartBlockContact dean) {
		this.dean = dean;
	}

	public ChartBlockContact getLiaison() {
		return liaison;
	}

	public void setLiaison(ChartBlockContact liaison) {
		this.liaison = liaison;
	}

	public ChartBlockContact getController() {
		return controller;
	}

	public void setController(ChartBlockContact controller) {
		this.controller = controller;
	}
}
