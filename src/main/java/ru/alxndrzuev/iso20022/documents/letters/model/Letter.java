package ru.alxndrzuev.iso20022.documents.letters.model;

import lombok.Data;

@Data
public class Letter {
    private String id;
    private String senderName;
    private String senderInn;
    private String senderAccount;
    private String receiverName;
    private String receiverBic;
    private String subject;
    private LetterCategory category;
    private String body;

    public enum LetterCategory {
        OTHR;
    }
}