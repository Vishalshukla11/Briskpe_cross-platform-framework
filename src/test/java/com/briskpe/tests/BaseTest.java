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
import com.briskpe.framework.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * BaseTest handles driver setup, test initialization,
 * login flow, and teardown with ExtentReports integration,
 * supporting cross-platform and cross-browser testing.
 */
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

    /**
     * Runs once before all tests. Cleans reports and initializes Extent report.
     */
    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        logger.info("Cleaning previous reports...");
        ReportCleaner.deleteAllReports();
        initExtentReport();
    }

    /**
     * Initializes ExtentReports with a timestamped HTML report.
     */
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

    /**
     * Setup before each test method. Initializes driver and performs login if required.
     *
     * @param method   Current test method
     * @param platform Platform parameter (default: WEB)
     * @param browser  Browser parameter (default: chrome)
     * @param deviceName Device name/UDID for mobile (optional)
     */
    @BeforeMethod(alwaysRun = true)
    @Parameters({"platform", "browser", "deviceName"})
    public void setUp(Method method,
                      @Optional("WEB") String platform,
                      @Optional("chrome") String browser,
                      @Optional("") String deviceName) {
        // Set system properties for DriverFactory usage
        System.setProperty("platform", platform);
        System.setProperty("browser", browser);
        if (!deviceName.isEmpty()) {
            System.setProperty("android.udid", deviceName);
            System.setProperty("ios.deviceName", deviceName);
        }

        try {
            DriverFactory.createDriver(platform, browser, deviceName);
            driver = DriverFactory.getDriver();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Driver initialization failed", e);
            Assert.fail("Driver initialization failed: " + e.getMessage());
        }

        elementUtils = new ElementUtils(driver);
        dash = new DashBoard();

        // Create a unique ExtentTest instance for this thread/test
        ExtentTest test = extent.createTest(method.getName());
        extentTest.set(test);

        logger.info(String.format("Starting test: %s on platform: %s, browser: %s, device: %s",
                method.getName(), platform, browser, deviceName));

        // For WEB tests, navigate to base URL
        if ("WEB".equalsIgnoreCase(platform)) {
            String baseUrl = Config.get("url");
            Assert.assertNotNull(baseUrl, "❌ Base URL is null in config.properties");
            driver.get(baseUrl);
        }

        // Perform login only if test belongs to "requiresLogin" group
        Test testAnnotation = method.getAnnotation(Test.class);
        if (testAnnotation != null &&
                testAnnotation.groups() != null &&
                Arrays.asList(testAnnotation.groups()).contains("requiresLogin")) {
            loginAndSkipAppTour();
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
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Otp screen verification failed: ", e);
            Assert.fail("Otp screen verification failed:: " + e.getMessage(), e);
        };
    }

    /**
     * Performs login and skips app tour if displayed.
     */
    public void loginAndSkipAppTour() {
        try {
            LoginPage login = new LoginPage();
            String mobile = Config.get("mobileNo");
            String otp = Config.get("OTP");

            extentTest.get().info("🔐 Logging in using mobile number...");
            logger.info("Verifying login tab visibility...");
            Assert.assertTrue(login.isLoginTabDisplayed(), "❌ Login tab not displayed");
            login.enterMobileNumber(mobile).tapGetOtp();

            extentTest.get().info("📲 Entering OTP...");
            Assert.assertTrue(login.isEnterOtpTabDisplayed(), "❌ OTP screen not visible");
            login.enterOTP(otp);
            Assert.assertTrue(elementUtils.isElementDisplayed(login.getVerifyButton()), "❌ Verify button not visible");
            login.clickVerifyButton();

            logger.info("Waiting for App Tour screen to load...");
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

    /**
     * Utility method to open user profile menu.
     *
     * @param usersProfile UsersProfile page object instance
     */
    protected void openUserProfileMenu(UsersProfile usersProfile) {
        usersProfile.clickProfileIcon();
        Assert.assertTrue(usersProfile.isProfileAndSettingButtonVisible(),
                "❌ Profile and Setting button should be visible");
    }

    /**
     * After each test, logs result, attaches screenshots, and quits driver.
     *
     * @param result Test result info
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            ExtentTest test = extentTest.get();

            switch (result.getStatus()) {
                case ITestResult.FAILURE:
                    test.fail("❌ Test Failed: ");
                    String screenshotPath = ScreenshotUtils.takeScreenshot(result.getMethod().getMethodName());
                    if (screenshotPath != null) {
                        test.addScreenCaptureFromPath(screenshotPath);
                    }
                    logger.warning("Test failed: " + result.getMethod().getMethodName() + ". Screenshot captured.");
                    break;

                case ITestResult.SKIP:
                    test.skip("⚠️ Test Skipped: " + result.getThrowable());
                    logger.info("Test skipped: " + result.getMethod().getMethodName());
                    break;

                case ITestResult.SUCCESS:
                    test.pass("✅ Test Passed");
                    logger.info("Test passed: " + result.getMethod().getMethodName());
                    break;

                default:
                    logger.info("Test with unknown status: " + result.getMethod().getMethodName());
                    break;
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error attaching screenshot or logging test result: ", e);
        } finally {
            DriverFactory.quitDriver();
            extentTest.remove(); // Clean up ThreadLocal
            logger.info("Driver quit and ThreadLocal cleared.");
        }
    }

    /**
     * Flushes Extent reports after all tests finish.
     */
    @AfterSuite(alwaysRun = true)
    public void flushReport() {
        if (extent != null) {
            extent.flush();
            logger.info("Extent report flushed.");
        }
    }
}
