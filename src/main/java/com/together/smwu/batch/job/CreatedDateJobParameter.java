package com.together.smwu.batch.job;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Getter
public class CreatedDateJobParameter {
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public CreatedDateJobParameter(String todayDateStr) {
        YearMonth targetYearMonth = YearMonth.from(
                LocalDate.parse(todayDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate start = targetYearMonth.atDay(1);
        LocalDate end = targetYearMonth.atEndOfMonth();
        this.startDate = start.atTime(0, 0, 0);
        this.endDate = end.atTime(23, 59, 59);
    }
}
