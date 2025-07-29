package com.briskpe.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DashBoardTest extends BaseTest {

    @Test(priority = 1, groups = "requiresLogin")
    public void testDashboardElements() {
        test.info("🧪 Verifying Dashboard Landing Elements...");
        Assert.assertTrue(dash.isPendingActionVisible(), "❌ Pending Action not visible");
        Assert.assertTrue(dash.isQuickActionButtonVisible(), "❌ Quick Action Button not visible");
        Assert.assertTrue(dash.isNotificationIconVisible(), "❌ Notification icon not visible");
        test.pass("✅ Dashboard basic elements are displayed correctly.");
    }

    @Test(priority = 2, groups = "requiresLogin")
    public void verifyUserCanNavigateToSideMenuItems() {
        test.info("🔍 Clicking Pending Action...");
        Assert.assertTrue(dash.isPendingActionVisible(), "❌ Pending Action not visible");
        dash.clickPendingAction();
        Assert.assertTrue(dash.isPendingActionPageVisible(), "❌ Pending Action page did not load");

        test.info("🔍 Clicking Received Payments...");
        Assert.assertTrue(dash.isReceivedPaymentVisible(), "❌ Received Payment not visible");
        dash.clickReceivedPayment();
        Assert.assertTrue(dash.isReceivedPaymentPageVisible(), "❌ Received Payment page did not load");

        test.info("🔍 Clicking Requested Payments...");
        Assert.assertTrue(dash.isRequestedPaymentVisible(), "❌ Requested Payment not visible");
        dash.clickRequestedPayment();
        Assert.assertTrue(dash.isRequestedPaymentPageVisible(), "❌ Requested Payment page did not load");

        test.info("🔍 Clicking Payment Links...");
        Assert.assertTrue(dash.isPaymentLinkVisible(), "❌ Payment Link not visible");
        dash.clickPaymentLink();
        Assert.assertTrue(dash.isPaymentLinkPageVisible(), "❌ Payment Link page did not load");

        test.pass("✅ All side menu navigations verified successfully.");
    }

    @Test(priority = 3, groups = "requiresLogin")
    public void verifyNavigationToShareVirtualAccountDetailsThroughQuickActions() {
        test.info("🔽 Clicking Quick Action Drop Down...");
        Assert.assertTrue(dash.isQuickActionButtonVisible(), "❌ Quick Action Button not visible");
        dash.ClickQuickActionButton();
        Assert.assertTrue(dash.isQuickActionOptionVisible("Share Virtual Account Details"), "❌ Option not visible");
        dash.ClickQuickActionOption("Share Virtual Account Details");
        test.pass("✅ Share Virtual Account Details opened successfully.");
    }

    @Test(priority = 4, groups = "requiresLogin")
    public void verifyNavigationToCreatePaymentRequestsThroughQuickActions() {
        test.info("🔽 Clicking Quick Action Drop Down...");
        Assert.assertTrue(dash.isQuickActionButtonVisible(), "❌ Quick Action Button not visible");
        dash.ClickQuickActionButton();
        Assert.assertTrue(dash.isQuickActionOptionVisible("Create Payment Request"), "❌ Option not visible");
        dash.ClickQuickActionOptions("Create Payment Request");
        Assert.assertTrue(dash.isCreatePaymentRequestPageVisible(), "❌ Page not visible");
        test.pass("✅ Create Payment Request opened successfully.");
    }

    @Test(priority = 5, groups = "requiresLogin")
    public void verifyNavigationToCreatePaymentLinkThroughQuickActions() {
        test.info("🔽 Clicking Quick Action Drop Down...");
        Assert.assertTrue(dash.isQuickActionButtonVisible(), "❌ Quick Action Button not visible");
        dash.ClickQuickActionButton();
        Assert.assertTrue(dash.isQuickActionOptionVisible("Create Payment Link"), "❌ Option not visible");
        dash.ClickQuickActionOptions("Create Payment Link");
        Assert.assertTrue(dash.isCreatePaymentLinkPageVisible(), "❌ Page not visible");
        test.pass("✅ Create Payment Link opened successfully.");
    }

    @Test(priority = 6, groups = "requiresLogin")
    public void verifyNavigationToAddPayerThroughQuickActions() {
        test.info("🔽 Clicking Quick Action Drop Down...");
        Assert.assertTrue(dash.isQuickActionButtonVisible(), "❌ Quick Action Button not visible");
        dash.ClickQuickActionButton();
        Assert.assertTrue(dash.isQuickActionOptionVisible("Add Payer"), "❌ Option not visible");
        dash.ClickQuickActionOptions("Add Payer");
        Assert.assertTrue(dash.isAddPayerPageVisible(), "❌ Page not visible");
        test.pass("✅ Add Payer page opened successfully.");
    }

    @Test(priority = 7, groups = "requiresLogin")
    public void verifyNavigationToCreateInvoiceThroughQuickActions() {
        test.info("🔽 Clicking Quick Action Drop Down...");
        Assert.assertTrue(dash.isQuickActionButtonVisible(), "❌ Quick Action Button not visible");
        dash.ClickQuickActionButton();
        Assert.assertTrue(dash.isQuickActionOptionVisible("Create Invoice"), "❌ Option not visible");
        dash.ClickQuickActionOptions("Create Invoice");
        Assert.assertTrue(dash.isCreateInvoicePageVisible(), "❌ Page not visible");
        test.pass("✅ Create Invoice page opened successfully.");
    }

    @Test(priority = 8, groups = "requiresLogin")
    public void verifyNavigationToDownloadStatementThroughQuickActions() {
        test.info("🔽 Clicking Quick Action Drop Down...");
        Assert.assertTrue(dash.isQuickActionButtonVisible(), "❌ Quick Action Button not visible");
        dash.ClickQuickActionButton();
        Assert.assertTrue(dash.isQuickActionOptionVisible("Download Statement"), "❌ Option not visible");
        dash.ClickQuickActionOptions("Download Statement");
        Assert.assertTrue(dash.isDownloadStatementPageVisible(), "❌ Page not visible");
        test.pass("✅ Download Statement page opened successfully.");
    }

    @Test(priority = 9, groups = "requiresLogin")
    public void verifyNavigateToNotificationCenterpageAfterclickingOnNotificationIcon() {
        test.info("🔔 Clicking Notification Icon...");
        Assert.assertTrue(dash.isNotificationIconVisible(), "❌ Notification icon not visible");
        dash.clickNotificationIcon();
        Assert.assertTrue(dash.isNotificationPopupVisible(), "❌ Notification popup not visible");
        test.pass("✅ Notification popup displayed successfully.");
    }
}
