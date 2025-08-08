package com.briskpe.framework.utils;

import com.briskpe.framework.core.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for explicit waits on WebElements and conditions,
 * enhancing synchronization stability for Selenium WebDriver tests.
 */
public class WaitUtils {

    private static final Logger logger = Logger.getLogger(WaitUtils.class.getName());

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(25);

    /**
     * Waits until the element located by the given locator is visible on the page.
     *
     * @param locator          By locator of the target element
     * @param timeoutInSeconds Maximum time to wait in seconds
     * @return true if element is visible within timeout, false otherwise
     */
    public static boolean untilVisible(By locator, int timeoutInSeconds) {
        WebDriver driver = DriverFactory.getDriver();
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Element not visible: " + locator + " | Exception: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Waits until the element located by the given locator is visible on the page using default timeout.
     *
     * @param locator By locator of the target element
     * @return true if element is visible within default timeout, false otherwise
     */
    public static boolean untilVisible(By locator) {
        return untilVisible(locator, (int) DEFAULT_TIMEOUT.getSeconds());
    }

    /**
     * Waits until the element located by the given locator is clickable.
     *
     * @param locator          By locator of the element
     * @param timeoutInSeconds Maximum wait time in seconds
     * @return WebElement once it is clickable
     * @throws org.openqa.selenium.TimeoutException if element is not clickable within timeout
     */
    public static WebElement untilClickable(By locator, int timeoutInSeconds) {
        WebDriver driver = DriverFactory.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits until the element located by the given locator is clickable using default timeout.
     *
     * @param locator By locator of the element
     * @return WebElement once clickable
     */
    public static WebElement untilClickable(By locator) {
        return untilClickable(locator, (int) DEFAULT_TIMEOUT.getSeconds());
    }

    /**
     * Waits until the element located by the given locator is present in the DOM,
     * regardless of visibility.
     * This is useful for Flutter or canvas-based apps where visibility is unreliable.
     *
     * @param locator          By locator of the element
     * @param timeoutInSeconds Maximum time to wait in seconds
     * @return WebElement once present, or null if timeout exceeded
     */
    public static WebElement untilPresent(By locator, int timeoutInSeconds) {
        WebDriver driver = DriverFactory.getDriver();
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            logger.log(Level.WARNING, "Element not present: " + locator + " | Exception: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Waits until the element located by the given locator is present in the DOM using default timeout.
     *
     * @param locator By locator of the element
     * @return WebElement once present, or null if not found
     */
    public static WebElement untilPresent(By locator) {
        return untilPresent(locator, (int) DEFAULT_TIMEOUT.getSeconds());
    }

    /**
     * Alias for untilVisible with timeout.
     *
     * @param locator          By locator of the element
     * @param timeoutInSeconds Maximum seconds to wait
     * @return true if element visible within timeout, else false
     */
    public static boolean waitForElementVisible(By locator, int timeoutInSeconds) {
        return untilVisible(locator, timeoutInSeconds);
    }

}
