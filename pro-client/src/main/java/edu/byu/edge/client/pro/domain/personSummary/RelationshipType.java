
package edu.byu.edge.client.pro.domain.personSummary;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;


/**
 * <p>Java class for relationshipType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="relationshipType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="person_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deceased" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="employee" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hired" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="years_of_service" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="student" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="affiliation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "relationshipType", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", propOrder = {
    "type",
    "personId",
    "name",
    "deceased",
    "employee",
    "hired",
    "yearsOfService",
    "student",
    "affiliation"
})
public class RelationshipType implements Serializable {

	private static final long serialVersionUID = 100L;

	@XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String type;

    @XmlElement(name = "person_id", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String personId;

    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String name;

    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected Boolean deceased;

    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String employee;

    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar hired;

    @XmlElement(name = "years_of_service", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String yearsOfService;

    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String student;

    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String affiliation;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
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
     * Gets the value of the deceased property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getDeceased() {
        return deceased;
    }

    /**
     * Sets the value of the deceased property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDeceased(Boolean value) {
        this.deceased = value;
    }

    /**
     * Gets the value of the employee property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmployee() {
        return employee;
    }

    /**
     * Sets the value of the employee property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmployee(String value) {
        this.employee = value;
    }

    /**
     * Gets the value of the hired property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getHired() {
        return hired;
    }

    /**
     * Sets the value of the hired property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setHired(XMLGregorianCalendar value) {
        this.hired = value;
    }

    /**
     * Gets the value of the yearsOfService property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYearsOfService() {
        return yearsOfService;
    }

    /**
     * Sets the value of the yearsOfService property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYearsOfService(String value) {
        this.yearsOfService = value;
    }

    /**
     * Gets the value of the student property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStudent() {
        return student;
    }

    /**
     * Sets the value of the student property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStudent(String value) {
        this.student = value;
    }

    /**
     * Gets the value of the affiliation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAffiliation() {
        return affiliation;
    }

    /**
     * Sets the value of the affiliation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAffiliation(String value) {
        this.affiliation = value;
    }

}
