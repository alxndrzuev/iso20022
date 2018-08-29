package ru.alxndrzuev.iso20022.documents.payments.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PaymentInstruction {
    private String instructionId;
    private String documentId;
    private PaymentUrgency urgency;
    private String priority;
    private String uin;
    private String description;
    private Date documentDate;
    private BigDecimal amount;
    private String creditorBankBic;
    private String creditorBankName;
    private String creditorBankCorrAccount;
    private String creditorName;
    private String creditorCountry;
    private String creditorInn;
    private String creditorAccount;
    private String creditorAccountCurrency;

    public enum PaymentUrgency {
        NURG,
        URGP;
    }
}