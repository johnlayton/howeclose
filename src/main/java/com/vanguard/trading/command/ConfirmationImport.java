package com.vanguard.trading.command;

import static net.logstash.logback.argument.StructuredArguments.kv;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanguard.trading.domain.xml.RequestConfirmation;
import com.vanguard.trading.service.ConfirmationService;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class ConfirmationImport implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmationImport.class);

    private final Resource[] files;

    private final ObjectMapper mapper;

    private final ConfirmationService service;

    @Autowired
    public ConfirmationImport(@Value("classpath*:events/*.xml") Resource[] files,
                              @Qualifier("xmlMapper") ObjectMapper mapper,
                              ConfirmationService service) {
        this.mapper = mapper;
        this.files = files;
        this.service = service;
    }

    @Override
    public void run(String... args) {
        Arrays.stream(files)
            .map(path -> {
                try (InputStream stream = path.getInputStream()) {
                    LOGGER.info("Importing trade {}", kv("file", path));
                    return mapper.readValue(stream, RequestConfirmation.class);
                } catch (IOException exception) {
                    LOGGER.error("Exception parsing confirmation {}", kv("file", path), exception);
                }
                return null;
            })
            .filter(Objects::nonNull)
            .forEach(confirmation -> {
                try {
                    service.save(confirmation);
                } catch (Exception exception) {
                    LOGGER.error("Exception importing trade", exception);
                }
            });
    }
}
