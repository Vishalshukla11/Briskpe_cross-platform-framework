package com.briskpe.framework.pages;

import com.briskpe.framework.core.DriverFactory;
import com.briskpe.framework.core.Platform;
import com.briskpe.framework.utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {

    private final WebDriver driver = DriverFactory.getDriver();
    private final Platform platform =
            Platform.fromString(System.getProperty("platform", "WEB"));

    /* ---------- Locators ---------- */
    private By loginTab() {
        return switch (platform) {
            case WEB, ANDROID, IOS -> By.xpath("//*[@flt-semantics-identifier='mobile_number_entry']");
        };
    }

    private By mobileInput() {
        return switch (platform) {
            case WEB -> By.cssSelector("input[aria-label^='mobileNumber']");
            case ANDROID, IOS -> By.xpath("//*[@key='mobileNumberField']");
        };
    }

    private By getOtpButton() {
        return switch (platform) {
            case WEB -> By.xpath("//flt-semantics[normalize-space(text())='Get OTP']");
            case ANDROID, IOS -> By.xpath("//*[@key='getOtpBtn']");
        };
    }

    private By enterOtpTab() {
        return switch (platform) {
            case WEB, ANDROID, IOS -> By.xpath("//*[@flt-semantics-identifier='verify_otp']");
        };
    }

    /* ---------- Page Actions ---------- */

    public boolean isLoginTabDisplayed() {
        try {
            if (platform == Platform.WEB) {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement placeholder = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("#body > flt-semantics-placeholder")
                ));

                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", placeholder);
                Thread.sleep(1000); // Let Flutter render
            }

            WaitUtils.untilVisible(loginTab());
            return driver.findElement(loginTab()).isDisplayed();
        } catch (Exception e) {
            System.out.println("Login tab not found: " + e.getMessage());
            return false;
        }
    }

    public LoginPage enterMobileNumber(String number) {
        WaitUtils.untilVisible(mobileInput());
        WebElement el = driver.findElement(mobileInput());
        el.clear();
        el.sendKeys(number);
        return this;
    }

    public void tapGetOtp() {
        WaitUtils.untilVisible(getOtpButton());
        driver.findElement(getOtpButton()).click();
        System.out.println("Clicked Get OTP, waiting for OTP screen...");
    }

    public boolean isEnterOtpTabDisplayed() {
        try {
            WaitUtils.untilVisible(enterOtpTab());
            return driver.findElement(enterOtpTab()).isDisplayed();
        } catch (Exception e) {
            System.out.println("Enter OTP tab not found: " + e.getMessage());
            return false;
        }
    }
}
