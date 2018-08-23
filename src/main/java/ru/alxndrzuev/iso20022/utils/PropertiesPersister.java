package ru.alxndrzuev.iso20022.utils;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.DefaultPropertiesPersister;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Service
public class PropertiesPersister {

    DefaultPropertiesPersister persister = new DefaultPropertiesPersister();

    @SneakyThrows
    public void persist(Properties properties) {
        Path path = Paths.get("config/app.properties");
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        persister.store(properties, new FileOutputStream(path.toFile()), "Configuration for iso20022 application");
    }

    @SneakyThrows
    public Properties load() {
        Properties properties = new Properties();
        Path path = Paths.get("config/app.properties");
        if (Files.exists(path)) {
            persister.load(properties, new FileInputStream(path.toFile()));
        }
        return properties;
    }
}
