package com.briskpe.framework.core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    /**
     * Creates driver instance based on platform, browser, and device configurations.
     *
     * @param platformStr Platform to run on ("WEB", "ANDROID", "IOS", "MOBILE_WEB")
     * @param browser     Browser type for WEB platform ("chrome", "firefox", etc.)
     * @param deviceName  Device UDID or device name for mobile platforms
     */
    public static void createDriver(String platformStr, String browser, String deviceName) {
        Platform platform = Platform.fromString(platformStr);
        try {
            switch (platform) {
                case WEB:
                    DRIVER.set(createWebDriver(browser));
                    break;
                case ANDROID:
                    DRIVER.set(createAndroidDriver(deviceName));
                    break;
                case IOS:
                    DRIVER.set(createIOSDriver(deviceName));
                    break;
                case MOBILE_WEB:
                    DRIVER.set(createMobileWebDriver());
                    break;
                default:
                    throw new IllegalStateException("Unhandled platform: " + platform);
            }
            System.out.println("✅ Driver initialized for platform: " + platform);
            System.out.println("🔧 Appium URL: " + Config.get("appium.url", "http://127.0.0.1:4723"));
        } catch (MalformedURLException e) {
            throw new RuntimeException("❌ Bad Appium server URL", e);
        }
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
            System.out.println("🛑 Driver quit successfully");
        }
    }

    /**
     * Create WebDriver for specified browser.
     *
     * @param browser Browser name ("chrome", "firefox", etc.) Defaults to chrome.
     * @return WebDriver instance
     */
    private static WebDriver createWebDriver(String browser) {
        if (browser == null || browser.isEmpty()) {
            browser = "chrome";
        }
        browser = browser.toLowerCase();

        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("profile.default_content_setting_values.notifications", 2);
                chromeOptions.setExperimentalOption("prefs", prefs);
                chromeOptions.addArguments("--disable-notifications");

                if ("true".equalsIgnoreCase(Config.get("headless", "false"))) {
                    chromeOptions.addArguments("--headless=new");
                }

                WebDriver chromeDriver = new org.openqa.selenium.chrome.ChromeDriver(chromeOptions);
                chromeDriver.manage().window().maximize();
                return chromeDriver;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                // Add Firefox-specific configurations here if needed
                WebDriver firefoxDriver = new FirefoxDriver(firefoxOptions);
                firefoxDriver.manage().window().maximize();
                return firefoxDriver;

            // Add other browsers (edge, safari) similarly if needed

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    /**
     * Create an Android AppiumDriver with optional device UDID.
     *
     * @param deviceName Android device UDID or emulator name
     * @return AppiumDriver instance
     * @throws MalformedURLException on bad Appium URL config
     */
    private static WebDriver createAndroidDriver(String deviceName) throws MalformedURLException {
        boolean isFlutter = Boolean.parseBoolean(Config.get("flutter", "false"));

        UiAutomator2Options opts = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName(isFlutter ? "Flutter" : "UiAutomator2")
                .amend("newCommandTimeout", 300)
                .setNoReset(Boolean.parseBoolean(Config.get("noReset", "true")));

        if (deviceName != null && !deviceName.isEmpty()) {
            opts.setUdid(deviceName);
        } else {
            opts.setUdid(requiredConfig("android.udid"));
        }

        if (!isFlutter) {
            opts.setAppPackage(requiredConfig("appPackage"))
                    .setAppActivity(requiredConfig("appActivity"));
        }

        return new AndroidDriver(
                URI.create(Config.get("appium.url", "http://127.0.0.1:4723")).toURL(),
                opts
        );
    }

    /**
     * Create an iOS AppiumDriver with optional device name.
     *
     * @param deviceName iOS device name or simulator
     * @return AppiumDriver instance
     * @throws MalformedURLException on bad Appium URL config
     */
    private static WebDriver createIOSDriver(String deviceName) throws MalformedURLException {
        boolean isFlutter = Boolean.parseBoolean(Config.get("flutter", "false"));

        XCUITestOptions opts = new XCUITestOptions()
                .setPlatformName("iOS")
                .setAutomationName(isFlutter ? "Flutter" : "XCUITest")
                .amend("newCommandTimeout", 300);

        if (deviceName != null && !deviceName.isEmpty()) {
            opts.setDeviceName(deviceName);
        } else {
            opts.setDeviceName(requiredConfig("ios.deviceName"));
        }

        String bundleId = Config.get("ios.bundleId", "com.briskpe.app");
        if (!isFlutter) {
            opts.setBundleId(bundleId);
        }

        return new IOSDriver(
                URI.create(Config.get("appium.url", "http://127.0.0.1:4723")).toURL(),
                opts
        );
    }

    /**
     * Create mobile web driver (Android Chrome or iOS Safari) using Appium.
     *
     * @return WebDriver instance
     * @throws MalformedURLException on bad Appium URL config
     */
    private static WebDriver createMobileWebDriver() throws MalformedURLException {
        String deviceType = Config.get("mweb.device", "android").toLowerCase();
        String appiumUrl = Config.get("appium.url", "http://127.0.0.1:4723");

        switch (deviceType) {
            case "android":
                UiAutomator2Options androidOptions = new UiAutomator2Options()
                        .setUdid(requiredConfig("android.udid"))
                        .setPlatformName("Android")
                        .setPlatformName(requiredConfig("deviceName"))
                        .setPlatformVersion(requiredConfig("platformVersion"))
                        .setAutomationName("UiAutomator2")
                        .amend("browserName", "Chrome")
                        .amend("newCommandTimeout", 300);
                return new AndroidDriver(URI.create(appiumUrl).toURL(), androidOptions);

            case "ios":
                XCUITestOptions iosOptions = new XCUITestOptions()
                        .setDeviceName(requiredConfig("ios.deviceName"))
                        .setPlatformName("iOS")
                        .setAutomationName("XCUITest")
                        .amend("browserName", "Safari")
                        .amend("newCommandTimeout", 300);
                return new IOSDriver(URI.create(appiumUrl).toURL(), iosOptions);

            default:
                throw new IllegalArgumentException("Unsupported mobile web device type: " + deviceType);
        }
    }

    /**
     * Helper method to get a mandatory config value or throw an exception if missing.
     *
     * @param key Configuration key
     * @return Config value
     */
    private static String requiredConfig(String key) {
        String value = Config.get(key);
        if (value == null || value.isEmpty()) {
            throw new IllegalStateException("Missing required config value for key: " + key);
        }
        return value;
    }
}
