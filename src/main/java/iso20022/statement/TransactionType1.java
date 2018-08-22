
package iso20022.statement;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransactionType1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionType1">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Sts" type="{urn:iso:std:iso:20022:tech:xsd:camt.060.001.03}EntryStatus2Code"/>
 *         &lt;element name="CdtDbtInd" type="{urn:iso:std:iso:20022:tech:xsd:camt.060.001.03}CreditDebitCode"/>
 *         &lt;element name="FlrLmt" type="{urn:iso:std:iso:20022:tech:xsd:camt.060.001.03}Limit2" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionType1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.03", propOrder = {
    "sts",
    "cdtDbtInd",
    "flrLmt"
})
public class TransactionType1 {

    @XmlElement(name = "Sts", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.03", required = true)
    @XmlSchemaType(name = "string")
    protected EntryStatus2Code sts;
    @XmlElement(name = "CdtDbtInd", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.03", required = true)
    @XmlSchemaType(name = "string")
    protected CreditDebitCode cdtDbtInd;
    @XmlElement(name = "FlrLmt", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.03")
    protected List<Limit2> flrLmt;

    /**
     * Gets the value of the sts property.
     * 
     * @return
     *     possible object is
     *     {@link EntryStatus2Code }
     *     
     */
    public EntryStatus2Code getSts() {
        return sts;
    }

    /**
     * Sets the value of the sts property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntryStatus2Code }
     *     
     */
    public void setSts(EntryStatus2Code value) {
        this.sts = value;
    }

    /**
     * Gets the value of the cdtDbtInd property.
     * 
     * @return
     *     possible object is
     *     {@link CreditDebitCode }
     *     
     */
    public CreditDebitCode getCdtDbtInd() {
        return cdtDbtInd;
    }

    /**
     * Sets the value of the cdtDbtInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditDebitCode }
     *     
     */
    public void setCdtDbtInd(CreditDebitCode value) {
        this.cdtDbtInd = value;
    }

    /**
     * Gets the value of the flrLmt property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the flrLmt property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFlrLmt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Limit2 }
     * 
     * 
     */
    public List<Limit2> getFlrLmt() {
        if (flrLmt == null) {
            flrLmt = new ArrayList<Limit2>();
        }
        return this.flrLmt;
    }

}
