package com.together.smwu.batch.processor;

import com.together.smwu.domain.crawl.application.NaverCrawlingService;
import com.together.smwu.domain.crawl.domain.KeyWord;
import com.together.smwu.domain.crawl.dto.NaverCrawlingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NaverCrawlProcessor implements ItemProcessor<KeyWord, List<NaverCrawlingResult>> {
    private static final Logger logger = LoggerFactory.getLogger(NaverCrawlProcessor.class);
    private final NaverCrawlingService naverCrawlingService;

    public NaverCrawlProcessor(NaverCrawlingService naverCrawlingService) {
        this.naverCrawlingService = naverCrawlingService;
    }

    @Override
    public List<NaverCrawlingResult> process(KeyWord keyWord) throws Exception {
        return naverCrawlingService.createCrawlingResult(keyWord);
    }
}
