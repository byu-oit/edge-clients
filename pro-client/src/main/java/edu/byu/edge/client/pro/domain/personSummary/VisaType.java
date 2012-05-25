
package edu.byu.edge.client.pro.domain.personSummary;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for visaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="visaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="expired" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="expires_in_30_days" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "visaType", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", propOrder = {
    "expired",
    "expiresIn30Days"
})
public class VisaType {

    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected boolean expired;
    @XmlElement(name = "expires_in_30_days", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected boolean expiresIn30Days;

    /**
     * Gets the value of the expired property.
     * 
     */
    public boolean isExpired() {
        return expired;
    }

    /**
     * Sets the value of the expired property.
     * 
     */
    public void setExpired(boolean value) {
        this.expired = value;
    }

    /**
     * Gets the value of the expiresIn30Days property.
     * 
     */
    public boolean isExpiresIn30Days() {
        return expiresIn30Days;
    }

    /**
     * Sets the value of the expiresIn30Days property.
     * 
     */
    public void setExpiresIn30Days(boolean value) {
        this.expiresIn30Days = value;
    }

}
