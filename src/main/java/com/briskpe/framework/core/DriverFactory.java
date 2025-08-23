package com.briskpe.framework.core;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

public class DriverFactory {

    public enum Platform {
        WEB,
        ANDROID,
        IOS
    }

    public static WebDriver createDriver(Platform platform, String browserOrAppType) throws MalformedURLException {
        switch (platform) {
            case WEB:
                return createWebDriver(browserOrAppType);
            case ANDROID:
                if ("flutter".equalsIgnoreCase(browserOrAppType)) {
                    return createFlutterAndroidDriver();
                }
                return createAndroidDriver();
            case IOS:
                if ("flutter".equalsIgnoreCase(browserOrAppType)) {
                    return createFlutterIOSDriver();
                }
                return createIOSDriver();
            default:
                throw new IllegalArgumentException("Unsupported platform: " + platform);
        }
    }

    private static WebDriver createWebDriver(String browser) {
        if (browser == null || browser.isEmpty()) browser = "chrome";
        if ("chrome".equalsIgnoreCase(browser)) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            options.addArguments("--start-maximized");
            return new org.openqa.selenium.chrome.ChromeDriver(options);
        }
        throw new IllegalArgumentException("Unsupported browser: " + browser);
    }

    private static WebDriver createAndroidDriver() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();

        // Read values from config
        String deviceName = Config.get("android.deviceName"); // e.g., "SM_A505F"
        String appPackage = Config.get("appPackage");         // e.g., "com.briskpe.sme.mobile.staging"
        String appActivity = Config.get("appActivity");       // e.g., "com.briskpe.sme_portal.MainActivity"
        String automationName = Config.get("android.automationName", "UiAutomator2"); // default to UiAutomator2

        options.setPlatformName("Android");
        options.setDeviceName(deviceName);
        options.setAppPackage(appPackage);
        options.setAppActivity(appActivity);
        options.setAutomationName(automationName);
        options.setNewCommandTimeout(Duration.ofSeconds(300));

        URL appiumServer = new URL("http://127.0.0.1:4723/wd/hub");
        return new AndroidDriver(appiumServer, options);
    }


    // For Flutter Android
    private static WebDriver createFlutterAndroidDriver() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setDeviceName(Config.get("android.deviceName"));  // from config
        options.setAppPackage(Config.get("appPackage"));          // from config
        options.setAppActivity(Config.get("appActivity"));        // from config
        options.setAutomationName("Flutter");                     // exact string for Appium Flutter Driver
        options.setNewCommandTimeout(Duration.ofSeconds(300));

        URL appiumServer = new URL("http://127.0.0.1:4723/wd/hub");
        return new AndroidDriver(appiumServer, options);
    }


    private static WebDriver createIOSDriver() throws MalformedURLException {
        XCUITestOptions options = new XCUITestOptions();
        options.setPlatformName("iOS");
        options.setDeviceName("iPhone Simulator");
        options.setBundleId("com.your.bundleid");
        options.setAutomationName("XCUITest");
        options.setNewCommandTimeout(Duration.ofSeconds(300));
        URL appiumServer = new URL("http://127.0.0.1:4723/wd/hub");
        return new IOSDriver(appiumServer, options);
    }

    private static WebDriver createFlutterIOSDriver() throws MalformedURLException {
        XCUITestOptions options = new XCUITestOptions();
        options.setPlatformName("iOS");
        options.setDeviceName("iPhone Simulator");
        options.setAutomationName("Flutter");
        options.setNewCommandTimeout(Duration.ofSeconds(300));
        URL appiumServer = new URL("http://127.0.0.1:4723/wd/hub");
        return new IOSDriver(appiumServer, options);
    }
}
