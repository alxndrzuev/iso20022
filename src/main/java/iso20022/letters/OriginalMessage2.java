
package iso20022.letters;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for OriginalMessage2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OriginalMessage2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrgnlSndr" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}Party28Choice" minOccurs="0"/>
 *         &lt;element name="OrgnlMsgId" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}Max35Text"/>
 *         &lt;element name="OrgnlMsgNmId" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}Max35Text"/>
 *         &lt;element name="OrgnlCreDtTm" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}ISODateTime" minOccurs="0"/>
 *         &lt;element name="OrgnlPackgId" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}Max35Text" minOccurs="0"/>
 *         &lt;element name="OrgnlRcrdId" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}Max35Text"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OriginalMessage2", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", propOrder = {
    "orgnlSndr",
    "orgnlMsgId",
    "orgnlMsgNmId",
    "orgnlCreDtTm",
    "orgnlPackgId",
    "orgnlRcrdId"
})
public class OriginalMessage2 {

    @XmlElement(name = "OrgnlSndr", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    protected Party28Choice orgnlSndr;
    @XmlElement(name = "OrgnlMsgId", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", required = true)
    protected String orgnlMsgId;
    @XmlElement(name = "OrgnlMsgNmId", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", required = true)
    protected String orgnlMsgNmId;
    @XmlElement(name = "OrgnlCreDtTm", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar orgnlCreDtTm;
    @XmlElement(name = "OrgnlPackgId", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    protected String orgnlPackgId;
    @XmlElement(name = "OrgnlRcrdId", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", required = true)
    protected String orgnlRcrdId;

    /**
     * Gets the value of the orgnlSndr property.
     * 
     * @return
     *     possible object is
     *     {@link Party28Choice }
     *     
     */
    public Party28Choice getOrgnlSndr() {
        return orgnlSndr;
    }

    /**
     * Sets the value of the orgnlSndr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Party28Choice }
     *     
     */
    public void setOrgnlSndr(Party28Choice value) {
        this.orgnlSndr = value;
    }

    /**
     * Gets the value of the orgnlMsgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgnlMsgId() {
        return orgnlMsgId;
    }

    /**
     * Sets the value of the orgnlMsgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgnlMsgId(String value) {
        this.orgnlMsgId = value;
    }

    /**
     * Gets the value of the orgnlMsgNmId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgnlMsgNmId() {
        return orgnlMsgNmId;
    }

    /**
     * Sets the value of the orgnlMsgNmId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgnlMsgNmId(String value) {
        this.orgnlMsgNmId = value;
    }

    /**
     * Gets the value of the orgnlCreDtTm property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOrgnlCreDtTm() {
        return orgnlCreDtTm;
    }

    /**
     * Sets the value of the orgnlCreDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOrgnlCreDtTm(XMLGregorianCalendar value) {
        this.orgnlCreDtTm = value;
    }

    /**
     * Gets the value of the orgnlPackgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgnlPackgId() {
        return orgnlPackgId;
    }

    /**
     * Sets the value of the orgnlPackgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgnlPackgId(String value) {
        this.orgnlPackgId = value;
    }

    /**
     * Gets the value of the orgnlRcrdId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgnlRcrdId() {
        return orgnlRcrdId;
    }

    /**
     * Sets the value of the orgnlRcrdId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgnlRcrdId(String value) {
        this.orgnlRcrdId = value;
    }

}
