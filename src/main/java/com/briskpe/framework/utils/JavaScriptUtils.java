package com.briskpe.framework.utils;

import com.briskpe.framework.core.Config;
import com.briskpe.framework.core.DriverFactory;
import com.briskpe.framework.core.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class JavaScriptUtils {

    /**
     * Executes Flutter placeholder JS only for WEB platform with active WebDriver session.
     *
     * @param driver the WebDriver instance
     */
    public static void executeFlutterPlaceholderJs(WebDriver driver) {
        try {
            if (driver == null || ((RemoteWebDriver) driver).getSessionId() == null) {
                System.out.println("⚠️ Skipping JS execution: WebDriver is null or session is closed.");
                return;
            }

            Platform platform = Platform.fromString(System.getProperty("platform", "WEB"));
            if (platform == Platform.WEB) {
                ((JavascriptExecutor) driver)
                        .executeScript("document.querySelector('#body > flt-semantics-placeholder').click();");
                System.out.println("✅ JS executed: Flutter placeholder clicked.");
            } else {
                System.out.println("⚠️ JS skipped: Platform is not WEB.");
            }
        } catch (Exception e) {
            throw new RuntimeException("❌ Flutter JS execution failed", e);
        }
    }

    /**
     * Clicks a WebElement using JavaScript (WEB only).
     *
     * @param driver  current WebDriver
     * @param element element to click
     */
    public static void clickElementWithJS(WebDriver driver, WebElement element) {
        Platform platform = Platform.fromString(System.getProperty("platform", "WEB"));
        if (platform == Platform.WEB) {
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", element);
                System.out.println("✅ JS clicked element successfully");
            } catch (Exception e) {
                System.out.println("❌ JS click failed: " + e.getMessage());
                throw new RuntimeException("Unable to click element using JS", e);
            }
        } else {
            System.out.println("⚠️ JS click skipped: Platform is not WEB");
        }
    }

    /**
     * Execute any custom JS (WEB only).
     *
     * @param driver current WebDriver
     * @param script JS code to run
     * @return result of the script (if any)
     */
    public static Object executeCustomJs(WebDriver driver, String script) {
        Platform platform = Platform.fromString(System.getProperty("platform", "WEB"));
        if (platform == Platform.WEB) {
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                Object result = js.executeScript(script);
                System.out.println("✅ Custom JS executed: " + script);
                return result;
            } catch (Exception e) {
                System.out.println("❌ Failed to execute custom JS: " + script);
                throw new RuntimeException("JS execution failed", e);
            }
        } else {
            System.out.println("⚠️ Custom JS execution skipped: Platform is not WEB");
            return null;
        }
    }

    /**
     * Smart click using JS for WEB or fallback to native click.
     *
     * @param locator            element to be clicked
     * @param elementDescription description for logging
     */
    public static void jsClick(By locator, String elementDescription) {
        WebDriver driver = DriverFactory.getDriver();  // ✅ Added to fix the error

        try {
            WaitUtils.untilVisible(locator, 30);
            WebElement element = driver.findElement(locator);
            Platform platform = Platform.fromString(System.getProperty("platform", "WEB"));

            if (platform == Platform.WEB) {
                String jsQuery = Config.get("js.Query", "");

                if (!jsQuery.isEmpty()) {
                    executeFlutterPlaceholderJs(driver);
                } else {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                    System.out.println("✅ JS clicked element: " + elementDescription);
                }
            } else {
                element.click();
                System.out.println("✅ Native clicked element: " + elementDescription);
            }

        } catch (Exception e) {
            throw new RuntimeException("❌ Click failed for element: " + elementDescription + " → " + e.getMessage(), e);
        }
    }
    /**
     * Clicks an element using JavaScript Executor.
     * Useful when normal Selenium click is intercepted or blocked by overlays.
     *
     * @param locator By locator of the element
     * @param driver  WebDriver instance
     */
    public static void JsClick(By locator, WebDriver driver) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }



}
