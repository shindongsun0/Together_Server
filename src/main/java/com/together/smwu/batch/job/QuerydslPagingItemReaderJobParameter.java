package com.together.smwu.batch.job;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class QuerydslPagingItemReaderJobParameter {
    private LocalDate txDate;

    @Value("#{jobParameters[txDate]}")
    public void setTxDate(String txDate){
        this.txDate = LocalDate.parse(txDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
