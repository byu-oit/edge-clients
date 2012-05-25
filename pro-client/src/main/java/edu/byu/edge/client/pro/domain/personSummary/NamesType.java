
package edu.byu.edge.client.pro.domain.personSummary;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for namesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="namesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="preferred_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="complete_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "namesType", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", propOrder = {
    "preferredName",
    "completeName"
})
public class NamesType {

    @XmlElement(name = "preferred_name", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected String preferredName;
    @XmlElement(name = "complete_name", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected String completeName;

    /**
     * Gets the value of the preferredName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferredName() {
        return preferredName;
    }

    /**
     * Sets the value of the preferredName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferredName(String value) {
        this.preferredName = value;
    }

    /**
     * Gets the value of the completeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompleteName() {
        return completeName;
    }

    /**
     * Sets the value of the completeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompleteName(String value) {
        this.completeName = value;
    }

}
