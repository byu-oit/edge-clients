
package edu.byu.edge.ypay.domain.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				A Credit Card.
 * 			
 * 
 * <p>Java class for CreditCardType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditCardType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.byu.edu/payment/payments}CreditCardBaseType">
 *       &lt;sequence>
 *         &lt;element name="creditCardId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="owner" type="{http://schemas.byu.edu/payment/common}personId"/>
 *         &lt;element name="mungedCardNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cardNumberLastFour" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="creditCardType" type="{http://schemas.byu.edu/payment/payments}CreditCardTypeType"/>
 *         &lt;element name="disabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="reasonDisabled" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditCardType", namespace = "http://schemas.byu.edu/payment/payments", propOrder = {
    "creditCardId",
    "owner",
    "mungedCardNumber",
    "cardNumberLastFour",
    "creditCardType",
    "disabled",
    "reasonDisabled"
})
public class CreditCardType
    extends CreditCardBaseType
{

    @XmlElement(required = true, nillable = true)
    protected String creditCardId;
    @XmlElement(required = true, nillable = true)
    protected String owner;
    @XmlElement(required = true)
    protected String mungedCardNumber;
    @XmlElement(required = true)
    protected String cardNumberLastFour;
    @XmlElement(required = true)
    protected CreditCardTypeType creditCardType;
    protected boolean disabled;
    @XmlElement(required = true)
    protected String reasonDisabled;

    /**
     * Gets the value of the creditCardId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditCardId() {
        return creditCardId;
    }

    /**
     * Sets the value of the creditCardId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditCardId(String value) {
        this.creditCardId = value;
    }

    /**
     * Gets the value of the owner property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the value of the owner property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwner(String value) {
        this.owner = value;
    }

    /**
     * Gets the value of the mungedCardNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMungedCardNumber() {
        return mungedCardNumber;
    }

    /**
     * Sets the value of the mungedCardNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMungedCardNumber(String value) {
        this.mungedCardNumber = value;
    }

    /**
     * Gets the value of the cardNumberLastFour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardNumberLastFour() {
        return cardNumberLastFour;
    }

    /**
     * Sets the value of the cardNumberLastFour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardNumberLastFour(String value) {
        this.cardNumberLastFour = value;
    }

    /**
     * Gets the value of the creditCardType property.
     * 
     * @return
     *     possible object is
     *     {@link CreditCardTypeType }
     *     
     */
    public CreditCardTypeType getCreditCardType() {
        return creditCardType;
    }

    /**
     * Sets the value of the creditCardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditCardTypeType }
     *     
     */
    public void setCreditCardType(CreditCardTypeType value) {
        this.creditCardType = value;
    }

    /**
     * Gets the value of the disabled property.
     * 
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Sets the value of the disabled property.
     * 
     */
    public void setDisabled(boolean value) {
        this.disabled = value;
    }

    /**
     * Gets the value of the reasonDisabled property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonDisabled() {
        return reasonDisabled;
    }

    /**
     * Sets the value of the reasonDisabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonDisabled(String value) {
        this.reasonDisabled = value;
    }

}
