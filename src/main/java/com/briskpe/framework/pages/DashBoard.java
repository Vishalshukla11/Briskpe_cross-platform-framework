package com.briskpe.framework.pages;

import com.briskpe.framework.core.DriverFactory;
import com.briskpe.framework.core.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashBoard {
    private final WebDriver driver = DriverFactory.getDriver();
    private final Platform platform = Platform.fromString(System.getProperty("platform", "WEB"));

    /* ---------- Locators ---------- */
    private By loginTab() {
        return switch (platform) {
            case WEB, ANDROID, IOS -> By.xpath("//*[@flt-semantics-identifier='mobile_number_entry']");
        };
    }

}
