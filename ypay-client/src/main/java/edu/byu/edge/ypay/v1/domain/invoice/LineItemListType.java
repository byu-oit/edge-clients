
package edu.byu.edge.ypay.v1.domain.invoice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LineItemListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LineItemListType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.byu.edu/payment/common}ListBaseType">
 *       &lt;sequence>
 *         &lt;element name="lineItem" type="{http://schemas.byu.edu/payment/invoice}LineItemType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LineItemListType", propOrder = {
    "lineItem"
})
public class LineItemListType
    extends ListBaseType
{

    @XmlElement(required = true)
    protected List<LineItemType> lineItem;

    /**
     * Gets the value of the lineItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lineItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLineItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LineItemType }
     * 
     * 
     */
    public List<LineItemType> getLineItem() {
        if (lineItem == null) {
            lineItem = new ArrayList<LineItemType>();
        }
        return this.lineItem;
    }

}
