
package edu.byu.edge.ypay.domain.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for BatchFileType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BatchFileType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fileId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fileDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="processingStarted" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="processingFinished" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="fileType" type="{http://schemas.byu.edu/payment/invoice}BatchFileTypeType"/>
 *         &lt;element name="processedBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="successfulFinish" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="fileName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="attributes" type="{http://schemas.byu.edu/payment/invoice}BatchFileTypeAttribute" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BatchFileType", propOrder = {
    "fileId",
    "fileDate",
    "processingStarted",
    "processingFinished",
    "fileType",
    "processedBy",
    "successfulFinish",
    "fileName",
    "attributes"
})
public class BatchFileType {

    @XmlElement(required = true)
    protected String fileId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fileDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar processingStarted;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar processingFinished;
    @XmlElement(required = true)
    protected BatchFileTypeType fileType;
    @XmlElement(required = true)
    protected String processedBy;
    protected boolean successfulFinish;
    @XmlElement(required = true)
    protected String fileName;
    protected List<BatchFileTypeAttribute> attributes;

    /**
     * Gets the value of the fileId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * Sets the value of the fileId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileId(String value) {
        this.fileId = value;
    }

    /**
     * Gets the value of the fileDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFileDate() {
        return fileDate;
    }

    /**
     * Sets the value of the fileDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFileDate(XMLGregorianCalendar value) {
        this.fileDate = value;
    }

    /**
     * Gets the value of the processingStarted property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getProcessingStarted() {
        return processingStarted;
    }

    /**
     * Sets the value of the processingStarted property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setProcessingStarted(XMLGregorianCalendar value) {
        this.processingStarted = value;
    }

    /**
     * Gets the value of the processingFinished property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getProcessingFinished() {
        return processingFinished;
    }

    /**
     * Sets the value of the processingFinished property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setProcessingFinished(XMLGregorianCalendar value) {
        this.processingFinished = value;
    }

    /**
     * Gets the value of the fileType property.
     * 
     * @return
     *     possible object is
     *     {@link BatchFileTypeType }
     *     
     */
    public BatchFileTypeType getFileType() {
        return fileType;
    }

    /**
     * Sets the value of the fileType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BatchFileTypeType }
     *     
     */
    public void setFileType(BatchFileTypeType value) {
        this.fileType = value;
    }

    /**
     * Gets the value of the processedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessedBy() {
        return processedBy;
    }

    /**
     * Sets the value of the processedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessedBy(String value) {
        this.processedBy = value;
    }

    /**
     * Gets the value of the successfulFinish property.
     * 
     */
    public boolean isSuccessfulFinish() {
        return successfulFinish;
    }

    /**
     * Sets the value of the successfulFinish property.
     * 
     */
    public void setSuccessfulFinish(boolean value) {
        this.successfulFinish = value;
    }

    /**
     * Gets the value of the fileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the value of the fileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileName(String value) {
        this.fileName = value;
    }

    /**
     * Gets the value of the attributes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BatchFileTypeAttribute }
     * 
     * 
     */
    public List<BatchFileTypeAttribute> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<BatchFileTypeAttribute>();
        }
        return this.attributes;
    }

}
