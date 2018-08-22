package ru.alxndrzuev.iso20022.statement.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alxndrzuev.iso20022.crypto.CryptoService;
import ru.alxndrzuev.iso20022.statement.builder.StatementRequestMessageBuilder;
import ru.alxndrzuev.iso20022.statement.model.StatementRequest;
import ru.alxndrzuev.iso20022.statement.model.StatementRequestMessage;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class TestService {

    @Autowired
    private StatementRequestMessageBuilder builder;

    @Autowired
    private CryptoService cryptoService;

    @PostConstruct
    public void init() {
        StatementRequestMessage message = new StatementRequestMessage();
        message.setMessageId(UUID.randomUUID().toString().substring(10));

        StatementRequest request = new StatementRequest();
        request.setRequestId(UUID.randomUUID().toString().substring(10));
        request.setAccount("40702810001300013144");
        request.setOrganizationName("Мир технологий");
        request.setDateFrom(DateUtils.truncate(new Date(), Calendar.HOUR));
        request.setDateTo(new Date());
        message.getRequests().add(request);

        String req = builder.buildRequest(message);
    }
}
