package com.example;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

@Configuration
public class RestTemplateCfg {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }
}
