package com.briskpe.tests;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.briskpe.framework.core.Config;
import com.briskpe.framework.core.DriverFactory;
import com.briskpe.framework.pages.DashBoard;
import com.briskpe.framework.pages.LoginPage;
import com.briskpe.framework.utils.ElementUtils;
import com.briskpe.framework.utils.JavaScriptUtils;
import com.briskpe.framework.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {

    protected WebDriver driver;
    protected static ExtentReports extent;
    protected static ExtentTest test;
    protected ElementUtils elementUtils;
    protected DashBoard dash;

    @BeforeSuite(alwaysRun = true)
    public void initReport() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportPath = System.getProperty("user.dir") + "/reports/DashboardTestReport_" + timestamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Tester", "Vishal Shukla");
        extent.setSystemInfo("Environment", "Staging");
        extent.setSystemInfo("Platform", System.getProperty("platform", "WEB"));
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters("platform")
    public void setUp(Method method, @Optional("WEB") String platform) throws InterruptedException {
        // Set platform and launch driver
        System.setProperty("platform", platform);
        DriverFactory.createDriver();
        driver = DriverFactory.getDriver();
        elementUtils = new ElementUtils(driver);
        dash = new DashBoard();

        // Create ExtentTest instance
        test = extent.createTest(method.getName());

        // Navigate to base URL
        if ("WEB".equalsIgnoreCase(platform)) {
            String baseUrl = Config.get("url");
            Assert.assertNotNull(baseUrl, "❌ Base URL is null in config.properties");
            driver.get(baseUrl);
        }

        // ✅ Login and Skip App Tour
        loginAndSkipAppTour();
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

        Thread.sleep(5000); // Replace with proper wait

        JavaScriptUtils.executeFlutterPlaceholderJs(driver);

        test.info("🧭 Waiting for App Tour screen...");
        Assert.assertTrue(dash.isAppTourScreenVisible(), "❌ App Tour screen not visible");

        test.info("⏭ Skipping App Tour...");
        Assert.assertTrue(dash.isSkipButtonVisible(), "❌ Skip button not visible");
        dash.clickSkipButton();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            switch (result.getStatus()) {
                case ITestResult.FAILURE:
                    test.fail("❌ Test Failed: " + result.getThrowable());
                    String screenshotPath = ScreenshotUtils.takeScreenshot(result.getMethod().getMethodName());
                    test.addScreenCaptureFromPath(screenshotPath);
                    break;

                case ITestResult.SKIP:
                    test.skip("⚠️ Test Skipped: " + result.getThrowable());
                    break;

                case ITestResult.SUCCESS:
                    test.pass("✅ Test Passed");
                    break;
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error attaching screenshot: " + e.getMessage());
        } finally {
            DriverFactory.quitDriver();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void flushReport() {
        extent.flush();
    }
}
