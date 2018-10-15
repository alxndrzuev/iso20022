
package iso20022.letters;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CurrencyControlHeader3 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CurrencyControlHeader3">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MsgId" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}Max35Text"/>
 *         &lt;element name="CreDtTm" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}ISODateTime"/>
 *         &lt;element name="NbOfItms" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}Max15NumericText"/>
 *         &lt;element name="InitgPty" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}Party28Choice"/>
 *         &lt;element name="FwdgAgt" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}BranchAndFinancialInstitutionIdentification5" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CurrencyControlHeader3", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", propOrder = {
    "msgId",
    "creDtTm",
    "nbOfItms",
    "initgPty",
    "fwdgAgt"
})
public class CurrencyControlHeader3 {

    @XmlElement(name = "MsgId", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", required = true)
    protected String msgId;
    @XmlElement(name = "CreDtTm", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creDtTm;
    @XmlElement(name = "NbOfItms", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", required = true)
    protected String nbOfItms;
    @XmlElement(name = "InitgPty", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", required = true)
    protected Party28Choice initgPty;
    @XmlElement(name = "FwdgAgt", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    protected BranchAndFinancialInstitutionIdentification5 fwdgAgt;

    /**
     * Gets the value of the msgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * Sets the value of the msgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsgId(String value) {
        this.msgId = value;
    }

    /**
     * Gets the value of the creDtTm property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreDtTm() {
        return creDtTm;
    }

    /**
     * Sets the value of the creDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreDtTm(XMLGregorianCalendar value) {
        this.creDtTm = value;
    }

    /**
     * Gets the value of the nbOfItms property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNbOfItms() {
        return nbOfItms;
    }

    /**
     * Sets the value of the nbOfItms property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNbOfItms(String value) {
        this.nbOfItms = value;
    }

    /**
     * Gets the value of the initgPty property.
     * 
     * @return
     *     possible object is
     *     {@link Party28Choice }
     *     
     */
    public Party28Choice getInitgPty() {
        return initgPty;
    }

    /**
     * Sets the value of the initgPty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Party28Choice }
     *     
     */
    public void setInitgPty(Party28Choice value) {
        this.initgPty = value;
    }

    /**
     * Gets the value of the fwdgAgt property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getFwdgAgt() {
        return fwdgAgt;
    }

    /**
     * Sets the value of the fwdgAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setFwdgAgt(BranchAndFinancialInstitutionIdentification5 value) {
        this.fwdgAgt = value;
    }

}
