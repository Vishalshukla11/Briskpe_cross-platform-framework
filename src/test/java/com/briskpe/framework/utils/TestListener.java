package com.briskpe.framework.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import io.qameta.allure.Allure;

import java.io.FileInputStream;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String screenshotPath = ScreenshotUtils.takeScreenshot(testName);
        System.out.println("❌ Test failed: " + testName);
        System.out.println("📸 Screenshot saved at: " + screenshotPath);

        // Attach to Allure, if Allure is used
        try {
            Allure.addAttachment("Failure Screenshot", new FileInputStream(screenshotPath));
        } catch (Exception ignored) {}
    }

    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestSuccess(ITestResult result) {}
    @Override public void onTestSkipped(ITestResult result) {}
    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
}
