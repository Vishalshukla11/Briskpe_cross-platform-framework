package com.briskpe.tests;

import com.briskpe.framework.core.Config;
import com.briskpe.framework.pages.LoginPage;
import com.briskpe.framework.utils.JavaScriptUtils;
import com.briskpe.framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * LoginTest contains tests related to login functionality.
 */
public class LoginTest extends BaseTest {

    /**
     * Test login with valid mobile number and OTP.
     */
    @Test(priority = 1)
    public void shouldLoginWithValidMobile() {
        getTest().info("🔐 Starting login flow with valid credentials");

        LoginPage loginPage = new LoginPage();
        String mobileNumber = Config.get("mobileNo");
        String otp = Config.get("OTP");

        getTest().info("🔹 Checking Login tab visibility");
        Assert.assertTrue(loginPage.isLoginTabDisplayed(), "❌ Login tab not visible");

        getTest().info("📱 Entering mobile number: " + mobileNumber);
        loginPage.enterMobileNumber(mobileNumber).tapGetOtp();

        getTest().info("📲 Verifying OTP screen display");
        Assert.assertTrue(loginPage.isEnterOtpTabDisplayed(), "❌ OTP screen not visible");

        getTest().info("🔐 Entering OTP: " + otp);
        loginPage.enterOTP(otp);

        getTest().info("👉 Clicking Verify button");
        Assert.assertTrue(elementUtils.isElementDisplayed(loginPage.getVerifyButton()), "❌ Verify button not visible");
        loginPage.clickVerifyButton();

        getTest().info("⏳ Waiting for dashboard/app to load...");
        // Wait for App Tour screen to be visible instead of fixed sleep
        boolean appTourVisible = WaitUtils.untilVisible(dash.getAppTourScreenLocator(), 30);
        Assert.assertTrue(appTourVisible, "❌ App Tour screen not visible after login");

        JavaScriptUtils.executeFlutterPlaceholderJs(driver);

        getTest().info("⏭ Skipping App Tour...");
        Assert.assertTrue(dash.isSkipButtonVisible(), "❌ Skip button not visible");
        dash.clickSkipButton();

        getTest().pass("✅ Login completed successfully and user landed on Dashboard");
    }
}
