package com.together.smwu.domain.crawl.application;

import com.together.smwu.domain.crawl.domain.KeyWord;
import com.together.smwu.domain.crawl.dto.NaverCrawlingResult;
import com.together.smwu.global.crawler.WebDriverConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.together.smwu.global.crawler.WebDriverConfiguration.createChromeConnector;

@Service
public class NaverCrawlingService {
    private final WebDriverConfiguration webDriverConfiguration;

    @Autowired
    public NaverCrawlingService(WebDriverConfiguration webDriverConfiguration) {
        this.webDriverConfiguration = webDriverConfiguration;
    }

    public List<NaverCrawlingResult> createCrawlingResult(KeyWord keyWord) {
        NaverCrawler naverCrawler = new NaverCrawler(createChromeConnector());
        return naverCrawler.crawl(keyWord.getName());
    }
}
