package com.together.smwu.global.config.resttemplate;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    private final RestTemplateBuilder restTemplateBuilder;

    public RestTemplateConfiguration(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Bean
    public RestTemplate localTestTemplate() {
        return restTemplateBuilder.rootUri("http://localhost:8000")
                .additionalInterceptors(new RestTemplateClientHttpRequestInterceptor())
                .errorHandler(new RestTemplateErrorHandler())
                .setConnectTimeout(3 * 60 * 1000) //3분
                .build();
    }
}
