package ru.alxndrzuev.iso20022.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.alxndrzuev.iso20022.model.Dialect;

@Data
@Configuration
@ConfigurationProperties
@PropertySource("file:config/app.properties")
public class ApplicationProperties {
    private String firstCertificateAlias;
    private String secondCertificateAlias;
    private String login;
    private String password;
    private String baseUrl;
    private Dialect dialect;
}
