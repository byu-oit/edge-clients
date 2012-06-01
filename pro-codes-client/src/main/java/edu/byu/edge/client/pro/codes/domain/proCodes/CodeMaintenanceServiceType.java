
package edu.byu.edge.client.pro.codes.domain.proCodes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CodeMaintenanceServiceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CodeMaintenanceServiceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="request" type="{uri://byu/c/ry/ae/prod/person/cgi/codeMaintenance.cgi}requestType"/>
 *         &lt;element name="errors" type="{uri://byu/c/ry/ae/prod/person/cgi/codeMaintenance.cgi}errorsType"/>
 *         &lt;element name="response" type="{uri://byu/c/ry/ae/prod/person/cgi/codeMaintenance.cgi}responseType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CodeMaintenanceServiceType", namespace = "uri://byu/c/ry/ae/prod/person/cgi/codeMaintenance.cgi", propOrder = {
    "request",
    "errors",
    "response"
})
public class CodeMaintenanceServiceType {

    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person/cgi/codeMaintenance.cgi", required = true)
    protected RequestType request;
    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person/cgi/codeMaintenance.cgi", required = true)
    protected ErrorsType errors;
    @XmlElement(namespace = "uri://byu/c/ry/ae/prod/person/cgi/codeMaintenance.cgi", required = true)
    protected ResponseType response;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link RequestType }
     *     
     */
    public RequestType getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestType }
     *     
     */
    public void setRequest(RequestType value) {
        this.request = value;
    }

    /**
     * Gets the value of the errors property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorsType }
     *     
     */
    public ErrorsType getErrors() {
        return errors;
    }

    /**
     * Sets the value of the errors property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorsType }
     *     
     */
    public void setErrors(ErrorsType value) {
        this.errors = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseType }
     *     
     */
    public ResponseType getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseType }
     *     
     */
    public void setResponse(ResponseType value) {
        this.response = value;
    }

}
