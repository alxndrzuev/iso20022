package ru.alxndrzuev.iso20022.documents.letters;

import org.springframework.http.ResponseEntity;

public interface LettersGateway {
    ResponseEntity<String> createLetter(String body);

    ResponseEntity<String> getLetterStatus(String messageId);

    ResponseEntity<String> addAttachment(String name, byte[] data, String letterId);
}
