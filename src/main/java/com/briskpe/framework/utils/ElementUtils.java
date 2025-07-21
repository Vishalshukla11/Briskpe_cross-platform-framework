package com.briskpe.framework.utils;

import com.briskpe.framework.core.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Random;

public class ElementUtils {
    private final WebDriver driver;

    public ElementUtils(WebDriver driver) {
        this.driver = DriverFactory.getDriver(); // ✅ Correctly initialize driver
    }

    /**
     * Checks if the specified element is displayed.
     *
     * @param locator The By locator returned by element method (e.g., VerifyButton())
     * @return true if the element is visible, false otherwise
     */
    public boolean isElementDisplayed(By locator) {
        try {
            WaitUtils.untilVisible(locator, 30); // ✅ Reuse your existing wait util
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            System.out.println("Element not displayed: " + e.getMessage());
            return false;
        }
    }


}
