
package edu.byu.edge.ypay.domain.invoice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InvoiceStatusType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="InvoiceStatusType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CREATED"/>
 *     &lt;enumeration value="PROCESSING"/>
 *     &lt;enumeration value="PAID"/>
 *     &lt;enumeration value="CANCELLED"/>
 *     &lt;enumeration value="REJECTED"/>
 *     &lt;enumeration value="REVERSED"/>
 *     &lt;enumeration value="RETURNED"/>
 *     &lt;enumeration value="NEEDS_ADMIN"/>
 *     &lt;enumeration value="SUBMITTED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "InvoiceStatusType")
@XmlEnum
public enum InvoiceStatusType {


    /**
     * 
     * 						The invoice has been created, but not accessed.
     * 					
     * 
     */
    CREATED,

    /**
     * 
     * 						The invoice has been accessed, but has not been processed yet.
     * 					
     * 
     */
    PROCESSING,

    /**
     * 
     * 						The invoice has been paid.
     * 					
     * 
     */
    PAID,

    /**
     * 
     * 						The user cancelled payment processing.
     * 					
     * 
     */
    CANCELLED,

    /**
     * 
     * 						The payment was rejected by CASHNet.
     * 					
     * 
     */
    REJECTED,

    /**
     * 
     * 						The payment was successfully completed, but then reversed.
     * 					
     * 
     */
    REVERSED,

    /**
     * 
     * 						The payment was successfully submitted to CASHNet, but then rejected/returned by the bank.
     * 					
     * 
     */
    RETURNED,

    /**
     * 
     * 						The payment requires manual resolution by an administrator
     * 					
     * 
     */
    NEEDS_ADMIN,

    /**
     * 
     * 						The payment has been submitted, but a response has not yet been received.
     * 					
     * 
     */
    SUBMITTED;

    public String value() {
        return name();
    }

    public static InvoiceStatusType fromValue(String v) {
        return valueOf(v);
    }

}
