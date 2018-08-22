package ru.alxndrzuev.iso20022.statement.model;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class StatementRequestMessage {
    private String messageId;
    private List<StatementRequest> requests = Lists.newArrayList();
}
