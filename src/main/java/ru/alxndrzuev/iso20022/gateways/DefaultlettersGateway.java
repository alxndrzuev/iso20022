package ru.alxndrzuev.iso20022.gateways;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import ru.alxndrzuev.iso20022.documents.letters.LettersGateway;

import javax.naming.OperationNotSupportedException;

@Slf4j
public class DefaultlettersGateway implements LettersGateway {

    @Override
    @SneakyThrows
    public ResponseEntity<String> createLetter(String body) {
        throw new OperationNotSupportedException();
    }

    @Override
    @SneakyThrows
    public ResponseEntity<String> getLetterStatus(String messageId) {
        throw new OperationNotSupportedException();
    }

    @Override
    @SneakyThrows
    public ResponseEntity<String> addAttachment(String name, byte[] data, String letterId) {
        throw new OperationNotSupportedException();
    }
}
