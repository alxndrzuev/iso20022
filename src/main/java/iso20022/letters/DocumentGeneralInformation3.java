
package iso20022.letters;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DocumentGeneralInformation3 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocumentGeneralInformation3">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DocTp" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}ExternalDocumentType1Code"/>
 *         &lt;element name="DocNb" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}Max35Text"/>
 *         &lt;element name="SndrRcvrSeqId" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}Max140Text" minOccurs="0"/>
 *         &lt;element name="IsseDt" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}ISODate" minOccurs="0"/>
 *         &lt;element name="URL" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}Max256Text" minOccurs="0"/>
 *         &lt;element name="LkFileHash" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}SignatureEnvelopeReference" minOccurs="0"/>
 *         &lt;element name="AttchdBinryFile" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}BinaryFile1"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentGeneralInformation3", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", propOrder = {
    "docTp",
    "docNb",
    "sndrRcvrSeqId",
    "isseDt",
    "url",
    "lkFileHash",
    "attchdBinryFile"
})
public class DocumentGeneralInformation3 {

    @XmlElement(name = "DocTp", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", required = true)
    protected String docTp;
    @XmlElement(name = "DocNb", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", required = true)
    protected String docNb;
    @XmlElement(name = "SndrRcvrSeqId", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    protected String sndrRcvrSeqId;
    @XmlElement(name = "IsseDt", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar isseDt;
    @XmlElement(name = "URL", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    protected String url;
    @XmlElement(name = "LkFileHash", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    protected SignatureEnvelopeReference lkFileHash;
    @XmlElement(name = "AttchdBinryFile", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", required = true)
    protected BinaryFile1 attchdBinryFile;

    /**
     * Gets the value of the docTp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocTp() {
        return docTp;
    }

    /**
     * Sets the value of the docTp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocTp(String value) {
        this.docTp = value;
    }

    /**
     * Gets the value of the docNb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocNb() {
        return docNb;
    }

    /**
     * Sets the value of the docNb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocNb(String value) {
        this.docNb = value;
    }

    /**
     * Gets the value of the sndrRcvrSeqId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSndrRcvrSeqId() {
        return sndrRcvrSeqId;
    }

    /**
     * Sets the value of the sndrRcvrSeqId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSndrRcvrSeqId(String value) {
        this.sndrRcvrSeqId = value;
    }

    /**
     * Gets the value of the isseDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getIsseDt() {
        return isseDt;
    }

    /**
     * Sets the value of the isseDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setIsseDt(XMLGregorianCalendar value) {
        this.isseDt = value;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getURL() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setURL(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the lkFileHash property.
     * 
     * @return
     *     possible object is
     *     {@link SignatureEnvelopeReference }
     *     
     */
    public SignatureEnvelopeReference getLkFileHash() {
        return lkFileHash;
    }

    /**
     * Sets the value of the lkFileHash property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureEnvelopeReference }
     *     
     */
    public void setLkFileHash(SignatureEnvelopeReference value) {
        this.lkFileHash = value;
    }

    /**
     * Gets the value of the attchdBinryFile property.
     * 
     * @return
     *     possible object is
     *     {@link BinaryFile1 }
     *     
     */
    public BinaryFile1 getAttchdBinryFile() {
        return attchdBinryFile;
    }

    /**
     * Sets the value of the attchdBinryFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link BinaryFile1 }
     *     
     */
    public void setAttchdBinryFile(BinaryFile1 value) {
        this.attchdBinryFile = value;
    }

}
