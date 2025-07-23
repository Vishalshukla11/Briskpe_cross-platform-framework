package com.briskpe.tests;

import com.briskpe.framework.core.Config;
import com.briskpe.framework.pages.LoginPage;
import com.briskpe.framework.utils.JavaScriptUtils;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class LoginTest extends BaseTest {

    @Test
    public void shouldLoginWithValidMobile() {
        test = extent.createTest("Login Test with Valid Mobile");

        LoginPage loginPage = new LoginPage();
        String mobileNumber = Config.get("mobileNo");
        String otp = Config.get("OTP");

        test.info("🔹 Checking Login tab visibility");
        Assert.assertTrue(loginPage.isLoginTabDisplayed(), "❌ Login tab not visible");

        test.info("🔹 Entering mobile number: " + mobileNumber);
        loginPage.enterMobileNumber(mobileNumber).tapGetOtp();

        test.info("🔹 Checking OTP screen...");
        Assert.assertTrue(loginPage.isEnterOtpTabDisplayed(), "❌ OTP screen not visible");

        test.info("🔹 Entering OTP: " + otp);
        loginPage.enterOTP(otp);

        test.info("🔹 Clicking Verify...");
        Assert.assertTrue(elementUtils.isElementDisplayed(loginPage.getVerifyButton()), "❌ Verify button not visible");
        loginPage.clickVerifyButton();

        try{
            Thread.sleep(5000); // Replace with proper wait
        }catch (Exception e)
        {
            System.out.print(e.getMessage());
        }

        JavaScriptUtils.executeFlutterPlaceholderJs(driver);

        test.info("🧭 Waiting for App Tour screen...");
        Assert.assertTrue(dash.isAppTourScreenVisible(), "❌ App Tour screen not visible");

        test.info("⏭ Skipping App Tour...");
        Assert.assertTrue(dash.isSkipButtonVisible(), "❌ Skip button not visible");
        dash.clickSkipButton();
        test.pass("✅ Login completed successfully");
    }
}
