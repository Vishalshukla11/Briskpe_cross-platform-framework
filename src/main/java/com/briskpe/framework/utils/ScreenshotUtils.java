package com.briskpe.framework.utils;

import com.briskpe.framework.core.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    public static String takeScreenshot(String testName) {
        WebDriver driver = DriverFactory.getDriver();
        if (!(driver instanceof TakesScreenshot)) {
            return null;
        }

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String path = "screenshots/" + testName + "_" + timestamp + ".png";

        File dest = new File(path);
        dest.getParentFile().mkdirs();

        try {
            Files.copy(screenshot.toPath(), dest.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dest.getAbsolutePath();
    }
}
