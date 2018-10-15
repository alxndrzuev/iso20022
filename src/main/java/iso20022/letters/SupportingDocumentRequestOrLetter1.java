
package iso20022.letters;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SupportingDocumentRequestOrLetter1 complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="SupportingDocumentRequestOrLetter1">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReqOrLttrId" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}Max35Text"/>
 *         &lt;element name="Dt" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}ISODate" minOccurs="0"/>
 *         &lt;element name="Sndr" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}Party28Choice" minOccurs="0"/>
 *         &lt;element name="Rcvr" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}Party28Choice" minOccurs="0"/>
 *         &lt;element name="OrgnlRefs" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}OriginalMessage2" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Sbjt" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}Max140Text"/>
 *         &lt;element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}SupportDocumentType1Code"/>
 *         &lt;element name="Desc" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}Max1025Text" minOccurs="0"/>
 *         &lt;element name="RspnReqrd" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}TrueFalseIndicator"/>
 *         &lt;element name="DueDt" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}ISODate" minOccurs="0"/>
 *         &lt;element name="Attchmnt" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}DocumentGeneralInformation3" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "SupportingDocumentRequestOrLetter1", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", propOrder = {
    "reqOrLttrId",
    "dt",
    "sndr",
    "rcvr",
    "orgnlRefs",
    "sbjt",
    "tp",
    "desc",
    "rspnReqrd",
    "dueDt",
    "attchmnt",
    "splmtryData"
})
public class SupportingDocumentRequestOrLetter1 {

    @XmlElement(name = "ReqOrLttrId", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", required = true)
    protected String reqOrLttrId;
    @XmlElement(name = "Dt", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dt;
    @XmlElement(name = "Sndr", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    protected Party28Choice sndr;
    @XmlElement(name = "Rcvr", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    protected Party28Choice rcvr;
    @XmlElement(name = "OrgnlRefs", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    protected List<OriginalMessage2> orgnlRefs;
    @XmlElement(name = "Sbjt", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", required = true)
    protected String sbjt;
    @XmlElement(name = "Tp", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", required = true)
    @XmlSchemaType(name = "string")
    //HACK
    //Change tp from SupportDocumentType1Code to String
    protected String tp;
    @XmlElement(name = "Desc", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    protected String desc;
    @XmlElement(name = "RspnReqrd", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    protected boolean rspnReqrd;
    @XmlElement(name = "DueDt", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dueDt;
    @XmlElement(name = "Attchmnt", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    protected List<DocumentGeneralInformation3> attchmnt;
    @XmlElement(name = "SplmtryData", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    protected List<SupplementaryData1> splmtryData;

    /**
     * Gets the value of the reqOrLttrId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getReqOrLttrId() {
        return reqOrLttrId;
    }

    /**
     * Sets the value of the reqOrLttrId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setReqOrLttrId(String value) {
        this.reqOrLttrId = value;
    }

    /**
     * Gets the value of the dt property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getDt() {
        return dt;
    }

    /**
     * Sets the value of the dt property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setDt(XMLGregorianCalendar value) {
        this.dt = value;
    }

    /**
     * Gets the value of the sndr property.
     *
     * @return
     *     possible object is
     *     {@link Party28Choice }
     *
     */
    public Party28Choice getSndr() {
        return sndr;
    }

    /**
     * Sets the value of the sndr property.
     *
     * @param value
     *     allowed object is
     *     {@link Party28Choice }
     *
     */
    public void setSndr(Party28Choice value) {
        this.sndr = value;
    }

    /**
     * Gets the value of the rcvr property.
     *
     * @return
     *     possible object is
     *     {@link Party28Choice }
     *
     */
    public Party28Choice getRcvr() {
        return rcvr;
    }

    /**
     * Sets the value of the rcvr property.
     *
     * @param value
     *     allowed object is
     *     {@link Party28Choice }
     *
     */
    public void setRcvr(Party28Choice value) {
        this.rcvr = value;
    }

    /**
     * Gets the value of the orgnlRefs property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orgnlRefs property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrgnlRefs().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OriginalMessage2 }
     *
     *
     */
    public List<OriginalMessage2> getOrgnlRefs() {
        if (orgnlRefs == null) {
            orgnlRefs = new ArrayList<OriginalMessage2>();
        }
        return this.orgnlRefs;
    }

    /**
     * Gets the value of the sbjt property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSbjt() {
        return sbjt;
    }

    /**
     * Sets the value of the sbjt property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSbjt(String value) {
        this.sbjt = value;
    }

    /**
     * Gets the value of the tp property.
     *
     * @return
     *     possible object is
     *     {@link SupportDocumentType1Code }
     *
     */
    public String getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     *
     * @param value
     *     allowed object is
     *     {@link SupportDocumentType1Code }
     *
     */
    public void setTp(String value) {
        this.tp = value;
    }

    /**
     * Gets the value of the desc property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Sets the value of the desc property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDesc(String value) {
        this.desc = value;
    }

    /**
     * Gets the value of the rspnReqrd property.
     *
     */
    public boolean isRspnReqrd() {
        return rspnReqrd;
    }

    /**
     * Sets the value of the rspnReqrd property.
     *
     */
    public void setRspnReqrd(boolean value) {
        this.rspnReqrd = value;
    }

    /**
     * Gets the value of the dueDt property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getDueDt() {
        return dueDt;
    }

    /**
     * Sets the value of the dueDt property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setDueDt(XMLGregorianCalendar value) {
        this.dueDt = value;
    }

    /**
     * Gets the value of the attchmnt property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attchmnt property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttchmnt().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentGeneralInformation3 }
     *
     *
     */
    public List<DocumentGeneralInformation3> getAttchmnt() {
        if (attchmnt == null) {
            attchmnt = new ArrayList<DocumentGeneralInformation3>();
        }
        return this.attchmnt;
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
