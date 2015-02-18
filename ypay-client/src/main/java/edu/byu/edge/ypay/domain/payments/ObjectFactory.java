
package edu.byu.edge.ypay.domain.payments;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the edu.byu.edge.ypay.domain.payments package. 
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
    private final static QName _BankAccountRequest_QNAME = new QName("http://schemas.byu.edu/payment/payments", "bankAccountRequest");
    private final static QName _PaymentRequest_QNAME = new QName("http://schemas.byu.edu/payment/payments", "paymentRequest");
    private final static QName _BankAccount_QNAME = new QName("http://schemas.byu.edu/payment/payments", "bankAccount");
    private final static QName _BillingAddresses_QNAME = new QName("http://schemas.byu.edu/payment/profile", "billingAddresses");
    private final static QName _CreditCards_QNAME = new QName("http://schemas.byu.edu/payment/payments", "creditCards");
    private final static QName _BillingAddress_QNAME = new QName("http://schemas.byu.edu/payment/profile", "billingAddress");
    private final static QName _BankAccounts_QNAME = new QName("http://schemas.byu.edu/payment/payments", "bankAccounts");
    private final static QName _CreditCard_QNAME = new QName("http://schemas.byu.edu/payment/payments", "creditCard");
    private final static QName _CreditCardRequest_QNAME = new QName("http://schemas.byu.edu/payment/payments", "creditCardRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: edu.byu.edge.ypay.domain.payments
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreditCardType }
     * 
     */
    public CreditCardType createCreditCardType() {
        return new CreditCardType();
    }

    /**
     * Create an instance of {@link CreditCardRequestType }
     * 
     */
    public CreditCardRequestType createCreditCardRequestType() {
        return new CreditCardRequestType();
    }

    /**
     * Create an instance of {@link PaymentRequestType }
     * 
     */
    public PaymentRequestType createPaymentRequestType() {
        return new PaymentRequestType();
    }

    /**
     * Create an instance of {@link BankAccountRequestType }
     * 
     */
    public BankAccountRequestType createBankAccountRequestType() {
        return new BankAccountRequestType();
    }

    /**
     * Create an instance of {@link BankAccountTypeList }
     * 
     */
    public BankAccountTypeList createBankAccountTypeList() {
        return new BankAccountTypeList();
    }

    /**
     * Create an instance of {@link CreditCardTypeList }
     * 
     */
    public CreditCardTypeList createCreditCardTypeList() {
        return new CreditCardTypeList();
    }

    /**
     * Create an instance of {@link BankAccountType }
     * 
     */
    public BankAccountType createBankAccountType() {
        return new BankAccountType();
    }

    /**
     * Create an instance of {@link CreditCardBaseType }
     * 
     */
    public CreditCardBaseType createCreditCardBaseType() {
        return new CreditCardBaseType();
    }

    /**
     * Create an instance of {@link BankAccountBaseType }
     * 
     */
    public BankAccountBaseType createBankAccountBaseType() {
        return new BankAccountBaseType();
    }

    /**
     * Create an instance of {@link ExpirationDateType }
     * 
     */
    public ExpirationDateType createExpirationDateType() {
        return new ExpirationDateType();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link BankAccountRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.byu.edu/payment/payments", name = "bankAccountRequest")
    public JAXBElement<BankAccountRequestType> createBankAccountRequest(BankAccountRequestType value) {
        return new JAXBElement<BankAccountRequestType>(_BankAccountRequest_QNAME, BankAccountRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaymentRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.byu.edu/payment/payments", name = "paymentRequest")
    public JAXBElement<PaymentRequestType> createPaymentRequest(PaymentRequestType value) {
        return new JAXBElement<PaymentRequestType>(_PaymentRequest_QNAME, PaymentRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BankAccountType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.byu.edu/payment/payments", name = "bankAccount")
    public JAXBElement<BankAccountType> createBankAccount(BankAccountType value) {
        return new JAXBElement<BankAccountType>(_BankAccount_QNAME, BankAccountType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link CreditCardTypeList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.byu.edu/payment/payments", name = "creditCards")
    public JAXBElement<CreditCardTypeList> createCreditCards(CreditCardTypeList value) {
        return new JAXBElement<CreditCardTypeList>(_CreditCards_QNAME, CreditCardTypeList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InternationalOrUSAddressType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.byu.edu/payment/profile", name = "billingAddress")
    public JAXBElement<InternationalOrUSAddressType> createBillingAddress(InternationalOrUSAddressType value) {
        return new JAXBElement<InternationalOrUSAddressType>(_BillingAddress_QNAME, InternationalOrUSAddressType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BankAccountTypeList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.byu.edu/payment/payments", name = "bankAccounts")
    public JAXBElement<BankAccountTypeList> createBankAccounts(BankAccountTypeList value) {
        return new JAXBElement<BankAccountTypeList>(_BankAccounts_QNAME, BankAccountTypeList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreditCardType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.byu.edu/payment/payments", name = "creditCard")
    public JAXBElement<CreditCardType> createCreditCard(CreditCardType value) {
        return new JAXBElement<CreditCardType>(_CreditCard_QNAME, CreditCardType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreditCardRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.byu.edu/payment/payments", name = "creditCardRequest")
    public JAXBElement<CreditCardRequestType> createCreditCardRequest(CreditCardRequestType value) {
        return new JAXBElement<CreditCardRequestType>(_CreditCardRequest_QNAME, CreditCardRequestType.class, null, value);
    }

}
