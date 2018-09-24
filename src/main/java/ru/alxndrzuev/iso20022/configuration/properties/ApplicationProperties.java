package ru.alxndrzuev.iso20022.configuration.properties;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.alxndrzuev.iso20022.model.Certificate;
import ru.alxndrzuev.iso20022.model.Dialect;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties
public class ApplicationProperties {
    private List<Certificate> certificates = Lists.newArrayList();
    private String login;
    private String password;
    private String baseUrl;
    private Dialect dialect;

    public ApplicationProperties() {
    }

    public ApplicationProperties(ApplicationProperties applicationProperties) {
        this.certificates = applicationProperties.getCertificates();
        this.login = applicationProperties.getLogin();
        this.password = applicationProperties.getPassword();
        this.baseUrl = applicationProperties.getBaseUrl();
        this.dialect = applicationProperties.getDialect();
    }
}
