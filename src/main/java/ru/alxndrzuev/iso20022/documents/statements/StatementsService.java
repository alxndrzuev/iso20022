package ru.alxndrzuev.iso20022.documents.statements;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.alxndrzuev.iso20022.crypto.CryptoService;
import ru.alxndrzuev.iso20022.documents.statements.model.StatementRequestMessage;

@Service
@Slf4j
public class StatementsService {
    private static final String SIGN_ELEMENT_XPATH = "/*[local-name()='Document' and namespace-uri()='urn:iso:std:iso:20022:tech:xsd:camt.060.001.03']" +
            "/*[local-name()='AcctRptgReq' and namespace-uri()='urn:iso:std:iso:20022:tech:xsd:camt.060.001.03']" +
            "/*[local-name()='SplmtryData' and namespace-uri()='urn:iso:std:iso:20022:tech:xsd:camt.060.001.03']" +
            "/*[local-name()='Envlp' and namespace-uri()='urn:iso:std:iso:20022:tech:xsd:camt.060.001.03']";

    @Autowired
    private StatementRequestMessageBuilder statementRequestMessageBuilder;

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private StatementsGateway gateway;

    public String generateRequest(StatementRequestMessage request, boolean isSigned) {
        String requestString = statementRequestMessageBuilder.buildRequest(request);
        if (isSigned) {
            requestString = cryptoService.signRequest(requestString, SIGN_ELEMENT_XPATH);
        }
        return requestString;
    }

    public ResponseEntity<String> sendRequest(String requestString) {
        return gateway.getStatement(requestString);
    }

    public ResponseEntity<String> getStatementResult(String messageId) {
        return gateway.getStatementResult(messageId);
    }
}
