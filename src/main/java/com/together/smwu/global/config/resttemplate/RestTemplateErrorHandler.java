package com.together.smwu.global.config.resttemplate;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RestTemplateErrorHandler implements ResponseErrorHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean hasError(@NonNull final ClientHttpResponse response) throws IOException {
        final HttpStatus statusCode = response.getStatusCode();
//    response.getBody() 넘겨 받은 body 값으로 적절한 예외 상태 확인 이후 boolean return
        return !statusCode.is2xxSuccessful();
    }

    @Override
    public void handleError(@NonNull final ClientHttpResponse response) throws IOException {
        //    hasError에서 true를 return하면 해당 메서드 실행.
        //    상황에 알맞는 Error handling 로직 작성....
        final String error = getErrorAsString(response);
        logger.error("================");
        logger.error("Headers: {}", response.getHeaders());
        logger.error("Response Status : {}", response.getRawStatusCode());
        logger.error("Request body: {}", error);
        logger.error("================");
    }

    private String getErrorAsString(@NonNull final ClientHttpResponse response) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(response.getBody()))) {
            return br.readLine();
        }
    }
}
