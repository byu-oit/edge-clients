
package edu.byu.edge.ypay.v1.domain.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Holds the value of the payment Id used on the invoice as well as the
 * 				payment type.
 * 			
 * 
 * <p>Java class for PaymentSourceIdType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentSourceIdType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PaymentIdUsed" type="{http://schemas.byu.edu/payment/common}uuid"/>
 *         &lt;element name="PaymentSourceUsed" type="{http://schemas.byu.edu/payment/invoice}PaymentSourceUsedType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentSourceIdType", propOrder = {
    "paymentIdUsed",
    "paymentSourceUsed"
})
public class PaymentSourceIdType {

    @XmlElement(name = "PaymentIdUsed", required = true)
    protected String paymentIdUsed;
    @XmlElement(name = "PaymentSourceUsed", required = true)
    protected PaymentSourceUsedType paymentSourceUsed;

    /**
     * Gets the value of the paymentIdUsed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentIdUsed() {
        return paymentIdUsed;
    }

    /**
     * Sets the value of the paymentIdUsed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentIdUsed(String value) {
        this.paymentIdUsed = value;
    }

    /**
     * Gets the value of the paymentSourceUsed property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentSourceUsedType }
     *     
     */
    public PaymentSourceUsedType getPaymentSourceUsed() {
        return paymentSourceUsed;
    }

    /**
     * Sets the value of the paymentSourceUsed property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentSourceUsedType }
     *     
     */
    public void setPaymentSourceUsed(PaymentSourceUsedType value) {
        this.paymentSourceUsed = value;
    }

}
