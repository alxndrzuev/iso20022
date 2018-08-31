package ru.alxndrzuev.iso20022.documents.payments.model;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PaymentPacket {
    private String packetId;
    private Date executionDate;
    private String debtorCountry;
    private String debtorInn;
    private String debtorName;
    private String debtorAccount;
    private String debtorAccountCurrency;
    private String debtorBankBic;
    private String debtorBankName;
    private String debtorBankCorrAccount;
    private List<PaymentInstruction> payments = Lists.newArrayList();
}
