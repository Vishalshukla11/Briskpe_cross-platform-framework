package com.briskpe.tests;

import com.briskpe.framework.core.Config;
import com.briskpe.framework.pages.LoginPage;
import com.briskpe.framework.utils.JavaScriptUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.briskpe.tests.BaseTest.test;

public class SignUpTest extends BaseTest {
    @Test(priority = 1)
    public void shouldLoginWithValidMobile() {
        test.info("🔐 Starting login flow with valid credentials");

        LoginPage loginPage = new LoginPage();
        String mobileNumber = Config.get("mobileNo");
        String otp = Config.get("OTP");

        test.info("🔹 Checking Login tab visibility");
        Assert.assertTrue(loginPage.isLoginTabDisplayed(), "❌ Login tab not visible");

    }
}
