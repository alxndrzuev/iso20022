package ru.alxndrzuev.iso20022.crypto.impl;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import ru.CryptoPro.JCPxml.xmldsig.JCPXMLDSigInit;
import ru.alxndrzuev.iso20022.crypto.CryptoService;
import ru.alxndrzuev.iso20022.utils.CertificateUtils;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.List;

@Service
@Slf4j
public class CryptoproCryptoService implements CryptoService {
    final private String signMethod = "urn:ietf:params:xml:ns:cpxmlsec:algorithms:gostr34102012-gostr34112012-256";
    final private String digestMethod = "urn:ietf:params:xml:ns:cpxmlsec:algorithms:gostr34112012-256";

    private KeyStore ks;

    @PostConstruct
    @SneakyThrows
    public void init() {
        JCPXMLDSigInit.init();

        ks = KeyStore.getInstance("HDImageStore");
        ks.load(null, null);
    }

    @Override
    @SneakyThrows
    public String signRequest(String request) {
        PrivateKey privateKey = (PrivateKey) ks.getKey("e57a9a52-f6c0-ce3b-0412-2188e5f720b1", null);
        X509Certificate cert = (X509Certificate) ks.getCertificate("e57a9a52-f6c0-ce3b-0412-2188e5f720b1");

        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setCoalescing(true);
        dbf.setNamespaceAware(true);

        final DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
        final Document doc = documentBuilder.parse(new InputSource(new StringReader(request)));

        final XMLSignature sig = new XMLSignature(doc, "", signMethod);
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList link = (NodeList) xpath.evaluate("/*[local-name()='Document' and namespace-uri()='urn:iso:std:iso:20022:tech:xsd:camt.060.001.03']" +
                        "/*[local-name()='AcctRptgReq' and namespace-uri()='urn:iso:std:iso:20022:tech:xsd:camt.060.001.03']" +
                        "/*[local-name()='SplmtryData' and namespace-uri()='urn:iso:std:iso:20022:tech:xsd:camt.060.001.03']" +
                        "/*[local-name()='Envlp' and namespace-uri()='urn:iso:std:iso:20022:tech:xsd:camt.060.001.03']" +
                        "/*[local-name()='SngtrSt' and namespace-uri()='urn:iso:std:iso:20022:tech:xsd:camt.060.001.03']"
                , doc, XPathConstants.NODESET);
        link.item(0).appendChild(sig.getElement());

        final Transforms transforms = new Transforms(doc);
        transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);
        transforms.addTransform(Transforms.TRANSFORM_C14N_WITH_COMMENTS);

        sig.addDocument("", transforms, digestMethod);
        sig.addKeyInfo(cert);
        sig.sign(privateKey);

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final TransformerFactory tf = TransformerFactory.newInstance();
        final Transformer trans = tf.newTransformer();
        trans.transform(new DOMSource(doc), new StreamResult(baos));

        return new String(baos.toByteArray());
    }

    @Override
    @SneakyThrows
    public List<ru.alxndrzuev.iso20022.model.Certificate> getAvailableCertificates() {
        List<ru.alxndrzuev.iso20022.model.Certificate> certificates = Lists.newArrayList();
        Enumeration<String> aliases = ks.aliases();
        while (aliases.hasMoreElements()) {
            try {
                String alias = aliases.nextElement();
                Certificate certificate = ks.getCertificate(alias);
                Key key = ks.getKey(alias, null);
                if (certificate != null && key != null) {
                    ru.alxndrzuev.iso20022.model.Certificate cert = CertificateUtils.parseCertificate(certificate.getEncoded());
                    cert.setId(alias);
                    certificates.add(cert);
                }
            } catch (Exception e) {
                log.warn("Can not get certificates. Exception:", e.getCause());
            }
        }

        return certificates;
    }


}