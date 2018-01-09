
package edu.byu.edge.ypay.v1.domain.invoice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MonthType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MonthType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="JAN"/&gt;
 *     &lt;enumeration value="FEB"/&gt;
 *     &lt;enumeration value="MAR"/&gt;
 *     &lt;enumeration value="APR"/&gt;
 *     &lt;enumeration value="MAY"/&gt;
 *     &lt;enumeration value="JUN"/&gt;
 *     &lt;enumeration value="JUL"/&gt;
 *     &lt;enumeration value="AUG"/&gt;
 *     &lt;enumeration value="SEP"/&gt;
 *     &lt;enumeration value="OCT"/&gt;
 *     &lt;enumeration value="NOV"/&gt;
 *     &lt;enumeration value="DEC"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
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
