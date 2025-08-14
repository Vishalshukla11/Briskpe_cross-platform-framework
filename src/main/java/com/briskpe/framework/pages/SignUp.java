package com.briskpe.framework.pages;

import com.briskpe.framework.base.BasePage;
import com.briskpe.framework.core.DriverFactory;
import com.briskpe.framework.core.Platform;
import com.briskpe.framework.utils.JavaScriptUtils;
import com.briskpe.framework.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.briskpe.framework.core.DriverFactory.getDriver;
import static com.briskpe.framework.utils.RandomDataUtils.getRandomEmail;
import static com.briskpe.framework.utils.RandomDataUtils.getRandomMobileNumber;

/**
 * Page object representing the SignUp screen of the app/web.
 * Supports Flutter keys for Android/iOS and XPath for Web/Mobile Web.
 */
public class SignUp extends BasePage {

    private static final Logger logger = Logger.getLogger(SignUp.class.getName());

    private final WebDriver driver = getDriver();
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
            case WEB, MOBILE_WEB -> By.xpath("//span[contains(text(),\"I agree to BRISKPE's \")]/..");
            case ANDROID, IOS -> AppiumBy.flutterKey("concern-checkbox-1");
        };
    }

    private By getWhatsappCheckbox() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[contains(@aria-label,\"I agree to receive important\")]");
            case ANDROID, IOS -> AppiumBy.flutterKey("concern-checkbox-2");
        };
    }


    private By getSignUpPageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier='signup-screen']");
            case ANDROID, IOS -> AppiumBy.flutterKey("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View");
        };
    }

    private By singUpButtonLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[contains(text(),\"Don’t have an account \")]");
            case ANDROID, IOS -> AppiumBy.flutterKey("get-otp-button");
        };
    }

    private By getOtpButtonLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier=\"btn_submit\"]//flt-semantics");
            case ANDROID, IOS -> AppiumBy.flutterKey("btn_submit");
        };
    }
    private By getSelectAccountTypepageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier=\"screen_kyc_account_type\"]//flt-semantics//span[contains(text(),'Select your account')]");
            case ANDROID, IOS -> AppiumBy.flutterKey("screen_kyc_account_type");
        };
    }

