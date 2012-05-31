
package edu.byu.edge.client.pro.domain.personSummary;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;


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
 *         &lt;element name="expired" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="expires_in_30_days" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
public class VisaType implements Serializable {

	private static final long serialVersionUID = 100L;

	@XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected Boolean expired;

    @XmlElement(name = "expires_in_30_days", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected Boolean expiresIn30Days;

    /**
     * Gets the value of the expired property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getExpired() {
        return expired;
    }

    /**
     * Sets the value of the expired property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExpired(Boolean value) {
        this.expired = value;
    }

    /**
     * Gets the value of the expiresIn30Days property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getExpiresIn30Days() {
        return expiresIn30Days;
    }

    /**
     * Sets the value of the expiresIn30Days property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExpiresIn30Days(Boolean value) {
        this.expiresIn30Days = value;
    }

}
