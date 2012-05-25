
package edu.byu.edge.client.pro.domain.personSummary;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="suffix" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="net_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="byu_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="person_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="date_of_birth" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="gender" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="student_role" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="employee_role" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="academic_record" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="is_employee" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="non_person_organization" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="restricted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="deceased" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="merge_pending" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="new_byu_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
public class PersonSummaryLineType {

    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected String email;
    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected String name;
    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected String suffix;
    @XmlElement(name = "net_id", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected String netId;
    @XmlElement(name = "byu_id", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected String byuId;
    @XmlElement(name = "person_id", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected String personId;
    @XmlElement(name = "date_of_birth", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateOfBirth;
    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected String gender;
    @XmlElement(name = "student_role", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected String studentRole;
    @XmlElement(name = "employee_role", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected String employeeRole;
    @XmlElement(name = "academic_record", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected boolean academicRecord;
    @XmlElement(name = "is_employee", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected boolean isEmployee;
    @XmlElement(name = "non_person_organization", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected boolean nonPersonOrganization;
    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected boolean restricted;
    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected boolean deceased;
    @XmlElement(name = "merge_pending", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected boolean mergePending;
    @XmlElement(name = "new_byu_id", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
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
     */
    public boolean isAcademicRecord() {
        return academicRecord;
    }

    /**
     * Sets the value of the academicRecord property.
     * 
     */
    public void setAcademicRecord(boolean value) {
        this.academicRecord = value;
    }

    /**
     * Gets the value of the isEmployee property.
     * 
     */
    public boolean isIsEmployee() {
        return isEmployee;
    }

    /**
     * Sets the value of the isEmployee property.
     * 
     */
    public void setIsEmployee(boolean value) {
        this.isEmployee = value;
    }

    /**
     * Gets the value of the nonPersonOrganization property.
     * 
     */
    public boolean isNonPersonOrganization() {
        return nonPersonOrganization;
    }

    /**
     * Sets the value of the nonPersonOrganization property.
     * 
     */
    public void setNonPersonOrganization(boolean value) {
        this.nonPersonOrganization = value;
    }

    /**
     * Gets the value of the restricted property.
     * 
     */
    public boolean isRestricted() {
        return restricted;
    }

    /**
     * Sets the value of the restricted property.
     * 
     */
    public void setRestricted(boolean value) {
        this.restricted = value;
    }

    /**
     * Gets the value of the deceased property.
     * 
     */
    public boolean isDeceased() {
        return deceased;
    }

    /**
     * Sets the value of the deceased property.
     * 
     */
    public void setDeceased(boolean value) {
        this.deceased = value;
    }

    /**
     * Gets the value of the mergePending property.
     * 
     */
    public boolean isMergePending() {
        return mergePending;
    }

    /**
     * Sets the value of the mergePending property.
     * 
     */
    public void setMergePending(boolean value) {
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
