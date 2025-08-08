package com.briskpe.framework.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class to perform mouse actions like click, double-click, move, and offset clicks.
 * Combines Selenium Actions and AWT Robot for flexible UI interactions.
 */
public class MouseActionsUtil {

    private static final Logger logger = Logger.getLogger(MouseActionsUtil.class.getName());

    private final WebDriver driver;
    private final Actions actions;
    private Robot robot;

    /**
     * Constructor initializing Actions and Robot instances.
     *
     * @param driver the WebDriver instance
     */
    public MouseActionsUtil(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            logger.log(Level.SEVERE, "Failed to initialize Robot for mouse control", e);
            // Optionally rethrow or handle gracefully
        }
    }

    /**
     * Performs a single left-click at the **current** OS cursor position.
     * Uses java.awt.Robot.
     */
    public void clickAtCurrentCursorPosition() {
        if (robot == null) {
            logger.warning("Robot not initialized; cannot perform clickAtCurrentCursorPosition.");
            return;
        }
        try {
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            logger.info("Performed single click at current mouse cursor position.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during Robot click at current position", e);
        }
    }

    /**
     * Performs double-click at the current mouse position using Selenium Actions.
     */
    public void doubleClickAtCurrentCursorPosition() {
        try {
            actions.doubleClick().perform();
            logger.info("Performed double click at current mouse cursor position.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during double click at current cursor position", e);
        }
    }

    /**
     * Moves mouse by a relative offset from current position and clicks.
     *
     * @param xOffset horizontal offset relative to current position
     * @param yOffset vertical offset relative to current position
     */
    public void moveMouseByOffsetAndClick(int xOffset, int yOffset) {
        try {
            actions.moveByOffset(xOffset, yOffset)
                    .click()
                    .perform();
            logger.info(String.format("Moved mouse by offset (%d, %d) and clicked.", xOffset, yOffset));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during move and click by offset", e);
        } finally {
            // Reset mouse position to avoid offset stacking issues in subsequent calls
            actions.moveByOffset(-xOffset, -yOffset).perform();
        }
    }

    /**
     * Moves mouse to a specific element, then offsets by (xOffset, yOffset) relative to the element, then clicks.
     *
     * @param locator WebElement locator to move to
     * @param xOffset horizontal offset relative to element center
     * @param yOffset vertical offset relative to element center
     */
    public void moveToElementThenOffsetAndClick(By locator, int xOffset, int yOffset) {
        try {
            WebElement element = driver.findElement(locator);
            actions.moveToElement(element)
                    .moveByOffset(xOffset, yOffset)
                    .click()
                    .perform();
            logger.info(String.format("Moved to element %s then offset (%d, %d) and clicked.", locator, xOffset, yOffset));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during move to element then offset and click", e);
        }
    }

    /**
     * Moves mouse cursor to the center of the specified element without clicking.
     *
     * @param locator locator of the WebElement to move to
     */
    public void moveMouseToElement(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            actions.moveToElement(element).perform();
            logger.info("Moved mouse to element: " + locator);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during move to element", e);
        }
    }
}
