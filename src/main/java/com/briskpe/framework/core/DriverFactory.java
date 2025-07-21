package com.briskpe.framework.core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * DriverFactory manages WebDriver/AppiumDriver creation for Web, Android, iOS, and Mobile Web.
 */
public class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    /* ==========================  Public API  ========================== */

    /**
     * Initializes the driver only if it's not already initialized.
     */
    public static void initDriver() {
        if (DRIVER.get() == null) {
            createDriver();
        }
    }

    /**
     * Creates the driver based on platform property or config.
     */
    public static void createDriver() {
        String raw = System.getProperty("platform", Config.get("platform"));
        createDriver(Platform.fromString(raw));
    }

    /**
     * Creates driver instance for the specified platform.
     * @param platform Platform enum (WEB, ANDROID, IOS, MOBILE_WEB)
     */
    public static void createDriver(Platform platform) {
        try {
            switch (platform) {
                case WEB         -> DRIVER.set(createWebDriver());
                case ANDROID     -> DRIVER.set(createAndroidDriver());
                case IOS         -> DRIVER.set(createIOSDriver());
                case MOBILE_WEB  -> DRIVER.set(createMobileWebDriver());
                default          -> throw new IllegalStateException("Unhandled platform: " + platform);
            }
            System.out.println("✅ Driver initialized for platform: " + platform);
        } catch (MalformedURLException e) {
            throw new RuntimeException("❌ Bad Appium server URL", e);
        }
    }

    /**
     * Gets the current thread's driver.
     */
    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    /**
     * Quits the driver and removes it from the thread.
     */
    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
            System.out.println("🛑 Driver quit successfully");
        }
    }

    /* ==========================  Internal Helper Methods  ========================== */

    private static WebDriver createWebDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", prefs);

        WebDriver driver = new org.openqa.selenium.chrome.ChromeDriver(options);
        driver.manage().window().maximize();
        return driver;
    }

    private static AppiumDriver createAndroidDriver() throws MalformedURLException {
        UiAutomator2Options opts = new UiAutomator2Options()
                .setUdid(Config.get("android.udid"))
                .setAppPackage(Config.get("appPackage"))
                .setAppActivity(Config.get("appActivity"))
                .setAutomationName("Flutter")
                .setPlatformName("Android");

        opts.setNoReset(Boolean.parseBoolean(Config.get("noReset", "true")));

        return new AndroidDriver(
                URI.create(Config.get("appium.url", "http://127.0.0.1:4723")).toURL(),
                opts
        );
    }

    private static AppiumDriver createIOSDriver() throws MalformedURLException {
        XCUITestOptions opts = new XCUITestOptions()
                .setDeviceName(Config.get("ios.deviceName"))
                .setBundleId("com.briskpe.app")
                .amend("appium:automationName", "Flutter");

        return new IOSDriver(
                URI.create(Config.get("appium.url", "http://127.0.0.1:4723")).toURL(),
                opts
        );
    }

    /**
     * Creates WebDriver for Mobile Web (Android Chrome / iOS Safari)
     */
    private static WebDriver createMobileWebDriver() throws MalformedURLException {
        String deviceType = Config.get("mweb.device", "android").toLowerCase();

        if (deviceType.equals("android")) {
            UiAutomator2Options options = new UiAutomator2Options()
                    .setUdid(Config.get("android.udid"))
                    .setPlatformName("Android")
                    .setAutomationName("UiAutomator2")
                    .amend("browserName", "Chrome");

            return new AndroidDriver(
                    URI.create(Config.get("appium.url", "http://127.0.0.1:4723")).toURL(),
                    options
            );

        } else if (deviceType.equals("ios")) {
            XCUITestOptions options = new XCUITestOptions()
                    .setDeviceName(Config.get("ios.deviceName"))
                    .setPlatformName("iOS")
                    .setAutomationName("XCUITest")
                    .amend("browserName", "Safari");

            return new IOSDriver(
                    URI.create(Config.get("appium.url", "http://127.0.0.1:4723")).toURL(),
                    options
            );
        } else {
            throw new IllegalArgumentException("Unsupported mWeb device type: " + deviceType);
        }
    }
}
