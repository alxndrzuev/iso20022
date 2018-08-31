
package iso20022.statements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReportingPeriod1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReportingPeriod1">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FrToDt" type="{urn:iso:std:iso:20022:tech:xsd:camt.060.001.03}DatePeriodDetails1"/>
 *         &lt;element name="FrToTm" type="{urn:iso:std:iso:20022:tech:xsd:camt.060.001.03}TimePeriodDetails1"/>
 *         &lt;element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:camt.060.001.03}QueryType3Code"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReportingPeriod1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.03", propOrder = {
    "frToDt",
    "frToTm",
    "tp"
})
public class ReportingPeriod1 {

    @XmlElement(name = "FrToDt", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.03", required = true)
    protected DatePeriodDetails1 frToDt;
    @XmlElement(name = "FrToTm", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.03", required = true)
    protected TimePeriodDetails1 frToTm;
    @XmlElement(name = "Tp", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.03", required = true)
    @XmlSchemaType(name = "string")
    protected QueryType3Code tp;

    /**
     * Gets the value of the frToDt property.
     * 
     * @return
     *     possible object is
     *     {@link DatePeriodDetails1 }
     *     
     */
    public DatePeriodDetails1 getFrToDt() {
        return frToDt;
    }

    /**
     * Sets the value of the frToDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatePeriodDetails1 }
     *     
     */
    public void setFrToDt(DatePeriodDetails1 value) {
        this.frToDt = value;
    }

    /**
     * Gets the value of the frToTm property.
     * 
     * @return
     *     possible object is
     *     {@link TimePeriodDetails1 }
     *     
     */
    public TimePeriodDetails1 getFrToTm() {
        return frToTm;
    }

    /**
     * Sets the value of the frToTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimePeriodDetails1 }
     *     
     */
    public void setFrToTm(TimePeriodDetails1 value) {
        this.frToTm = value;
    }

    /**
     * Gets the value of the tp property.
     * 
     * @return
     *     possible object is
     *     {@link QueryType3Code }
     *     
     */
    public QueryType3Code getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryType3Code }
     *     
     */
    public void setTp(QueryType3Code value) {
        this.tp = value;
    }

}
