package ru.alxndrzuev.iso20022.crypto.impl;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.transforms.params.XPath2FilterContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import ru.CryptoPro.JCPxml.xmldsig.JCPXMLDSigInit;
import ru.alxndrzuev.iso20022.configuration.properties.ApplicationProperties;
import ru.alxndrzuev.iso20022.crypto.CryptoService;
import ru.alxndrzuev.iso20022.crypto.SignatureAlgorithm;
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
    private KeyStore ks;

    @Autowired
    private ApplicationProperties applicationProperties;

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
        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setCoalescing(true);
        dbf.setNamespaceAware(true);

        final DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
        final Document doc = documentBuilder.parse(new InputSource(new StringReader(request)));
        XPath xpath = XPathFactory.newInstance().newXPath();
        NodeList link = (NodeList) xpath.evaluate("/*[local-name()='Document' and namespace-uri()='urn:iso:std:iso:20022:tech:xsd:camt.060.001.03']" +
                        "/*[local-name()='AcctRptgReq' and namespace-uri()='urn:iso:std:iso:20022:tech:xsd:camt.060.001.03']" +
                        "/*[local-name()='SplmtryData' and namespace-uri()='urn:iso:std:iso:20022:tech:xsd:camt.060.001.03']" +
                        "/*[local-name()='Envlp' and namespace-uri()='urn:iso:std:iso:20022:tech:xsd:camt.060.001.03']"
                , doc, XPathConstants.NODESET);
        Element signatureNode = doc.createElement("SngtrSt");
        link.item(0).appendChild(signatureNode);

        for (int i = 0; i < applicationProperties.getCertificateAliases().size(); i++) {
            String certificateAlias = applicationProperties.getCertificateAliases().get(i);
            sign(certificateAlias, doc, signatureNode);
        }

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final TransformerFactory tf = TransformerFactory.newInstance();
        final Transformer trans = tf.newTransformer();
        trans.transform(new DOMSource(doc), new StreamResult(baos));

        return new String(baos.toByteArray());
    }

    @SneakyThrows
    private XMLSignature sign(String alias, Document doc, Element element) {
        PrivateKey privateKey = (PrivateKey) ks.getKey(alias, null);
        X509Certificate cert = (X509Certificate) ks.getCertificate(alias);

        return sign(privateKey, cert, doc, element);
    }

    @SneakyThrows
    private XMLSignature sign(PrivateKey privateKey, X509Certificate cert, Document doc, Element element) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.getSignatureAlgorithmByKey(privateKey.getAlgorithm());
        if (signatureAlgorithm == null) {
            throw new RuntimeException("Unsupported key type " + privateKey.getAlgorithm());
        }
        final XMLSignature sig = new XMLSignature(doc, "", signatureAlgorithm.getSignatureMethod());

        final Transforms transforms = new Transforms(doc);
        transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);
        transforms.addTransform(Transforms.TRANSFORM_C14N_WITH_COMMENTS);
        String filter = "//ds:Signature";
        XPath2FilterContainer xpathC = XPath2FilterContainer.newInstanceSubtract(doc, filter);
        xpathC.setXPathNamespaceContext("dsig-xpath", Transforms.TRANSFORM_XPATH2FILTER);
        Element node = xpathC.getElement();
        transforms.addTransform(Transforms.TRANSFORM_XPATH2FILTER, node);

        element.appendChild(sig.getElement());

        sig.addDocument("", transforms, signatureAlgorithm.getDigestMethod());
        sig.addKeyInfo(cert);
        sig.sign(privateKey);
        return sig;
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