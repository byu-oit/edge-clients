
package edu.byu.edge.ypay.v1.domain.invoice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentSourceUsedType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PaymentSourceUsedType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="BANK_ACCOUNT"/&gt;
 *     &lt;enumeration value="CREDIT_CARD"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "PaymentSourceUsedType")
@XmlEnum
public enum PaymentSourceUsedType {


    /**
     * 
     * 						Bank Account was used to pay for invoice.
     * 					
     * 
     */
    BANK_ACCOUNT,

    /**
     * 
     * 						A Credit Card was used to pay for the invoice.
     * 					
     * 
     */
    CREDIT_CARD;

    public String value() {
        return name();
    }

    public static PaymentSourceUsedType fromValue(String v) {
        return valueOf(v);
    }

}
