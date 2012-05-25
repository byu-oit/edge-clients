
package edu.byu.edge.client.pro.domain.personSummary;

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
 *         &lt;element name="person_summary_line" type="{uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi}person_summary_lineType"/>
 *         &lt;element name="identifiers" type="{uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi}identifiersType"/>
 *         &lt;element name="names" type="{uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi}namesType"/>
 *         &lt;element name="personal_information" type="{uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi}personal_informationType"/>
 *         &lt;element name="employee_information" type="{uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi}employee_informationType"/>
 *         &lt;element name="contact_information" type="{uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi}contact_informationType"/>
 *         &lt;element name="student_information" type="{uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi}student_informationType"/>
 *         &lt;element name="relationships" type="{uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi}relationshipsType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "responseType", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", propOrder = {
    "personSummaryLine",
    "identifiers",
    "names",
    "personalInformation",
    "employeeInformation",
    "contactInformation",
    "studentInformation",
    "relationships"
})
public class ResponseType {

    @XmlElement(name = "person_summary_line", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected PersonSummaryLineType personSummaryLine;
    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected IdentifiersType identifiers;
    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected NamesType names;
    @XmlElement(name = "personal_information", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected PersonalInformationType personalInformation;
    @XmlElement(name = "employee_information", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected EmployeeInformationType employeeInformation;
    @XmlElement(name = "contact_information", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected ContactInformationType contactInformation;
    @XmlElement(name = "student_information", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected StudentInformationType studentInformation;
    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected RelationshipsType relationships;

    /**
     * Gets the value of the personSummaryLine property.
     * 
     * @return
     *     possible object is
     *     {@link PersonSummaryLineType }
     *     
     */
    public PersonSummaryLineType getPersonSummaryLine() {
        return personSummaryLine;
    }

    /**
     * Sets the value of the personSummaryLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonSummaryLineType }
     *     
     */
    public void setPersonSummaryLine(PersonSummaryLineType value) {
        this.personSummaryLine = value;
    }

    /**
     * Gets the value of the identifiers property.
     * 
     * @return
     *     possible object is
     *     {@link IdentifiersType }
     *     
     */
    public IdentifiersType getIdentifiers() {
        return identifiers;
    }

    /**
     * Sets the value of the identifiers property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentifiersType }
     *     
     */
    public void setIdentifiers(IdentifiersType value) {
        this.identifiers = value;
    }

    /**
     * Gets the value of the names property.
     * 
     * @return
     *     possible object is
     *     {@link NamesType }
     *     
     */
    public NamesType getNames() {
        return names;
    }

    /**
     * Sets the value of the names property.
     * 
     * @param value
     *     allowed object is
     *     {@link NamesType }
     *     
     */
    public void setNames(NamesType value) {
        this.names = value;
    }

    /**
     * Gets the value of the personalInformation property.
     * 
     * @return
     *     possible object is
     *     {@link PersonalInformationType }
     *     
     */
    public PersonalInformationType getPersonalInformation() {
        return personalInformation;
    }

    /**
     * Sets the value of the personalInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonalInformationType }
     *     
     */
    public void setPersonalInformation(PersonalInformationType value) {
        this.personalInformation = value;
    }

    /**
     * Gets the value of the employeeInformation property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeInformationType }
     *     
     */
    public EmployeeInformationType getEmployeeInformation() {
        return employeeInformation;
    }

    /**
     * Sets the value of the employeeInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeInformationType }
     *     
     */
    public void setEmployeeInformation(EmployeeInformationType value) {
        this.employeeInformation = value;
    }

    /**
     * Gets the value of the contactInformation property.
     * 
     * @return
     *     possible object is
     *     {@link ContactInformationType }
     *     
     */
    public ContactInformationType getContactInformation() {
        return contactInformation;
    }

    /**
     * Sets the value of the contactInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactInformationType }
     *     
     */
    public void setContactInformation(ContactInformationType value) {
        this.contactInformation = value;
    }

    /**
     * Gets the value of the studentInformation property.
     * 
     * @return
     *     possible object is
     *     {@link StudentInformationType }
     *     
     */
    public StudentInformationType getStudentInformation() {
        return studentInformation;
    }

    /**
     * Sets the value of the studentInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudentInformationType }
     *     
     */
    public void setStudentInformation(StudentInformationType value) {
        this.studentInformation = value;
    }

    /**
     * Gets the value of the relationships property.
     * 
     * @return
     *     possible object is
     *     {@link RelationshipsType }
     *     
     */
    public RelationshipsType getRelationships() {
        return relationships;
    }

    /**
     * Sets the value of the relationships property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelationshipsType }
     *     
     */
    public void setRelationships(RelationshipsType value) {
        this.relationships = value;
    }

}
