package com.briskpe.framework.pages;

import com.briskpe.framework.core.DriverFactory;
import com.briskpe.framework.core.Platform;
import com.briskpe.framework.utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Page object representing the Login screen supporting Web, Mobile Web, Android, and iOS.
 * Uses platform-aware locators and provides actions for login workflow.
 */
public class LoginPage {

    private static final Logger logger = Logger.getLogger(LoginPage.class.getName());

    private final WebDriver driver = DriverFactory.getDriver();
    private final Platform platform = Platform.fromString(System.getProperty("platform", "WEB"));

    /* ---------- Locators ---------- */

    private By loginTab() {
        return switch (platform) {
            case WEB, ANDROID, IOS, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier='mobile_number_entry']");
        };
    }

    private By mobileInput() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.cssSelector("input[aria-label^='mobileNumber']");
            case ANDROID, IOS -> By.xpath("//*[@key='mobileNumberField']");
        };
    }

    private By getOtpButton() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[normalize-space(text())='Get OTP']");
            case ANDROID, IOS -> By.xpath("//*[@key='getOtpBtn']");
        };
    }

    private By enterOtpTab() {
        return switch (platform) {
            case WEB, ANDROID, IOS, MOBILE_WEB -> By.xpath("//*[@flt-semantics-identifier='verify_otp']");
        };
    }

    private By otpTextField() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//input[@data-semantics-role='text-field']");
            case ANDROID, IOS -> By.xpath("//*[@key='otpField']");
        };
    }

    private By verifyButton() {
        return switch (platform) {
            case WEB, ANDROID, IOS, MOBILE_WEB -> By.xpath("//*[@flt-semantics-identifier='btn_submit']");
        };
    }

    /* ---------- Page Actions ---------- */

    /**
     * Check if the login tab is displayed, with special handling for Flutter Web rendering.
     *
     * @return true if login tab is visible; false otherwise
     */
    public boolean isLoginTabDisplayed() {
        try {
            if (platform == Platform.WEB) {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
                WebElement placeholder = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("#body > flt-semantics-placeholder")
                ));

                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", placeholder);

                // Allow UI animation/rendering to complete
                Thread.sleep(1000);
            }

            WaitUtils.untilVisible(loginTab(), 30);

            boolean isDisplayed = driver.findElement(loginTab()).isDisplayed();
            logger.info("Login tab visibility: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "[ERROR] Login tab not found on " + platform + ": " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Enters the mobile number into the input field.
     *
     * @param number the mobile number to enter
     * @return this LoginPage instance for method chaining
     */
    public LoginPage enterMobileNumber(String number) {
        WaitUtils.untilVisible(mobileInput(), 30);
        WebElement el = driver.findElement(mobileInput());
        el.clear();
        el.sendKeys(number);
        return this;
    }

    /**
     * Clicks the Get OTP button.
     */
    public void tapGetOtp() {
        WaitUtils.untilVisible(getOtpButton(), 30);
        driver.findElement(getOtpButton()).click();
        logger.info("[INFO] Clicked Get OTP, waiting for OTP screen...");
    }

    /**
     * Checks if the Enter OTP tab is displayed.
     *
     * @return true if Enter OTP tab is visible; false otherwise
     */
    public boolean isEnterOtpTabDisplayed() {
        try {
            WaitUtils.untilVisible(enterOtpTab(), 30);
            boolean isDisplayed = driver.findElement(enterOtpTab()).isDisplayed();
            logger.info("Enter OTP tab visibility: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "[ERROR] Enter OTP tab not found on " + platform + ": " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Enters the OTP into the OTP input field.
     *
     * @param otp the OTP string to enter
     * @return this LoginPage instance for method chaining
     */
    public LoginPage enterOTP(String otp) {
        WaitUtils.untilVisible(otpTextField(), 30);
        WebElement el = driver.findElement(otpTextField());
        el.clear();
        el.sendKeys(otp);
        return this;
    }

    /**
     * Clicks the verify button.
     */
    public void clickVerifyButton() {
        WaitUtils.untilVisible(verifyButton(), 30);
        driver.findElement(verifyButton()).click();
        logger.info("[INFO] Clicked verify button, waiting for dashboard...");
    }

    /**
     * Expose the verify button locator, useful for advanced waits or checks in tests.
     *
     * @return By locator of verify button
     */
    public By getVerifyButton() {
        return verifyButton();
    }
}
