
package edu.byu.edge.ypay.v1.domain.invoice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreditCardTypeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CreditCardTypeType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="VISA"/&gt;
 *     &lt;enumeration value="DISCOVER"/&gt;
 *     &lt;enumeration value="MASTER_CARD"/&gt;
 *     &lt;enumeration value="AMERICAN_EXPRESS"/&gt;
 *     &lt;enumeration value="UNKNOWN_CARD"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "CreditCardTypeType", namespace = "http://schemas.byu.edu/payment/payments")
@XmlEnum
public enum CreditCardTypeType {

    VISA,
    DISCOVER,
    MASTER_CARD,
    AMERICAN_EXPRESS,
    UNKNOWN_CARD;

    public String value() {
        return name();
    }

    public static CreditCardTypeType fromValue(String v) {
        return valueOf(v);
    }

}
