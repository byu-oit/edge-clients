package edu.byu.edge.client.pro.domain.standing;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for errorType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="errorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="httpStatusCode" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "errorType", namespace = "uri://byu/c/ry/ae/prod/records/cgi/recStdAcadStanding.cgi",
				propOrder = {
									"code", "name", "message", "httpStatusCode"
})
public class ErrorType {

	@XmlElement(namespace = "uri://byu/c/ry/ae/prod/records/cgi/recStdAcadStanding.cgi", required = true)
	protected BigInteger code;

	@XmlElement(namespace = "uri://byu/c/ry/ae/prod/records/cgi/recStdAcadStanding.cgi", required = true)
	protected String name;

	@XmlElement(namespace = "uri://byu/c/ry/ae/prod/records/cgi/recStdAcadStanding.cgi", required = true)
	protected String message;

	@XmlElement(namespace = "uri://byu/c/ry/ae/prod/records/cgi/recStdAcadStanding.cgi", required = true)
	protected BigInteger httpStatusCode;

	/**
	 * Gets the value of the code property.
	 *
	 * @return possible object is
	 *         {@link BigInteger }
	 */
	public BigInteger getCode() {
		return code;
	}

	/**
	 * Sets the value of the code property.
	 *
	 * @param value allowed object is
	 *              {@link BigInteger }
	 */
	public void setCode(BigInteger value) {
		this.code = value;
	}

	/**
	 * Gets the value of the name property.
	 *
	 * @return possible object is
	 *         {@link String }
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the name property.
	 *
	 * @param value allowed object is
	 *              {@link String }
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * Gets the value of the message property.
	 *
	 * @return possible object is
	 *         {@link String }
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the value of the message property.
	 *
	 * @param value allowed object is
	 *              {@link String }
	 */
	public void setMessage(String value) {
		this.message = value;
	}

	/**
	 * Gets the value of the httpStatusCode property.
	 *
	 * @return possible object is
	 *         {@link BigInteger }
	 */
	public BigInteger getHttpStatusCode() {
		return httpStatusCode;
	}

	/**
	 * Sets the value of the httpStatusCode property.
	 *
	 * @param value allowed object is
	 *              {@link BigInteger }
	 */
	public void setHttpStatusCode(BigInteger value) {
		this.httpStatusCode = value;
	}

}
