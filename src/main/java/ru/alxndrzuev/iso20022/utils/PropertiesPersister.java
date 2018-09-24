package ru.alxndrzuev.iso20022.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Service;
import ru.alxndrzuev.iso20022.configuration.properties.ApplicationProperties;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class PropertiesPersister {
    private static final String CONFIG_FILE_NAME = "config/application.yml";

    @Autowired
    private StandardEnvironment environment;

    @Autowired
    private ApplicationProperties applicationProperties;

    ObjectMapper om = new ObjectMapper(new YAMLFactory());

    @SneakyThrows
    public void persist(ApplicationProperties applicationProperties) {
        Path path = Paths.get(CONFIG_FILE_NAME);
        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        }
        Files.write(path, om.writeValueAsBytes(applicationProperties));
    }
}
