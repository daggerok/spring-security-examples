package com.example;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// tag::content[]
@Configuration
public class RestTemplateCfg {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplateBuilder().basicAuthentication("user", "password")
                                        .build();
    }
}
// end::content[]
