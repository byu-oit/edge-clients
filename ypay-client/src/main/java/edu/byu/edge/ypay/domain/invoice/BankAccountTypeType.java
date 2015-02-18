
package edu.byu.edge.ypay.domain.invoice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BankAccountTypeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BankAccountTypeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CHECKING"/>
 *     &lt;enumeration value="SAVINGS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BankAccountTypeType", namespace = "http://schemas.byu.edu/payment/payments")
@XmlEnum
public enum BankAccountTypeType {

    CHECKING,
    SAVINGS;

    public String value() {
        return name();
    }

    public static BankAccountTypeType fromValue(String v) {
        return valueOf(v);
    }

}
