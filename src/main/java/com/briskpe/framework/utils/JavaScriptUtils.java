package com.briskpe.framework.utils;

import com.briskpe.framework.core.Config;
import com.briskpe.framework.core.DriverFactory;
import com.briskpe.framework.core.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtils {

    private static final WebDriver driver = DriverFactory.getDriver();
    private static final Platform platform = Platform.fromString(System.getProperty("platform", "WEB"));

    /**
     * Executes JavaScript to click on Flutter placeholder, only for WEB platform.
     */
    public static void executeJs() {
        if (platform == Platform.WEB) {
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript(Config.get("js.Query"));
                System.out.println("✅ JS Query executed");
            } catch (Exception e) {
                System.out.println("❌ Failed to execute JS click: " + e.getMessage());
                throw new RuntimeException("Flutter placeholder not clicked. JS Query failed.", e);
            }
        } else {
            System.out.println("⚠️ JS execution skipped: Platform is not WEB");
        }
    }

    /**
     * Click on any WebElement using JavaScript (WEB only).
     * @param element WebElement to click.
     */
    public static void clickElementWithJS(WebElement element) {
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
     * Execute a custom JavaScript command (WEB only).
     * @param script The JS code to execute.
     * @return Object returned by JS execution (if any).
     */
    public static Object executeCustomJs(String script) {
        if (platform == Platform.WEB) {
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                Object result = js.executeScript(script);
                System.out.println("✅ JS executed: " + script);
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
     * Smart click that uses JS only for WEB platform. Falls back to native click otherwise.
     *
     * @param locator        Target element's By locator.
     * @param pendingActions
     */
    public static void jsClick(By locator, String pendingActions) {
        try {
            WaitUtils.untilVisible(locator, 30);
            WebElement element = driver.findElement(locator);

            if (platform == Platform.WEB) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                String jsQuery = Config.get("js.Query");

                if (jsQuery == null || jsQuery.isEmpty()) {
                    js.executeScript("arguments[0].click();", element);
                    System.out.println("✅ JS clicked element using default JS click: " + locator);
                } else {
                    js.executeScript(jsQuery);
                    System.out.println("✅ JS clicked element using config query: " + jsQuery);
                }
            } else {
                element.click();
                System.out.println("✅ Native click performed for: " + locator);
            }

        } catch (Exception e) {
            throw new RuntimeException("❌ JS/native click failed for locator: " + locator + " → " + e.getMessage(), e);
        }
    }
}
