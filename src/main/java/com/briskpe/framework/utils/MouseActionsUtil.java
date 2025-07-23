package com.briskpe.framework.utils;

import java.awt.Robot;
import java.awt.event.InputEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MouseActionsUtil {

    private final WebDriver driver;
    private final Actions actions;

    public MouseActionsUtil(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver); // Initialize Actions object here
    }

    public void clickAtCurrentCursorPosition() {
        try {
            Robot robot = new Robot();
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doubleClickAtCurrentCursorPosition() {
        actions.doubleClick().perform();
    }

    public void moveMouseByOffsetAndClick(int xOffset, int yOffset) {
        actions.moveByOffset(xOffset, yOffset).click().perform();
    }

    public void moveToElementThenOffsetAndClick(By locator, int xOffset, int yOffset) {
        WebElement element = driver.findElement(locator);
        actions.moveToElement(element)
                .moveByOffset(xOffset, yOffset)
                .click()
                .perform();
    }

    public void moveMouseToElement(By locator) {
        WebElement element = driver.findElement(locator);
        actions.moveToElement(element).perform();
    }
}
