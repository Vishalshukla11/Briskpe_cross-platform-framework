package com.briskpe.framework.pages;

import com.briskpe.framework.base.BasePage;
import com.briskpe.framework.core.DriverFactory;
import com.briskpe.framework.core.Platform;
import com.briskpe.framework.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Page object representing the SignUp screen of the app/web.
 * Supports Flutter keys for Android/iOS and XPath for Web/Mobile Web.
 */
public class SignUp extends BasePage {

    private static final Logger logger = Logger.getLogger(SignUp.class.getName());

    private final WebDriver driver = DriverFactory.getDriver();
    private final Platform platform = Platform.fromString(System.getProperty("platform", "WEB"));

    // ===================== LOCATORS ====================== //

    private By getMobileNumberField() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//input[contains(@aria-label,'mobileNumber')]");
            case ANDROID, IOS -> AppiumBy.flutterKey("mobile-number-field");
        };
    }

    private By getEmailField() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//input[contains(@aria-label,'email')]");
            case ANDROID, IOS -> AppiumBy.flutterKey("email-field");
        };
    }

    private By getTnCCheckbox() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("(//flt-semantics[@flt-semantics-identifier='concern-checkbox'])[1]");
            case ANDROID, IOS -> AppiumBy.flutterKey("concern-checkbox-1");
        };
    }

    private By getWhatsappCheckbox() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("(//flt-semantics[@flt-semantics-identifier='concern-checkbox'])[2]");
            case ANDROID, IOS -> AppiumBy.flutterKey("concern-checkbox-2");
        };
    }

    private By getOtpButton() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier='get-otp-button']");
            case ANDROID, IOS -> AppiumBy.flutterKey("get-otp-button");
        };
    }

    private By getSignUpPageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier='signup-screen']");
            case ANDROID, IOS -> AppiumBy.flutterKey("signup-screen");
        };
    }

    // ===================== ACTION METHODS ====================== //

    /**
     * Enters mobile number in Mobile Number field.
     *
     * @param mobileNumber mobile number to enter
     * @return this SignUp instance for chaining
     */
    public SignUp enterMobileNumber(String mobileNumber) {
        try {
            WaitUtils.untilVisible(getMobileNumberField(), 30);
            WebElement mobileField = driver.findElement(getMobileNumberField());
            mobileField.clear();
            mobileField.sendKeys(mobileNumber);
            logger.info("Entered mobile number: " + mobileNumber);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error entering mobile number: " + e.getMessage(), e);
            throw e;
        }
        return this;
    }

    /**
     * Enters email address in Email field.
     *
     * @param email email address to enter
     * @return this SignUp instance for chaining
     */
    public SignUp enterEmail(String email) {
        try {
            WaitUtils.untilVisible(getEmailField(), 30);
            WebElement emailField = driver.findElement(getEmailField());
            emailField.clear();
            emailField.sendKeys(email);
            logger.info("Entered email address: " + email);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error entering email: " + e.getMessage(), e);
            throw e;
        }
        return this;
    }

    /**
     * Clicks/Checks Terms and Conditions checkbox.
     */
    public void clickTnCCheckbox() {
        try {
            WaitUtils.untilClickable(getTnCCheckbox(), 30);
            driver.findElement(getTnCCheckbox()).click();
            logger.info("Clicked Terms and Conditions checkbox.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error clicking TnC checkbox: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Clicks/Checks WhatsApp opt-in checkbox.
     */
    public void clickWhatsappCheckbox() {
        try {
            WaitUtils.untilClickable(getWhatsappCheckbox(), 30);
            driver.findElement(getWhatsappCheckbox()).click();
            logger.info("Clicked WhatsApp checkbox.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error clicking WhatsApp checkbox: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Clicks the Get OTP button.
     */
    public void clickGetOtpButton() {
        try {
            WaitUtils.untilClickable(getOtpButton(), 30);
            driver.findElement(getOtpButton()).click();
            logger.info("Clicked Get OTP button.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error clicking Get OTP button: " + e.getMessage(), e);
            throw e;
        }
    }

    // ===================== VERIFICATION METHODS ====================== //

    /**
     * Checks if the Sign Up page is displayed by verifying the presence of a unique element.
     *
     * @return true if Sign Up page is visible; false otherwise
     */
    public boolean isSignUpPageVisible() {
        try {
            WaitUtils.untilVisible(getSignUpPageLocator(), 30);
            boolean visible = driver.findElement(getSignUpPageLocator()).isDisplayed();
            logger.info("SignUp page visibility: " + visible);
            return visible;
        } catch (Exception e) {
            logger.log(Level.WARNING, "SignUp page not visible: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Checks if Terms and Conditions checkbox is selected.
     *
     * @return true if selected; false otherwise
     */
    public boolean isTnCCheckboxSelected() {
        try {
            WebElement checkbox = driver.findElement(getTnCCheckbox());
            boolean selected = checkbox.isSelected();
            logger.info("TnC checkbox selected state: " + selected);
            return selected;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error checking TnC checkbox state: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Checks if WhatsApp checkbox is selected.
     *
     * @return true if selected; false otherwise
     */
    public boolean isWhatsappCheckboxSelected() {
        try {
            WebElement checkbox = driver.findElement(getWhatsappCheckbox());
            boolean selected = checkbox.isSelected();
            logger.info("WhatsApp checkbox selected state: " + selected);
            return selected;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error checking WhatsApp checkbox state: " + e.getMessage(), e);
            return false;
        }
    }
}
