package com.together.smwu.global.crawler;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Getter
public class ChromeConnector {

    private final ChromeDriver webDriver;
    private final WebDriverWait wait;

    public ChromeConnector(ChromeDriver webDriver, WebDriverWait wait) {
        this.webDriver = webDriver;
        this.wait = wait;
    }

    public void get(String pageLink) {
        webDriver.get(pageLink);
    }

    public WebElement getWebElementByXpath(String xpath) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        return webDriver.findElement(By.xpath(xpath));
    }

    public void waitUntilByXpath(String xpath) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public void waitUntilByTag(String tag) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(tag)));
    }

    public WebElement findElementByXpath(String tag) {
        return webDriver.findElement(By.xpath(tag));
    }

    public WebElement findElementByTag(String tag) {
        return webDriver.findElement(By.tagName(tag));
    }

    public void clickWebElement(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public WebElement findElementByClassName(String name) {
        return webDriver.findElement(By.className(name));
    }

    public String getCurrentUrl() {
        return webDriver.getCurrentUrl();
    }

    public void deleteCookies() {
        webDriver.manage().deleteAllCookies();
    }

    public void clickElement(WebElement webElement) {
        Actions action = new Actions(webDriver);
        action.moveToElement(webElement).build().perform();
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", webElement);
    }
}
