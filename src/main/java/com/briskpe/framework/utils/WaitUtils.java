package com.briskpe.framework.utils;

import com.briskpe.framework.core.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(25);

    public static boolean untilVisible(By locator, int timeoutInSeconds) {
        WebDriver driver = DriverFactory.getDriver();
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            System.out.println("❌ Element not visible: " + locator + " | " + e.getMessage());
            return false;
        }
    }

    /**
     * Waits until the element located by the given locator is clickable.
     * @param locator By locator of the element.
     * @param timeoutInSeconds Maximum wait time in seconds.
     * @return WebElement once it is clickable.
     */
    public static WebElement waitUntilElementIsClickable(By locator, int timeoutInSeconds) {
        WebDriver driver = DriverFactory.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}
