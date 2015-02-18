
package edu.byu.edge.ypay.domain.profile;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				List of invoice filters
 * 			
 * 
 * <p>Java class for UIDataFilterListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UIDataFilterListType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.byu.edu/payment/common}ListBaseType">
 *       &lt;sequence>
 *         &lt;element name="filter" type="{http://schemas.byu.edu/payment/profile}UIDataFilterType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UIDataFilterListType", propOrder = {
    "filter"
})
public class UIDataFilterListType
    extends ListBaseType
{

    protected List<UIDataFilterType> filter;

    /**
     * Gets the value of the filter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the filter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFilter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UIDataFilterType }
     * 
     * 
     */
    public List<UIDataFilterType> getFilter() {
        if (filter == null) {
            filter = new ArrayList<UIDataFilterType>();
        }
        return this.filter;
    }

}
