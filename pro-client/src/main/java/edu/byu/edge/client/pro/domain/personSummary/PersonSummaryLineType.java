
package edu.byu.edge.client.pro.domain.personSummary;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;


/**
 * <p>Java class for person_summary_lineType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="person_summary_lineType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="suffix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="net_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="byu_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="person_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="date_of_birth" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="gender" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="student_role" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="employee_role" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="academic_record" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="is_employee" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="non_person_organization" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="restricted" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="deceased" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="merge_pending" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="new_byu_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "person_summary_lineType", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", propOrder = {
    "email",
    "name",
    "suffix",
    "netId",
    "byuId",
    "personId",
    "dateOfBirth",
    "gender",
    "studentRole",
    "employeeRole",
    "academicRecord",
    "isEmployee",
    "nonPersonOrganization",
    "restricted",
    "deceased",
    "mergePending",
    "newByuId"
})
public class PersonSummaryLineType implements Serializable {

	private static final long serialVersionUID = 100L;

	@XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String email;

    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String name;

    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String suffix;

    @XmlElement(name = "net_id", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String netId;

    @XmlElement(name = "byu_id", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String byuId;

    @XmlElement(name = "person_id", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String personId;

    @XmlElement(name = "date_of_birth", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateOfBirth;

    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String gender;

    @XmlElement(name = "student_role", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String studentRole;

    @XmlElement(name = "employee_role", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String employeeRole;

    @XmlElement(name = "academic_record", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected Boolean academicRecord;

    @XmlElement(name = "is_employee", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected Boolean isEmployee;

    @XmlElement(name = "non_person_organization", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected Boolean nonPersonOrganization;

    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected Boolean restricted;

    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected Boolean deceased;

    @XmlElement(name = "merge_pending", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected Boolean mergePending;

    @XmlElement(name = "new_byu_id", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String newByuId;

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
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
     * Gets the value of the suffix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * Sets the value of the suffix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuffix(String value) {
        this.suffix = value;
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
     * Gets the value of the studentRole property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStudentRole() {
        return studentRole;
    }

    /**
     * Sets the value of the studentRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStudentRole(String value) {
        this.studentRole = value;
    }

    /**
     * Gets the value of the employeeRole property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmployeeRole() {
        return employeeRole;
    }

    /**
     * Sets the value of the employeeRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmployeeRole(String value) {
        this.employeeRole = value;
    }

    /**
     * Gets the value of the academicRecord property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getAcademicRecord() {
        return academicRecord;
    }

    /**
     * Sets the value of the academicRecord property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAcademicRecord(Boolean value) {
        this.academicRecord = value;
    }

    /**
     * Gets the value of the isEmployee property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIsEmployee() {
        return isEmployee;
    }

    /**
     * Sets the value of the isEmployee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsEmployee(Boolean value) {
        this.isEmployee = value;
    }

    /**
     * Gets the value of the nonPersonOrganization property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getNonPersonOrganization() {
        return nonPersonOrganization;
    }

    /**
     * Sets the value of the nonPersonOrganization property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNonPersonOrganization(Boolean value) {
        this.nonPersonOrganization = value;
    }

    /**
     * Gets the value of the restricted property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getRestricted() {
        return restricted;
    }

    /**
     * Sets the value of the restricted property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRestricted(Boolean value) {
        this.restricted = value;
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
     * Gets the value of the mergePending property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getMergePending() {
        return mergePending;
    }

    /**
     * Sets the value of the mergePending property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMergePending(Boolean value) {
        this.mergePending = value;
    }

    /**
     * Gets the value of the newByuId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewByuId() {
        return newByuId;
    }

    /**
     * Sets the value of the newByuId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewByuId(String value) {
        this.newByuId = value;
    }

}
