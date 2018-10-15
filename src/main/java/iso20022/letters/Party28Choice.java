
package iso20022.letters;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Party28Choice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Party28Choice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="Pty" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}PartyIdentification77"/>
 *         &lt;element name="Agt" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}BranchAndFinancialInstitutionIdentification5"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Party28Choice", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", propOrder = {
    "pty",
    "agt"
})
public class Party28Choice {

    @XmlElement(name = "Pty", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    protected PartyIdentification77 pty;
    @XmlElement(name = "Agt", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    protected BranchAndFinancialInstitutionIdentification5 agt;

    /**
     * Gets the value of the pty property.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification77 }
     *     
     */
    public PartyIdentification77 getPty() {
        return pty;
    }

    /**
     * Sets the value of the pty property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification77 }
     *     
     */
    public void setPty(PartyIdentification77 value) {
        this.pty = value;
    }

    /**
     * Gets the value of the agt property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification5 getAgt() {
        return agt;
    }

    /**
     * Sets the value of the agt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification5 }
     *     
     */
    public void setAgt(BranchAndFinancialInstitutionIdentification5 value) {
        this.agt = value;
    }

}
