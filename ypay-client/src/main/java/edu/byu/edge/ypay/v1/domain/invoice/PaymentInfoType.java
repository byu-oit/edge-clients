
package edu.byu.edge.ypay.v1.domain.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PaymentInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="transactionId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="paidWith" type="{http://schemas.byu.edu/payment/invoice}PaidWithType"/>
 *         &lt;element name="errors" type="{http://schemas.byu.edu/payment/invoice}PaymentErrorListType"/>
 *         &lt;element name="dateStartedPayment" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dateFinishedPayment" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dateAgreedToTerms" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="paidBy" type="{http://schemas.byu.edu/payment/common}personId"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentInfoType", propOrder = {
    "transactionId",
    "paidWith",
    "errors",
    "dateStartedPayment",
    "dateFinishedPayment",
    "dateAgreedToTerms",
    "paidBy"
})
public class PaymentInfoType {

    @XmlElement(required = true)
    protected String transactionId;
    @XmlElement(required = true)
    protected PaidWithType paidWith;
    @XmlElement(required = true)
    protected PaymentErrorListType errors;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateStartedPayment;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateFinishedPayment;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateAgreedToTerms;
    @XmlElement(required = true)
    protected String paidBy;

    /**
     * Gets the value of the transactionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the value of the transactionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionId(String value) {
        this.transactionId = value;
    }

    /**
     * Gets the value of the paidWith property.
     * 
     * @return
     *     possible object is
     *     {@link PaidWithType }
     *     
     */
    public PaidWithType getPaidWith() {
        return paidWith;
    }

    /**
     * Sets the value of the paidWith property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaidWithType }
     *     
     */
    public void setPaidWith(PaidWithType value) {
        this.paidWith = value;
    }

    /**
     * Gets the value of the errors property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentErrorListType }
     *     
     */
    public PaymentErrorListType getErrors() {
        return errors;
    }

    /**
     * Sets the value of the errors property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentErrorListType }
     *     
     */
    public void setErrors(PaymentErrorListType value) {
        this.errors = value;
    }

    /**
     * Gets the value of the dateStartedPayment property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateStartedPayment() {
        return dateStartedPayment;
    }

    /**
     * Sets the value of the dateStartedPayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateStartedPayment(XMLGregorianCalendar value) {
        this.dateStartedPayment = value;
    }

    /**
     * Gets the value of the dateFinishedPayment property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateFinishedPayment() {
        return dateFinishedPayment;
    }

    /**
     * Sets the value of the dateFinishedPayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateFinishedPayment(XMLGregorianCalendar value) {
        this.dateFinishedPayment = value;
    }

    /**
     * Gets the value of the dateAgreedToTerms property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateAgreedToTerms() {
        return dateAgreedToTerms;
    }

    /**
     * Sets the value of the dateAgreedToTerms property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateAgreedToTerms(XMLGregorianCalendar value) {
        this.dateAgreedToTerms = value;
    }

    /**
     * Gets the value of the paidBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaidBy() {
        return paidBy;
    }

    /**
     * Sets the value of the paidBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaidBy(String value) {
        this.paidBy = value;
    }

}
