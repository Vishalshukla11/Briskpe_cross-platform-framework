package com.briskpe.framework.pages;

import com.briskpe.framework.core.Config;
import com.briskpe.framework.core.DriverFactory;
import com.briskpe.framework.core.Platform;
import com.briskpe.framework.utils.JavaScriptUtils;
import com.briskpe.framework.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DashBoard {

    private final WebDriver driver = DriverFactory.getDriver();
    private final Platform platform = Platform.fromString(System.getProperty("platform", "WEB"));

    // Execute JS Query only if platform is WEB
    public void executeJsQueryIfWeb() {
        if (platform == Platform.WEB) {
            try {
                String jsQuery = Config.get("js.Query");
                if (jsQuery != null && !jsQuery.isEmpty()) {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript(jsQuery);
                    System.out.println("✅ JS Query executed on Web platform.");
                } else {
                    System.out.println("⚠️ js.Query is empty or not configured.");
                }
            } catch (Exception e) {
                System.out.println("❌ Failed to execute JS Query: " + e.getMessage());
            }
        }
    }

    // Locators
    private By getAppTourScreenLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier='btn_next_app_guide']");
            case ANDROID, IOS -> AppiumBy.flutterKey("btn_next_app_guide");
        };
    }

    private By getNextButtonLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//span[text()='Next'] | //*[text()='Next']");
            case ANDROID, IOS -> AppiumBy.flutterKey("next_button");
        };
    }

    private By getSkipButtonLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//span[contains(text(),'Skip')]/..");
            case ANDROID, IOS -> AppiumBy.flutterKey("skip_button");
        };
    }

    private By getPendingActionLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier='navitem-pending_actions']");
            case ANDROID, IOS -> AppiumBy.flutterKey("navitem-pending_actions");
        };
    }

    private By getPendingActionPageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier=\"screen_pendingaction_list\"]");
            case ANDROID, IOS -> AppiumBy.flutterKey("screen_pendingaction_list");
        };
    }

    private By getReceivedLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier='navitem-received_payments']");
            case ANDROID, IOS -> AppiumBy.flutterKey("navitem-received_payments");
        };
    }

    private By getReceivedPaymentPageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier='screen_receivedpayment_list']");
            case ANDROID, IOS -> AppiumBy.flutterKey("screen_receivedpayment_list");
        };
    }

    private By getRequestedPaymentLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier='navitem-requested_payments']");
            case ANDROID, IOS -> AppiumBy.flutterKey("navitem-requested_payments");
        };
    }

    private By getRequestedPaymentPageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier='screen_paymentrequest_list']");
            case ANDROID, IOS -> AppiumBy.flutterKey("screen_paymentrequest_list");
        };
    }

    private By getPaymentLinkLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier='navitem-payment_links']");
            case ANDROID, IOS -> AppiumBy.flutterKey("navitem-payment_links");
        };
    }

    private By getPaymentLinkPageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier='screen_paymentlink_landing']");
            case ANDROID, IOS -> AppiumBy.flutterKey("screen_paymentlink_landing");
        };
    }

    // Visibility Checks
    public boolean isAppTourScreenVisible() {
        return isElementVisible(getAppTourScreenLocator(), "App Tour screen");
    }

    public boolean isNextButtonVisible() {
        return isElementVisible(getNextButtonLocator(), "Next button");
    }

    public boolean isSkipButtonVisible() {
        return isElementVisible(getSkipButtonLocator(), "Skip button");
    }

    public boolean isPendingActionVisible() {
        return isElementVisible(getPendingActionLocator(), "Pending Actions");
    }

    public boolean isPendingActionPageVisible() {
        return isElementVisible(getPendingActionPageLocator(), "Pending Actions");
    }

    public boolean isReceivedPaymentVisible() {
        return isElementVisible(getReceivedLocator(), "Received Payments");
    }

    public boolean isReceivedPaymentPageVisible() {
        return isElementVisible(getReceivedPaymentPageLocator(), "Received Payments");
    }

    public boolean isRequestedPaymentVisible() {
        return isElementVisible(getRequestedPaymentLocator(), "Requested Payments");
    }

    public boolean isRequestedPaymentPageVisible() {
        return isElementVisible(getRequestedPaymentPageLocator(), "Requested Payments");
    }

    public boolean isPaymentLinkVisible() {
        return isElementVisible(getPaymentLinkLocator(), "Payment Links");
    }

    public boolean isPaymentLinkPageVisible() {
        return isElementVisible(getPaymentLinkPageLocator(), "Payment Links");
    }

    // Click Actions
    public void clickSkipButton() {
        clickElement(getSkipButtonLocator(), "Skip");
    }

    public void clickNextButton() {
        clickElement(getNextButtonLocator(), "Next");
    }

    public void clickPendingAction() {
        JavaScriptUtils.jsClick(getPendingActionLocator(), "Pending Actions");
    }

    public void clickReceivedPayment() {
        clickElement(getReceivedLocator(), "Received Payments");
    }

    public void clickRequestedPayment() {
        clickElement(getRequestedPaymentLocator(), "Requested Payments");
    }

    public void clickPaymentLink() {
        clickElement(getPaymentLinkLocator(), "Payment Links");
    }

    // Helper Methods
    public boolean isElementVisible(By locator, String elementName) {
        try {
            WaitUtils.untilVisible(locator, 30);
            boolean visible = driver.findElement(locator).isDisplayed();
            System.out.println("✅ " + elementName + " is visible");
            return visible;
        } catch (Exception e) {
            System.out.println("❌ " + elementName + " not visible: " + e.getMessage());
            return false;
        }
    }

    public void clickElement(By locator, String elementName) {
        try {
            WebElement element = driver.findElement(locator);
            element.click();
            System.out.println("✅ Clicked on " + elementName + " button");
        } catch (Exception e) {
            System.out.println("❌ Failed to click " + elementName + " button: " + e.getMessage());
        }
    }
}
