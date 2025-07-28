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
import com.briskpe.framework.utils.MouseActionsUtil;
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
    public By getAppTourScreenLocator() {
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
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier='navitem-pending_actions']//span");
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
    private  By getQuickActionsDropDownLocator(){
        return  switch (platform)
        {
            case WEB, MOBILE_WEB -> By.xpath("(//flt-semantics[@role='button' and contains(@aria-label, 'Quick Actions')]//flt-semantics)[2]");
            case ANDROID, IOS -> AppiumBy.flutterKey("screen_paymentlink_landing");

        };
    }


    private By getQuickActionOptionLocator(String optionText) {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath(String.format("//flt-semantics[contains(text(), '%s')]", optionText));
            case ANDROID, IOS -> AppiumBy.flutterText(optionText);
        };
    }

    private By getShareVirtualAccountDetailsPageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier=\"screen_virtualaccount_list\"]");
            case ANDROID, IOS -> AppiumBy.flutterKey("screen_virtualaccount_list");
        };
    }

    private By getCreatePaymentRequestPageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[contains(@aria-label,'Create Payment Request') and @flt-semantics-identifier=\"btn_close\" ]");
            case ANDROID, IOS -> AppiumBy.flutterKey("btn_close");
        };
    }

    private By getCreatePaymentLinkPageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier=\"screen_paymentlink_landing\"]");
            case ANDROID, IOS -> AppiumBy.flutterKey("screen_paymentlink_landing");
        };
    }

    private By getAddPayerPageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier=\"btn_close\"]");
            case ANDROID, IOS -> AppiumBy.flutterKey("btn_close");
        };
    }

    private By getCreateInvoicePageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier=\"btn_close\"]");
            case ANDROID, IOS -> AppiumBy.flutterKey("btn_close");
        };
    }
    private By getDownloadStatementPageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier=\"screen_reports_screen\"]");
            case ANDROID, IOS -> AppiumBy.flutterKey("screen_reports_screen");
        };
    }

    private By getNotificationIconLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[contains(text(),'Show menu')]");
            case ANDROID, IOS -> AppiumBy.flutterKey("screen_reports_screen");
        };
    }

    private By getNotificationTabLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[contains(text(),'Notifications')]");
            case ANDROID, IOS -> AppiumBy.flutterKey("screen_reports_screen");
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

    public boolean isNotificationIconVisible() {
        return isElementVisible(getNotificationIconLocator(), "Notification Icon");
    }

    public boolean isNotificationPopupVisible() {
        return isElementVisible(getNotificationTabLocator(), "Notification popup tab");
    }

    public boolean isPaymentLinkVisible() {
        return isElementVisible(getPaymentLinkLocator(), "Payment Links");
    }

    public boolean isPaymentLinkPageVisible() {
        return isElementVisible(getPaymentLinkPageLocator(), "Payment Links");
    }
    public boolean isQuickActionButtonVisible(){
        return isElementVisible(getQuickActionsDropDownLocator(),"Quick Action");
    }

    public boolean isQuickActionOptionVisible(String str) {
        return isElementVisible(getQuickActionOptionLocator(str), "Quick Action Option");
    }

    public boolean isShareVirtualAccountPageDetailsVisible() {
        return isElementVisible(getShareVirtualAccountDetailsPageLocator(), "Share Virtual Account Details");
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


    // Click Actions
    public void clickSkipButton() {
        clickElement(getSkipButtonLocator(), "Skip");
    }

    public void clickNextButton() {
        clickElement(getNextButtonLocator(), "Next");
    }

    public void clickNotificationIcon() {
       // clickElement(getNotificationIconLocator(),"Get Notification icon clicked ");
        JavaScriptUtils.jsClick(getNotificationIconLocator(),driver);
    }



    public void clickPendingAction() {
        JavaScriptUtils.jsClick(getPendingActionLocator(),driver);
    }

    public void clickReceivedPayment() {
        JavaScriptUtils.jsClick(getReceivedLocator(),driver);
    }

    public void clickRequestedPayment() {
        JavaScriptUtils.jsClick(getRequestedPaymentLocator(),driver);
    }

    public void clickPaymentLink() {
        JavaScriptUtils.jsClick(getPaymentLinkLocator(),driver);
    }

    public  void WaitTillpendingButtonIsClickble(){
        WaitUtils.waitUntilElementIsClickable(getPendingActionLocator(),20);
    }

    public void ClickQuickActionButton(){
        JavaScriptUtils.jsClick(getQuickActionsDropDownLocator(),driver);

    }

    public void ClickQuickActionOption(String str){
        MouseActionsUtil mouseActionsUtil= new MouseActionsUtil(driver);
        mouseActionsUtil.moveMouseToElement(getQuickActionOptionLocator(str));
        mouseActionsUtil.clickAtCurrentCursorPosition();


    }
    public void ClickQuickActionOptions(String str){
        MouseActionsUtil mouseActionsUtil= new MouseActionsUtil(driver);
        mouseActionsUtil.moveMouseToElement(getQuickActionOptionLocator(str));
        mouseActionsUtil.doubleClickAtCurrentCursorPosition();


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
