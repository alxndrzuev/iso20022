
package iso20022.letters;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BinaryFile1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BinaryFile1">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MIMETp" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}Max35Text" minOccurs="0"/>
 *         &lt;element name="NcodgTp" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}Max35Text" minOccurs="0"/>
 *         &lt;element name="CharSet" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}Max35Text" minOccurs="0"/>
 *         &lt;element name="InclBinryObjct" type="{urn:iso:std:iso:20022:tech:xsd:auth.026.001.01}Max100KBinary" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BinaryFile1", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01", propOrder = {
    "mimeTp",
    "ncodgTp",
    "charSet",
    "inclBinryObjct"
})
public class BinaryFile1 {

    @XmlElement(name = "MIMETp", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    protected String mimeTp;
    @XmlElement(name = "NcodgTp", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    protected String ncodgTp;
    @XmlElement(name = "CharSet", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    protected String charSet;
    @XmlElement(name = "InclBinryObjct", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
    protected byte[] inclBinryObjct;

    /**
     * Gets the value of the mimeTp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMIMETp() {
        return mimeTp;
    }

    /**
     * Sets the value of the mimeTp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMIMETp(String value) {
        this.mimeTp = value;
    }

    /**
     * Gets the value of the ncodgTp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNcodgTp() {
        return ncodgTp;
    }

    /**
     * Sets the value of the ncodgTp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNcodgTp(String value) {
        this.ncodgTp = value;
    }

    /**
     * Gets the value of the charSet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCharSet() {
        return charSet;
    }

    /**
     * Sets the value of the charSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCharSet(String value) {
        this.charSet = value;
    }

    /**
     * Gets the value of the inclBinryObjct property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getInclBinryObjct() {
        return inclBinryObjct;
    }

    /**
     * Sets the value of the inclBinryObjct property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setInclBinryObjct(byte[] value) {
        this.inclBinryObjct = value;
    }

}
