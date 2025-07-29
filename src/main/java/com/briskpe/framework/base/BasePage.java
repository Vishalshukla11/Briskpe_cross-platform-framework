package com.briskpe.framework.base;

import org.openqa.selenium.WebElement;

public class BasePage {

    /**
     * Enters text into a field after checking visibility.
     *
     * @param element     WebElement where text is to be entered
     * @param text        Text to enter
     * @param elementName Logical name for logs
     */
    public void enterText(WebElement element, String text, String elementName) {
        try {
            if (element.isDisplayed()) {
                element.clear();
                element.sendKeys(text);
                System.out.println("✅ Entered text into '" + elementName + "': " + text);
            } else {
                System.err.println("⚠️ Element '" + elementName + "' is not visible to enter text.");
            }
        } catch (Exception e) {
            System.err.println("❌ Exception while entering text in '" + elementName + "': " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Safely clicks a WebElement after visibility check.
     *
     * @param element     WebElement to click
     * @param elementName Logical name for logs
     */
    public void clickElement(WebElement element, String elementName) {
        try {
            if (element.isDisplayed()) {
                element.click();
                System.out.println("✅ Clicked on '" + elementName + "'");
            } else {
                System.err.println("⚠️ Element '" + elementName + "' is not visible to click.");
            }
        } catch (Exception e) {
            System.err.println("❌ Exception while clicking on '" + elementName + "': " + e.getMessage());
            e.printStackTrace();
        }
    }
}
