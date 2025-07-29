package com.briskpe.tests;

import com.briskpe.framework.core.Config;
import com.briskpe.framework.pages.LoginPage;
import com.briskpe.framework.utils.JavaScriptUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(priority = 1)
    public void shouldLoginWithValidMobile() {
        test.info("🔐 Starting login flow with valid credentials");

        LoginPage loginPage = new LoginPage();
        String mobileNumber = Config.get("mobileNo");
        String otp = Config.get("OTP");

        test.info("🔹 Checking Login tab visibility");
        Assert.assertTrue(loginPage.isLoginTabDisplayed(), "❌ Login tab not visible");

        test.info("📱 Entering mobile number: " + mobileNumber);
        loginPage.enterMobileNumber(mobileNumber).tapGetOtp();

        test.info("📲 Verifying OTP screen display");
        Assert.assertTrue(loginPage.isEnterOtpTabDisplayed(), "❌ OTP screen not visible");

        test.info("🔐 Entering OTP: " + otp);
        loginPage.enterOTP(otp);

        test.info("👉 Clicking Verify button");
        Assert.assertTrue(elementUtils.isElementDisplayed(loginPage.getVerifyButton()), "❌ Verify button not visible");
        loginPage.clickVerifyButton();

        try {
            Thread.sleep(5000); // Replace with proper wait utility
        } catch (Exception e) {
            System.out.println("⚠️ Sleep interrupted: " + e.getMessage());
        }

        JavaScriptUtils.executeFlutterPlaceholderJs(driver);

        test.info("🧭 Waiting for App Tour screen...");
        Assert.assertTrue(dash.isAppTourScreenVisible(), "❌ App Tour screen not visible");

        test.info("⏭ Skipping App Tour...");
        Assert.assertTrue(dash.isSkipButtonVisible(), "❌ Skip button not visible");
        dash.clickSkipButton();

        test.pass("✅ Login completed successfully and user landed on Dashboard");
    }
}
