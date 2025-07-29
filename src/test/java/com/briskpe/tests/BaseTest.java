package com.briskpe.tests;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.briskpe.framework.core.Config;
import com.briskpe.framework.core.DriverFactory;
import com.briskpe.framework.pages.DashBoard;
import com.briskpe.framework.pages.LoginPage;
import com.briskpe.framework.pages.UsersProfile;
import com.briskpe.framework.utils.ElementUtils;
import com.briskpe.framework.utils.JavaScriptUtils;
import com.briskpe.framework.utils.ReportCleaner;
import com.briskpe.framework.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * BaseTest handles driver setup, report initialization,
 * login flow, and teardown logic for all test classes.
 */
public class BaseTest {

    protected WebDriver driver;
    protected static ExtentReports extent;
    protected static ExtentTest test;
    protected ElementUtils elementUtils;
    protected DashBoard dash;

    /**
     * Runs once before all tests. Cleans previous reports and initializes Extent report.
     */
    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        ReportCleaner.deleteAllReports();
        initExtentReport();
    }

    /**
     * Initializes ExtentReports with timestamped HTML report.
     */
    private void initExtentReport() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportPath = System.getProperty("user.dir") + "/reports/DashboardTestReport_" + timestamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(spark);

        extent.setSystemInfo("Tester", "Vishal Shukla");
        extent.setSystemInfo("Environment", "Staging");
        extent.setSystemInfo("Platform", System.getProperty("platform", "WEB"));
    }

    /**
     * Runs before each test method. Launches browser and performs login if required.
     */
    @BeforeMethod(alwaysRun = true)
    @Parameters("platform")
    public void setUp(Method method, @Optional("WEB") String platform) throws InterruptedException {
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

        // Perform login only for tests in group "requiresLogin"
        Test testAnnotation = method.getAnnotation(Test.class);
        if (testAnnotation != null &&
                testAnnotation.groups() != null &&
                Arrays.asList(testAnnotation.groups()).contains("requiresLogin")) {
            loginAndSkipAppTour();
        }
    }

    /**
     * Handles login and skips the app tour after verifying required elements.
     */
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

        Thread.sleep(5000); // Replace with explicit wait in production

        JavaScriptUtils.executeFlutterPlaceholderJs(driver);

        test.info("🧭 Waiting for App Tour screen...");
        Assert.assertTrue(dash.isAppTourScreenVisible(), "❌ App Tour screen not visible");

        test.info("⏭ Skipping App Tour...");
        Assert.assertTrue(dash.isSkipButtonVisible(), "❌ Skip button not visible");
        dash.clickSkipButton();
    }

    /**
     * Optional utility method to open the user profile menu from any test.
     */
    protected void openUserProfileMenu(UsersProfile usersProfile) {
        usersProfile.clickProfileIcon();
        Assert.assertTrue(usersProfile.isProfileAndSettingButtonVisible(),
                "❌ Profile and Setting button should be visible");
    }

    /**
     * Runs after each test method. Attaches result and screenshot to the report.
     */
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

    /**
     * Flushes the Extent report once the suite finishes.
     */
    @AfterSuite(alwaysRun = true)
    public void flushReport() {
        extent.flush();
    }
}
