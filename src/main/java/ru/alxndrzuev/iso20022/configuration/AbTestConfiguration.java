package ru.alxndrzuev.iso20022.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.alxndrzuev.iso20022.gateways.ab.AbTestGateway;

@Configuration
@ConditionalOnProperty(prefix = "", name = "dialect", havingValue = "ALFA_BANK", matchIfMissing = true)
public class AbTestConfiguration {

    @Bean
    public AbTestGateway AbTestGateway() {
        return new AbTestGateway();
    }
}
