package ru.alxndrzuev.iso20022.documents.payments.model;

import com.google.common.collect.Lists;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class PaymentRequest {
    private String requestId;
    private Date executionDate;
    private String debitorCountry;
    private String debtorInn;
    private String debtorName;
    private String debtorAccount;
    private String debtorAccountCurrency;
    private String debtorBankBic;
    private String debtorBankName;
    private String debtorBankCorrAccount;
    private List<Payment> payments = Lists.newArrayList();

    @Data
    public static class Payment {
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
    }

    public enum PaymentUrgency {
        NURG,
        URGP;
    }
}
