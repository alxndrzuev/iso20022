package ru.alxndrzuev.iso20022.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.cert.X509CertificateHolder;
import ru.alxndrzuev.iso20022.model.Certificate;

@Slf4j
public class CertificateUtils {
    private final static String SNILS_OID = "1.2.643.100.3";
    private final static String INN_OID = "1.2.643.3.131.1.1";
    private final static String OGRN_OID = "1.2.643.100.1";
    private final static String ORG_NAME_OID = "2.5.4.10";
    private final static String SUBJECT_NAME_OID = "2.5.4.3";
    private final static String SIGNING_DATE_OID = "1.2.840.113549.1.9.5";
    private final static String SURNAME_OID = "2.5.4.4";
    private final static String GIVEN_NAME_OID = "2.5.4.42";

    @SneakyThrows
    public static Certificate parseCertificate(byte[] certificateData) {
        Certificate certificate = new Certificate();
        X509CertificateHolder certificateHolder = new X509CertificateHolder(certificateData);
        String inn = getSingleCertificateValue(INN_OID, certificateHolder);
        if (inn != null && inn.startsWith("00")) {
            inn = inn.substring(2, inn.length());
        }
        certificate.setInn(inn);
        certificate.setSnils(getSingleCertificateValue(SNILS_OID, certificateHolder));
        certificate.setOgrn(getSingleCertificateValue(OGRN_OID, certificateHolder));
        certificate.setOrganizationName(getSingleCertificateValue(ORG_NAME_OID, certificateHolder));
        String surname = getSingleCertificateValue(SURNAME_OID, certificateHolder);
        String givenName = getSingleCertificateValue(GIVEN_NAME_OID, certificateHolder);
        if (StringUtils.isNotBlank(surname) && StringUtils.isNotBlank(givenName)) {
            certificate.setSubjectName(new StringBuilder().append(surname).append(" ").append(givenName).toString());
        } else {
            certificate.setSubjectName(getSingleCertificateValue(SUBJECT_NAME_OID, certificateHolder));
        }
        certificate.setName(new StringBuilder()
                .append(certificate.getSubjectName())
                .append(" ")
                .append(certificate.getOrganizationName() != null ? certificate.getOrganizationName() : "")
                .toString());
        return certificate;
    }

    private static String getSingleCertificateValue(String oid, X509CertificateHolder certificate) {
        RDN[] data = certificate.getSubject().getRDNs(new ASN1ObjectIdentifier(oid));
        if (data != null && data.length > 0) {
            return data[0].getFirst().getValue().toString();
        }
        return null;
    }
}
