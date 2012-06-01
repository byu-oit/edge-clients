
package edu.byu.edge.client.pro.codes.domain.proCodes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for responseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="responseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="domains" type="{uri://byu/c/ry/ae/prod/person/cgi/codeMaintenance.cgi}domainsType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "responseType", namespace = "uri://byu/c/ry/ae/prod/person/cgi/codeMaintenance.cgi", propOrder = {
    "domains"
})
public class ResponseType {

    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person/cgi/codeMaintenance.cgi", required = true)
    protected DomainsType domains;

    /**
     * Gets the value of the domains property.
     * 
     * @return
     *     possible object is
     *     {@link DomainsType }
     *     
     */
    public DomainsType getDomains() {
        return domains;
    }

    /**
     * Sets the value of the domains property.
     * 
     * @param value
     *     allowed object is
     *     {@link DomainsType }
     *     
     */
    public void setDomains(DomainsType value) {
        this.domains = value;
    }

}
