
package edu.byu.edge.ypay.v1.domain.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ReconciliationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReconciliationType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dateReconciled" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="reconciledIn" type="{http://schemas.byu.edu/payment/invoice}BatchFileType"/&gt;
 *         &lt;element name="returnedIn" type="{http://schemas.byu.edu/payment/invoice}BatchFileType"/&gt;
 *         &lt;element name="returnReconciledInFile" type="{http://schemas.byu.edu/payment/invoice}BatchFileType"/&gt;
 *         &lt;element name="returnTransactionId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReconciliationType", propOrder = {
    "dateReconciled",
    "reconciledIn",
    "returnedIn",
    "returnReconciledInFile",
    "returnTransactionId"
})
public class ReconciliationType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateReconciled;
    @XmlElement(required = true)
    protected BatchFileType reconciledIn;
    @XmlElement(required = true)
    protected BatchFileType returnedIn;
    @XmlElement(required = true)
    protected BatchFileType returnReconciledInFile;
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
     *     {@link BatchFileType }
     *     
     */
    public BatchFileType getReconciledIn() {
        return reconciledIn;
    }

    /**
     * Sets the value of the reconciledIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link BatchFileType }
     *     
     */
    public void setReconciledIn(BatchFileType value) {
        this.reconciledIn = value;
    }

    /**
     * Gets the value of the returnedIn property.
     * 
     * @return
     *     possible object is
     *     {@link BatchFileType }
     *     
     */
    public BatchFileType getReturnedIn() {
        return returnedIn;
    }

    /**
     * Sets the value of the returnedIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link BatchFileType }
     *     
     */
    public void setReturnedIn(BatchFileType value) {
        this.returnedIn = value;
    }

    /**
     * Gets the value of the returnReconciledInFile property.
     * 
     * @return
     *     possible object is
     *     {@link BatchFileType }
     *     
     */
    public BatchFileType getReturnReconciledInFile() {
        return returnReconciledInFile;
    }

    /**
     * Sets the value of the returnReconciledInFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link BatchFileType }
     *     
     */
    public void setReturnReconciledInFile(BatchFileType value) {
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
