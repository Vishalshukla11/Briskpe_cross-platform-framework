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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for taking screenshots during test execution.
 */
public class ScreenshotUtils {

    private static final Logger logger = Logger.getLogger(ScreenshotUtils.class.getName());

    /**
     * Captures a screenshot and saves it to the screenshots folder with a timestamp.
     *
     * @param testName Logical name of the test or step
     * @return absolute path of the screenshot file, or null if screenshot is not supported
     */
    public static String takeScreenshot(String testName) {
        WebDriver driver = DriverFactory.getDriver();

        if (!(driver instanceof TakesScreenshot)) {
            logger.warning("Driver does not support taking screenshots.");
            return null;
        }

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotDir = System.getProperty("user.dir") + File.separator + "screenshots";
        String filename = testName + "_" + timestamp + ".png";
        File dest = new File(screenshotDir, filename);

        try {
            if (!dest.getParentFile().exists()) {
                boolean dirsCreated = dest.getParentFile().mkdirs();
                if (dirsCreated) {
                    logger.info("Created screenshot directory: " + dest.getParent());
                }
            }

            Files.copy(screenshot.toPath(), dest.toPath());
            logger.info("Screenshot saved: " + dest.getAbsolutePath());
            return dest.getAbsolutePath();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save screenshot: " + dest.getAbsolutePath(), e);
            return null;
        }
    }
}
