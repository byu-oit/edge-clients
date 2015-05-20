
package edu.byu.edge.ypay.v1.domain.system;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *         <p>Java class for ProcessorProfileListType complex type.
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;complexType name="ProcessorProfileListType">
 * &lt;complexContent>
 * &lt;extension base="{http://schemas.byu.edu/payment/common}ListBaseType">
 * &lt;sequence>
 * &lt;element ref="{http://schemas.byu.edu/payment/system}profile" maxOccurs="unbounded" minOccurs="0"/>
 * &lt;/sequence>
 * &lt;/extension>
 * &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *       
 * 
 * <p>Java class for ProcessorProfileListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProcessorProfileListType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.byu.edu/payment/common}ListBaseType">
 *       &lt;sequence>
 *         &lt;element name="profile" type="{http://schemas.byu.edu/payment/system}ProcessorProfileType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessorProfileListType", propOrder = {
    "profile"
})
public class ProcessorProfileListType
    extends ListBaseType
{

    protected List<ProcessorProfileType> profile;

    /**
     * Gets the value of the profile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the profile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProfile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProcessorProfileType }
     * 
     * 
     */
    public List<ProcessorProfileType> getProfile() {
        if (profile == null) {
            profile = new ArrayList<ProcessorProfileType>();
        }
        return this.profile;
    }

}
