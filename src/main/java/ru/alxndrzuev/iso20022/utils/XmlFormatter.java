package ru.alxndrzuev.iso20022.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Utility Class for formatting XML
 *
 * @author Pankaj
 */
@Service
@Slf4j
public class XmlFormatter {

    private DocumentBuilder db;

    @PostConstruct
    @SneakyThrows
    public void init() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        db = dbf.newDocumentBuilder();
    }

    @SneakyThrows
    public String format(String input) {
        if (StringUtils.isBlank(input)) {
            return input;
        }

        Document doc = db.parse(new ByteArrayInputStream(input.getBytes()));

        try {
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            Writer out = new StringWriter();
            tf.transform(new DOMSource(doc), new StreamResult(out));
            return out.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}