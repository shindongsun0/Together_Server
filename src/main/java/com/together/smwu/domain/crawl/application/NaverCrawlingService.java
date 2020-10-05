package com.together.smwu.domain.crawl.application;

import com.together.smwu.domain.crawl.dao.NaverRepository;
import com.together.smwu.domain.crawl.domain.KeyWord;
import com.together.smwu.domain.crawl.dto.NaverCrawlingResult;
import com.together.smwu.global.crawler.WebDriverConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.together.smwu.global.crawler.WebDriverConfiguration.createChromeConnector;

@Service
public class NaverCrawlingService {
    private final NaverRepository naverRepository;
    private final WebDriverConfiguration webDriverConfiguration;

    @Autowired
    public NaverCrawlingService(NaverRepository naverRepository, WebDriverConfiguration webDriverConfiguration) {
        this.naverRepository = naverRepository;
        this.webDriverConfiguration = webDriverConfiguration;
    }

    public List<NaverCrawlingResult> createCrawlingResult(KeyWord keyWord) {
        NaverCrawler naverCrawler = new NaverCrawler(createChromeConnector());
        return naverCrawler.crawl(keyWord.getName());
    }
}
