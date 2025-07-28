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

    /**
     * Waits until the element located by the given locator is present in the DOM, regardless of visibility.
     * Useful for Flutter or canvas-based applications where visibility is not always detectable.
     *
     * @param locator By locator of the element.
     * @param timeoutInSeconds Maximum wait time in seconds.
     * @return WebElement once it is present.
     */
    public static WebElement untilPresent(By locator, int timeoutInSeconds) {
        WebDriver driver = DriverFactory.getDriver();
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            System.out.println("❌ Element not present: " + locator + " | " + e.getMessage());
            return null;
        }
    }
    public static boolean waitForElementVisible(By locator, int timeoutInSeconds) {
        return untilVisible(locator, timeoutInSeconds);
    }


}
