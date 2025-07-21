package com.briskpe.tests;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.briskpe.framework.core.Config;
import com.briskpe.framework.core.DriverFactory;
import com.briskpe.framework.pages.LoginPage;
import com.briskpe.framework.utils.ElementUtils;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.text.SimpleDateFormat;
import java.util.Date;
public class LoginTest extends BaseTest {

    @BeforeSuite
    public void initReport() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportPath = System.getProperty("user.dir") + "/reports/LoginTestReport_" + timestamp + ".html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Tester", Config.get("testerName"));
        extent.setSystemInfo("Environment", Config.get("environment"));
        extent.setSystemInfo("Platform", System.getProperty("platform", "WEB"));
    }

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

        test.pass("✅ Login completed successfully");
    }

    @Test
    public void shouldNotLoginWithInvalidMobileNumber()
    {
        test= extent.createTest("Login with inValid Mobile Number ");
        LoginPage loginPage = new LoginPage();

    }

    @AfterMethod(alwaysRun = true)
    public void tearDownLoginTest(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail("❌ Test Failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("⚠️ Test Skipped: " + result.getThrowable());
        } else {
            test.pass("✅ Test Passed");
        }

        DriverFactory.quitDriver();
    }

    @AfterSuite
    public void flushLoginReport() {
        extent.flush();
    }
}
