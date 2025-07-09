package com.briskpe.tests;

import com.briskpe.framework.core.Config;
import com.briskpe.framework.core.DriverFactory;
import com.briskpe.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTest {

    @Parameters("platform")
    @BeforeMethod
    public void setUp(@Optional("WEB") String platform) {
        // Set platform for driver and framework context
        System.setProperty("platform", platform);
        DriverFactory.createDriver();

        // For web: open application URL from config
        if ("WEB".equalsIgnoreCase(platform)) {
            String baseUrl = Config.get("url");
            Assert.assertNotNull(baseUrl, "❌ Config 'url' is null. Please check your config.properties file.");
            DriverFactory.getDriver().get(baseUrl);

        }
    }

    @Test
    public void shouldLoginWithValidMobile() {
        LoginPage loginPage = new LoginPage();

        // 🔹 Flutter Web: executes JS to reveal semantics → waits for login tab
        Assert.assertTrue(loginPage.isLoginTabDisplayed(), "❌ Login tab should be visible");

        // 🔹 Enter a valid mobile number and request OTP
        loginPage.enterMobileNumber("9833010550")
                .tapGetOtp();

        // 🔹 TODO: Add assertion here to verify OTP screen or confirmation state
        // Example: Assert.assertTrue(new OtpPage().isOtpFieldDisplayed());
        Assert.assertTrue(loginPage.isEnterOtpTabDisplayed(), "Enter OTP tab not found");


    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
