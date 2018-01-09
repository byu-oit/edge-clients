
package edu.byu.edge.ypay.v1.domain.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PaymentRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="personId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="bankAccountId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *           &lt;element name="creditCardId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="userAgreedToTerms" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="uuid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="encrpytedCVVCode" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentRequestType", namespace = "http://schemas.byu.edu/payment/payments", propOrder = {
    "personId",
    "bankAccountId",
    "creditCardId",
    "userAgreedToTerms",
    "uuid",
    "encrpytedCVVCode"
})
public class PaymentRequestType {

    @XmlElement(required = true)
    protected String personId;
    protected String bankAccountId;
    protected String creditCardId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar userAgreedToTerms;
    @XmlElement(required = true)
    protected String uuid;
    @XmlElement(required = true)
    protected byte[] encrpytedCVVCode;

    /**
     * Gets the value of the personId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonId() {
        return personId;
    }

    /**
     * Sets the value of the personId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonId(String value) {
        this.personId = value;
    }

    /**
     * Gets the value of the bankAccountId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAccountId() {
        return bankAccountId;
    }

    /**
     * Sets the value of the bankAccountId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankAccountId(String value) {
        this.bankAccountId = value;
    }

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
     * Gets the value of the userAgreedToTerms property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUserAgreedToTerms() {
        return userAgreedToTerms;
    }

    /**
     * Sets the value of the userAgreedToTerms property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUserAgreedToTerms(XMLGregorianCalendar value) {
        this.userAgreedToTerms = value;
    }

    /**
     * Gets the value of the uuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the value of the uuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUuid(String value) {
        this.uuid = value;
    }

    /**
     * Gets the value of the encrpytedCVVCode property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getEncrpytedCVVCode() {
        return encrpytedCVVCode;
    }

    /**
     * Sets the value of the encrpytedCVVCode property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setEncrpytedCVVCode(byte[] value) {
        this.encrpytedCVVCode = value;
    }

}
