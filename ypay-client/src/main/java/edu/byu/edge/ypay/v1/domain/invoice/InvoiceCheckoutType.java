
package edu.byu.edge.ypay.v1.domain.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceCheckoutType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceCheckoutType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="owner" type="{http://schemas.byu.edu/payment/common}personId"/>
 *         &lt;element name="returnUrl" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="clientSystemId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="clientSystemName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lineItems" type="{http://schemas.byu.edu/payment/invoice}LineItemListType"/>
 *         &lt;element name="paymentSourceAllowed" type="{http://schemas.byu.edu/payment/invoice}PaymentSourceAllowedType"/>
 *         &lt;element name="creditCardTerms" type="{http://schemas.byu.edu/payment/invoice}TermsAndConditions"/>
 *         &lt;element name="bankAccountTerms" type="{http://schemas.byu.edu/payment/invoice}TermsAndConditions"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceCheckoutType", propOrder = {
    "owner",
    "returnUrl",
    "clientSystemId",
    "clientSystemName",
    "lineItems",
    "paymentSourceAllowed",
    "creditCardTerms",
    "bankAccountTerms"
})
public class InvoiceCheckoutType {

    @XmlElement(required = true)
    protected String owner;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String returnUrl;
    @XmlElement(required = true)
    protected String clientSystemId;
    @XmlElement(required = true)
    protected String clientSystemName;
    @XmlElement(required = true)
    protected LineItemListType lineItems;
    @XmlElement(required = true)
    protected PaymentSourceAllowedType paymentSourceAllowed;
    @XmlElement(required = true)
    protected TermsAndConditions creditCardTerms;
    @XmlElement(required = true)
    protected TermsAndConditions bankAccountTerms;

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
     * Gets the value of the returnUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnUrl() {
        return returnUrl;
    }

    /**
     * Sets the value of the returnUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnUrl(String value) {
        this.returnUrl = value;
    }

    /**
     * Gets the value of the clientSystemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientSystemId() {
        return clientSystemId;
    }

    /**
     * Sets the value of the clientSystemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientSystemId(String value) {
        this.clientSystemId = value;
    }

    /**
     * Gets the value of the clientSystemName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientSystemName() {
        return clientSystemName;
    }

    /**
     * Sets the value of the clientSystemName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientSystemName(String value) {
        this.clientSystemName = value;
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
     * Gets the value of the creditCardTerms property.
     * 
     * @return
     *     possible object is
     *     {@link TermsAndConditions }
     *     
     */
    public TermsAndConditions getCreditCardTerms() {
        return creditCardTerms;
    }

    /**
     * Sets the value of the creditCardTerms property.
     * 
     * @param value
     *     allowed object is
     *     {@link TermsAndConditions }
     *     
     */
    public void setCreditCardTerms(TermsAndConditions value) {
        this.creditCardTerms = value;
    }

    /**
     * Gets the value of the bankAccountTerms property.
     * 
     * @return
     *     possible object is
     *     {@link TermsAndConditions }
     *     
     */
    public TermsAndConditions getBankAccountTerms() {
        return bankAccountTerms;
    }

    /**
     * Sets the value of the bankAccountTerms property.
     * 
     * @param value
     *     allowed object is
     *     {@link TermsAndConditions }
     *     
     */
    public void setBankAccountTerms(TermsAndConditions value) {
        this.bankAccountTerms = value;
    }

}
