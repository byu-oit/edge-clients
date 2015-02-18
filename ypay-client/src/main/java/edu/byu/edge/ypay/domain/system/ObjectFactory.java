
package edu.byu.edge.ypay.domain.system;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the edu.byu.edge.ypay.domain.system package. 
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

    private final static QName _Message_QNAME = new QName("http://schemas.byu.edu/payment/system", "message");
    private final static QName _NewProfile_QNAME = new QName("http://schemas.byu.edu/payment/system", "newProfile");
    private final static QName _Profiles_QNAME = new QName("http://schemas.byu.edu/payment/system", "profiles");
    private final static QName _Messages_QNAME = new QName("http://schemas.byu.edu/payment/system", "messages");
    private final static QName _Profile_QNAME = new QName("http://schemas.byu.edu/payment/system", "profile");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: edu.byu.edge.ypay.domain.system
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SystemMessageType }
     * 
     */
    public SystemMessageType createSystemMessageType() {
        return new SystemMessageType();
    }

    /**
     * Create an instance of {@link ProcessorProfileRequestType }
     * 
     */
    public ProcessorProfileRequestType createProcessorProfileRequestType() {
        return new ProcessorProfileRequestType();
    }

    /**
     * Create an instance of {@link ProcessorProfileListType }
     * 
     */
    public ProcessorProfileListType createProcessorProfileListType() {
        return new ProcessorProfileListType();
    }

    /**
     * Create an instance of {@link SystemMessageListType }
     * 
     */
    public SystemMessageListType createSystemMessageListType() {
        return new SystemMessageListType();
    }

    /**
     * Create an instance of {@link ProcessorProfileType }
     * 
     */
    public ProcessorProfileType createProcessorProfileType() {
        return new ProcessorProfileType();
    }

    /**
     * Create an instance of {@link CashnetInfoType }
     * 
     */
    public CashnetInfoType createCashnetInfoType() {
        return new CashnetInfoType();
    }

    /**
     * Create an instance of {@link ListBaseType }
     * 
     */
    public ListBaseType createListBaseType() {
        return new ListBaseType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SystemMessageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.byu.edu/payment/system", name = "message")
    public JAXBElement<SystemMessageType> createMessage(SystemMessageType value) {
        return new JAXBElement<SystemMessageType>(_Message_QNAME, SystemMessageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessorProfileRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.byu.edu/payment/system", name = "newProfile")
    public JAXBElement<ProcessorProfileRequestType> createNewProfile(ProcessorProfileRequestType value) {
        return new JAXBElement<ProcessorProfileRequestType>(_NewProfile_QNAME, ProcessorProfileRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessorProfileListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.byu.edu/payment/system", name = "profiles")
    public JAXBElement<ProcessorProfileListType> createProfiles(ProcessorProfileListType value) {
        return new JAXBElement<ProcessorProfileListType>(_Profiles_QNAME, ProcessorProfileListType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SystemMessageListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.byu.edu/payment/system", name = "messages")
    public JAXBElement<SystemMessageListType> createMessages(SystemMessageListType value) {
        return new JAXBElement<SystemMessageListType>(_Messages_QNAME, SystemMessageListType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessorProfileType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.byu.edu/payment/system", name = "profile")
    public JAXBElement<ProcessorProfileType> createProfile(ProcessorProfileType value) {
        return new JAXBElement<ProcessorProfileType>(_Profile_QNAME, ProcessorProfileType.class, null, value);
    }

}
