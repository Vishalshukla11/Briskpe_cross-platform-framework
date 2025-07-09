package com.briskpe.framework.core;

// Import Appium and Selenium drivers and options
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;  // Handles driver binaries automatically

import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * DriverFactory manages WebDriver/AppiumDriver creation for Web, Android, and iOS.
 * Platform can be set via:
 *   1. JVM system property  -> -Dplatform=ANDROID
 *   2. config.properties    -> platform=WEB
 */
public class DriverFactory {

    // ThreadLocal ensures thread-safe WebDriver for parallel execution
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    /* ==========================  Public API  ========================== */

    /**
     * createDriver(): Determines the platform and creates the corresponding driver.
     * Priority: system property > config.properties file
     */
    public static void createDriver() {
        // Get platform from system property or config file
        String raw = System.getProperty("platform", Config.get("platform"));

        // Convert string to enum Platform and delegate to createDriver(Platform)
        createDriver(Platform.fromString(raw));
    }

    /**
     * createDriver(platform): Accepts a Platform enum and creates the correct driver.
     */
    public static void createDriver(Platform platform) {
        try {
            // Switch based on selected platform
            switch (platform) {
                case WEB     -> DRIVER.set(createWebDriver());     // For browser automation
                case ANDROID -> DRIVER.set(createAndroidDriver()); // For Android mobile app
                case IOS     -> DRIVER.set(createIOSDriver());     // For iOS mobile app
                default      -> throw new IllegalStateException("Unhandled platform: " + platform);
            }
        } catch (MalformedURLException e) {
            // Wrap any bad URL into a runtime exception
            throw new RuntimeException("Bad Appium server URL", e);
        }
    }

    /**
     * Returns the current thread's WebDriver/AppiumDriver
     */
    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    /**
     * Gracefully quits and removes the current thread's WebDriver/AppiumDriver
     */
    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();   // Quit browser/app session
            DRIVER.remove(); // Remove from ThreadLocal to prevent memory leaks
        }
    }

    /* ==========================  Internal Helper Methods  ========================== */

    /**
     * Creates a Chrome WebDriver with popup notifications disabled.
     */
    private static WebDriver createWebDriver() {
        // Automatically sets up compatible ChromeDriver
        WebDriverManager.chromedriver().setup();

        // Chrome options for custom capabilities
        ChromeOptions options = new ChromeOptions();

        // Disable browser notification popups (like 'Allow/Block notifications')
        options.addArguments("--disable-notifications");

        // Explicitly block notifications using browser preferences
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2); // 1 = allow, 2 = block
        options.setExperimentalOption("prefs", prefs);

        // Return a ChromeDriver with the configured options
        org.openqa.selenium.chrome.ChromeDriver driver = new org.openqa.selenium.chrome.ChromeDriver(options);
        driver.manage().window().maximize(); // ✅ Add this line to maximize
        return driver;

    }

    /**
     * Creates an AndroidDriver for Flutter app automation.
     */
    private static AppiumDriver createAndroidDriver() throws MalformedURLException {
        UiAutomator2Options opts = new UiAutomator2Options()
                .setUdid(Config.get("android.udid"))
                .setAppPackage(Config.get("appPackage"))
                .setAppActivity(Config.get("appActivity")) // ✅ fixed key
                .setAutomationName("Flutter")
                .setPlatformName("Android");

        // Read noReset flag from config (defaults to true)
        String noReset = Config.get("noReset", "true");
        opts.setNoReset(Boolean.parseBoolean(noReset));

        return new AndroidDriver(
                URI.create(Config.get("appium.url", "http://127.0.0.1:4723")).toURL(),
                opts
        );
    }


    /**
     * Creates an IOSDriver for Flutter app automation.
     */
    private static AppiumDriver createIOSDriver() throws MalformedURLException {
        // Setup desired capabilities for iOS + Flutter
        XCUITestOptions opts = new XCUITestOptions()
                .setDeviceName(Config.get("ios.deviceName"))       // Device name from config
                .setBundleId  ("com.briskpe.app")                  // App bundle ID (adjust if needed)
                .amend("appium:automationName", "Flutter");        // Use Flutter automation engine

        // Create IOSDriver with Appium server URL
        return new IOSDriver(
                URI.create(Config.get("appium.url", "http://127.0.0.1:4723")).toURL(),
                opts
        );
    }
}
