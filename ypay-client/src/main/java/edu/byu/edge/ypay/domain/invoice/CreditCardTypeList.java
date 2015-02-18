
package edu.byu.edge.ypay.domain.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreditCardTypeList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditCardTypeList">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.byu.edu/payment/common}ListBaseType">
 *       &lt;sequence>
 *         &lt;element name="creditCard" type="{http://schemas.byu.edu/payment/payments}CreditCardType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditCardTypeList", namespace = "http://schemas.byu.edu/payment/payments", propOrder = {
    "creditCard"
})
public class CreditCardTypeList
    extends ListBaseType
{

    protected List<CreditCardType> creditCard;

    /**
     * Gets the value of the creditCard property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the creditCard property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCreditCard().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CreditCardType }
     * 
     * 
     */
    public List<CreditCardType> getCreditCard() {
        if (creditCard == null) {
            creditCard = new ArrayList<CreditCardType>();
        }
        return this.creditCard;
    }

}
