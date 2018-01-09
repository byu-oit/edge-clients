
package edu.byu.edge.ypay.v1.domain.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Gives errors for the invoice.
 * 			
 * 
 * <p>Java class for PaymentErrorListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentErrorListType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.byu.edu/payment/common}ListBaseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="error" type="{http://schemas.byu.edu/payment/invoice}PaymentErrorType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentErrorListType", propOrder = {
    "error"
})
public class PaymentErrorListType
    extends ListBaseType
{

    protected List<PaymentErrorType> error;

    /**
     * Gets the value of the error property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the error property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getError().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentErrorType }
     * 
     * 
     */
    public List<PaymentErrorType> getError() {
        if (error == null) {
            error = new ArrayList<PaymentErrorType>();
        }
        return this.error;
    }

}
