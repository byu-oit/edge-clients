
package edu.byu.edge.ypay.v1.domain.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				A Bank Account
 * 			
 * 
 * <p>Java class for BankAccountType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BankAccountType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.byu.edu/payment/payments}BankAccountBaseType">
 *       &lt;sequence>
 *         &lt;element name="bankAccountId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="owner" type="{http://schemas.byu.edu/payment/common}personId"/>
 *         &lt;element name="bank" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mungedAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="accountNumberLastFour" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="disabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="reasonDisabled" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankAccountType", namespace = "http://schemas.byu.edu/payment/payments", propOrder = {
    "bankAccountId",
    "owner",
    "bank",
    "mungedAccountNumber",
    "accountNumberLastFour",
    "disabled",
    "reasonDisabled"
})
public class BankAccountType
    extends BankAccountBaseType
{

    @XmlElement(required = true)
    protected String bankAccountId;
    @XmlElement(required = true, nillable = true)
    protected String owner;
    @XmlElement(required = true)
    protected String bank;
    @XmlElement(required = true)
    protected String mungedAccountNumber;
    @XmlElement(required = true)
    protected String accountNumberLastFour;
    protected boolean disabled;
    @XmlElement(required = true)
    protected String reasonDisabled;

    /**
     * Gets the value of the bankAccountId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAccountId() {
        return bankAccountId;
    }

    /**
     * Sets the value of the bankAccountId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankAccountId(String value) {
        this.bankAccountId = value;
    }

    /**
     * Gets the value of the owner property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the value of the owner property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwner(String value) {
        this.owner = value;
    }

    /**
     * Gets the value of the bank property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBank() {
        return bank;
    }

    /**
     * Sets the value of the bank property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBank(String value) {
        this.bank = value;
    }

    /**
     * Gets the value of the mungedAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMungedAccountNumber() {
        return mungedAccountNumber;
    }

    /**
     * Sets the value of the mungedAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMungedAccountNumber(String value) {
        this.mungedAccountNumber = value;
    }

    /**
     * Gets the value of the accountNumberLastFour property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountNumberLastFour() {
        return accountNumberLastFour;
    }

    /**
     * Sets the value of the accountNumberLastFour property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountNumberLastFour(String value) {
        this.accountNumberLastFour = value;
    }

    /**
     * Gets the value of the disabled property.
     * 
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Sets the value of the disabled property.
     * 
     */
    public void setDisabled(boolean value) {
        this.disabled = value;
    }

    /**
     * Gets the value of the reasonDisabled property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonDisabled() {
        return reasonDisabled;
    }

    /**
     * Sets the value of the reasonDisabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonDisabled(String value) {
        this.reasonDisabled = value;
    }

}
