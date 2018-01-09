
package edu.byu.edge.ypay.v1.domain.system;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *         <p>Java class for CashnetInfoType complex type.
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;complexType name="CashnetInfoType"&gt;
 * &lt;complexContent&gt;
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 * &lt;sequence&gt;
 * &lt;element name="operator" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * &lt;element name="station" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * &lt;element name="merchant" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * &lt;element name="customerCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * &lt;element name="itemCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * &lt;element name="virtualDirectory" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * &lt;/sequence&gt;
 * &lt;/restriction&gt;
 * &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *       
 * 
 * <p>Java class for CashnetInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CashnetInfoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="operator" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="station" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="merchant" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="customerCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="itemCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="virtualDirectory" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CashnetInfoType", propOrder = {
    "operator",
    "password",
    "station",
    "merchant",
    "customerCode",
    "itemCode",
    "virtualDirectory"
})
public class CashnetInfoType {

    @XmlElement(required = true)
    protected String operator;
    @XmlElement(required = true)
    protected String password;
    @XmlElement(required = true)
    protected String station;
    @XmlElement(required = true)
    protected String merchant;
    @XmlElement(required = true)
    protected String customerCode;
    @XmlElement(required = true)
    protected String itemCode;
    @XmlElement(required = true)
    protected String virtualDirectory;

    /**
     * Gets the value of the operator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Sets the value of the operator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperator(String value) {
        this.operator = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the station property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStation() {
        return station;
    }

    /**
     * Sets the value of the station property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStation(String value) {
        this.station = value;
    }

    /**
     * Gets the value of the merchant property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMerchant() {
        return merchant;
    }

    /**
     * Sets the value of the merchant property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMerchant(String value) {
        this.merchant = value;
    }

    /**
     * Gets the value of the customerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerCode() {
        return customerCode;
    }

    /**
     * Sets the value of the customerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerCode(String value) {
        this.customerCode = value;
    }

    /**
     * Gets the value of the itemCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * Sets the value of the itemCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemCode(String value) {
        this.itemCode = value;
    }

    /**
     * Gets the value of the virtualDirectory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVirtualDirectory() {
        return virtualDirectory;
    }

    /**
     * Sets the value of the virtualDirectory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVirtualDirectory(String value) {
        this.virtualDirectory = value;
    }

}
