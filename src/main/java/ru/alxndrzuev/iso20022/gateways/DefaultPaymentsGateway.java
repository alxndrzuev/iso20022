package ru.alxndrzuev.iso20022.gateways;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import ru.alxndrzuev.iso20022.documents.payments.PaymentsGateway;

import javax.naming.OperationNotSupportedException;

@Slf4j
public class DefaultPaymentsGateway implements PaymentsGateway {

    @Override
    @SneakyThrows
    public ResponseEntity<String> createPayment(String body) {
        throw new OperationNotSupportedException();
    }

    @Override
    @SneakyThrows
    public ResponseEntity<String> getPaymentStatus(String messageId) {
        throw new OperationNotSupportedException();
    }
}
