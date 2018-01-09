
package edu.byu.edge.ypay.v1.domain.system;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *         <p>Java class for ProcessorProfileRequestType complex type.
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;complexType name="ProcessorProfileRequestType"&gt;
 * &lt;complexContent&gt;
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 * &lt;sequence&gt;
 * &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * &lt;element name="doesAVS" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 * &lt;element name="doesCVV" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 * &lt;element name="whenToUse" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * &lt;element name="cashnetInfo" type="{http://schemas.byu.edu/payment/system}CashnetInfoType"/&gt;
 * &lt;/sequence&gt;
 * &lt;/restriction&gt;
 * &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *       
 * 
 * <p>Java class for ProcessorProfileRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProcessorProfileRequestType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="doesAVS" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="doesCVV" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="whenToUse" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="cashnetInfo" type="{http://schemas.byu.edu/payment/system}CashnetInfoType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessorProfileRequestType", propOrder = {
    "name",
    "doesAVS",
    "doesCVV",
    "whenToUse",
    "cashnetInfo"
})
@XmlSeeAlso({
    ProcessorProfileType.class
})
public class ProcessorProfileRequestType {

    @XmlElement(required = true)
    protected String name;
    protected boolean doesAVS;
    protected boolean doesCVV;
    @XmlElement(required = true)
    protected String whenToUse;
    @XmlElement(required = true)
    protected CashnetInfoType cashnetInfo;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the doesAVS property.
     * 
     */
    public boolean isDoesAVS() {
        return doesAVS;
    }

    /**
     * Sets the value of the doesAVS property.
     * 
     */
    public void setDoesAVS(boolean value) {
        this.doesAVS = value;
    }

    /**
     * Gets the value of the doesCVV property.
     * 
     */
    public boolean isDoesCVV() {
        return doesCVV;
    }

    /**
     * Sets the value of the doesCVV property.
     * 
     */
    public void setDoesCVV(boolean value) {
        this.doesCVV = value;
    }

    /**
     * Gets the value of the whenToUse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWhenToUse() {
        return whenToUse;
    }

    /**
     * Sets the value of the whenToUse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWhenToUse(String value) {
        this.whenToUse = value;
    }

    /**
     * Gets the value of the cashnetInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CashnetInfoType }
     *     
     */
    public CashnetInfoType getCashnetInfo() {
        return cashnetInfo;
    }

    /**
     * Sets the value of the cashnetInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashnetInfoType }
     *     
     */
    public void setCashnetInfo(CashnetInfoType value) {
        this.cashnetInfo = value;
    }

}
