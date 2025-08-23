package com.briskpe.tests;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.briskpe.framework.core.Config;
import com.briskpe.framework.core.DriverFactory;
import com.briskpe.framework.core.DriverManager;
import com.briskpe.framework.pages.DashBoard;
import com.briskpe.framework.pages.LoginPage;
import com.briskpe.framework.pages.UsersProfile;
import com.briskpe.framework.utils.ElementUtils;
import com.briskpe.framework.utils.JavaScriptUtils;
import com.briskpe.framework.utils.ReportCleaner;
import com.briskpe.framework.utils.ScreenshotUtils;
import com.briskpe.framework.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseTest {

    protected WebDriver driver;
    protected ElementUtils elementUtils;
    protected DashBoard dash;

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static final Logger logger = Logger.getLogger(BaseTest.class.getName());

    protected ExtentTest getTest() {
        return extentTest.get();
    }

    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        logger.info("Cleaning previous reports...");
        ReportCleaner.deleteAllReports();
        initExtentReport();
    }

    private void initExtentReport() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportDir = System.getProperty("user.dir") + "/reports";
        String reportPath = reportDir + "/DashboardTestReport_" + timestamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(spark);

        extent.setSystemInfo("Tester", "Vishal Shukla");
        extent.setSystemInfo("Environment", "Staging");
        extent.setSystemInfo("Platform", System.getProperty("platform", "WEB"));

        logger.info("Extent Report initialized at: " + reportPath);
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"platform", "browser"})
    public void setUp(Method method,
                      @Optional("WEB") String platform,
                      @Optional("chrome") String browser) {

        // Initialize ExtentTest first
        ExtentTest test = extent.createTest(method.getName());
        extentTest.set(test);

        System.setProperty("platform", platform);
        System.setProperty("browser", browser);

        try {
            // Create driver using 2-parameter method
            DriverFactory.createDriver(DriverFactory.Platform.valueOf(platform.toUpperCase()), browser);
            driver = DriverManager.getDriver();
            elementUtils = new ElementUtils(driver);
            dash = new DashBoard();

            logger.info(String.format("Starting test: %s on platform: %s, browser: %s",
                    method.getName(), platform, browser));

            // Navigate to Web URL if platform is WEB
            if ("WEB".equalsIgnoreCase(platform)) {
                String baseUrl = Config.get("url");
                Assert.assertNotNull(baseUrl, "❌ Base URL is null in config.properties");
                driver.get(baseUrl);
            }

            // Login if test requires login
            Test testAnnotation = method.getAnnotation(Test.class);
            if (testAnnotation != null &&
                    testAnnotation.groups() != null &&
                    Arrays.asList(testAnnotation.groups()).contains("requiresLogin")) {
                loginAndSkipAppTour();
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Driver initialization failed", e);
            if (test != null) {
                test.skip("⚠️ Driver failed to initialize: " + e.getMessage());
            }
            DriverManager.quitDriver();
            driver = null;
            throw new SkipException("Driver initialization failed, skipping test.");
        }
    }

    public void loginAndSkipAppTour() {
        try {
            LoginPage login = new LoginPage();
            String mobile = Config.get("mobileNo");
            String otp = Config.get("OTP");

            extentTest.get().info("🔐 Logging in using mobile number...");
            Assert.assertTrue(login.isLoginTabDisplayed(), "❌ Login tab not displayed");
            login.enterMobileNumber(mobile).tapGetOtp();

            extentTest.get().info("📲 Entering OTP...");
            Assert.assertTrue(login.isEnterOtpTabDisplayed(), "❌ OTP screen not visible");
            login.enterOTP(otp);
            Assert.assertTrue(elementUtils.isElementDisplayed(login.getVerifyButton()), "❌ Verify button not visible");
            login.clickVerifyButton();

            WaitUtils.untilVisible(dash.getAppTourScreenLocator(), 30);
            JavaScriptUtils.executeFlutterPlaceholderJs(driver);

            extentTest.get().info("🧭 Waiting for App Tour screen...");
            Assert.assertTrue(dash.isAppTourScreenVisible(), "❌ App Tour screen not visible");

            extentTest.get().info("⏭ Skipping App Tour...");
            Assert.assertTrue(dash.isSkipButtonVisible(), "❌ Skip button not visible");
            dash.clickSkipButton();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Login and App Tour skipping failed: ", e);
            Assert.fail("Login and App Tour skipping failed: " + e.getMessage(), e);
        }
    }
    public void VerifyOtpScreen() {
        try {
            LoginPage login = new LoginPage();
            String otp = Config.get("OTP");
            extentTest.get().info("📲 Entering OTP...");
            Assert.assertTrue(login.isEnterOtpTabDisplayed(), "❌ OTP screen not visible");
            login.enterOTP(otp);
            Assert.assertTrue(elementUtils.isElementDisplayed(login.getVerifyButton()), "❌ Verify button not visible");
            login.clickVerifyButton();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Otp screen verification failed: ", e);
            Assert.fail("Otp screen verification failed:: " + e.getMessage(), e);
        }
    }

    protected void openUserProfileMenu(UsersProfile usersProfile) {
        usersProfile.clickProfileIcon();
        Assert.assertTrue(usersProfile.isProfileAndSettingButtonVisible(),
                "❌ Profile and Setting button should be visible");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            ExtentTest test = extentTest.get();

            switch (result.getStatus()) {
                case ITestResult.FAILURE:
                    if (test != null) {
                        test.fail("❌ Test Failed: ");
                        String screenshotPath = ScreenshotUtils.takeScreenshot(result.getMethod().getMethodName());
                        if (screenshotPath != null) {
                            test.addScreenCaptureFromPath(screenshotPath);
                        }
                    }
                    logger.warning("Test failed: " + result.getMethod().getMethodName());
                    break;
                case ITestResult.SKIP:
                    if (test != null) test.skip("⚠️ Test Skipped: " + result.getThrowable());
                    logger.info("Test skipped: " + result.getMethod().getMethodName());
                    break;
                case ITestResult.SUCCESS:
                    if (test != null) test.pass("✅ Test Passed");
                    logger.info("Test passed: " + result.getMethod().getMethodName());
                    break;
                default:
                    logger.info("Test with unknown status: " + result.getMethod().getMethodName());
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error attaching screenshot or logging test result: ", e);
        } finally {
            DriverManager.quitDriver();
            extentTest.remove();
            logger.info("Driver quit and ThreadLocal cleared.");
        }
    }

    @AfterSuite(alwaysRun = true)
    public void flushReport() {
        if (extent != null) {
            extent.flush();
            logger.info("Extent report flushed.");
        }
    }
}
