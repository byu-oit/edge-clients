package edu.byu.chartblock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by yoshutch on 4/7/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChartBlock implements Serializable {
	private static final long serialVersionUID = 1L;

	private String effectiveDate;
	private String status;
	private ChartBlockOperatingUnit operatingUnit;
	private ChartBlockField accountField;
	private ChartBlockField classField;

	public ChartBlock() {
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ChartBlockOperatingUnit getOperatingUnit() {
		return operatingUnit;
	}

	public void setOperatingUnit(ChartBlockOperatingUnit operatingUnit) {
		this.operatingUnit = operatingUnit;
	}

	public ChartBlockField getAccountField() {
		return accountField;
	}

	public void setAccountField(ChartBlockField accountField) {
		this.accountField = accountField;
	}

	public ChartBlockField getClassField() {
		return classField;
	}

	public void setClassField(ChartBlockField classField) {
		this.classField = classField;
	}


}
