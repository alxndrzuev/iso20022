package ru.alxndrzuev.iso20022.documents.letters.model;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class LetterMessage {
    private String messageId;
    private String agentName;
    private String agentInn;

    private List<Letter> letters = Lists.newArrayList();
}
