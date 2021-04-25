package com.vanguard.trading.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.stereotype.Component;

@OpenAPIDefinition(
    info = @Info(
        title = "**The Title Is Missing",
        description = "**The Description Is Missing",
        version = "**The Version Is Missing"
    ),
    servers = {
        @Server(
            description = "localhost",
            url = "http://localhost:8080"
        )
    }
)
@Component
public class ApiDefinition {
}
