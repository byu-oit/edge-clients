
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
 *         &lt;element name="mailing_address" type="{uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi}mailing_addressType"/>
 *         &lt;element name="mailing_address_unlisted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="phone_number" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mailing_phone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mailing_phone_unlisted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="work_address" type="{uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi}work_addressType"/>
 *         &lt;element name="work_address_unlisted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="work_phone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="work_phone_unlisted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="email_address" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="email_address_unlisted" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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

	/** */
    @XmlElement(name = "mailing_address", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected MailingAddressType mailingAddress;

	/** */
    @XmlElement(name = "mailing_address_unlisted", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected boolean mailingAddressUnlisted;

	/** */
    @XmlElement(name = "phone_number", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected String phoneNumber;

	/** */
    @XmlElement(name = "mailing_phone", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected String mailingPhone;

	/** */
    @XmlElement(name = "mailing_phone_unlisted", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected boolean mailingPhoneUnlisted;

	/** */
    @XmlElement(name = "work_address", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected WorkAddressType workAddress;

	/** */
    @XmlElement(name = "work_address_unlisted", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected boolean workAddressUnlisted;

	/** */
    @XmlElement(name = "work_phone", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected String workPhone;

	/** */
    @XmlElement(name = "work_phone_unlisted", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected boolean workPhoneUnlisted;

	/** */
    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected String email;

	/** */
    @XmlElement(name = "email_address", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi", required = true)
    protected String emailAddress;

	/** */
    @XmlElement(name = "email_address_unlisted", namespace = "uri://byu/c/ry/ae/prod/person_new/cgi/personSummary.cgi")
    protected boolean emailAddressUnlisted;

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
     */
    public boolean isMailingAddressUnlisted() {
        return mailingAddressUnlisted;
    }

    /**
     * Sets the value of the mailingAddressUnlisted property.
     * 
     */
    public void setMailingAddressUnlisted(boolean value) {
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
     */
    public boolean isMailingPhoneUnlisted() {
        return mailingPhoneUnlisted;
    }

    /**
     * Sets the value of the mailingPhoneUnlisted property.
     * 
     */
    public void setMailingPhoneUnlisted(boolean value) {
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
     */
    public boolean isWorkAddressUnlisted() {
        return workAddressUnlisted;
    }

    /**
     * Sets the value of the workAddressUnlisted property.
     * 
     */
    public void setWorkAddressUnlisted(boolean value) {
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
     */
    public boolean isWorkPhoneUnlisted() {
        return workPhoneUnlisted;
    }

    /**
     * Sets the value of the workPhoneUnlisted property.
     * 
     */
    public void setWorkPhoneUnlisted(boolean value) {
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
     */
    public boolean isEmailAddressUnlisted() {
        return emailAddressUnlisted;
    }

    /**
     * Sets the value of the emailAddressUnlisted property.
     * 
     */
    public void setEmailAddressUnlisted(boolean value) {
        this.emailAddressUnlisted = value;
    }

}
