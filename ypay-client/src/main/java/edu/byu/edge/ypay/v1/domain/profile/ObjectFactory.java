
package edu.byu.edge.ypay.v1.domain.profile;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the edu.byu.edge.ypay.v1.domain.profile package.
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Filter_QNAME = new QName("http://schemas.byu.edu/payment/profile", "filter");
    private final static QName _Filters_QNAME = new QName("http://schemas.byu.edu/payment/profile", "filters");
    private final static QName _BillingAddresses_QNAME = new QName("http://schemas.byu.edu/payment/profile", "billingAddresses");
    private final static QName _BillingAddress_QNAME = new QName("http://schemas.byu.edu/payment/profile", "billingAddress");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: edu.byu.edge.ypay.v1.domain.profile
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BillingAddressListType }
     * 
     */
    public BillingAddressListType createBillingAddressListType() {
        return new BillingAddressListType();
    }

    /**
     * Create an instance of {@link InternationalOrUSAddressType }
     * 
     */
    public InternationalOrUSAddressType createInternationalOrUSAddressType() {
        return new InternationalOrUSAddressType();
    }

    /**
     * Create an instance of {@link UIDataFilterListType }
     * 
     */
    public UIDataFilterListType createUIDataFilterListType() {
        return new UIDataFilterListType();
    }

    /**
     * Create an instance of {@link UIDataFilterType }
     * 
     */
    public UIDataFilterType createUIDataFilterType() {
        return new UIDataFilterType();
    }

    /**
     * Create an instance of {@link InternationalBillingAddressType }
     * 
     */
    public InternationalBillingAddressType createInternationalBillingAddressType() {
        return new InternationalBillingAddressType();
    }

    /**
     * Create an instance of {@link USBillingAddressType }
     * 
     */
    public USBillingAddressType createUSBillingAddressType() {
        return new USBillingAddressType();
    }

    /**
     * Create an instance of {@link ListBaseType }
     * 
     */
    public ListBaseType createListBaseType() {
        return new ListBaseType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UIDataFilterType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.byu.edu/payment/profile", name = "filter")
    public JAXBElement<UIDataFilterType> createFilter(UIDataFilterType value) {
        return new JAXBElement<UIDataFilterType>(_Filter_QNAME, UIDataFilterType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UIDataFilterListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.byu.edu/payment/profile", name = "filters")
    public JAXBElement<UIDataFilterListType> createFilters(UIDataFilterListType value) {
        return new JAXBElement<UIDataFilterListType>(_Filters_QNAME, UIDataFilterListType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BillingAddressListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.byu.edu/payment/profile", name = "billingAddresses")
    public JAXBElement<BillingAddressListType> createBillingAddresses(BillingAddressListType value) {
        return new JAXBElement<BillingAddressListType>(_BillingAddresses_QNAME, BillingAddressListType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InternationalOrUSAddressType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.byu.edu/payment/profile", name = "billingAddress")
    public JAXBElement<InternationalOrUSAddressType> createBillingAddress(InternationalOrUSAddressType value) {
        return new JAXBElement<InternationalOrUSAddressType>(_BillingAddress_QNAME, InternationalOrUSAddressType.class, null, value);
    }

}
