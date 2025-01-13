package com.example.test.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Info info =
                new Info()
                        .title("ROSHKA TEST")
                        .version("1.0.0")
                        .description("APIFICAR UNA PAGINA WEB DE NOTICIAS [Mas info detallada de la api](https://docs.google.com/document/d/1MuCuppJveYWvRmHWJdttB47IsoSIeLQF_E8ypp7fhRc/edit?tab=t.0)");

        return new OpenAPI().info(info);
    }
}
