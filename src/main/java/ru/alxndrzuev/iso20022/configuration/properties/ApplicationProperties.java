package ru.alxndrzuev.iso20022.configuration.properties;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.alxndrzuev.iso20022.model.Dialect;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties
@PropertySource(value = "file:config/app.properties", ignoreResourceNotFound = true)
public class ApplicationProperties {
    private List<String> CertificateAliases = Lists.newArrayList();
    private String login;
    private String password;
    private String baseUrl;
    private Dialect dialect;
}
