package com.briskpe.framework.pages;

import com.briskpe.framework.core.Config;
import com.briskpe.framework.core.Platform;
import com.briskpe.framework.utils.JavaScriptUtils;
import com.briskpe.framework.utils.WaitUtils;
import com.briskpe.framework.utils.MouseActionsUtil;

import io.appium.java_client.AppiumBy;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.briskpe.framework.core.DriverManager.getDriver;


/**
 * Page object representing the DashBoard page for the cross-platform Flutter-based app.
 * Supports Web, Mobile Web, Android, and iOS using platform-specific locators and actions.
 */
public class DashBoard {

    private static final Logger logger = Logger.getLogger(DashBoard.class.getName());

    private final WebDriver driver = getDriver();
    private final Platform platform = Platform.fromString(System.getProperty("platform", "WEB"));
    private final MouseActionsUtil mouseActionsUtil = new MouseActionsUtil(driver);

    /**
     * Execute configured JS query on web platforms only, useful for Flutter web rendering tasks.
     * Logs execution status and errors.
     */
    public void executeJsQueryIfWeb() {
        if (platform == Platform.WEB || platform == Platform.MOBILE_WEB) {
            String jsQuery = Config.get("js.Query");
            if (jsQuery == null || jsQuery.isEmpty()) {
                logger.warning("js.Query is empty or not configured.");
                return;
            }
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript(jsQuery);
                logger.info("JS Query executed on Web platform.");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Failed to execute JS Query: " + e.getMessage(), e);
            }
        }
    }

    /* ================================ Locator Helpers ================================ */

    private By getFlutterKeyLocator(String key) {
        return AppiumBy.flutterKey(key);
    }

    private By getFlutterTextLocator(String text) {
        return AppiumBy.flutterText(text);
    }

    private By getWebXPath(String xpath) {
        return By.xpath(xpath);
    }

    // Example locator getter with cross-platform switch

    public By getAppTourScreenLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> getWebXPath("//flt-semantics[@flt-semantics-identifier='btn_next_app_guide']");
            case ANDROID, IOS -> getFlutterKeyLocator("btn_next_app_guide");
        };
    }

    private By getNextButtonLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> getWebXPath("//span[text()='Next'] | //*[text()='Next']");
            case ANDROID, IOS -> getFlutterKeyLocator("next_button");
        };
    }

    private By getSkipButtonLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> getWebXPath("//span[contains(text(),'Skip')]/..");
            case ANDROID, IOS -> getFlutterKeyLocator("skip_button");
        };
    }

    private By getPendingActionLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> getWebXPath("//flt-semantics[@flt-semantics-identifier='navitem-pending_actions']//span");
            case ANDROID, IOS -> getFlutterKeyLocator("navitem-pending_actions");
        };
    }

    private By getPendingActionPageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> getWebXPath("//flt-semantics[@flt-semantics-identifier=\"screen_pendingaction_list\"]");
            case ANDROID, IOS -> getFlutterKeyLocator("screen_pendingaction_list");
        };
    }

    private By getReceivedLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> getWebXPath("//flt-semantics[@flt-semantics-identifier='navitem-received_payments']");
            case ANDROID, IOS -> getFlutterKeyLocator("navitem-received_payments");
        };
    }

    private By getReceivedPaymentPageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> getWebXPath("//flt-semantics[@flt-semantics-identifier='screen_receivedpayment_list']");
            case ANDROID, IOS -> getFlutterKeyLocator("screen_receivedpayment_list");
        };
    }

    private By getRequestedPaymentLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> getWebXPath("//flt-semantics[@flt-semantics-identifier='navitem-requested_payments']");
            case ANDROID, IOS -> getFlutterKeyLocator("navitem-requested_payments");
        };
    }

    private By getRequestedPaymentPageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> getWebXPath("//flt-semantics[@flt-semantics-identifier='screen_paymentrequest_list']");
            case ANDROID, IOS -> getFlutterKeyLocator("screen_paymentrequest_list");
        };
    }

    private By getPaymentLinkLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> getWebXPath("//flt-semantics[@flt-semantics-identifier='navitem-payment_links']");
            case ANDROID, IOS -> getFlutterKeyLocator("navitem-payment_links");
        };
    }

    private By getPaymentLinkPageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> getWebXPath("//flt-semantics[@flt-semantics-identifier='screen_paymentlink_landing']");
            case ANDROID, IOS -> getFlutterKeyLocator("screen_paymentlink_landing");
        };
    }

    private By getQuickActionsDropdownLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> getWebXPath("(//flt-semantics[@role='button' and contains(@aria-label, 'Quick Actions')]//flt-semantics)[2]");
            case ANDROID, IOS -> getFlutterKeyLocator("quick_actions_dropdown");  // Fixed key – please verify actual Flutter key
        };
    }

    private By getQuickActionOptionLocator(String optionText) {
        return switch (platform) {
            case WEB, MOBILE_WEB -> getWebXPath(String.format("//flt-semantics[contains(text(), '%s')]", optionText));
            case ANDROID, IOS -> getFlutterTextLocator(optionText);
        };
    }

    private By getShareVirtualAccountPageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> getWebXPath("//flt-semantics[@flt-semantics-identifier=\"screen_virtualaccount_list\"]");
            case ANDROID, IOS -> getFlutterKeyLocator("screen_virtualaccount_list");
        };
    }

    private By getCreatePaymentRequestPageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> getWebXPath("//flt-semantics[contains(@aria-label,'Create Payment Request') and @flt-semantics-identifier=\"btn_close\"]");
            case ANDROID, IOS -> getFlutterKeyLocator("btn_close");
        };
    }

    private By getCreatePaymentLinkPageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> getWebXPath("//flt-semantics[@flt-semantics-identifier=\"screen_paymentlink_landing\"]");
            case ANDROID, IOS -> getFlutterKeyLocator("screen_paymentlink_landing");
        };
    }

    private By getAddPayerPageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> getWebXPath("//flt-semantics[@flt-semantics-identifier=\"btn_close\"]");
            case ANDROID, IOS -> getFlutterKeyLocator("btn_close");
        };
    }

    private By getCreateInvoicePageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> getWebXPath("//flt-semantics[@flt-semantics-identifier=\"btn_close\"]");
            case ANDROID, IOS -> getFlutterKeyLocator("btn_close");
        };
    }

    private By getDownloadStatementPageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> getWebXPath("//flt-semantics[@flt-semantics-identifier=\"screen_reports_screen\"]");
            case ANDROID, IOS -> getFlutterKeyLocator("screen_reports_screen");
        };
    }

    private By getNotificationIconLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> getWebXPath("//flt-semantics[contains(text(),'Show menu')]");
            case ANDROID, IOS -> getFlutterKeyLocator("notification_icon"); // Please verify key correctness
        };
    }

    private By getNotificationTabLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> getWebXPath("//flt-semantics[contains(text(),'Notifications')]");
            case ANDROID, IOS -> getFlutterKeyLocator("notification_tab"); // Please verify key correctness
        };
    }

    /* ================================ Visibility Checks ================================ */

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
        return isElementVisible(getPendingActionPageLocator(), "Pending Actions Page");
    }

    public boolean isReceivedPaymentVisible() {
        return isElementVisible(getReceivedLocator(), "Received Payments");
    }

    public boolean isReceivedPaymentPageVisible() {
        return isElementVisible(getReceivedPaymentPageLocator(), "Received Payments Page");
    }

    public boolean isRequestedPaymentVisible() {
        return isElementVisible(getRequestedPaymentLocator(), "Requested Payments");
    }

    public boolean isRequestedPaymentPageVisible() {
        return isElementVisible(getRequestedPaymentPageLocator(), "Requested Payments Page");
    }

    public boolean isPaymentLinkVisible() {
        return isElementVisible(getPaymentLinkLocator(), "Payment Links");
    }

    public boolean isPaymentLinkPageVisible() {
        return isElementVisible(getPaymentLinkPageLocator(), "Payment Links Page");
    }

    public boolean isQuickActionDropdownVisible() {
        return isElementVisible(getQuickActionsDropdownLocator(), "Quick Actions Dropdown");
    }

    public boolean isQuickActionOptionVisible(String optionText) {
        return isElementVisible(getQuickActionOptionLocator(optionText), "Quick Action Option '" + optionText + "'");
    }

    public boolean isShareVirtualAccountPageVisible() {
        return isElementVisible(getShareVirtualAccountPageLocator(), "Share Virtual Account Details");
    }

    public boolean isCreatePaymentRequestPageVisible() {
        return isElementVisible(getCreatePaymentRequestPageLocator(), "Create Payment Request");
    }

    public boolean isCreatePaymentLinkPageVisible() {
        return isElementVisible(getCreatePaymentLinkPageLocator(), "Create Payment Link");
    }

    public boolean isAddPayerPageVisible() {
        return isElementVisible(getAddPayerPageLocator(), "Add Payer");
    }

    public boolean isCreateInvoicePageVisible() {
        return isElementVisible(getCreateInvoicePageLocator(), "Create Invoice");
    }

    public boolean isDownloadStatementPageVisible() {
        return isElementVisible(getDownloadStatementPageLocator(), "Download Statement");
    }

    public boolean isNotificationIconVisible() {
        return isElementVisible(getNotificationIconLocator(), "Notification Icon");
    }

    public boolean isNotificationPopupVisible() {
        return isElementVisible(getNotificationTabLocator(), "Notification Popup Tab");
    }

    /* ================================ Click Actions ================================ */

    public void clickSkipButton() {
        clickElement(getSkipButtonLocator(), "Skip");
    }

    public void clickNextButton() {
        clickElement(getNextButtonLocator(), "Next");
    }

    public void clickNotificationIcon() {
        JavaScriptUtils.jsClick(getNotificationIconLocator(), driver);
        logger.info("Clicked Notification Icon using JS click.");
    }

    public void clickPendingAction() {
        JavaScriptUtils.jsClick(getPendingActionLocator(), driver);
        logger.info("Clicked Pending Action using JS click.");
    }

    public void clickReceivedPayment() {
        JavaScriptUtils.jsClick(getReceivedLocator(), driver);
        logger.info("Clicked Received Payment using JS click.");
    }

    public void clickRequestedPayment() {
        JavaScriptUtils.jsClick(getRequestedPaymentLocator(), driver);
        logger.info("Clicked Requested Payment using JS click.");
    }

    public void clickPaymentLink() {
        JavaScriptUtils.jsClick(getPaymentLinkLocator(), driver);
        logger.info("Clicked Payment Link using JS click.");
    }

    /**
     * Waits until the pending action button is clickable, with 20 seconds timeout.
     */
    public void waitUntilPendingActionClickable() {
        WaitUtils.untilClickable(getPendingActionLocator(), 20);
        logger.info("Waited for Pending Action button to become clickable.");
    }

    public void clickQuickActionDropdown() {
        JavaScriptUtils.jsClick(getQuickActionsDropdownLocator(), driver);
        logger.info("Clicked Quick Actions Dropdown using JS click.");
    }

    public void clickQuickActionOption(String optionText) {
        try {
            mouseActionsUtil.moveMouseToElement(getQuickActionOptionLocator(optionText));
            mouseActionsUtil.clickAtCurrentCursorPosition();
            logger.info("Clicked Quick Action Option: " + optionText);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to click Quick Action Option '" + optionText + "': " + e.getMessage(), e);
            throw e;
        }
    }

    public void doubleClickQuickActionOption(String optionText) {
        try {
            mouseActionsUtil.moveMouseToElement(getQuickActionOptionLocator(optionText));
            mouseActionsUtil.doubleClickAtCurrentCursorPosition();
            logger.info("Double-clicked Quick Action Option: " + optionText);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to double-click Quick Action Option '" + optionText + "': " + e.getMessage(), e);
            throw e;
        }
    }

    /* ================================ Helper Methods ================================ */

    /**
     * Helper method to check if an element is visible with wait and detailed logging.
     * @param locator By locator of the element
     * @param elementName Logical name of the element for logs
     * @return true if visible; false otherwise
     */
    private boolean isElementVisible(By locator, String elementName) {
        try {
            WaitUtils.untilVisible(locator, 30);
            boolean visible = driver.findElement(locator).isDisplayed();
            logger.info(elementName + " is visible.");
            return visible;
        } catch (Exception e) {
            logger.log(Level.WARNING, elementName + " not visible: " + e.getMessage(), e);
            return false;
        }
    }
    public void   clickElement(By locator, String skip) {
        WaitUtils.untilVisible(locator, 30); // Wait until element is visible
        WebElement element = getDriver().findElement(locator);
        element.click();  // Perform click
    }


}
