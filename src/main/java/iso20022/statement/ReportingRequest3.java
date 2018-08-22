
package iso20022.statement;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReportingRequest3 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReportingRequest3">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{urn:iso:std:iso:20022:tech:xsd:camt.060.001.03}Max35Text" minOccurs="0"/>
 *         &lt;element name="ReqdMsgNmId" type="{urn:iso:std:iso:20022:tech:xsd:camt.060.001.03}Max35Text"/>
 *         &lt;element name="Acct" type="{urn:iso:std:iso:20022:tech:xsd:camt.060.001.03}CashAccount24" minOccurs="0"/>
 *         &lt;element name="AcctOwnr" type="{urn:iso:std:iso:20022:tech:xsd:camt.060.001.03}Party12Choice"/>
 *         &lt;element name="AcctSvcr" type="{urn:iso:std:iso:20022:tech:xsd:camt.060.001.03}BranchAndFinancialInstitutionIdentification5" minOccurs="0"/>
 *         &lt;element name="RptgPrd" type="{urn:iso:std:iso:20022:tech:xsd:camt.060.001.03}ReportingPeriod1" minOccurs="0"/>
 *         &lt;element name="ReqdTxTp" type="{urn:iso:std:iso:20022:tech:xsd:camt.060.001.03}TransactionType1" minOccurs="0"/>
 *         &lt;element name="ReqdBalTp" type="{urn:iso:std:iso:20022:tech:xsd:camt.060.001.03}BalanceType12" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReportingRequest3", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.03", propOrder = {
    "id",
    "reqdMsgNmId",
    "acct",
    "acctOwnr",
    "acctSvcr",
    "rptgPrd",
    "reqdTxTp",
    "reqdBalTp"
})
public class ReportingRequest3 {

    @XmlElement(name = "Id", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.03")
    protected String id;
    @XmlElement(name = "ReqdMsgNmId", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.03", required = true)
    protected String reqdMsgNmId;
    @XmlElement(name = "Acct", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.03")
    protected CashAccount24 acct;
    @XmlElement(name = "AcctOwnr", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.03", required = true)
    protected Party12Choice acctOwnr;
    @XmlElement(name = "AcctSvcr", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.03")
    protected BranchAndFinancialInstitutionIdentification5 acctSvcr;
    @XmlElement(name = "RptgPrd", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.03")
    protected ReportingPeriod1 rptgPrd;
    @XmlElement(name = "ReqdTxTp", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.03")
    protected TransactionType1 reqdTxTp;
    @XmlElement(name = "ReqdBalTp", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.03")
    protected List<BalanceType12> reqdBalTp;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the reqdMsgNmId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReqdMsgNmId() {
        return reqdMsgNmId;
    }

    /**
     * Sets the value of the reqdMsgNmId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReqdMsgNmId(String value) {
        this.reqdMsgNmId = value;
    }

    /**
     * Gets the value of the acct property.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount24 }
     *     
     */
    public CashAccount24 getAcct() {
        return acct;
    }

    /**
     * Sets the value of the acct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount24 }
     *     
     */
    public void setAcct(CashAccount24 value) {
        this.acct = value;
    }

    /**
     * Gets the value of the acctOwnr property.
     * 
     * @return
     *     possible object is
     *     {@link Party12Choice }
     *     
     */
    public Party12Choice getAcctOwnr() {
        return acctOwnr;
    }

    /**
     * Sets the value of the acctOwnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Party12Choice }
     *     
     */
    public void setAcctOwnr(Party12Choice value) {
        this.acctOwnr = value;
    }

    /**
     * Gets the value of the acctSvcr property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getAcctSvcr() {
        return acctSvcr;
    }

    /**
     * Sets the value of the acctSvcr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setAcctSvcr(BranchAndFinancialInstitutionIdentification5 value) {
        this.acctSvcr = value;
    }

    /**
     * Gets the value of the rptgPrd property.
     * 
     * @return
     *     possible object is
     *     {@link ReportingPeriod1 }
     *     
     */
    public ReportingPeriod1 getRptgPrd() {
        return rptgPrd;
    }

    /**
     * Sets the value of the rptgPrd property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportingPeriod1 }
     *     
     */
    public void setRptgPrd(ReportingPeriod1 value) {
        this.rptgPrd = value;
    }

    /**
     * Gets the value of the reqdTxTp property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionType1 }
     *     
     */
    public TransactionType1 getReqdTxTp() {
        return reqdTxTp;
    }

    /**
     * Sets the value of the reqdTxTp property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionType1 }
     *     
     */
    public void setReqdTxTp(TransactionType1 value) {
        this.reqdTxTp = value;
    }

    /**
     * Gets the value of the reqdBalTp property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reqdBalTp property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReqdBalTp().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BalanceType12 }
     * 
     * 
     */
    public List<BalanceType12> getReqdBalTp() {
        if (reqdBalTp == null) {
            reqdBalTp = new ArrayList<BalanceType12>();
        }
        return this.reqdBalTp;
    }

}
