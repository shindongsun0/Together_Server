package com.together.smwu.batch.provider;

import com.together.smwu.batch.properties.QuartzProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(QuartzProperties.class)
public class BatchNameProvider {
    private final QuartzProperties quartzProperties;

    public BatchNameProvider(QuartzProperties quartzProperties) {
        this.quartzProperties = quartzProperties;
    }
}
