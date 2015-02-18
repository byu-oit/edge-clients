
package edu.byu.edge.ypay.domain.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ReconciliationSummaryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReconciliationSummaryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dateReconciled" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="reconciledIn" type="{http://schemas.byu.edu/payment/invoice}BatchFileSummary"/>
 *         &lt;element name="returnedIn" type="{http://schemas.byu.edu/payment/invoice}BatchFileSummary"/>
 *         &lt;element name="returnReconciledInFile" type="{http://schemas.byu.edu/payment/invoice}BatchFileSummary"/>
 *         &lt;element name="returnTransactionId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReconciliationSummaryType", propOrder = {
    "dateReconciled",
    "reconciledIn",
    "returnedIn",
    "returnReconciledInFile",
    "returnTransactionId"
})
public class ReconciliationSummaryType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateReconciled;
    @XmlElement(required = true)
    protected BatchFileSummary reconciledIn;
    @XmlElement(required = true)
    protected BatchFileSummary returnedIn;
    @XmlElement(required = true)
    protected BatchFileSummary returnReconciledInFile;
    @XmlElement(required = true)
    protected String returnTransactionId;

    /**
     * Gets the value of the dateReconciled property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateReconciled() {
        return dateReconciled;
    }

    /**
     * Sets the value of the dateReconciled property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateReconciled(XMLGregorianCalendar value) {
        this.dateReconciled = value;
    }

    /**
     * Gets the value of the reconciledIn property.
     * 
     * @return
     *     possible object is
     *     {@link BatchFileSummary }
     *     
     */
    public BatchFileSummary getReconciledIn() {
        return reconciledIn;
    }

    /**
     * Sets the value of the reconciledIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link BatchFileSummary }
     *     
     */
    public void setReconciledIn(BatchFileSummary value) {
        this.reconciledIn = value;
    }

    /**
     * Gets the value of the returnedIn property.
     * 
     * @return
     *     possible object is
     *     {@link BatchFileSummary }
     *     
     */
    public BatchFileSummary getReturnedIn() {
        return returnedIn;
    }

    /**
     * Sets the value of the returnedIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link BatchFileSummary }
     *     
     */
    public void setReturnedIn(BatchFileSummary value) {
        this.returnedIn = value;
    }

    /**
     * Gets the value of the returnReconciledInFile property.
     * 
     * @return
     *     possible object is
     *     {@link BatchFileSummary }
     *     
     */
    public BatchFileSummary getReturnReconciledInFile() {
        return returnReconciledInFile;
    }

    /**
     * Sets the value of the returnReconciledInFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link BatchFileSummary }
     *     
     */
    public void setReturnReconciledInFile(BatchFileSummary value) {
        this.returnReconciledInFile = value;
    }

    /**
     * Gets the value of the returnTransactionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnTransactionId() {
        return returnTransactionId;
    }

    /**
     * Sets the value of the returnTransactionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnTransactionId(String value) {
        this.returnTransactionId = value;
    }

}
