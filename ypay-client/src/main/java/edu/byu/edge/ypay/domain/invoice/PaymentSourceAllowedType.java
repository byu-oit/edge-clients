
package edu.byu.edge.ypay.domain.invoice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentSourceAllowedType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PaymentSourceAllowedType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ALL"/>
 *     &lt;enumeration value="BANK_ACCOUNT"/>
 *     &lt;enumeration value="CREDIT_CARD"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PaymentSourceAllowedType")
@XmlEnum
public enum PaymentSourceAllowedType {


    /**
     * 
     * 						Only Credit Cards can be used to pay for this invoice.
     * 					
     * 
     */
    ALL,

    /**
     * 
     * 						Only Bank Accounts can be used to pay for this invoice.
     * 					
     * 
     */
    BANK_ACCOUNT,

    /**
     * 
     * 						Any payment type (credit card or bank account) can be used to pay for this invoice.
     * 					
     * 
     */
    CREDIT_CARD;

    public String value() {
        return name();
    }

    public static PaymentSourceAllowedType fromValue(String v) {
        return valueOf(v);
    }

}
