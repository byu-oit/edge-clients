
package edu.byu.edge.ypay.domain.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				A Bank Account Base Type.
 * 			
 * 
 * <p>Java class for BankAccountBaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BankAccountBaseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nickname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="accountType" type="{http://schemas.byu.edu/payment/payments}BankAccountTypeType"/>
 *         &lt;element name="useInProfile" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankAccountBaseType", namespace = "http://schemas.byu.edu/payment/payments", propOrder = {
    "nickname",
    "accountType",
    "useInProfile"
})
@XmlSeeAlso({
    BankAccountRequestType.class,
    BankAccountType.class
})
public class BankAccountBaseType {

    @XmlElement(required = true)
    protected String nickname;
    @XmlElement(required = true)
    protected BankAccountTypeType accountType;
    protected boolean useInProfile;

    /**
     * Gets the value of the nickname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the value of the nickname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNickname(String value) {
        this.nickname = value;
    }

    /**
     * Gets the value of the accountType property.
     * 
     * @return
     *     possible object is
     *     {@link BankAccountTypeType }
     *     
     */
    public BankAccountTypeType getAccountType() {
        return accountType;
    }

    /**
     * Sets the value of the accountType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAccountTypeType }
     *     
     */
    public void setAccountType(BankAccountTypeType value) {
        this.accountType = value;
    }

    /**
     * Gets the value of the useInProfile property.
     * 
     */
    public boolean isUseInProfile() {
        return useInProfile;
    }

    /**
     * Sets the value of the useInProfile property.
     * 
     */
    public void setUseInProfile(boolean value) {
        this.useInProfile = value;
    }

}
