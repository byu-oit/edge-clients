
package edu.byu.edge.ypay.v1.domain.invoice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BatchFileTypeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="BatchFileTypeType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="END_OF_DAY"/&gt;
 *     &lt;enumeration value="RETURNS"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
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
