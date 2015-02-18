
package edu.byu.edge.ypay.domain.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Filters for admin data screens
 * 			
 * 
 * <p>Java class for UIDataFilterType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UIDataFilterType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="filterId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="filterName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="filterType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="personId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="filterData" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UIDataFilterType", namespace = "http://schemas.byu.edu/payment/profile", propOrder = {
    "filterId",
    "filterName",
    "filterType",
    "personId",
    "filterData"
})
public class UIDataFilterType {

    @XmlElement(required = true)
    protected String filterId;
    @XmlElement(required = true)
    protected String filterName;
    @XmlElement(required = true)
    protected String filterType;
    @XmlElement(required = true)
    protected String personId;
    @XmlElement(required = true)
    protected String filterData;

    /**
     * Gets the value of the filterId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilterId() {
        return filterId;
    }

    /**
     * Sets the value of the filterId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilterId(String value) {
        this.filterId = value;
    }

    /**
     * Gets the value of the filterName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilterName() {
        return filterName;
    }

    /**
     * Sets the value of the filterName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilterName(String value) {
        this.filterName = value;
    }

    /**
     * Gets the value of the filterType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilterType() {
        return filterType;
    }

    /**
     * Sets the value of the filterType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilterType(String value) {
        this.filterType = value;
    }

    /**
     * Gets the value of the personId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonId() {
        return personId;
    }

    /**
     * Sets the value of the personId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonId(String value) {
        this.personId = value;
    }

    /**
     * Gets the value of the filterData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilterData() {
        return filterData;
    }

    /**
     * Sets the value of the filterData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilterData(String value) {
        this.filterData = value;
    }

}
