package com.briskpe.framework.base;

import org.openqa.selenium.WebElement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * BasePage provides common utility methods for interacting with WebElements safely,
 * including visibility checks, logging, and exception handling.
 */
public class BasePage {

    private static final Logger logger = Logger.getLogger(BasePage.class.getName());

    /**
     * Enters text into a WebElement (e.g., input field) after checking if it is displayed.
     *
     * @param element     WebElement where text is to be entered
     * @param text        Text to enter
     * @param elementName Logical name for logging purposes
     */
    public void enterText(WebElement element, String text, String elementName) {
        try {
            if (element.isDisplayed()) {
                element.clear();
                element.sendKeys(text);
                logger.info("✅ Entered text into '" + elementName + "': " + text);
            } else {
                logger.warning("⚠️ Element '" + elementName + "' is not visible to enter text.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "❌ Exception while entering text in '" + elementName + "': " + e.getMessage(), e);
            throw new RuntimeException("Failed to enter text in '" + elementName + "'", e);
        }
    }

    /**
     * Clicks a WebElement safely after confirming visibility.
     *
     * @param element     WebElement to click
     * @param elementName Logical name for logging purposes
     */
    public void clickElement(WebElement element, String elementName) {
        try {
            if (element.isDisplayed()) {
                element.click();
                logger.info("✅ Clicked on '" + elementName + "'");
            } else {
                logger.warning("⚠️ Element '" + elementName + "' is not visible to click.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "❌ Exception while clicking on '" + elementName + "': " + e.getMessage(), e);
            throw new RuntimeException("Failed to click on '" + elementName + "'", e);
        }
    }
}
