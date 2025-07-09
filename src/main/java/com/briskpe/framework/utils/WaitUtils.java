package com.briskpe.framework.utils;

import com.briskpe.framework.core.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {
    private static final Duration TIMEOUT = Duration.ofSeconds(15);

    public static boolean untilVisible(By locator) {
        WebDriver driver = DriverFactory.getDriver();
        new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        return false;
    }
}
