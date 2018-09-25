
package iso20022.letters;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Document complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Document">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CcyCtrlReqOrLttr" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}CurrencyControlRequestOrLetterV01"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", propOrder = {
    "ccyCtrlReqOrLttr"
})
public class Document {

    @XmlElement(name = "CcyCtrlReqOrLttr", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", required = true)
    protected CurrencyControlRequestOrLetterV01 ccyCtrlReqOrLttr;

    /**
     * Gets the value of the ccyCtrlReqOrLttr property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyControlRequestOrLetterV01 }
     *     
     */
    public CurrencyControlRequestOrLetterV01 getCcyCtrlReqOrLttr() {
        return ccyCtrlReqOrLttr;
    }

    /**
     * Sets the value of the ccyCtrlReqOrLttr property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyControlRequestOrLetterV01 }
     *     
     */
    public void setCcyCtrlReqOrLttr(CurrencyControlRequestOrLetterV01 value) {
        this.ccyCtrlReqOrLttr = value;
    }

}
