package ru.alxndrzuev.iso20022.documents.statements;

import org.springframework.http.ResponseEntity;

public interface StatementsGateway {
    ResponseEntity<String> getStatement(String body);

    ResponseEntity<String> getStatementResult(String messageId);
}
