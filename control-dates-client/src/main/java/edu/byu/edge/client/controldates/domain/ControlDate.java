package edu.byu.edge.client.controldates.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ControlDate", propOrder = { "request", "errors", "response" })
public class ControlDate {
	private ControlDateType type;
	private String yearTerm;
	private Date startDate;
	private Date endDate;
	private String description;

	private ControlDate() {
		super();
	}

	/**
	 * See {@link ControlDate#type}
	 * 
	 * @return the type
	 */
	public ControlDateType getType() {
		return type;
	}

	/**
	 * See {@link ControlDate#type}
	 * 
	 * @param type
	 *            the type to set
	 */
	public void setType(ControlDateType type) {
		this.type = type;
	}

	/**
	 * See {@link ControlDate#yearTerm}
	 * 
	 * @return the yearTerm
	 */
	public String getYearTerm() {
		return yearTerm;
	}

	/**
	 * See {@link ControlDate#yearTerm}
	 * 
	 * @param yearTerm
	 *            the yearTerm to set
	 */
	public void setYearTerm(String yearTerm) {
		this.yearTerm = yearTerm;
	}

	/**
	 * See {@link ControlDate#startDate}
	 * 
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * See {@link ControlDate#startDate}
	 * 
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * See {@link ControlDate#endDate}
	 * 
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * See {@link ControlDate#endDate}
	 * 
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * See {@link ControlDate#description}
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * See {@link ControlDate#description}
	 * 
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
