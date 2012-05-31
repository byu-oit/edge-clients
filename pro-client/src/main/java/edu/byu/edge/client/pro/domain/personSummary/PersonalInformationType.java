
package edu.byu.edge.client.pro.domain.personSummary;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for personal_informationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="personal_informationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="date_of_birth" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="age" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="ethnicity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="gender" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="marital_status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="citizenship" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="visa" type="{uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi}visaType" maxOccurs="2" minOccurs="0"/>
 *         &lt;element name="home_town" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="religion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deceased" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="date_of_death" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="restricted_record" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "personal_informationType", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", propOrder = {
    "dateOfBirth",
    "age",
    "ethnicity",
    "gender",
    "maritalStatus",
    "citizenship",
    "visa",
    "homeTown",
    "religion",
    "deceased",
    "dateOfDeath",
    "restrictedRecord"
})
public class PersonalInformationType implements Serializable {

	private static final long serialVersionUID = 100L;

	@XmlElement(name = "date_of_birth", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateOfBirth;

    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected BigInteger age;

    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String ethnicity;

    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String gender;

    @XmlElement(name = "marital_status", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String maritalStatus;

    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String citizenship;

    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected List<VisaType> visa;

    @XmlElement(name = "home_town", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String homeTown;

    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String religion;

    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected Boolean deceased;

    @XmlElement(name = "date_of_death", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateOfDeath;

    @XmlElement(name = "restricted_record", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected Boolean restrictedRecord;

    /**
     * Gets the value of the dateOfBirth property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the value of the dateOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateOfBirth(XMLGregorianCalendar value) {
        this.dateOfBirth = value;
    }

    /**
     * Gets the value of the age property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAge() {
        return age;
    }

    /**
     * Sets the value of the age property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAge(BigInteger value) {
        this.age = value;
    }

    /**
     * Gets the value of the ethnicity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEthnicity() {
        return ethnicity;
    }

    /**
     * Sets the value of the ethnicity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEthnicity(String value) {
        this.ethnicity = value;
    }

    /**
     * Gets the value of the gender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGender(String value) {
        this.gender = value;
    }

    /**
     * Gets the value of the maritalStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Sets the value of the maritalStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaritalStatus(String value) {
        this.maritalStatus = value;
    }

    /**
     * Gets the value of the citizenship property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitizenship() {
        return citizenship;
    }

    /**
     * Sets the value of the citizenship property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitizenship(String value) {
        this.citizenship = value;
    }

    /**
     * Gets the value of the visa property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the visa property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVisa().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VisaType }
     * 
     * 
     */
    public List<VisaType> getVisa() {
        if (visa == null) {
            visa = new ArrayList<VisaType>();
        }
        return this.visa;
    }

    /**
     * Gets the value of the homeTown property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHomeTown() {
        return homeTown;
    }

    /**
     * Sets the value of the homeTown property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHomeTown(String value) {
        this.homeTown = value;
    }

    /**
     * Gets the value of the religion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReligion() {
        return religion;
    }

    /**
     * Sets the value of the religion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReligion(String value) {
        this.religion = value;
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
     * Gets the value of the dateOfDeath property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateOfDeath() {
        return dateOfDeath;
    }

    /**
     * Sets the value of the dateOfDeath property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateOfDeath(XMLGregorianCalendar value) {
        this.dateOfDeath = value;
    }

    /**
     * Gets the value of the restrictedRecord property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getRestrictedRecord() {
        return restrictedRecord;
    }

    /**
     * Sets the value of the restrictedRecord property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRestrictedRecord(Boolean value) {
        this.restrictedRecord = value;
    }

}
