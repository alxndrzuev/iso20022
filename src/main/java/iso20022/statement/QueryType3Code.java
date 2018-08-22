
package iso20022.statement;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QueryType3Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="QueryType3Code">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ALLL"/>
 *     &lt;enumeration value="CHNG"/>
 *     &lt;enumeration value="MODF"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "QueryType3Code", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.060.001.03")
@XmlEnum
public enum QueryType3Code {

    ALLL,
    CHNG,
    MODF;

    public String value() {
        return name();
    }

    public static QueryType3Code fromValue(String v) {
        return valueOf(v);
    }

}
