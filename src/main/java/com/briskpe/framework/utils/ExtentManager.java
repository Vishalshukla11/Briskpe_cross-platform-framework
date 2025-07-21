package com.briskpe.framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setReportName("BriskPe Automation Report");
            spark.config().setDocumentTitle("BriskPe Test Results");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            // System info
            extent.setSystemInfo("Environment", "Staging");
            extent.setSystemInfo("Tester", "Vishal Shukla");
            extent.setSystemInfo("Platform", System.getProperty("platform", "WEB"));
        }
        return extent;
    }
}
