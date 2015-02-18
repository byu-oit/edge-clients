
package edu.byu.edge.ypay.v1.domain.system;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *         Created during a system event.
 * <p>Java class for SystemMessageType complex type.
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;complexType name="SystemMessageType">
 * &lt;complexContent>
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 * &lt;sequence>
 * &lt;element name="systemMessageId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 * &lt;element name="timeOfMessage" type="{http://www.w3.org/2001/XMLSchema}date"/>
 * &lt;element name="server" type="{http://www.w3.org/2001/XMLSchema}string"/>
 * &lt;element name="category" type="{http://www.w3.org/2001/XMLSchema}string"/>
 * &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 * &lt;element name="currentUser" type="{http://www.w3.org/2001/XMLSchema}string"/>
 * &lt;element name="currentActor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 * &lt;element name="dateCleared" type="{http://www.w3.org/2001/XMLSchema}date"/>
 * &lt;element name="clearedBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 * &lt;element name="trace" type="{http://www.w3.org/2001/XMLSchema}string"/>
 * &lt;element name="level" type="{http://schemas.byu.edu/payment/system}LevelType"/>
 * &lt;/sequence>
 * &lt;/restriction>
 * &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *       
 * 
 * <p>Java class for SystemMessageType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SystemMessageType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="systemMessageId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="timeOfMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="server" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="category" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="currentUser" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="currentActor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dateCleared" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="clearedBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="trace" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="level" type="{http://schemas.byu.edu/payment/system}LevelType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SystemMessageType", propOrder = {
    "systemMessageId",
    "timeOfMessage",
    "server",
    "category",
    "message",
    "currentUser",
    "currentActor",
    "dateCleared",
    "clearedBy",
    "trace",
    "level"
})
public class SystemMessageType {

    @XmlElement(required = true)
    protected String systemMessageId;
    @XmlElement(required = true)
    protected String timeOfMessage;
    @XmlElement(required = true)
    protected String server;
    @XmlElement(required = true)
    protected String category;
    @XmlElement(required = true)
    protected String message;
    @XmlElement(required = true)
    protected String currentUser;
    @XmlElement(required = true)
    protected String currentActor;
    @XmlElement(required = true)
    protected String dateCleared;
    @XmlElement(required = true)
    protected String clearedBy;
    @XmlElement(required = true)
    protected String trace;
    @XmlElement(required = true)
    protected LevelType level;

    /**
     * Gets the value of the systemMessageId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemMessageId() {
        return systemMessageId;
    }

    /**
     * Sets the value of the systemMessageId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemMessageId(String value) {
        this.systemMessageId = value;
    }

    /**
     * Gets the value of the timeOfMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeOfMessage() {
        return timeOfMessage;
    }

    /**
     * Sets the value of the timeOfMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeOfMessage(String value) {
        this.timeOfMessage = value;
    }

    /**
     * Gets the value of the server property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServer() {
        return server;
    }

    /**
     * Sets the value of the server property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServer(String value) {
        this.server = value;
    }

    /**
     * Gets the value of the category property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategory(String value) {
        this.category = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the currentUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the value of the currentUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentUser(String value) {
        this.currentUser = value;
    }

    /**
     * Gets the value of the currentActor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentActor() {
        return currentActor;
    }

    /**
     * Sets the value of the currentActor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentActor(String value) {
        this.currentActor = value;
    }

    /**
     * Gets the value of the dateCleared property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateCleared() {
        return dateCleared;
    }

    /**
     * Sets the value of the dateCleared property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateCleared(String value) {
        this.dateCleared = value;
    }

    /**
     * Gets the value of the clearedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClearedBy() {
        return clearedBy;
    }

    /**
     * Sets the value of the clearedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClearedBy(String value) {
        this.clearedBy = value;
    }

    /**
     * Gets the value of the trace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrace() {
        return trace;
    }

    /**
     * Sets the value of the trace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrace(String value) {
        this.trace = value;
    }

    /**
     * Gets the value of the level property.
     * 
     * @return
     *     possible object is
     *     {@link LevelType }
     *     
     */
    public LevelType getLevel() {
        return level;
    }

    /**
     * Sets the value of the level property.
     * 
     * @param value
     *     allowed object is
     *     {@link LevelType }
     *     
     */
    public void setLevel(LevelType value) {
        this.level = value;
    }

}
