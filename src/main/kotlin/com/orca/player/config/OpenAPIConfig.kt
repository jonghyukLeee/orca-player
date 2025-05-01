package com.orca.player.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("ORCA FC - Player Service")
                    .description("ORCA FC Player 서비스 OpenAPI 문서")
                    .version("1.0.0")
            )
    }
}
