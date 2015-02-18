
package edu.byu.edge.ypay.domain.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExpirationDateType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExpirationDateType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="year" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="month" use="required" type="{http://schemas.byu.edu/payment/payments}MonthType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExpirationDateType", namespace = "http://schemas.byu.edu/payment/payments")
public class ExpirationDateType {

    @XmlAttribute(name = "year", required = true)
    protected int year;
    @XmlAttribute(name = "month", required = true)
    protected MonthType month;

    /**
     * Gets the value of the year property.
     * 
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the value of the year property.
     * 
     */
    public void setYear(int value) {
        this.year = value;
    }

    /**
     * Gets the value of the month property.
     * 
     * @return
     *     possible object is
     *     {@link MonthType }
     *     
     */
    public MonthType getMonth() {
        return month;
    }

    /**
     * Sets the value of the month property.
     * 
     * @param value
     *     allowed object is
     *     {@link MonthType }
     *     
     */
    public void setMonth(MonthType value) {
        this.month = value;
    }

}
