package com.together.smwu.global.config.resttemplate;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class RestTemplateClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @NonNull
    @Override
    public ClientHttpResponse intercept(@NonNull final HttpRequest request,
                                        @NonNull final byte[] body, final @NonNull ClientHttpRequestExecution execution)
            throws IOException {
        final ClientHttpResponse response = execution.execute(request, body);

        loggingResponse(response);
        loggingRequest(request, body);
        return execution.execute(request, body);
    }

    private void loggingRequest(final HttpRequest request, byte[] body) {
        logger.info("=====Request======");
        logger.info("Headers: {}", request.getHeaders());
        logger.info("Request Method: {}", request.getMethod());
        logger.info("Request URI: {}", request.getURI());
        logger.info("Request body: {}",
                body.length == 0 ? null : new String(body, StandardCharsets.UTF_8));
        logger.info("=====Request======");
    }

    private void loggingResponse(ClientHttpResponse response) throws IOException {

        final String body = getBody(response);

        logger.info("======Response=======");
        logger.info("Headers: {}", response.getHeaders());
        logger.info("Response Status : {}", response.getRawStatusCode());
        logger.info("Request body: {}", body);
        logger.info("======Response=======");

    }

    private String getBody(@NonNull final ClientHttpResponse response) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(response.getBody()))) {
            return br.readLine();
        }
    }
}
