
package iso20022.statements;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FloorLimitType1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FloorLimitType1Code">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CRED"/>
 *     &lt;enumeration value="DEBT"/>
 *     &lt;enumeration value="BOTH"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FloorLimitType1Code", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.03")
@XmlEnum
public enum FloorLimitType1Code {

    CRED,
    DEBT,
    BOTH;

    public String value() {
        return name();
    }

    public static FloorLimitType1Code fromValue(String v) {
        return valueOf(v);
    }

}
