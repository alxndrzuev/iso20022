package ru.alxndrzuev.iso20022.documents.payments;

import org.springframework.http.ResponseEntity;

public interface PaymentsGateway {
    ResponseEntity<String> createPayment(String body);

    ResponseEntity<String> getPaymentStatus(String messageId);
}
