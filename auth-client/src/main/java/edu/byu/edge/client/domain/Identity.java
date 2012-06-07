package edu.byu.edge.client.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


/**
 * 
 * 				
 * 						An identity represents an principal that can participate in authentication and authorization with BYU.
 * 						
 * 			
 * 
 * <p>Java class for identity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="identity">
 *   &lt;complexContent>
 *	 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *	   &lt;sequence>
 *		 &lt;element name="personId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *		 &lt;element name="byuId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *		 &lt;element name="netId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *		 &lt;element name="sortName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *		 &lt;element name="preferredFirstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *		 &lt;element name="surname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *		 &lt;element name="fullName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *		 &lt;element name="gender" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *		 &lt;element name="restricted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *		 &lt;element name="deceased" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *		 &lt;element name="organization" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *	   &lt;/sequence>
 *	 &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "identity", namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0", propOrder = {
	"personId",
	"byuId",
	"netId",
	"sortName",
	"preferredFirstName",
	"surname",
	"fullName",
	"gender",
	"restricted",
	"deceased",
	"organization"
})
public class Identity implements Serializable {

	/** */
	@XmlElement(namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0", required = true)
	protected String personId;

	/** */
	@XmlElement(namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0", required = true)
	protected String byuId;

	/** */
	@XmlElement(namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0")
	protected String netId;

	/** */
	@XmlElement(namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0", required = true)
	protected String sortName;

	/** */
	@XmlElement(namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0", required = true)
	protected String preferredFirstName;

	/** */
	@XmlElement(namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0", required = true)
	protected String surname;

	/** */
	@XmlElement(namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0", required = true)
	protected String fullName;

	/** */
	@XmlElement(namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0", required = true)
	protected String gender;

	/** */
	@XmlElement(namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0")
	protected boolean restricted;

	/** */
	@XmlElement(namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0")
	protected boolean deceased;

	/** */
	@XmlElement(namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0")
	protected boolean organization;

	/** */
	public Identity() {
	}

	/**
	 * Gets the value of the personId property.
	 * 
	 * @return
	 *	 possible object is
	 *	 {@link String }
	 *	 
	 */
	public String getPersonId() {
		return personId;
	}

	/**
	 * Sets the value of the personId property.
	 * 
	 * @param value
	 *	 allowed object is
	 *	 {@link String }
	 *	 
	 */
	public void setPersonId(String value) {
		this.personId = value;
	}

	/**
	 * Gets the value of the byuId property.
	 * 
	 * @return
	 *	 possible object is
	 *	 {@link String }
	 *	 
	 */
	public String getByuId() {
		return byuId;
	}

	/**
	 * Sets the value of the byuId property.
	 * 
	 * @param value
	 *	 allowed object is
	 *	 {@link String }
	 *	 
	 */
	public void setByuId(String value) {
		this.byuId = value;
	}

	/**
	 * Gets the value of the netId property.
	 * 
	 * @return
	 *	 possible object is
	 *	 {@link String }
	 *	 
	 */
	public String getNetId() {
		return netId;
	}

	/**
	 * Sets the value of the netId property.
	 * 
	 * @param value
	 *	 allowed object is
	 *	 {@link String }
	 *	 
	 */
	public void setNetId(String value) {
		this.netId = value;
	}

	/**
	 * Gets the value of the sortName property.
	 * 
	 * @return
	 *	 possible object is
	 *	 {@link String }
	 *	 
	 */
	public String getSortName() {
		return sortName;
	}

	/**
	 * Sets the value of the sortName property.
	 * 
	 * @param value
	 *	 allowed object is
	 *	 {@link String }
	 *	 
	 */
	public void setSortName(String value) {
		this.sortName = value;
	}

	/**
	 * Gets the value of the preferredFirstName property.
	 * 
	 * @return
	 *	 possible object is
	 *	 {@link String }
	 *	 
	 */
	public String getPreferredFirstName() {
		return preferredFirstName;
	}

	/**
	 * Sets the value of the preferredFirstName property.
	 * 
	 * @param value
	 *	 allowed object is
	 *	 {@link String }
	 *	 
	 */
	public void setPreferredFirstName(String value) {
		this.preferredFirstName = value;
	}

	/**
	 * Gets the value of the surname property.
	 * 
	 * @return
	 *	 possible object is
	 *	 {@link String }
	 *	 
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Sets the value of the surname property.
	 * 
	 * @param value
	 *	 allowed object is
	 *	 {@link String }
	 *	 
	 */
	public void setSurname(String value) {
		this.surname = value;
	}

	/**
	 * Gets the value of the fullName property.
	 * 
	 * @return
	 *	 possible object is
	 *	 {@link String }
	 *	 
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Sets the value of the fullName property.
	 * 
	 * @param value
	 *	 allowed object is
	 *	 {@link String }
	 *	 
	 */
	public void setFullName(String value) {
		this.fullName = value;
	}

	/**
	 * Gets the value of the gender property.
	 * 
	 * @return
	 *	 possible object is
	 *	 {@link String }
	 *	 
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets the value of the gender property.
	 * 
	 * @param value
	 *	 allowed object is
	 *	 {@link String }
	 *	 
	 */
	public void setGender(String value) {
		this.gender = value;
	}

	/**
	 * Gets the value of the restricted property.
	 * 
	 */
	public boolean isRestricted() {
		return restricted;
	}

	/**
	 * Sets the value of the restricted property.
	 * 
	 */
	public void setRestricted(boolean value) {
		this.restricted = value;
	}

	/**
	 * Gets the value of the deceased property.
	 * 
	 */
	public boolean isDeceased() {
		return deceased;
	}

	/**
	 * Sets the value of the deceased property.
	 * 
	 */
	public void setDeceased(boolean value) {
		this.deceased = value;
	}

	/**
	 * Gets the value of the organization property.
	 * 
	 */
	public boolean isOrganization() {
		return organization;
	}

	/**
	 * Sets the value of the organization property.
	 * 
	 */
	public void setOrganization(boolean value) {
		this.organization = value;
	}

}
