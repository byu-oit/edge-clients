
package edu.byu.edge.ypay.v1.domain.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * 
 * 				A PaymentError Object which is created when a payment fails.
 * 			
 * 
 * <p>Java class for PaymentErrorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentErrorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="paymentErrorId" type="{http://schemas.byu.edu/payment/common}uuid"/>
 *         &lt;element name="dateFinished" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="errorCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="invoiceId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="paymentSourceId" type="{http://schemas.byu.edu/payment/invoice}PaymentSourceIdType"/>
 *         &lt;element name="paidWith" type="{http://schemas.byu.edu/payment/invoice}PaidWithType"/>
 *         &lt;element name="creditCardError" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentErrorType", propOrder = {
    "paymentErrorId",
    "dateFinished",
    "errorCode",
    "errorMessage",
    "invoiceId",
    "paymentSourceId",
    "paidWith",
    "creditCardError"
})
public class PaymentErrorType {

    @XmlElement(required = true)
    protected String paymentErrorId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateFinished;
    protected int errorCode;
    @XmlElement(required = true)
    protected String errorMessage;
    protected long invoiceId;
    @XmlElement(required = true, nillable = true)
    protected PaymentSourceIdType paymentSourceId;
    @XmlElement(required = true, nillable = true)
    protected PaidWithType paidWith;
    protected boolean creditCardError;

    /**
     * Gets the value of the paymentErrorId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentErrorId() {
        return paymentErrorId;
    }

    /**
     * Sets the value of the paymentErrorId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentErrorId(String value) {
        this.paymentErrorId = value;
    }

    /**
     * Gets the value of the dateFinished property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateFinished() {
        return dateFinished;
    }

    /**
     * Sets the value of the dateFinished property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateFinished(XMLGregorianCalendar value) {
        this.dateFinished = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     */
    public void setErrorCode(int value) {
        this.errorCode = value;
    }

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    /**
     * Gets the value of the invoiceId property.
     * 
     */
    public long getInvoiceId() {
        return invoiceId;
    }

    /**
     * Sets the value of the invoiceId property.
     * 
     */
    public void setInvoiceId(long value) {
        this.invoiceId = value;
    }

    /**
     * Gets the value of the paymentSourceId property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentSourceIdType }
     *     
     */
    public PaymentSourceIdType getPaymentSourceId() {
        return paymentSourceId;
    }

    /**
     * Sets the value of the paymentSourceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentSourceIdType }
     *     
     */
    public void setPaymentSourceId(PaymentSourceIdType value) {
        this.paymentSourceId = value;
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
     * Gets the value of the creditCardError property.
     * 
     */
    public boolean isCreditCardError() {
        return creditCardError;
    }

    /**
     * Sets the value of the creditCardError property.
     * 
     */
    public void setCreditCardError(boolean value) {
        this.creditCardError = value;
    }

}
