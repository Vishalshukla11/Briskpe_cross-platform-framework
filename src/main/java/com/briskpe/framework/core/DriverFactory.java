package com.briskpe.framework.core;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.options.SupportsBrowserNameOption;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

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

    /** ---------------- WEB DRIVER ---------------- **/
    private static WebDriver createWebDriver(String browser) {
        if (browser == null || browser.isEmpty()) browser = "chrome";
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
                if ("true".equalsIgnoreCase(Config.get("headless", "false"))) {
                    firefoxOptions.addArguments("--headless");
                }
                WebDriver firefoxDriver = new FirefoxDriver(firefoxOptions);
                firefoxDriver.manage().window().maximize();
                return firefoxDriver;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    /** ---------------- ANDROID NATIVE DRIVER ---------------- **/
    private static WebDriver createAndroidDriver(String deviceName) throws MalformedURLException {
        boolean isFlutter = Boolean.parseBoolean(Config.get("flutter", "false"));
        UiAutomator2Options opts = new UiAutomator2Options();
        applyCommonMobileOptions(opts, "Android", deviceName, isFlutter ? "Flutter" : "UiAutomator2");

        String appPath = Config.get("appPath", "");
        if (!appPath.isEmpty()) {
            opts.setApp(appPath);
        } else if (!isFlutter) {
            opts.setAppPackage(requiredConfig("appPackage"))
                    .setAppActivity(requiredConfig("appActivity"));
        }

        logCapabilities(opts.asMap());
        return new AndroidDriver(getAppiumUrl(), opts);
    }

    /** ---------------- IOS NATIVE DRIVER ---------------- **/
    private static WebDriver createIOSDriver(String deviceName) throws MalformedURLException {
        boolean isFlutter = Boolean.parseBoolean(Config.get("flutter", "false"));
        XCUITestOptions opts = new XCUITestOptions();
        applyCommonMobileOptions(opts, "iOS", deviceName, isFlutter ? "Flutter" : "XCUITest");

        String appPath = Config.get("appPath", "");
        if (!appPath.isEmpty()) {
            opts.setApp(appPath);
        } else if (!isFlutter) {
            opts.setBundleId(Config.get("ios.bundleId", "com.briskpe.app"));
        }

        logCapabilities(opts.asMap());
        return new IOSDriver(getAppiumUrl(), opts);
    }

    /** ---------------- MOBILE WEB DRIVER ---------------- **/
    private static WebDriver createMobileWebDriver() throws MalformedURLException {
        String deviceType = Config.get("mweb.device", "android").toLowerCase();

        switch (deviceType) {
            case "android":
                UiAutomator2Options androidOptions = new UiAutomator2Options();
                applyCommonMobileOptions(androidOptions, "Android", requiredConfig("deviceName"), "UiAutomator2");
                androidOptions.amend("browserName", "Chrome");
                logCapabilities(androidOptions.asMap());
                return new AndroidDriver(getAppiumUrl(), androidOptions);

            case "ios":
                XCUITestOptions iosOptions = new XCUITestOptions();
                applyCommonMobileOptions(iosOptions, "iOS", requiredConfig("ios.deviceName"), "XCUITest");
                iosOptions.amend("browserName", "Safari");
                logCapabilities(iosOptions.asMap());
                return new IOSDriver(getAppiumUrl(), iosOptions);

            default:
                throw new IllegalArgumentException("Unsupported mobile web device type: " + deviceType);
        }
    }

    /** ---------------- COMMON HELPERS ---------------- **/
    private static void applyCommonMobileOptions(Object opts, String platformName, String deviceName, String automationName) {
        if (opts instanceof UiAutomator2Options) {
            ((UiAutomator2Options) opts)
                    .setPlatformName(platformName)
                    .setDeviceName(deviceName)
                    .setPlatformVersion(requiredConfig("platformVersion"))
                    .setAutomationName(automationName)
                    .setNewCommandTimeout(Duration.ofSeconds(300))
                    .setNoReset(Boolean.parseBoolean(Config.get("noReset", "true")))
                    .setUdid(requiredConfig(platformName.equalsIgnoreCase("Android") ? "android.udid" : "ios.udid"));
        } else if (opts instanceof XCUITestOptions) {
            ((XCUITestOptions) opts)
                    .setPlatformName(platformName)
                    .setDeviceName(deviceName)
                    .setPlatformVersion(requiredConfig("platformVersion"))
                    .setAutomationName(automationName)
                    .setNewCommandTimeout(Duration.ofSeconds(300));
        }
    }

    private static URL getAppiumUrl() throws MalformedURLException {
        return URI.create(Config.get("appium.url", "http://127.0.0.1:4723")).toURL();
    }

    private static String requiredConfig(String key) {
        String value = Config.get(key);
        if (value == null || value.isEmpty()) {
            throw new IllegalStateException("Missing required config value for key: " + key);
        }
        return value;
    }

    private static void logCapabilities(Map<String, ?> caps) {
        System.out.println("📋 Capabilities: " + caps);
    }
}
