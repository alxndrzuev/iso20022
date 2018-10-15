
package iso20022.letters;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CurrencyControlRequestOrLetterV01 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CurrencyControlRequestOrLetterV01">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GrpHdr" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}CurrencyControlHeader3"/>
 *         &lt;element name="ReqOrLttr" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}SupportingDocumentRequestOrLetter1" maxOccurs="unbounded"/>
 *         &lt;element name="SplmtryData" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}SupplementaryData1" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CurrencyControlRequestOrLetterV01", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", propOrder = {
    "grpHdr",
    "reqOrLttr",
    "splmtryData"
})
public class CurrencyControlRequestOrLetterV01 {

    @XmlElement(name = "GrpHdr", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", required = true)
    protected CurrencyControlHeader3 grpHdr;
    @XmlElement(name = "ReqOrLttr", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", required = true)
    protected List<SupportingDocumentRequestOrLetter1> reqOrLttr;
    @XmlElement(name = "SplmtryData", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    protected List<SupplementaryData1> splmtryData;

    /**
     * Gets the value of the grpHdr property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyControlHeader3 }
     *     
     */
    public CurrencyControlHeader3 getGrpHdr() {
        return grpHdr;
    }

    /**
     * Sets the value of the grpHdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyControlHeader3 }
     *     
     */
    public void setGrpHdr(CurrencyControlHeader3 value) {
        this.grpHdr = value;
    }

    /**
     * Gets the value of the reqOrLttr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reqOrLttr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReqOrLttr().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SupportingDocumentRequestOrLetter1 }
     * 
     * 
     */
    public List<SupportingDocumentRequestOrLetter1> getReqOrLttr() {
        if (reqOrLttr == null) {
            reqOrLttr = new ArrayList<SupportingDocumentRequestOrLetter1>();
        }
        return this.reqOrLttr;
    }

    /**
     * Gets the value of the splmtryData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the splmtryData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSplmtryData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SupplementaryData1 }
     * 
     * 
     */
    public List<SupplementaryData1> getSplmtryData() {
        if (splmtryData == null) {
            splmtryData = new ArrayList<SupplementaryData1>();
        }
        return this.splmtryData;
    }

}
