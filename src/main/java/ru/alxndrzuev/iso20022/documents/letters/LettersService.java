package ru.alxndrzuev.iso20022.documents.letters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.alxndrzuev.iso20022.crypto.CryptoService;
import ru.alxndrzuev.iso20022.documents.letters.model.Letter;
import ru.alxndrzuev.iso20022.documents.letters.model.LetterMessage;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class LettersService {
    private static final String SIGN_ELEMENT_XPATH = "/*[local-name()='Document' and namespace-uri()='urn:iso:std:iso:20022:tech:xsd:auth.026.001.01']" +
            "/*[local-name()='CcyCtrlReqOrLttr' and namespace-uri()='urn:iso:std:iso:20022:tech:xsd:auth.026.001.01']" +
            "/*[local-name()='SplmtryData' and namespace-uri()='urn:iso:std:iso:20022:tech:xsd:auth.026.001.01']" +
            "/*[local-name()='Envlp' and namespace-uri()='urn:iso:std:iso:20022:tech:xsd:auth.026.001.01']";

    @Autowired
    private LettersRequestMessageBuilder lettersRequestMessageBuilder;

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private LettersGateway gateway;

    public String generateRequest(LetterMessage request, boolean isSigned) {
        String requestString = lettersRequestMessageBuilder.buildRequest(request);
        if (isSigned) {
            requestString = cryptoService.signRequest(requestString, SIGN_ELEMENT_XPATH);
        }
        return requestString;
    }

    public ResponseEntity<String> sendRequest(String requestString) {
        return gateway.createLetter(requestString);
    }

    public ResponseEntity<String> getLetterStatus(String messageId, String instructionId) {
        return gateway.getLetterStatus(messageId);
    }
}
