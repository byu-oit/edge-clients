
package edu.byu.edge.client.pro.domain.personSummary;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for contact_informationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="contact_informationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mailing_address" type="{uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi}mailing_addressType" minOccurs="0"/>
 *         &lt;element name="mailing_address_unlisted" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="phone_number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mailing_phone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mailing_phone_unlisted" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="work_address" type="{uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi}work_addressType" minOccurs="0"/>
 *         &lt;element name="work_address_unlisted" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="work_phone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="work_phone_unlisted" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email_address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email_address_unlisted" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "contact_informationType", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", propOrder = {
    "mailingAddress",
    "mailingAddressUnlisted",
    "phoneNumber",
    "mailingPhone",
    "mailingPhoneUnlisted",
    "workAddress",
    "workAddressUnlisted",
    "workPhone",
    "workPhoneUnlisted",
    "email",
    "emailAddress",
    "emailAddressUnlisted"
})
public class ContactInformationType {

    @XmlElement(name = "mailing_address", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected MailingAddressType mailingAddress;
    @XmlElement(name = "mailing_address_unlisted", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected Boolean mailingAddressUnlisted;
    @XmlElement(name = "phone_number", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String phoneNumber;
    @XmlElement(name = "mailing_phone", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String mailingPhone;
    @XmlElement(name = "mailing_phone_unlisted", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected Boolean mailingPhoneUnlisted;
    @XmlElement(name = "work_address", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected WorkAddressType workAddress;
    @XmlElement(name = "work_address_unlisted", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected Boolean workAddressUnlisted;
    @XmlElement(name = "work_phone", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String workPhone;
    @XmlElement(name = "work_phone_unlisted", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected Boolean workPhoneUnlisted;
    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String email;
    @XmlElement(name = "email_address", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected String emailAddress;
    @XmlElement(name = "email_address_unlisted", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected Boolean emailAddressUnlisted;

    /**
     * Gets the value of the mailingAddress property.
     * 
     * @return
     *     possible object is
     *     {@link MailingAddressType }
     *     
     */
    public MailingAddressType getMailingAddress() {
        return mailingAddress;
    }

    /**
     * Sets the value of the mailingAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link MailingAddressType }
     *     
     */
    public void setMailingAddress(MailingAddressType value) {
        this.mailingAddress = value;
    }

    /**
     * Gets the value of the mailingAddressUnlisted property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getMailingAddressUnlisted() {
        return mailingAddressUnlisted;
    }

    /**
     * Sets the value of the mailingAddressUnlisted property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMailingAddressUnlisted(Boolean value) {
        this.mailingAddressUnlisted = value;
    }

    /**
     * Gets the value of the phoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the value of the phoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneNumber(String value) {
        this.phoneNumber = value;
    }

    /**
     * Gets the value of the mailingPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailingPhone() {
        return mailingPhone;
    }

    /**
     * Sets the value of the mailingPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailingPhone(String value) {
        this.mailingPhone = value;
    }

    /**
     * Gets the value of the mailingPhoneUnlisted property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getMailingPhoneUnlisted() {
        return mailingPhoneUnlisted;
    }

    /**
     * Sets the value of the mailingPhoneUnlisted property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMailingPhoneUnlisted(Boolean value) {
        this.mailingPhoneUnlisted = value;
    }

    /**
     * Gets the value of the workAddress property.
     * 
     * @return
     *     possible object is
     *     {@link WorkAddressType }
     *     
     */
    public WorkAddressType getWorkAddress() {
        return workAddress;
    }

    /**
     * Sets the value of the workAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkAddressType }
     *     
     */
    public void setWorkAddress(WorkAddressType value) {
        this.workAddress = value;
    }

    /**
     * Gets the value of the workAddressUnlisted property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getWorkAddressUnlisted() {
        return workAddressUnlisted;
    }

    /**
     * Sets the value of the workAddressUnlisted property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setWorkAddressUnlisted(Boolean value) {
        this.workAddressUnlisted = value;
    }

    /**
     * Gets the value of the workPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkPhone() {
        return workPhone;
    }

    /**
     * Sets the value of the workPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkPhone(String value) {
        this.workPhone = value;
    }

    /**
     * Gets the value of the workPhoneUnlisted property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getWorkPhoneUnlisted() {
        return workPhoneUnlisted;
    }

    /**
     * Sets the value of the workPhoneUnlisted property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setWorkPhoneUnlisted(Boolean value) {
        this.workPhoneUnlisted = value;
    }

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
     * Gets the value of the emailAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the value of the emailAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailAddress(String value) {
        this.emailAddress = value;
    }

    /**
     * Gets the value of the emailAddressUnlisted property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getEmailAddressUnlisted() {
        return emailAddressUnlisted;
    }

    /**
     * Sets the value of the emailAddressUnlisted property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEmailAddressUnlisted(Boolean value) {
        this.emailAddressUnlisted = value;
    }

}
