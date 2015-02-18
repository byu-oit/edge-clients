
package edu.byu.edge.ypay.domain.invoice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BatchFileTypeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BatchFileTypeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="END_OF_DAY"/>
 *     &lt;enumeration value="RETURNS"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "BatchFileTypeType")
@XmlEnum
public enum BatchFileTypeType {

    END_OF_DAY,
    RETURNS;

    public String value() {
        return name();
    }

    public static BatchFileTypeType fromValue(String v) {
        return valueOf(v);
    }

}
