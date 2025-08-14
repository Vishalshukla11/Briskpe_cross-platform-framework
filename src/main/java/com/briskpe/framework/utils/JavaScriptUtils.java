package com.briskpe.framework.utils;

import com.briskpe.framework.core.Platform;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for JavaScript-based actions tailored for Flutter Web automation.
 * Ensures actions run only on Web platform, leveraging JavaScript execution for Flutter-specific rendering challenges.
 */
public class JavaScriptUtils {

    private static final Logger logger = Logger.getLogger(JavaScriptUtils.class.getName());

    private static final int DEFAULT_WAIT_SECONDS = 15;

    /**
     * Clicks on the Flutter placeholder to trigger UI rendering (for WEB only).
     *
     * @param driver WebDriver instance
     */
    public static void executeFlutterPlaceholderJs(WebDriver driver) {
        if (!isWebPlatform(driver)) return;

        try {
            WebElement placeholder = driver.findElement(By.cssSelector("#body > flt-semantics-placeholder"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", placeholder);
            logger.info("JS executed: Flutter placeholder clicked.");
        } catch (NoSuchElementException e) {
            logger.log(Level.SEVERE, "Placeholder element not found.", e);
            throw new RuntimeException("Flutter placeholder not found", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "JS execution failed unexpectedly.", e);
            throw new RuntimeException("Flutter JS execution failed", e);
        }
    }

    /**
     * Performs a JavaScript click on the given WebElement (for WEB only).
     *
     * @param driver  WebDriver instance
     * @param element WebElement to be clicked
     */
    public static void clickElementWithJS(WebDriver driver, WebElement element) {
        if (!isWebPlatform(driver)) return;

        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            logger.info("JS clicked element successfully.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "JS click failed: " + e.getMessage(), e);
            throw new RuntimeException("Unable to click element using JS", e);
        }
    }

    /**
     * Executes custom JavaScript (for WEB only).
     *
     * @param driver WebDriver instance
     * @param script JavaScript code to execute
     * @return result of the execution, or null if not on WEB platform
     */
    public static void executeCustomJs(WebDriver driver, String script) {
        if (!isWebPlatform(driver)) {
            logger.warning("JS execution skipped - not running on WEB platform.");
            return;
        }
        try {
            logger.info("Executing custom JS: " + script);
            ((JavascriptExecutor) driver).executeScript(script);
            Thread.sleep(1000); // Allow Flutter DOM to update
            logger.info("Custom JS executed successfully.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "JS execution failed: " + script, e);
            throw new RuntimeException("JS execution failed", e);
        }
    }


    /**
     * Performs JavaScript click using a locator (for WEB only).
     *
     * @param locator Locator of the element
     * @param driver  WebDriver instance
     */
    public static void jsClick(By locator, WebDriver driver) {
        if (!isWebPlatform(driver)) return;

        try {
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            logger.info("JS clicked element by locator: " + locator);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "JS click failed for locator: " + locator, e);
            throw new RuntimeException("Unable to JS click element using locator", e);
        }
    }

    /**
     * Waits for and clicks the Flutter semantics placeholder to trigger rendering (for WEB only).
     *
     * @param driver WebDriver instance
     */
    public static void triggerFlutterRender(WebDriver driver) {
        if (!isWebPlatform(driver)) return;

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_SECONDS));
            WebElement placeholder = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#body > flt-semantics-placeholder")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", placeholder);
            Thread.sleep(1000); // Allow UI rendering time
            logger.info("Flutter render triggered via JS click.");
        } catch (TimeoutException e) {
            logger.log(Level.SEVERE, "Flutter placeholder not found within timeout.", e);
            throw new RuntimeException("Flutter placeholder not visible in time", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.log(Level.WARNING, "Interrupted while waiting for Flutter render trigger.", e);
            throw new RuntimeException("Interrupted during Flutter render trigger wait", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error while triggering Flutter render.", e);
            throw new RuntimeException("Flutter render trigger failed", e);
        }
    }

    /**
     * Checks if the platform is WEB and driver/session is valid.
     *
     * @param driver WebDriver instance
     * @return true if valid and platform is WEB
     */
    private static boolean isWebPlatform(WebDriver driver) {
        if (driver == null) {
            logger.warning("Skipping JS: WebDriver is null.");
            return false;
        }
        if (!(driver instanceof RemoteWebDriver)) {
            logger.warning("Skipping JS: WebDriver is not a RemoteWebDriver instance.");
            return false;
        }
        RemoteWebDriver remoteDriver = (RemoteWebDriver) driver;
        if (remoteDriver.getSessionId() == null) {
            logger.warning("Skipping JS: WebDriver session is null or closed.");
            return false;
        }

        Platform platform = Platform.fromString(System.getProperty("platform", "WEB"));
        if (platform != Platform.WEB) {
            logger.info("Skipping JS: Platform is not WEB.");
            return false;
        }

        return true;
    }
}
