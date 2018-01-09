
package edu.byu.edge.ypay.v1.domain.invoice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SortColumnType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SortColumnType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="invoice-id"/&gt;
 *     &lt;enumeration value="system-name"/&gt;
 *     &lt;enumeration value="system-id"/&gt;
 *     &lt;enumeration value="status"/&gt;
 *     &lt;enumeration value="items-total"/&gt;
 *     &lt;enumeration value="items-fees"/&gt;
 *     &lt;enumeration value="payment-transaction"/&gt;
 *     &lt;enumeration value="payment-started"/&gt;
 *     &lt;enumeration value="payment-finished"/&gt;
 *     &lt;enumeration value="payment-agreement-date"/&gt;
 *     &lt;enumeration value="reconciliation-date"/&gt;
 *     &lt;enumeration value="reconciliation-file"/&gt;
 *     &lt;enumeration value="return-date"/&gt;
 *     &lt;enumeration value="return-file"/&gt;
 *     &lt;enumeration value="return-reconciled-date"/&gt;
 *     &lt;enumeration value="return-reconciled-file"/&gt;
 *     &lt;enumeration value="return-transaction"/&gt;
 *     &lt;enumeration value="payment-source-allowed"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "SortColumnType")
@XmlEnum
public enum SortColumnType {

    @XmlEnumValue("invoice-id")
    INVOICE_ID("invoice-id"),
    @XmlEnumValue("system-name")
    SYSTEM_NAME("system-name"),
    @XmlEnumValue("system-id")
    SYSTEM_ID("system-id"),
    @XmlEnumValue("status")
    STATUS("status"),
    @XmlEnumValue("items-total")
    ITEMS_TOTAL("items-total"),
    @XmlEnumValue("items-fees")
    ITEMS_FEES("items-fees"),
    @XmlEnumValue("payment-transaction")
    PAYMENT_TRANSACTION("payment-transaction"),
    @XmlEnumValue("payment-started")
    PAYMENT_STARTED("payment-started"),
    @XmlEnumValue("payment-finished")
    PAYMENT_FINISHED("payment-finished"),
    @XmlEnumValue("payment-agreement-date")
    PAYMENT_AGREEMENT_DATE("payment-agreement-date"),
    @XmlEnumValue("reconciliation-date")
    RECONCILIATION_DATE("reconciliation-date"),
    @XmlEnumValue("reconciliation-file")
    RECONCILIATION_FILE("reconciliation-file"),
    @XmlEnumValue("return-date")
    RETURN_DATE("return-date"),
    @XmlEnumValue("return-file")
    RETURN_FILE("return-file"),
    @XmlEnumValue("return-reconciled-date")
    RETURN_RECONCILED_DATE("return-reconciled-date"),
    @XmlEnumValue("return-reconciled-file")
    RETURN_RECONCILED_FILE("return-reconciled-file"),
    @XmlEnumValue("return-transaction")
    RETURN_TRANSACTION("return-transaction"),
    @XmlEnumValue("payment-source-allowed")
    PAYMENT_SOURCE_ALLOWED("payment-source-allowed");
    private final String value;

    SortColumnType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SortColumnType fromValue(String v) {
        for (SortColumnType c: SortColumnType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
