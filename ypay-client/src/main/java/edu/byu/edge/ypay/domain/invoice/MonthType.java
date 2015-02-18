
package edu.byu.edge.ypay.domain.invoice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MonthType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MonthType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="JAN"/>
 *     &lt;enumeration value="FEB"/>
 *     &lt;enumeration value="MAR"/>
 *     &lt;enumeration value="APR"/>
 *     &lt;enumeration value="MAY"/>
 *     &lt;enumeration value="JUN"/>
 *     &lt;enumeration value="JUL"/>
 *     &lt;enumeration value="AUG"/>
 *     &lt;enumeration value="SEP"/>
 *     &lt;enumeration value="OCT"/>
 *     &lt;enumeration value="NOV"/>
 *     &lt;enumeration value="DEC"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MonthType", namespace = "http://schemas.byu.edu/payment/payments")
@XmlEnum
public enum MonthType {

    JAN,
    FEB,
    MAR,
    APR,
    MAY,
    JUN,
    JUL,
    AUG,
    SEP,
    OCT,
    NOV,
    DEC;

    public String value() {
        return name();
    }

    public static MonthType fromValue(String v) {
        return valueOf(v);
    }

}
