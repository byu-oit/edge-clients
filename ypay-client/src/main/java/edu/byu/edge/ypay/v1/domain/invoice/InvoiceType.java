
package edu.byu.edge.ypay.v1.domain.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Use to create an invoice id. At least one line item is needed, but
 * 				can have as many as are wanted.
 * 			
 * 
 * <p>Java class for InvoiceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InvoiceType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://schemas.byu.edu/payment/invoice}InvoiceBaseType">
 *       &lt;sequence>
 *         &lt;element name="invoiceId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="clientSystem" type="{http://schemas.byu.edu/payment/invoice}ClientSystemLinkType"/>
 *         &lt;element name="status" type="{http://schemas.byu.edu/payment/invoice}InvoiceStatusType"/>
 *         &lt;element name="payment" type="{http://schemas.byu.edu/payment/invoice}PaymentInfoType"/>
 *         &lt;element name="reconciliation" type="{http://schemas.byu.edu/payment/invoice}ReconciliationType"/>
 *         &lt;element name="urls" type="{http://schemas.byu.edu/payment/invoice}UrlsType"/>
 *         &lt;element name="scheduledPaymentId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvoiceType", propOrder = {
    "invoiceId",
    "clientSystem",
    "status",
    "payment",
    "reconciliation",
    "urls",
    "scheduledPaymentId"
})
public class InvoiceType
    extends InvoiceBaseType
{

    protected long invoiceId;
    @XmlElement(required = true)
    protected ClientSystemLinkType clientSystem;
    @XmlElement(required = true)
    protected InvoiceStatusType status;
    @XmlElement(required = true, nillable = true)
    protected PaymentInfoType payment;
    @XmlElement(required = true, nillable = true)
    protected ReconciliationType reconciliation;
    @XmlElement(required = true, nillable = true)
    protected UrlsType urls;
    @XmlElement(required = true, nillable = true)
    protected String scheduledPaymentId;

    /**
     * Gets the value of the invoiceId property.
     * 
     */
    public long getInvoiceId() {
        return invoiceId;
    }

    /**
     * Sets the value of the invoiceId property.
     * 
     */
    public void setInvoiceId(long value) {
        this.invoiceId = value;
    }

    /**
     * Gets the value of the clientSystem property.
     * 
     * @return
     *     possible object is
     *     {@link ClientSystemLinkType }
     *     
     */
    public ClientSystemLinkType getClientSystem() {
        return clientSystem;
    }

    /**
     * Sets the value of the clientSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClientSystemLinkType }
     *     
     */
    public void setClientSystem(ClientSystemLinkType value) {
        this.clientSystem = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceStatusType }
     *     
     */
    public InvoiceStatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceStatusType }
     *     
     */
    public void setStatus(InvoiceStatusType value) {
        this.status = value;
    }

    /**
     * Gets the value of the payment property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentInfoType }
     *     
     */
    public PaymentInfoType getPayment() {
        return payment;
    }

    /**
     * Sets the value of the payment property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentInfoType }
     *     
     */
    public void setPayment(PaymentInfoType value) {
        this.payment = value;
    }

    /**
     * Gets the value of the reconciliation property.
     * 
     * @return
     *     possible object is
     *     {@link ReconciliationType }
     *     
     */
    public ReconciliationType getReconciliation() {
        return reconciliation;
    }

    /**
     * Sets the value of the reconciliation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReconciliationType }
     *     
     */
    public void setReconciliation(ReconciliationType value) {
        this.reconciliation = value;
    }

    /**
     * Gets the value of the urls property.
     * 
     * @return
     *     possible object is
     *     {@link UrlsType }
     *     
     */
    public UrlsType getUrls() {
        return urls;
    }

    /**
     * Sets the value of the urls property.
     * 
     * @param value
     *     allowed object is
     *     {@link UrlsType }
     *     
     */
    public void setUrls(UrlsType value) {
        this.urls = value;
    }

    /**
     * Gets the value of the scheduledPaymentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScheduledPaymentId() {
        return scheduledPaymentId;
    }

    /**
     * Sets the value of the scheduledPaymentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScheduledPaymentId(String value) {
        this.scheduledPaymentId = value;
    }

    /**
     * Return whether the status of this invoice indicates problems
     * @return result
     */
    public boolean hasIssueStatus() {
        switch(this.status) {
            case CREATED:
            case PROCESSING:
            case SUBMITTED:
            case PAID:
                return false;
            default:
                return true;
        }
    }

}
