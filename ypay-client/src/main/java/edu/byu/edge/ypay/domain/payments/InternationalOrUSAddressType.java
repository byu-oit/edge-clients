
package edu.byu.edge.ypay.domain.payments;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InternationalOrUSAddressType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InternationalOrUSAddressType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="usAddress" type="{http://schemas.byu.edu/payment/profile}USBillingAddressType"/>
 *         &lt;element name="internationalAddress" type="{http://schemas.byu.edu/payment/profile}InternationalBillingAddressType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InternationalOrUSAddressType", namespace = "http://schemas.byu.edu/payment/profile", propOrder = {
    "usAddress",
    "internationalAddress"
})
public class InternationalOrUSAddressType {

    protected USBillingAddressType usAddress;
    protected InternationalBillingAddressType internationalAddress;

    /**
     * Gets the value of the usAddress property.
     * 
     * @return
     *     possible object is
     *     {@link USBillingAddressType }
     *     
     */
    public USBillingAddressType getUsAddress() {
        return usAddress;
    }

    /**
     * Sets the value of the usAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link USBillingAddressType }
     *     
     */
    public void setUsAddress(USBillingAddressType value) {
        this.usAddress = value;
    }

    /**
     * Gets the value of the internationalAddress property.
     * 
     * @return
     *     possible object is
     *     {@link InternationalBillingAddressType }
     *     
     */
    public InternationalBillingAddressType getInternationalAddress() {
        return internationalAddress;
    }

    /**
     * Sets the value of the internationalAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link InternationalBillingAddressType }
     *     
     */
    public void setInternationalAddress(InternationalBillingAddressType value) {
        this.internationalAddress = value;
    }

}
