package com.briskpe.framework.utils;

import com.briskpe.framework.core.Platform;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Utility class for JavaScript-based actions tailored for Flutter Web automation.
 */
public class JavaScriptUtils {

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
            System.out.println("✅ JS executed: Flutter placeholder clicked.");
        } catch (NoSuchElementException e) {
            System.err.println("❌ Placeholder element not found.");
            throw new RuntimeException("Flutter placeholder not found", e);
        } catch (Exception e) {
            System.err.println("❌ JS execution failed unexpectedly.");
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
            System.out.println("✅ JS clicked element successfully.");
        } catch (Exception e) {
            System.err.println("❌ JS click failed: " + e.getMessage());
            throw new RuntimeException("Unable to click element using JS", e);
        }
    }

    /**
     * Executes custom JavaScript (for WEB only).
     *
     * @param driver WebDriver instance
     * @param script JavaScript code to execute
     * @return result of the execution
     */
    public static Object executeCustomJs(WebDriver driver, String script) {
        if (!isWebPlatform(driver)) return null;

        try {
            Object result = ((JavascriptExecutor) driver).executeScript(script);
            System.out.println("✅ Custom JS executed: " + script);
            return result;
        } catch (Exception e) {
            System.err.println("❌ Failed to execute custom JS: " + script);
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
            System.out.println("✅ JS clicked element by locator: " + locator);
        } catch (Exception e) {
            System.err.println("❌ JS click failed for locator: " + locator);
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
            WebElement placeholder = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_SECONDS))
                    .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#body > flt-semantics-placeholder")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", placeholder);
            Thread.sleep(1000);
            System.out.println("✅ Flutter render triggered via JS click.");
        } catch (TimeoutException e) {
            System.err.println("❌ Flutter placeholder not found within timeout.");
            throw new RuntimeException("Flutter placeholder not visible in time", e);
        } catch (Exception e) {
            System.err.println("❌ Unexpected error while triggering Flutter render.");
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
        if (driver == null || ((RemoteWebDriver) driver).getSessionId() == null) {
            System.err.println("⚠️ Skipping JS: WebDriver is null or session is closed.");
            return false;
        }

        Platform platform = Platform.fromString(System.getProperty("platform", "WEB"));
        if (platform != Platform.WEB) {
            System.out.println("⚠️ Skipping JS: Platform is not WEB.");
            return false;
        }

        return true;
    }
}
