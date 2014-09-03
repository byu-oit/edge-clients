
package edu.byu.auth.domain;

import java.math.BigInteger;
import javax.xml.bind.annotation.*;


/**
 * 
 * 				
 * 						The nonce is used when performing HMAC based authentication to a web service. The nonceKey is passed
 * 						to the service as part of the authentication along with the signed nonceValue.  The nonceValue is signed
 * 						using a shared secret.  The nonce can be obtained using the Web Service Authentication Service.
 * 						
 * 			
 * 
 * <p>Java class for nonce complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="nonce">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nonceKey" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/>
 *         &lt;element name="nonceValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nonce", namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0", propOrder = {
    "nonceKey",
    "nonceValue"
})
@XmlRootElement(name = "nonce", namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0")
public class Nonce {

    @XmlElement(namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0", required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger nonceKey;

    @XmlElement(namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0", required = true)
    protected String nonceValue;

	/** Convenience */
	@XmlTransient
	protected String apiKey;

	/** Convenience */
	@XmlTransient
	protected String sharedSecret;

	/**
     * Gets the value of the nonceKey property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNonceKey() {
        return nonceKey;
    }

    /**
     * Sets the value of the nonceKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNonceKey(BigInteger value) {
        this.nonceKey = value;
    }

    /**
     * Gets the value of the nonceValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNonceValue() {
        return nonceValue;
    }

    /**
     * Sets the value of the nonceValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNonceValue(String value) {
        this.nonceValue = value;
    }

	/**
	 *
	 * @return api key (or ws id)
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 *
	 * @param apiKey api key or ws id
	 */
	public void setApiKey(final String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 *
	 * @return shared secret
	 */
	public String getSharedSecret() {
		return sharedSecret;
	}

	/**
	 *
	 * @param sharedSecret shared secret
	 */
	public void setSharedSecret(final String sharedSecret) {
		this.sharedSecret = sharedSecret;
	}
}
