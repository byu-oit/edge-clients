
package edu.byu.edge.ypay.domain.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceBaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceBaseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="owner" type="{http://schemas.byu.edu/payment/common}personId"/>
 *         &lt;element name="paymentSourceAllowed" type="{http://schemas.byu.edu/payment/invoice}PaymentSourceAllowedType"/>
 *         &lt;element name="lineItems" type="{http://schemas.byu.edu/payment/invoice}LineItemListType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceBaseType", propOrder = {
    "owner",
    "paymentSourceAllowed",
    "lineItems"
})
@XmlSeeAlso({
    InvoiceRequestType.class,
    InvoiceType.class
})
public class InvoiceBaseType {

    @XmlElement(required = true)
    protected String owner;
    @XmlElement(required = true)
    protected PaymentSourceAllowedType paymentSourceAllowed;
    @XmlElement(required = true)
    protected LineItemListType lineItems;

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
     * Gets the value of the paymentSourceAllowed property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentSourceAllowedType }
     *     
     */
    public PaymentSourceAllowedType getPaymentSourceAllowed() {
        return paymentSourceAllowed;
    }

    /**
     * Sets the value of the paymentSourceAllowed property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentSourceAllowedType }
     *     
     */
    public void setPaymentSourceAllowed(PaymentSourceAllowedType value) {
        this.paymentSourceAllowed = value;
    }

    /**
     * Gets the value of the lineItems property.
     * 
     * @return
     *     possible object is
     *     {@link LineItemListType }
     *     
     */
    public LineItemListType getLineItems() {
        return lineItems;
    }

    /**
     * Sets the value of the lineItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link LineItemListType }
     *     
     */
    public void setLineItems(LineItemListType value) {
        this.lineItems = value;
    }

}
