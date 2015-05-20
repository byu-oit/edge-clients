
package edu.byu.edge.ypay.v1.domain.system;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *         <p>Java class for SystemMessageListType complex type.
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;complexType name="SystemMessageListType">
 * &lt;complexContent>
 * &lt;extension base="{http://schemas.byu.edu/payment/common}ListBaseType">
 * &lt;sequence>
 * &lt;element name="message" type="{http://schemas.byu.edu/payment/system}SystemMessageType" maxOccurs="unbounded" minOccurs="0"/>
 * &lt;/sequence>
 * &lt;/extension>
 * &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *       
 * 
 * <p>Java class for SystemMessageListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SystemMessageListType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.byu.edu/payment/common}ListBaseType">
 *       &lt;sequence>
 *         &lt;element name="message" type="{http://schemas.byu.edu/payment/system}SystemMessageType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SystemMessageListType", propOrder = {
    "message"
})
public class SystemMessageListType
    extends ListBaseType
{

    protected List<SystemMessageType> message;

    /**
     * Gets the value of the message property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the message property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMessage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SystemMessageType }
     * 
     * 
     */
    public List<SystemMessageType> getMessage() {
        if (message == null) {
            message = new ArrayList<SystemMessageType>();
        }
        return this.message;
    }

}
