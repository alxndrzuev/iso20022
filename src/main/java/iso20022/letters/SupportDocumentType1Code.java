
package iso20022.letters;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SupportDocumentType1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SupportDocumentType1Code">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="LFBK"/>
 *     &lt;enumeration value="LTBK"/>
 *     &lt;enumeration value="SUPP"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SupportDocumentType1Code", namespace = "urn:iso:std:iso:20022:tech:xsd:auth.026.001.01")
@XmlEnum
public enum SupportDocumentType1Code {

    LFBK,
    LTBK,
    SUPP;

    public String value() {
        return name();
    }

    public static SupportDocumentType1Code fromValue(String v) {
        return valueOf(v);
    }

}
