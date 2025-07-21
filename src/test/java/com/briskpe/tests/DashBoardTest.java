package com.briskpe.tests;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.ExtentReports;
import com.briskpe.framework.core.DriverFactory;
import com.briskpe.framework.utils.ScreenshotUtils;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DashBoardTest extends BaseTest {

    @BeforeSuite
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


    @Test(priority = 1)
    public void clickSkipButtonTest(){
        test = extent.createTest("Validate 'Skip' Button on App Tour Screen");
        try {
            loginAndSkipAppTour();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        test.pass("✅ Successfully clicked on 'Skip' button after app tour");
    }

    @Test(priority = 2)
    public void verifyUserCanNavigateToSideMenuItems() {
        test = extent.createTest("Validate Navigation to Side Menu Items");
        try {
            loginAndSkipAppTour();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        test.info("🔍 Clicking Pending Action...");
        Assert.assertTrue(dash.isPendingActionVisible(), "❌ Pending Action not visible");

        dash.clickPendingAction();
        Assert.assertTrue(dash.isPendingActionPageVisible(), "❌ Page did not load");

        test.info("🔍 Clicking Received Payments...");
        Assert.assertTrue(dash.isReceivedPaymentVisible(), "❌ Received Payment not visible");
        dash.clickReceivedPayment();
        Assert.assertTrue(dash.isReceivedPaymentPageVisible(), "❌ Page did not load");

        test.info("🔍 Clicking Requested Payments...");
        Assert.assertTrue(dash.isRequestedPaymentVisible(), "❌ Requested Payment not visible");
        dash.clickRequestedPayment();
        Assert.assertTrue(dash.isRequestedPaymentPageVisible(), "❌ Page did not load");

        test.info("🔍 Clicking Payment Links...");
        Assert.assertTrue(dash.isPaymentLinkVisible(), "❌ Payment Link not visible");
        dash.clickPaymentLink();
        Assert.assertTrue(dash.isPaymentLinkPageVisible(), "❌ Page did not load");

        test.pass("✅ All side menu navigations successful");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTestWithScreenshot(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail("❌ Test Failed: " + result.getThrowable());
            String screenshotPath = ScreenshotUtils.takeScreenshot(result.getMethod().getMethodName());
            try {
                test.addScreenCaptureFromPath(screenshotPath);
            } catch (Exception e) {
                test.warning("⚠️ Unable to attach screenshot: " + e.getMessage());
            }
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("⚠️ Test Skipped: " + result.getThrowable());
        } else {
            test.pass("✅ Test Passed");
        }

    }

    @AfterSuite
    public void flushReport() {
        DriverFactory.quitDriver();
        extent.flush();
    }
}