//    private By getAccountTypeLocator(String accountType) {
//        return switch (platform) {
//            case WEB, MOBILE_WEB -> By.xpath(
//                    String.format("//flt-semantics[@flt-semantics-identifier=\"screen_kyc_account_type\"]//flt-semantics//span[contains(text(),'%s')]/..", accountType)
//
//            );
//            case ANDROID, IOS -> AppiumBy.flutterKey("screen_kyc_account_type");
//
//        };
//    }
    private By getAccountTypeLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//span[contains(text(),'Company')]/..");
            case ANDROID, IOS -> AppiumBy.flutterKey("screen_kyc_account_type");
        };
    }

    private By getContinueButtonLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[contains(text(),'Continue')]");
            case ANDROID, IOS -> AppiumBy.flutterKey("screen_kyc_account_type");
        };
    }




    // ===================== ACTION METHODS ====================== //

    /**
     * Enters mobile number in Mobile Number field.
     *
//     * @param mobileNumber mobile number to enter
     * @return this SignUp instance for chaining
     */
    public SignUp enterMobileNumber() {
        try {
            WaitUtils.untilVisible(getMobileNumberField(), 30);
            WebElement mobileField = driver.findElement(getMobileNumberField());
            mobileField.clear();
            mobileField.sendKeys(getRandomMobileNumber());
            logger.info("Entered mobile number: " + getRandomMobileNumber());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error entering mobile number: " + e.getMessage(), e);
            throw e;
        }
        return this;
    }

    /**
     * Enters email address in Email field.
     *
//     * @param email email address to enter
     * @return this SignUp instance for chaining
     */
    public SignUp enterEmail() {
        try {
            WaitUtils.untilVisible(getEmailField(), 30);
            WebElement emailField = driver.findElement(getEmailField());
            emailField.clear();
            emailField.sendKeys(getRandomEmail());
            logger.info("Entered email address: " + getRandomEmail());
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
            WaitUtils.untilClickable(getOtpButtonLocator(), 30);
            driver.findElement(getOtpButtonLocator()).click();
            logger.info("Clicked Get OTP button.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error clicking Get OTP button: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Clicks the Signup text Link
     */
    public void clickSignuptextLink() {
        try {
            WaitUtils.untilClickable(singUpButtonLocator(), 30);
            driver.findElement(singUpButtonLocator()).click();
            logger.info("Clicked Signup Text Link .");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error clicking on Signup text Link: " + e.getMessage(), e);
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
     * Checks if the Sign Up page is displayed by verifying the presence of a unique element.
     *
     * @return true if Sign Up page is visible; false otherwise
     */
    public boolean isContinueButtonEnable() {
        try {
            WaitUtils.untilVisible(getContinueButtonLocator(), 30);
            boolean visible = driver.findElement(getContinueButtonLocator()).isEnabled();
            logger.info("Continue Button Enablailty: " + visible);
            return visible;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Continue Button not Enable: " + e.getMessage(), e);
            return false;
        }
    }



    /**
     * Checks if the get OTP Button  is enable by verifying the presence of a unique element.
     *
     * @return true if Sign Up page is visible; false otherwise
     */
    public boolean isGetOtpButtonClickble() {
        try {
            WaitUtils.untilVisible(getOtpButtonLocator(), 30);
            boolean enable = driver.findElement(getOtpButtonLocator()).isEnabled();
            logger.info("Get Otp Button is : " + enable);
            return enable;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Get Otp Button not enable: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Checks if Select Account Type page is visible.
     *
     * @return true if the page is visible, false otherwise
     */
    public boolean isSelectAccountTypePageVisible() {
        try {
            By locator = getSelectAccountTypepageLocator();
            WaitUtils.untilVisible(locator, 30);
            boolean visible = driver.findElement(locator).isDisplayed();
            logger.info("Select Account Type page is visible: " + visible);
            return visible;
        } catch (TimeoutException te) {
            logger.warning("Select Account Type page did not become visible within timeout.");
            return false;
        } catch (NoSuchElementException ne) {
            logger.warning("Select Account Type page element not found.");
            return false;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Unexpected error while checking Select Account Type page visibility: " + e.getMessage(), e);
            return false;
        }
    }


//    public void selectAccountType(String accountType) {
//        By locator = getAccountTypeLocator(accountType);
//
//        try {
//            // Wait until visible
//            WaitUtils.waitForElementVisible(locator, 30);
//            WebElement element = getDriver().findElement(locator);
//
//            try {
//                element.click();
//                logger.info("✅ Successfully clicked account type: " + accountType);
//            } catch (ElementNotInteractableException e) {
//                logger.warning("⚠️ Normal click failed for account type: " + accountType + ". Trying JS click...");
//                JavaScriptUtils.clickElementWithJS(getDriver(), element);
//                logger.info("✅ JS click executed for account type: " + accountType);
//            }
//
//        } catch (Exception e) {
//            logger.log(Level.SEVERE, "❌ Failed to select account type: " + accountType, e);
//            throw new RuntimeException("Could not select account type: " + accountType, e);
//        }
//    }
public void selectAccountType() {
    String accountType = "Company"; // default
    By locator = getAccountTypeLocator();

    try {
        WaitUtils.waitForElementVisible(locator, 30);
        WebElement element = getDriver().findElement(locator);

        try {
            element.click();
            logger.info("✅ Clicked account type: " + accountType);
        } catch (ElementNotInteractableException e) {
            logger.warning("⚠️ Normal click failed. Trying JS click for: " + accountType);
            JavaScriptUtils.clickElementWithJS(getDriver(), element);
        }
    } catch (Exception e) {
        logger.log(Level.SEVERE, "❌ Failed to select account type: " + accountType, e);
        throw new RuntimeException("Could not select account type: " + accountType, e);
    }
}






}
