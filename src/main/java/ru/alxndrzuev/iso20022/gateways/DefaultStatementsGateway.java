package ru.alxndrzuev.iso20022.gateways;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import ru.alxndrzuev.iso20022.documents.statements.StatementsGateway;

import javax.naming.OperationNotSupportedException;

@Slf4j
public class DefaultStatementsGateway implements StatementsGateway {

    @Override
    @SneakyThrows
    public ResponseEntity<String> getStatement(String body) {
        throw new OperationNotSupportedException();
    }

    @Override
    @SneakyThrows
    public ResponseEntity<String> getStatementResult(String messageId) {
        throw new OperationNotSupportedException();
    }
}
