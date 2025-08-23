package com.briskpe.framework.base;

import com.briskpe.framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.briskpe.framework.core.Platform;
import com.briskpe.framework.core.Config;

import static com.briskpe.framework.core.DriverManager.getDriver;

/**
 * BasePage provides common utility methods for interacting with elements across
 * Web, Android, iOS, and Mobile Web platforms safely, with visibility checks and logging.
 */
public class BasePage {

    private static final Logger logger = Logger.getLogger(BasePage.class.getName());

    /**
     * Returns platform-specific locator based on current platform.
     */
    private By getLocatorForPlatform(By webLocator, By androidLocator, By iosLocator) {
        String platform = Config.get("platform", "WEB").toUpperCase();
        switch (platform) {
            case "ANDROID":
                return androidLocator != null ? androidLocator : webLocator;
            case "IOS":
                return iosLocator != null ? iosLocator : webLocator;
            case "MOBILE_WEB":
            case "WEB":
            default:
                return webLocator;
        }
    }

    /**
     * Enter text into element safely for all platforms.
     */
    public void enterText(By webLocator, By androidLocator, By iosLocator, String text, String elementName) {
        try {
            By locator = getLocatorForPlatform(webLocator, androidLocator, iosLocator);
            WaitUtils.untilVisible(locator, 30);
            WebElement element = getDriver().findElement(locator);
            element.clear();
            element.sendKeys(text);
            logger.info("✅ Entered text into '" + elementName + "': " + text);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "❌ Exception while entering text in '" + elementName + "': " + e.getMessage(), e);
            throw new RuntimeException("Failed to enter text in '" + elementName + "'", e);
        }
    }

    /**
     * Click element safely for all platforms.
     */
    public void clickElement(By webLocator, By androidLocator, By iosLocator, String elementName) {
        try {
            By locator = getLocatorForPlatform(webLocator, androidLocator, iosLocator);
            WaitUtils.untilVisible(locator, 30);
            WebElement element = getDriver().findElement(locator);
            element.click();
            logger.info("✅ Clicked on '" + elementName + "'");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "❌ Exception while clicking on '" + elementName + "': " + e.getMessage(), e);
            throw new RuntimeException("Failed to click on '" + elementName + "'", e);
        }
    }
}
