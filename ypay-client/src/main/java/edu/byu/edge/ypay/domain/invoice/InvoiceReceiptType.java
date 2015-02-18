
package edu.byu.edge.ypay.domain.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Wraps the receipt text for an invoice.
 * 			
 * 
 * <p>Java class for invoiceReceiptType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="invoiceReceiptType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="receiptText" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "invoiceReceiptType", propOrder = {
    "receiptText"
})
public class InvoiceReceiptType {

    @XmlElement(required = true)
    protected String receiptText;

    /**
     * Gets the value of the receiptText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiptText() {
        return receiptText;
    }

    /**
     * Sets the value of the receiptText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiptText(String value) {
        this.receiptText = value;
    }

}
