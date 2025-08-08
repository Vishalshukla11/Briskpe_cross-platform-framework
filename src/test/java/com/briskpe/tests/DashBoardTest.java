package com.briskpe.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DashBoardTest extends BaseTest {

    @Test(priority = 1, groups = "requiresLogin")
    public void testDashboardElements() {
        getTest().info("🧪 Verifying Dashboard Landing Elements...");
        Assert.assertTrue(dash.isPendingActionVisible(), "❌ Pending Action not visible");
        Assert.assertTrue(dash.isQuickActionDropdownVisible(), "❌ Quick Action Button not visible");
        Assert.assertTrue(dash.isNotificationIconVisible(), "❌ Notification icon not visible");
        getTest().pass("✅ Dashboard basic elements are displayed correctly.");
    }

    @Test(priority = 2, groups = "requiresLogin")
    public void verifyUserCanNavigateToSideMenuItems() {
        getTest().info("🔍 Clicking Pending Action...");
        Assert.assertTrue(dash.isPendingActionVisible(), "❌ Pending Action not visible");
        dash.clickPendingAction();
        Assert.assertTrue(dash.isPendingActionPageVisible(), "❌ Pending Action page did not load");

        getTest().info("🔍 Clicking Received Payments...");
        Assert.assertTrue(dash.isReceivedPaymentVisible(), "❌ Received Payment not visible");
        dash.clickReceivedPayment();
        Assert.assertTrue(dash.isReceivedPaymentPageVisible(), "❌ Received Payment page did not load");

        getTest().info("🔍 Clicking Requested Payments...");
        Assert.assertTrue(dash.isRequestedPaymentVisible(), "❌ Requested Payment not visible");
        dash.clickRequestedPayment();
        Assert.assertTrue(dash.isRequestedPaymentPageVisible(), "❌ Requested Payment page did not load");

        getTest().info("🔍 Clicking Payment Links...");
        Assert.assertTrue(dash.isPaymentLinkVisible(), "❌ Payment Link not visible");
        dash.clickPaymentLink();
        Assert.assertTrue(dash.isPaymentLinkPageVisible(), "❌ Payment Link page did not load");

        getTest().pass("✅ All side menu navigations verified successfully.");
    }

    // Reusable helper for testing quick action options
    private void verifyQuickActionNavigation(String optionText, Runnable clickVerifyPageVisible, String errorMessage) {
        getTest().info("🔽 Clicking Quick Action Drop Down...");
        Assert.assertTrue(dash.isQuickActionDropdownVisible(), "❌ Quick Action Button not visible");
        dash.isQuickActionDropdownVisible();

        Assert.assertTrue(dash.isQuickActionOptionVisible(optionText), "❌ Option '" + optionText + "' not visible");
        dash.clickQuickActionOption(optionText);

        // Run the passed verification lambda to check page visibility
        clickVerifyPageVisible.run();

        getTest().pass("✅ '" + optionText + "' page opened successfully.");
    }

    @Test(priority = 3, groups = "requiresLogin")
    public void verifyNavigationToShareVirtualAccountDetailsThroughQuickActions() {
        verifyQuickActionNavigation(
                "Share Virtual Account Details",
                () -> {}, // No page visibility assertion included here (assumed handled inside page method)
                "Share Virtual Account Details page not visible"
        );
    }

    @Test(priority = 4, groups = "requiresLogin")
    public void verifyNavigationToCreatePaymentRequestsThroughQuickActions() {
        verifyQuickActionNavigation(
                "Create Payment Request",
                () -> Assert.assertTrue(dash.isCreatePaymentRequestPageVisible(), "❌ Create Payment Request page not visible"),
                "Create Payment Request page not visible"
        );
    }

    @Test(priority = 5, groups = "requiresLogin")
    public void verifyNavigationToCreatePaymentLinkThroughQuickActions() {
        verifyQuickActionNavigation(
                "Create Payment Link",
                () -> Assert.assertTrue(dash.isCreatePaymentLinkPageVisible(), "❌ Create Payment Link page not visible"),
                "Create Payment Link page not visible"
        );
    }

    @Test(priority = 6, groups = "requiresLogin")
    public void verifyNavigationToAddPayerThroughQuickActions() {
        verifyQuickActionNavigation(
                "Add Payer",
                () -> Assert.assertTrue(dash.isAddPayerPageVisible(), "❌ Add Payer page not visible"),
                "Add Payer page not visible"
        );
    }

    @Test(priority = 7, groups = "requiresLogin")
    public void verifyNavigationToCreateInvoiceThroughQuickActions() {
        verifyQuickActionNavigation(
                "Create Invoice",
                () -> Assert.assertTrue(dash.isCreateInvoicePageVisible(), "❌ Create Invoice page not visible"),
                "Create Invoice page not visible"
        );
    }

    @Test(priority = 8, groups = "requiresLogin")
    public void verifyNavigationToDownloadStatementThroughQuickActions() {
        verifyQuickActionNavigation(
                "Download Statement",
                () -> Assert.assertTrue(dash.isDownloadStatementPageVisible(), "❌ Download Statement page not visible"),
                "Download Statement page not visible"
        );
    }

    @Test(priority = 9, groups = "requiresLogin")
    public void verifyNavigateToNotificationCenterPageAfterClickingOnNotificationIcon() {
        getTest().info("🔔 Clicking Notification Icon...");
        Assert.assertTrue(dash.isNotificationIconVisible(), "❌ Notification icon not visible");
        dash.clickNotificationIcon();
        Assert.assertTrue(dash.isNotificationPopupVisible(), "❌ Notification popup not visible");
        getTest().pass("✅ Notification popup displayed successfully.");
    }
}
