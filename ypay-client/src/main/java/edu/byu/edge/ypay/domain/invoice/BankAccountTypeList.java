
package edu.byu.edge.ypay.domain.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BankAccountTypeList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BankAccountTypeList">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.byu.edu/payment/common}ListBaseType">
 *       &lt;sequence>
 *         &lt;element name="bankAccount" type="{http://schemas.byu.edu/payment/payments}BankAccountType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankAccountTypeList", namespace = "http://schemas.byu.edu/payment/payments", propOrder = {
    "bankAccount"
})
public class BankAccountTypeList
    extends ListBaseType
{

    protected List<BankAccountType> bankAccount;

    /**
     * Gets the value of the bankAccount property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bankAccount property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBankAccount().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BankAccountType }
     * 
     * 
     */
    public List<BankAccountType> getBankAccount() {
        if (bankAccount == null) {
            bankAccount = new ArrayList<BankAccountType>();
        }
        return this.bankAccount;
    }

}
