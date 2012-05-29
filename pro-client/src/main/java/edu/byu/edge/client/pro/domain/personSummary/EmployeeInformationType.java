
package edu.byu.edge.client.pro.domain.personSummary;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for employee_informationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="employee_informationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="employee_role" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="department" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="job_title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="date_hired" type="{uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi}date_hiredType" minOccurs="0"/>
 *         &lt;element name="termination_date" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="retirement_date" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="secondary_role" type="{uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi}secondary_roleType" minOccurs="0"/>
 *         &lt;element name="university_affiliation" type="{uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi}university_affiliationType" minOccurs="0"/>
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
@XmlType(name = "employee_informationType", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", propOrder = {
    "employeeRole",
    "department",
    "jobTitle",
    "dateHired",
    "terminationDate",
    "retirementDate",
    "secondaryRole",
    "universityAffiliation",
    "restrictedRecord"
})
public class EmployeeInformationType {

    @XmlElement(name = "employee_role", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String employeeRole;
    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String department;
    @XmlElement(name = "job_title", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String jobTitle;
    @XmlElement(name = "date_hired", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected DateHiredType dateHired;
    @XmlElement(name = "termination_date", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String terminationDate;
    @XmlElement(name = "retirement_date", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar retirementDate;
    @XmlElement(name = "secondary_role", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected SecondaryRoleType secondaryRole;
    @XmlElement(name = "university_affiliation", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected UniversityAffiliationType universityAffiliation;
    @XmlElement(name = "restricted_record", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected Boolean restrictedRecord;

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
     * Gets the value of the department property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the value of the department property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartment(String value) {
        this.department = value;
    }

    /**
     * Gets the value of the jobTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Sets the value of the jobTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobTitle(String value) {
        this.jobTitle = value;
    }

    /**
     * Gets the value of the dateHired property.
     * 
     * @return
     *     possible object is
     *     {@link DateHiredType }
     *     
     */
    public DateHiredType getDateHired() {
        return dateHired;
    }

    /**
     * Sets the value of the dateHired property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateHiredType }
     *     
     */
    public void setDateHired(DateHiredType value) {
        this.dateHired = value;
    }

    /**
     * Gets the value of the terminationDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTerminationDate() {
        return terminationDate;
    }

    /**
     * Sets the value of the terminationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTerminationDate(String value) {
        this.terminationDate = value;
    }

    /**
     * Gets the value of the retirementDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRetirementDate() {
        return retirementDate;
    }

    /**
     * Sets the value of the retirementDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRetirementDate(XMLGregorianCalendar value) {
        this.retirementDate = value;
    }

    /**
     * Gets the value of the secondaryRole property.
     * 
     * @return
     *     possible object is
     *     {@link SecondaryRoleType }
     *     
     */
    public SecondaryRoleType getSecondaryRole() {
        return secondaryRole;
    }

    /**
     * Sets the value of the secondaryRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecondaryRoleType }
     *     
     */
    public void setSecondaryRole(SecondaryRoleType value) {
        this.secondaryRole = value;
    }

    /**
     * Gets the value of the universityAffiliation property.
     * 
     * @return
     *     possible object is
     *     {@link UniversityAffiliationType }
     *     
     */
    public UniversityAffiliationType getUniversityAffiliation() {
        return universityAffiliation;
    }

    /**
     * Sets the value of the universityAffiliation property.
     * 
     * @param value
     *     allowed object is
     *     {@link UniversityAffiliationType }
     *     
     */
    public void setUniversityAffiliation(UniversityAffiliationType value) {
        this.universityAffiliation = value;
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
