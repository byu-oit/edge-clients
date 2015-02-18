
package edu.byu.edge.ypay.domain.payments;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreditCardTypeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CreditCardTypeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="VISA"/>
 *     &lt;enumeration value="DISCOVER"/>
 *     &lt;enumeration value="MASTER_CARD"/>
 *     &lt;enumeration value="AMERICAN_EXPRESS"/>
 *     &lt;enumeration value="UNKNOWN_CARD"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CreditCardTypeType")
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
