package edu.byu.edge.client.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Calendar;


/**
 * The credential is used when doing web service authentication. The wsId is passed along with a
 * signature that was obtained using the shared secret.  Upon successful validation of the HMAC
 * based authentication the identity associated with this credential is known.
 * <p/>
 * <p/>
 * <p/>
 * <p>Java class for credential complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="credential">
 *   &lt;complexContent>
 *	 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *	   &lt;sequence>
 *		 &lt;element name="personId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *		 &lt;element name="wsId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *		 &lt;element name="sharedSecret" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *		 &lt;element name="expirationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *	   &lt;/sequence>
 *	 &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "credential", namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0", propOrder = {
	"personId",
	"wsId",
	"sharedSecret",
	"expirationDate"
})
public class Credential implements Serializable {

	/** */
	@XmlElement(namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0", required = true)
	protected String personId;

	/** */
	@XmlElement(namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0", required = true)
	protected String wsId;

	/** */
	@XmlElement(namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0", required = true)
	protected String sharedSecret;

	/** */
	@XmlElement(namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0", required = true)
	@XmlJavaTypeAdapter(CalendarConverter.class)
	protected Calendar expirationDate;

	/** */
	public Credential() {
	}

	/**
	 * Gets the value of the personId property.
	 *
	 * @return possible object is
	 *		 {@link String }
	 */
	public String getPersonId() {
		return personId;
	}

	/**
	 * Sets the value of the personId property.
	 *
	 * @param value allowed object is
	 *			  {@link String }
	 */
	public void setPersonId(String value) {
		this.personId = value;
	}

	/**
	 * Gets the value of the wsId property.
	 *
	 * @return possible object is
	 *		 {@link String }
	 */
	public String getWsId() {
		return wsId;
	}

	/**
	 * Sets the value of the wsId property.
	 *
	 * @param value allowed object is
	 *			  {@link String }
	 */
	public void setWsId(String value) {
		this.wsId = value;
	}

	/**
	 * Gets the value of the sharedSecret property.
	 *
	 * @return possible object is
	 *		 {@link String }
	 */
	public String getSharedSecret() {
		return sharedSecret;
	}

	/**
	 * Sets the value of the sharedSecret property.
	 *
	 * @param value allowed object is
	 *			  {@link String }
	 */
	public void setSharedSecret(String value) {
		this.sharedSecret = value;
	}

	/**
	 * Gets the value of the expirationDate property.
	 *
	 * @return possible object is
	 *		 {@link Calendar }
	 */
	public Calendar getExpirationDate() {
		return expirationDate;
	}

	/**
	 * Sets the value of the expirationDate property.
	 *
	 * @param value allowed object is
	 *			  {@link Calendar }
	 */
	public void setExpirationDate(Calendar value) {
		this.expirationDate = value;
	}

}
