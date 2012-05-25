
package edu.byu.edge.client.pro.domain.personSummary;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for student_informationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="student_informationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="student_role" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="year_term" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="credit_hours" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="classes" type="{uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi}classesType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "student_informationType", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", propOrder = {
    "studentRole",
    "yearTerm",
    "creditHours",
    "classes"
})
public class StudentInformationType {

    @XmlElement(name = "student_role", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected String studentRole;
    @XmlElement(name = "year_term", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected String yearTerm;
    @XmlElement(name = "credit_hours", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected String creditHours;
    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected ClassesType classes;

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
     * Gets the value of the yearTerm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYearTerm() {
        return yearTerm;
    }

    /**
     * Sets the value of the yearTerm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYearTerm(String value) {
        this.yearTerm = value;
    }

    /**
     * Gets the value of the creditHours property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditHours() {
        return creditHours;
    }

    /**
     * Sets the value of the creditHours property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditHours(String value) {
        this.creditHours = value;
    }

    /**
     * Gets the value of the classes property.
     * 
     * @return
     *     possible object is
     *     {@link ClassesType }
     *     
     */
    public ClassesType getClasses() {
        return classes;
    }

    /**
     * Sets the value of the classes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassesType }
     *     
     */
    public void setClasses(ClassesType value) {
        this.classes = value;
    }

}
