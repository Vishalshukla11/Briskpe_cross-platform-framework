package com.briskpe.framework.utils;

import com.briskpe.framework.core.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ElementUtils {
    private static final Logger logger = Logger.getLogger(ElementUtils.class.getName());

    private final WebDriver driver;

    /**
     * Constructor initializes driver from ThreadLocal DriverFactory to support parallel execution.
     */
    public ElementUtils() {
        this.driver = DriverFactory.getDriver();  // Use DriverFactory to get current thread's driver
    }

    public ElementUtils(WebDriver driver) {
        // Allow injection of custom WebDriver (optional)
        this.driver = driver;
    }

    /**
     * Checks if the specified element located by the given locator is displayed on the page.
     * Waits up to 30 seconds for the element to be visible using your WaitUtils.
     *
     * @param locator The Selenium By locator of the element to check
     * @return true if element is visible; false otherwise
     */
    public boolean isElementDisplayed(By locator) {
        try {
            WaitUtils.untilVisible(locator, 30); // Wait util you already have for explicit visibility
            boolean displayed = driver.findElement(locator).isDisplayed();
            logger.info("Element located by " + locator + " is displayed: " + displayed);
            return displayed;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Element not displayed: " + locator + ". Exception: " + e.getMessage());
            return false;
        }
    }

    // You may add additional reusable element helper methods here, for example:

    /**
     * Clicks the element located by the given locator after waiting for visibility.
     *
     * @param locator The By locator of the element to click.
     */
    public void clickElement(By locator) {
        try {
            WaitUtils.untilVisible(locator, 30);
            driver.findElement(locator).click();
            logger.info("Clicked element: " + locator);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to click element: " + locator + ". Exception: " + e.getMessage(), e);
            throw new RuntimeException("Failed to click element: " + locator, e);
        }
    }

    /**
     * Sends the specified keys to the element located by the locator after waiting for visibility.
     * Clears the element before sending keys.
     *
     * @param locator The By locator of the input field.
     * @param text    The text to input.
     */
    public void sendKeys(By locator, String text) {
        try {
            WaitUtils.untilVisible(locator, 30);
            var element = driver.findElement(locator);
            element.clear();
            element.sendKeys(text);
            logger.info("Entered text '" + text + "' into element: " + locator);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to enter text into element: " + locator + ". Exception: " + e.getMessage(), e);
            throw new RuntimeException("Failed to enter text into element: " + locator, e);
        }
    }

}
