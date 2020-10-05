package com.together.smwu.domain.crawl.application;

import com.together.smwu.domain.crawl.dto.NaverCrawlingResult;
import com.together.smwu.global.crawler.ChromeConnector;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NaverCrawler {
    private static final Logger logger = LoggerFactory.getLogger(NaverCrawler.class);
    private final ChromeConnector connector;
    private static final String BASE_URL = "https://search.naver.com/search.naver?where=post&sm=tab_jum&query=%s";

    public NaverCrawler(ChromeConnector connector) {
        this.connector = connector;
    }

    public List<NaverCrawlingResult> crawl(String keyWord) {
        String keyWordUrl = String.format(BASE_URL, keyWord);
        connector.get(keyWordUrl);
        List<String> pageUrls = getPageUrls();
        return getNaverDtoFromPageUrls(pageUrls, keyWord);
    }

    private List<NaverCrawlingResult> getNaverDtoFromPageUrls(List<String> pageUrls, String keyWord) {
        List<NaverCrawlingResult> naverCrawlingResults = new ArrayList<>();
        for (String pageUrl : pageUrls) {
            connector.get(pageUrl);
            connector.deleteCookies();
            int liCount = countLiElements();
            naverCrawlingResults.addAll(findDetailInfoFromLiMap(liCount, keyWord));
        }
        connector.getWebDriver().quit();
        return naverCrawlingResults;
    }

    private List<String> getPageUrls() {
        WebElement pagination;
        try {
            pagination = connector.getWebElementByXpath("/html/body/div[3]/div[2]/div/div[1]/div[3]");
        } catch (TimeoutException timeoutException) {
            pagination = connector.getWebElementByXpath("/html/body/div[3]/div[2]/div/div[1]/div[2]");
        }
        List<WebElement> pageWebElements = pagination.findElements(By.xpath(".//a[@href!='']"));
//        List<String> pageUrls = pageWebElements.stream()
//                .map(pageWebElement -> pageWebElement.getAttribute("href"))
//                .collect(Collectors.toList());
        List<String> pageUrls = new ArrayList<>();
        pageUrls.add(0, connector.getCurrentUrl());
        return pageUrls;
    }

    private List<NaverCrawlingResult> findDetailInfoFromLiMap(int liCount, String keyWord) {
        String mapXpath = "/html/body/div[3]/div[2]/div/div[1]/div[2]/ul/li[%s]/dl/dd[3]/span/a[4]";
        try {
            connector.waitUntilByXpath(String.format(mapXpath, 1));
        } catch (TimeoutException timeoutException) {
            mapXpath = "/html/body/div[3]/div[2]/div/div[1]/div[1]/ul/li[%s]/dl/dd[3]/span/a[4]";
        }
        List<String> mapPaths = mapIntegerToString(liCount, mapXpath);
        return mapPaths.stream()
                .map(mapPath -> getNaverDto(mapPath, keyWord))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<NaverCrawlingResult> getNaverDto(String mapPath, String keyWord) {
        try {
            WebElement placeMap = connector.getWebElementByXpath(mapPath);
            connector.clickElement(placeMap);
            connector.getWebDriver().switchTo().frame("mapLayerFrame");
            String placeName = connector.getWebElementByXpath("/html/body/div/div[1]/h1/a").getText();
            String author = getAuthor();
            String placeLocation = connector.getWebElementByXpath("/html/body/div/div[3]/address").getText();
            connector.getWebDriver().switchTo().parentFrame();
            connector.clickElement(placeMap);
            return Optional.of(NaverCrawlingResult.builder()
                    .keyWord(keyWord)
                    .placeName(placeName)
                    .placeLocation(placeLocation)
                    .author(author)
                    .build());
        } catch (TimeoutException timeoutException) {
            logger.error("[Naver Crawler]: 약도가 존재하지 않는 게시물입니다.");
            return Optional.empty();
        }
    }

    private String getAuthor() {
        return connector.getWebElementByXpath("/html/body/div/div[1]/h1/a")
                .getAttribute("href");
    }

    private List<String> mapIntegerToString(int count, String location) {
        int current = 0;
        List<String> locations = new ArrayList<>();
        while (current < count) {
            locations.add(String.format(location, ++current));
        }
        return locations;
    }

    private int countLiElements() {
        WebElement ulElement;
        try {
            ulElement = connector.getWebElementByXpath(
                    "/html/body/div[3]/div[2]/div/div[1]/div[2]/ul");
        }catch(TimeoutException timeoutException){
            ulElement = connector.getWebElementByXpath(
                    "/html/body/div[3]/div[2]/div/div[1]/div[1]/ul");
        }
        return ulElement.findElements(By.xpath(".//li")).size();
    }
}
