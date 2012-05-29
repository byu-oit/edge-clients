
package edu.byu.edge.client.pro.domain.personSummary;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for identifiersType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="identifiersType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="person_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="byu_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="byu_id_issue_number" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="id_card_status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="net_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ssn" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="2" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "identifiersType", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", propOrder = {
    "personId",
    "byuId",
    "byuIdIssueNumber",
    "idCardStatus",
    "netId",
    "ssn"
})
public class IdentifiersType {

    @XmlElement(name = "person_id", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected String personId;
    @XmlElement(name = "byu_id", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected String byuId;
    @XmlElement(name = "byu_id_issue_number", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected String byuIdIssueNumber;
    @XmlElement(name = "id_card_status", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String idCardStatus;
    @XmlElement(name = "net_id", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String netId;
    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected List<String> ssn;

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
     * Gets the value of the byuId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getByuId() {
        return byuId;
    }

    /**
     * Sets the value of the byuId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setByuId(String value) {
        this.byuId = value;
    }

    /**
     * Gets the value of the byuIdIssueNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getByuIdIssueNumber() {
        return byuIdIssueNumber;
    }

    /**
     * Sets the value of the byuIdIssueNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setByuIdIssueNumber(String value) {
        this.byuIdIssueNumber = value;
    }

    /**
     * Gets the value of the idCardStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdCardStatus() {
        return idCardStatus;
    }

    /**
     * Sets the value of the idCardStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdCardStatus(String value) {
        this.idCardStatus = value;
    }

    /**
     * Gets the value of the netId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNetId() {
        return netId;
    }

    /**
     * Sets the value of the netId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNetId(String value) {
        this.netId = value;
    }

    /**
     * Gets the value of the ssn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ssn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSsn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSsn() {
        if (ssn == null) {
            ssn = new ArrayList<String>();
        }
        return this.ssn;
    }

}
