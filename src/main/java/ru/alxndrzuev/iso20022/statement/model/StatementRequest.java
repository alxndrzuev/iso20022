package ru.alxndrzuev.iso20022.statement.model;

import lombok.Data;

import java.util.Date;

@Data
public class StatementRequest {
    private String requestId;
    private String account;
    private String organizationName;
    private Date dateFrom;
    private Date dateTo;
}
