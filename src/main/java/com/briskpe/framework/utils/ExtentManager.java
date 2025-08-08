package com.briskpe.framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports extent;

    private static synchronized ExtentReports createInstance() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport_" + timestamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setReportName("BriskPe Automation Report");
        spark.config().setDocumentTitle("BriskPe Test Results");

        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(spark);

        // Setting system info dynamically - can read from config or env variables
        extentReports.setSystemInfo("Environment", System.getProperty("env", "Staging"));
        extentReports.setSystemInfo("Tester", System.getProperty("tester", "Vishal Shukla"));
        extentReports.setSystemInfo("Platform", System.getProperty("platform", "WEB"));

        // Optionally add more info like OS, Java version, etc.
        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));

        return extentReports;
    }

    public static ExtentReports getInstance() {
        if (extent == null) {
            extent = createInstance();
        }
        return extent;
    }

    /**
     * Flushes the report output to the disk.
     * Should be called once after all tests are complete.
     */
    public static synchronized void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
