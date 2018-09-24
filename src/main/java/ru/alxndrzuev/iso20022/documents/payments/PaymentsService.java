package ru.alxndrzuev.iso20022.documents.payments;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.alxndrzuev.iso20022.crypto.CryptoService;
import ru.alxndrzuev.iso20022.documents.payments.model.PaymentMessage;

@Service
@Slf4j
public class PaymentsService {
    private static final String SIGN_ELEMENT_XPATH = "/*[local-name()='Document' and namespace-uri()='urn:iso:std:iso:20022:tech:xsd:pain.001.001.06']" +
            "/*[local-name()='CstmrCdtTrfInitn' and namespace-uri()='urn:iso:std:iso:20022:tech:xsd:pain.001.001.06']" +
            "/*[local-name()='SplmtryData' and namespace-uri()='urn:iso:std:iso:20022:tech:xsd:pain.001.001.06']" +
            "/*[local-name()='Envlp' and namespace-uri()='urn:iso:std:iso:20022:tech:xsd:pain.001.001.06']";

    @Autowired
    private PaymentsRequestMessageBuilder paymentsRequestMessageBuilder;

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private PaymentsGateway gateway;

    public String generateRequest(PaymentMessage request, boolean isSigned) {
        String requestString = paymentsRequestMessageBuilder.buildRequest(request);
        if (isSigned) {
            requestString = cryptoService.signRequest(requestString, SIGN_ELEMENT_XPATH);
        }
        return requestString;
    }

    public ResponseEntity<String> sendRequest(String requestString) {
        return gateway.createPayment(requestString);
    }

    public ResponseEntity<String> getPaymentStatus(String messageId, String instructionId) {
        return gateway.getPaymentStatus(messageId);
    }
}
