package com.together.smwu.batch.job;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class CreatedDateJobParameter {
    private final LocalDateTime createdDate;

    public CreatedDateJobParameter(String createdDateStr) {
        this.createdDate = LocalDateTime.parse(
                createdDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
