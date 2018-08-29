package ru.alxndrzuev.iso20022.documents.payments.model;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class PaymentRequestMessage {
    private String messageId;
    private String agentName;
    private String agentInn;

    private List<PaymentRequest> requests = Lists.newArrayList();
}
