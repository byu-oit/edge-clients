
package edu.byu.edge.ypay.v1.domain.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 
 * 			
 * 
 * <p>Java class for BillingAddressListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BillingAddressListType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.byu.edu/payment/common}ListBaseType"&gt;
 *       &lt;choice maxOccurs="unbounded" minOccurs="0"&gt;
 *         &lt;element name="usAddress" type="{http://schemas.byu.edu/payment/profile}USBillingAddressType"/&gt;
 *         &lt;element name="internationalAddress" type="{http://schemas.byu.edu/payment/profile}InternationalBillingAddressType"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BillingAddressListType", namespace = "http://schemas.byu.edu/payment/profile", propOrder = {
    "usAddressOrInternationalAddress"
})
public class BillingAddressListType
    extends ListBaseType
{

    @XmlElements({
        @XmlElement(name = "usAddress", type = USBillingAddressType.class),
        @XmlElement(name = "internationalAddress", type = InternationalBillingAddressType.class)
    })
    protected List<BillingAddressType> usAddressOrInternationalAddress;

    /**
     * Gets the value of the usAddressOrInternationalAddress property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the usAddressOrInternationalAddress property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUsAddressOrInternationalAddress().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link USBillingAddressType }
     * {@link InternationalBillingAddressType }
     * 
     * 
     */
    public List<BillingAddressType> getUsAddressOrInternationalAddress() {
        if (usAddressOrInternationalAddress == null) {
            usAddressOrInternationalAddress = new ArrayList<BillingAddressType>();
        }
        return this.usAddressOrInternationalAddress;
    }

}
