
package iso20022.statements;

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
 *         &lt;element name="AcctRptgReq" type="{urn:iso:std:iso:20022:tech:xsd:camt.060.001.03}AccountReportingRequestV03"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.03", propOrder = {
    "acctRptgReq"
})
public class Document {

    @XmlElement(name = "AcctRptgReq", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.03", required = true)
    protected AccountReportingRequestV03 acctRptgReq;

    /**
     * Gets the value of the acctRptgReq property.
     * 
     * @return
     *     possible object is
     *     {@link AccountReportingRequestV03 }
     *     
     */
    public AccountReportingRequestV03 getAcctRptgReq() {
        return acctRptgReq;
    }

    /**
     * Sets the value of the acctRptgReq property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountReportingRequestV03 }
     *     
     */
    public void setAcctRptgReq(AccountReportingRequestV03 value) {
        this.acctRptgReq = value;
    }

}
