package com.together.smwu.global.crawler;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class WebDriverConfiguration {
    @Value("${user.chromedriver}")
    private String webDriverLocation;

    public static String webDriverURL;

    @PostConstruct
    public void initURL() {
        webDriverURL = webDriverLocation;
    }

    public static ChromeConnector createChromeConnector() {
        System.setProperty("webdriver.chrome.driver", webDriverURL);
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
        options.addArguments("--whitelisted-ips");
        options.addArguments("--no-sandbox");
        options.addArguments(
                "--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.122 Safari/537.36");
        options.addArguments("--proxy-server='direct://'");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--proxy-bypass-list=*");
        options.addArguments("--start-maximized");
        options.addArguments("disable-gpu");
        options.addArguments("--lang=ko_KR");
        options.setPageLoadStrategy(PageLoadStrategy.NONE);
        options.addArguments(
                "--Accept=text/html,application/xhtml+xml,application/xml;\\q=0.9,imgwebp,*/*;q=0.8");
        ChromeDriver webDriver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(webDriver, 30);
        return new ChromeConnector(webDriver, wait);
    }
}
