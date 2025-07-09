package com.briskpe.tests;

import com.briskpe.framework.core.Config;
import com.briskpe.framework.core.DriverFactory;
import com.briskpe.framework.pages.LoginPage;

import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTest {

    private utils.ElementUtils elementUtils;

    @Parameters("platform")
    @BeforeMethod
    public void setUp(@Optional("WEB") String platform) {
        // Set platform for driver and framework context
        System.setProperty("platform", platform);
        DriverFactory.createDriver();

        // Initialize ElementUtils with driver
        elementUtils = new utils.ElementUtils(DriverFactory.getDriver());

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

        // 🔹 Wait for login tab
        Assert.assertTrue(loginPage.isLoginTabDisplayed(), "❌ Login tab should be visible");

        // 🔹 Enter valid number and click Get OTP
        loginPage.enterMobileNumber("9833010550")
                .tapGetOtp();

        // 🔹 Assert OTP screen
        Assert.assertTrue(loginPage.isEnterOtpTabDisplayed(), "❌ Enter OTP tab not found");

        // 🔹 Enter OTP
        loginPage.enterOTP(Config.get("OTP"));

        // 🔹 Verify if Verify Button is displayed before clicking
        Assert.assertTrue(elementUtils.isElementDisplayed(loginPage.getVerifyButton()), "❌ Verify Button not displayed");

        // 🔹 Click Verify
        loginPage.ClickVerifyButton();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
