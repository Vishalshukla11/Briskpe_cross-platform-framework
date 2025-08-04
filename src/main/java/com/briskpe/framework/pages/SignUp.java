package com.briskpe.framework.pages;

import com.briskpe.framework.base.BasePage;
import com.briskpe.framework.core.DriverFactory;
import com.briskpe.framework.core.Platform;
import com.briskpe.framework.utils.RandomDataUtils;
import com.briskpe.framework.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignUp extends BasePage {

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



    // ===================== VERIFICATION METHODS ====================== //


}
