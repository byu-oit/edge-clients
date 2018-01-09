
package edu.byu.edge.ypay.v1.domain.system;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *         <p>Java class for ProcessorProfileType complex type.
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;complexType name="ProcessorProfileType"&gt;
 * &lt;complexContent&gt;
 * &lt;extension base="{http://schemas.byu.edu/payment/system}ProcessorProfileRequestType"&gt;
 * &lt;sequence&gt;
 * &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * &lt;element name="clientSystemId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 * &lt;/sequence&gt;
 * &lt;/extension&gt;
 * &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *       
 * 
 * <p>Java class for ProcessorProfileType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProcessorProfileType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.byu.edu/payment/system}ProcessorProfileRequestType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="clientSystemId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessorProfileType", propOrder = {
    "id",
    "clientSystemId"
})
public class ProcessorProfileType
    extends ProcessorProfileRequestType
{

    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected String clientSystemId;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the clientSystemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientSystemId() {
        return clientSystemId;
    }

    /**
     * Sets the value of the clientSystemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientSystemId(String value) {
        this.clientSystemId = value;
    }

}
