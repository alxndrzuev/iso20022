package ru.alxndrzuev.iso20022.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Service;
import org.springframework.util.DefaultPropertiesPersister;
import ru.alxndrzuev.iso20022.configuration.properties.ApplicationProperties;
import ru.alxndrzuev.iso20022.model.Dialect;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Service
@Slf4j
public class PropertiesPersister {
    public static final String BASE_URL_PROPERTY = "base_url";
    public static final String FIRST_CERTIFICATE_ALIAS_PROPERTY = "first_certificate_alias";
    public static final String SECOND_CERTIFICATE_ALIAS_PROPERTY = "second_certificate_alias";
    public static final String LOGIN_PROPERTY = "login";
    public static final String PASSWORD_PROPERTY = "password";
    public static final String DIALECT_PROPERTY = "dialect";

    @Autowired
    private StandardEnvironment environment;

    @Autowired
    private ApplicationProperties applicationProperties;

    DefaultPropertiesPersister persister = new DefaultPropertiesPersister();

    @SneakyThrows
    public void persist(Properties properties) {
        Path path = Paths.get("config/app.properties");
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        persister.store(properties, new FileOutputStream(path.toFile()), "Configuration for iso20022 application");

        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.replace("URL [file:config/app.properties]", new PropertiesPropertySource("URL [file:config/app.properties]", properties));
        update();
    }

    private void update() {
        MutablePropertySources propertySources = environment.getPropertySources();
        PropertySource ps = propertySources.get("URL [file:config/app.properties]");
        Properties properties = (Properties) ps.getSource();
        applicationProperties.setBaseUrl(properties.getProperty(BASE_URL_PROPERTY));
        applicationProperties.setFirstCertificateAlias(properties.getProperty(FIRST_CERTIFICATE_ALIAS_PROPERTY));
        applicationProperties.setSecondCertificateAlias(properties.getProperty(SECOND_CERTIFICATE_ALIAS_PROPERTY));
        applicationProperties.setLogin(properties.getProperty(LOGIN_PROPERTY));
        applicationProperties.setPassword(properties.getProperty(PASSWORD_PROPERTY));
        if (properties.getProperty(DIALECT_PROPERTY) != null) {
            applicationProperties.setDialect(Dialect.valueOf(properties.getProperty(DIALECT_PROPERTY)));
        }
    }
}
