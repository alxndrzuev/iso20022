package ru.alxndrzuev.iso20022.documents.payments.model;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class PaymentMessage {
    private String messageId;
    private String agentName;
    private String agentInn;

    private List<PaymentPacket> paymentPackets = Lists.newArrayList();
}
