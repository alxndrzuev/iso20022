package ru.alxndrzuev.iso20022.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.alxndrzuev.iso20022.documents.letters.LettersGateway;
import ru.alxndrzuev.iso20022.documents.payments.PaymentsGateway;
import ru.alxndrzuev.iso20022.documents.statements.StatementsGateway;
import ru.alxndrzuev.iso20022.gateways.DefaultLettersGateway;
import ru.alxndrzuev.iso20022.gateways.DefaultPaymentsGateway;
import ru.alxndrzuev.iso20022.gateways.DefaultStatementsGateway;

@Configuration
public class DefaultConfiguration {

    @Bean
    @ConditionalOnMissingBean(PaymentsGateway.class)
    public PaymentsGateway PaymentGateway() {
        return new DefaultPaymentsGateway();
    }


    @Bean
    @ConditionalOnMissingBean(StatementsGateway.class)
    public StatementsGateway StatementsGateway() {
        return new DefaultStatementsGateway();
    }

    @Bean
    @ConditionalOnMissingBean(LettersGateway.class)
    public LettersGateway LettersGateway() {
        return new DefaultLettersGateway();
    }
}
