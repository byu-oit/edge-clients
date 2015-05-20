
package edu.byu.edge.ypay.v1.domain.payments;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 A Credit Card Base Type.
 *             
 * 
 * <p>Java class for CreditCardBaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditCardBaseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nickname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nameOnCard" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="billingAddress" type="{http://schemas.byu.edu/payment/profile}InternationalOrUSAddressType"/>
 *         &lt;element name="expirationDate" type="{http://schemas.byu.edu/payment/payments}ExpirationDateType"/>
 *         &lt;element name="useInProfile" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditCardBaseType", propOrder = {
    "nickname",
    "nameOnCard",
    "billingAddress",
    "expirationDate",
    "useInProfile"
})
@XmlSeeAlso({
    CreditCardType.class,
    CreditCardRequestType.class
})
public class CreditCardBaseType {

    @XmlElement(required = true, nillable = true)
    protected String nickname;
    @XmlElement(required = true)
    protected String nameOnCard;
    @XmlElement(required = true)
    protected InternationalOrUSAddressType billingAddress;
    @XmlElement(required = true)
    protected ExpirationDateType expirationDate;
    protected boolean useInProfile;

    /**
     * Gets the value of the nickname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the value of the nickname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNickname(String value) {
        this.nickname = value;
    }

    /**
     * Gets the value of the nameOnCard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameOnCard() {
        return nameOnCard;
    }

    /**
     * Sets the value of the nameOnCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameOnCard(String value) {
        this.nameOnCard = value;
    }

    /**
     * Gets the value of the billingAddress property.
     * 
     * @return
     *     possible object is
     *     {@link InternationalOrUSAddressType }
     *     
     */
    public InternationalOrUSAddressType getBillingAddress() {
        return billingAddress;
    }

    /**
     * Sets the value of the billingAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link InternationalOrUSAddressType }
     *     
     */
    public void setBillingAddress(InternationalOrUSAddressType value) {
        this.billingAddress = value;
    }

    /**
     * Gets the value of the expirationDate property.
     * 
     * @return
     *     possible object is
     *     {@link ExpirationDateType }
     *     
     */
    public ExpirationDateType getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the value of the expirationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpirationDateType }
     *     
     */
    public void setExpirationDate(ExpirationDateType value) {
        this.expirationDate = value;
    }

    /**
     * Gets the value of the useInProfile property.
     * 
     */
    public boolean isUseInProfile() {
        return useInProfile;
    }

    /**
     * Sets the value of the useInProfile property.
     * 
     */
    public void setUseInProfile(boolean value) {
        this.useInProfile = value;
    }

}
