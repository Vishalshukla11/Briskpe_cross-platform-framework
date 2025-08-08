package com.briskpe.framework.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import io.qameta.allure.Allure;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TestNG listener that captures screenshots on test failures,
 * logs results, and attaches screenshots to Allure reports.
 */
public class TestListener implements ITestListener {

    private static final Logger logger = Logger.getLogger(TestListener.class.getName());

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String screenshotPath = ScreenshotUtils.takeScreenshot(testName);

        logger.severe("❌ Test failed: " + testName);
        if (screenshotPath != null) {
            logger.info("📸 Screenshot saved at: " + screenshotPath);

            // Attach screenshot to Allure report if Allure is available and screenshot exists
            try (InputStream is = new FileInputStream(screenshotPath)) {
                Allure.addAttachment("Failure Screenshot - " + testName, is);
                logger.info("Screenshot attached to Allure report.");
            } catch (Exception e) {
                logger.log(Level.WARNING, "Failed to attach screenshot to Allure for test: " + testName, e);
            }
        } else {
            logger.warning("Screenshot path is null, no screenshot taken for test: " + testName);
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Optional: add logging or reporting for test start
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Optional: add logging or reporting for test success
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Optional: add logging or reporting for skipped tests
    }

    @Override
    public void onStart(ITestContext context) {
        // Optional: add logging or setup before test suite starts
    }

    @Override
    public void onFinish(ITestContext context) {
        // Optional: add logging or teardown after test suite finishes
    }
}
