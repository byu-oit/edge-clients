package edu.byu.edge.client.pro.domain.standing;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for responseType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="responseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="year_term" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="standing" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="explanation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "responseType", namespace = "uri://byu/c/ry/ae/prod/records/cgi/recStdAcadStanding.cgi",
				propOrder = {
									"yearTerm", "standing", "explanation"
})
public class ResponseType {

	@XmlElement(name = "year_term", namespace = "uri://byu/c/ry/ae/prod/records/cgi/recStdAcadStanding.cgi",
					   required = true)
	protected String yearTerm;

	@XmlElement(namespace = "uri://byu/c/ry/ae/prod/records/cgi/recStdAcadStanding.cgi", required = true)
	protected String standing;

	@XmlElement(namespace = "uri://byu/c/ry/ae/prod/records/cgi/recStdAcadStanding.cgi", required = true)
	protected String explanation;

	/**
	 * Gets the value of the yearTerm property.
	 *
	 * @return possible object is
	 *         {@link String }
	 */
	public String getYearTerm() {
		return yearTerm;
	}

	/**
	 * Sets the value of the yearTerm property.
	 *
	 * @param value allowed object is
	 *              {@link String }
	 */
	public void setYearTerm(String value) {
		this.yearTerm = value;
	}

	/**
	 * Gets the value of the standing property.
	 *
	 * @return possible object is
	 *         {@link String }
	 */
	public String getStanding() {
		return standing;
	}

	/**
	 * Sets the value of the standing property.
	 *
	 * @param value allowed object is
	 *              {@link String }
	 */
	public void setStanding(String value) {
		this.standing = value;
	}

	/**
	 * Gets the value of the explanation property.
	 *
	 * @return possible object is
	 *         {@link String }
	 */
	public String getExplanation() {
		return explanation;
	}

	/**
	 * Sets the value of the explanation property.
	 *
	 * @param value allowed object is
	 *              {@link String }
	 */
	public void setExplanation(String value) {
		this.explanation = value;
	}

}
