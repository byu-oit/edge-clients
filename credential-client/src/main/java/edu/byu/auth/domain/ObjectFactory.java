
package edu.byu.auth.domain;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the edu.byu.auth.domain package.
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

    private final static QName _WsSession_QNAME = new QName("http://ws.byu.edu/namespaces/security/authentication/v1.0", "wsSession");
    private final static QName _Credential_QNAME = new QName("http://ws.byu.edu/namespaces/security/authentication/v1.0", "credential");
    private final static QName _Identity_QNAME = new QName("http://ws.byu.edu/namespaces/security/authentication/v1.0", "identity");
    private final static QName _Nonce_QNAME = new QName("http://ws.byu.edu/namespaces/security/authentication/v1.0", "nonce");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: edu.byu.auth.domain
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Credential }
     * @return an instance of {@link Credential }
     */
    public Credential createCredential() {
        return new Credential();
    }

    /**
     * @return an instance of {@link Nonce }
     *
     */
    public Nonce createNonce() {
        return new Nonce();
    }

    /**
     * @return an instance of {@link WsSession }
     */
    public WsSession createWsSession() {
        return new WsSession();
    }

    /**
     *  @return an instance of {@link Identity }
     *
     */
    public Identity createIdentity() {
        return new Identity();
    }

    /**
     * @return an instance of {@link JAXBElement }{@code <}{@link WsSession }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0", name = "wsSession")
    public JAXBElement<WsSession> createWsSession(WsSession value) {
        return new JAXBElement<WsSession>(_WsSession_QNAME, WsSession.class, null, value);
    }

    /**
     * @return an instance of {@link JAXBElement }{@code <}{@link Credential }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0", name = "credential")
    public JAXBElement<Credential> createCredential(Credential value) {
        return new JAXBElement<Credential>(_Credential_QNAME, Credential.class, null, value);
    }

    /**
     * @return an instance of {@link JAXBElement }{@code <}{@link Identity }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0", name = "identity")
    public JAXBElement<Identity> createIdentity(Identity value) {
        return new JAXBElement<Identity>(_Identity_QNAME, Identity.class, null, value);
    }

    /**
     * @return an instance of {@link JAXBElement }{@code <}{@link Nonce }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://ws.byu.edu/namespaces/security/authentication/v1.0", name = "nonce")
    public JAXBElement<Nonce> createNonce(Nonce value) {
        return new JAXBElement<Nonce>(_Nonce_QNAME, Nonce.class, null, value);
    }

}
