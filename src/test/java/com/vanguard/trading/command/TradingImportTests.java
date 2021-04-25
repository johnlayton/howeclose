package com.vanguard.trading.command;

import static java.lang.Boolean.TRUE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.io.CharSource;
import com.vanguard.trading.config.TradingConfiguration;
import com.vanguard.trading.domain.xml.RequestConfirmation;
import com.vanguard.trading.service.ConfirmationService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;

@ExtendWith(MockitoExtension.class)
class TradingImportTests {

    @ParameterizedTest
    @MethodSource("malformedEvents")
    public void shouldAttemptImportMalformedEvents(final String event) throws Exception {
        final TradingConfiguration configuration = new TradingConfiguration();
        final Resource resource = mock(Resource.class);
        final ConfirmationService service = mock(ConfirmationService.class);

        final ConfirmationImport command = new ConfirmationImport(new Resource[] {resource},
            configuration.xmlMapper(), service);

        when(resource.getInputStream())
            .thenReturn(CharSource.wrap(event).asByteSource(StandardCharsets.UTF_8).openStream());

        command.run();

        verify(service, times(1))
            .save(any(RequestConfirmation.class));
    }

    @ParameterizedTest
    @MethodSource("invalidEvents")
    public void shouldNotAttemptImportInvalidEvents(final String event) throws Exception {
        final TradingConfiguration configuration = new TradingConfiguration();
        final Resource resource = mock(Resource.class);
        final ConfirmationService service = mock(ConfirmationService.class);

        final ConfirmationImport command = new ConfirmationImport(new Resource[] {resource},
            configuration.xmlMapper(), service);

        when(resource.getInputStream())
            .thenReturn(CharSource.wrap(event).asByteSource(StandardCharsets.UTF_8).openStream());

        command.run();

        verify(service, never())
            .save(any(RequestConfirmation.class));
    }

    private static Stream<String> malformedEvents() throws Exception {
        final String directory = TradingImportTests.class.getResource("/events-malformed").getFile();
        return StreamSupport.stream(Files.newDirectoryStream(Path.of(directory)).spliterator(), TRUE)
            .map(path -> {
                try {
                    return Files.readString(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            })
            .filter(Objects::nonNull);
    }

    private static Stream<String> invalidEvents() throws Exception {
        final String directory = TradingImportTests.class.getResource("/events-invalid").getFile();
        return StreamSupport.stream(Files.newDirectoryStream(Path.of(directory)).spliterator(), TRUE)
            .map(path -> {
                try {
                    return Files.readString(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            })
            .filter(Objects::nonNull);
    }
}
