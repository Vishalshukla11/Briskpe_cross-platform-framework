package com.briskpe.tests;

import com.aventstack.extentreports.*;
import com.briskpe.framework.core.Config;
import com.briskpe.framework.core.DriverFactory;
import com.briskpe.framework.pages.DashBoard;
import com.briskpe.framework.pages.LoginPage;
import com.briskpe.framework.utils.ElementUtils;
import com.briskpe.framework.utils.ExtentManager;
import com.briskpe.framework.utils.JavaScriptUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public class BaseTest {
    protected WebDriver driver;
    protected static ExtentReports extent;
    protected static ExtentTest test;
    protected ElementUtils elementUtils;
    protected DashBoard dash;

    @BeforeSuite(alwaysRun = true)
    public void startReport() {
        extent = ExtentManager.getInstance();
    }

    @AfterSuite(alwaysRun = true)
    public void endReport() {
        extent.flush();
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters("platform")
    public void setUp(Method method, @Optional("WEB") String platform) {
        System.setProperty("platform", platform);
        DriverFactory.createDriver();
        driver = DriverFactory.getDriver();

        elementUtils = new ElementUtils(driver);
        dash = new DashBoard();

        test = extent.createTest(method.getName());

        if ("WEB".equalsIgnoreCase(platform)) {
            String baseUrl = Config.get("url");
            Assert.assertNotNull(baseUrl, "❌ Base URL is null in config.properties");
            driver.get(baseUrl);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        switch (result.getStatus()) {
            case ITestResult.FAILURE:
                test.fail(result.getThrowable());
                break;
            case ITestResult.SUCCESS:
                test.pass("✅ Test passed");
                break;
            case ITestResult.SKIP:
                test.skip("⚠️ Test skipped: " + result.getThrowable());
                break;
        }
        DriverFactory.quitDriver();
    }

    public void loginAndSkipAppTour() throws InterruptedException {
        LoginPage login = new LoginPage();
        String mobile = Config.get("mobileNo");
        String otp = Config.get("OTP");

        test.info("🔐 Logging in using mobile number...");
        Assert.assertTrue(login.isLoginTabDisplayed(), "❌ Login tab not displayed");
        login.enterMobileNumber(mobile).tapGetOtp();

        test.info("📲 Entering OTP...");
        Assert.assertTrue(login.isEnterOtpTabDisplayed(), "❌ OTP screen not visible");
        login.enterOTP(otp);
        Assert.assertTrue(elementUtils.isElementDisplayed(login.getVerifyButton()), "❌ Verify button not visible");
        login.clickVerifyButton();

        Thread.sleep(5000); // Optional: Replace with explicit wait

        JavaScriptUtils.executeFlutterPlaceholderJs(driver);

        test.info("🧭 Waiting for App Tour screen...");
        Assert.assertTrue(dash.isAppTourScreenVisible(), "❌ App Tour screen not visible");

        test.info("⏭ Skipping App Tour...");
        Assert.assertTrue(dash.isSkipButtonVisible(), "❌ Skip button not visible");
        dash.clickSkipButton();
    }
}
