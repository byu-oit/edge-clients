
package edu.byu.edge.ypay.domain.system;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LevelType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="LevelType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="WARNING"/>
 *     &lt;enumeration value="ERROR"/>
 *     &lt;enumeration value="ALERT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "LevelType")
@XmlEnum
public enum LevelType {

    WARNING,
    ERROR,
    ALERT;

    public String value() {
        return name();
    }

    public static LevelType fromValue(String v) {
        return valueOf(v);
    }

}
