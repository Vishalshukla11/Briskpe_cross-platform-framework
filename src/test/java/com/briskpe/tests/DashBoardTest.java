package com.briskpe.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DashBoardTest extends BaseTest {

    @Test(priority = 1)
    public void verifyUserCanNavigateToSideMenuItems() {
        test.info("🔍 Clicking Pending Action...");
        Assert.assertTrue(dash.isPendingActionVisible(), "❌ Pending Action not visible");
        dash.clickPendingAction();
        Assert.assertTrue(dash.isPendingActionPageVisible(), "❌ Page did not load");

        test.info("🔍 Clicking Received Payments...");
        Assert.assertTrue(dash.isReceivedPaymentVisible(), "❌ Received Payment not visible");
        dash.clickReceivedPayment();
        Assert.assertTrue(dash.isReceivedPaymentPageVisible(), "❌ Page did not load");

        test.info("🔍 Clicking Requested Payments...");
        Assert.assertTrue(dash.isRequestedPaymentVisible(), "❌ Requested Payment not visible");
        dash.clickRequestedPayment();
        Assert.assertTrue(dash.isRequestedPaymentPageVisible(), "❌ Page did not load");

        test.info("🔍 Clicking Payment Links...");
        Assert.assertTrue(dash.isPaymentLinkVisible(), "❌ Payment Link not visible");
        dash.clickPaymentLink();
        Assert.assertTrue(dash.isPaymentLinkPageVisible(), "❌ Page did not load");

        test.pass("✅ All side menu navigations successful");
    }

    @Test(priority = 2)
    public void verifyNavigationToShareVirtualAccountDetailsThroughQuickActions(){
        test.info("Clicking Quick Action Drop Down");
        Assert.assertTrue(dash.isQuickActionButtonVisible(),"❌ Quick Action DropDown Button not visible");
        dash.ClickQuickActionButton();
        Assert.assertTrue(dash.isQuickActionOptionVisible("Share Virtual Account Details"),"❌ Quick Action Option not visible");
        dash.ClickQuickActionOption("Share Virtual Account Details");
        test.pass("Share Virtual Account Details clicked successfully ");

    }
    @Test(priority = 3)
    public void verifyNavigationToCreatePaymentRequestsThroughQuickActions(){
        test.info("Clicking Quick Action Drop Down");
        Assert.assertTrue(dash.isQuickActionButtonVisible(),"❌ Quick Action DropDown Button not visible");
        dash.ClickQuickActionButton();
        Assert.assertTrue(dash.isQuickActionOptionVisible("Create Payment Request"),"❌ Quick Action Option not visible");
        dash.ClickQuickActionOptions("Create Payment Request");
        test.pass("Create Payment Request clicked successfully ");
        Assert.assertTrue(dash.isCreatePaymentRequestPageVisible(),"❌ Create Payment Request page not visible");

    }
    @Test(priority = 4)
    public void verifyNavigationToCreatePaymentLinkThroughQuickActions(){
        test.info("Clicking Quick Action Drop Down");
        Assert.assertTrue(dash.isQuickActionButtonVisible(),"❌ Quick Action DropDown Button not visible");
        dash.ClickQuickActionButton();
        Assert.assertTrue(dash.isQuickActionOptionVisible("Create Payment Link"),"❌ Quick Action Option not visible");
        dash.ClickQuickActionOptions("Create Payment Link");
        test.pass("Create Payment Link clicked successfully ");
        Assert.assertTrue(dash.isCreatePaymentLinkPageVisible(),"❌ Create Payment Link page not visible");


    }

    @Test(priority = 5)
    public void verifyNavigationToAddPayerThroughQuickActions(){
        test.info("Clicking Quick Action Drop Down");
        Assert.assertTrue(dash.isQuickActionButtonVisible(),"❌ Quick Action DropDown Button not visible");
        dash.ClickQuickActionButton();
        Assert.assertTrue(dash.isQuickActionOptionVisible("Add Payer"),"❌ Quick Action Option not visible");
        dash.ClickQuickActionOptions("Add Payer");
        test.pass("Add Payer clicked successfully ");
        Assert.assertTrue(dash.isAddPayerPageVisible(),"❌ Add Payer page not visible");

    }

    @Test(priority = 6)
    public void verifyNavigationToCreateInvoiceThroughQuickActions(){
        test.info("Clicking Quick Action Drop Down");
        Assert.assertTrue(dash.isQuickActionButtonVisible(),"❌ Quick Action DropDown Button not visible");
        dash.ClickQuickActionButton();
        Assert.assertTrue(dash.isQuickActionOptionVisible("Create Invoice"),"❌ Quick Action Option not visible");
        dash.ClickQuickActionOptions("Create Invoice");
        test.pass("Create Invoice clicked successfully ");
        Assert.assertTrue(dash.isCreateInvoicePageVisible(),"❌ Create Invoice page not visible");

    }

    @Test(priority = 7)
    public void verifyNavigationToDownloadStatementThroughQuickActions(){
        test.info("Clicking Quick Action Drop Down");
        Assert.assertTrue(dash.isQuickActionButtonVisible(),"❌ Quick Action DropDown Button not visible");
        dash.ClickQuickActionButton();
        Assert.assertTrue(dash.isQuickActionOptionVisible("Download Statement"),"❌ Quick Action Option not visible");
        dash.ClickQuickActionOptions("Download Statement");
        test.pass("Download Statement clicked successfully ");
        Assert.assertTrue(dash.isDownloadStatementPageVisible(),"❌ Download Statement page not visible");

    }

    @Test(priority = 8)
    public void verifyNavigateToNotificationCenterpageAfterclickingOnNotificationIcon()
    {
        test.info("Clicking Notification Icon");
        Assert.assertTrue(dash.isNotificationIconVisible(),"❌ Notification icon not visible");
        dash.clickNotificationIcon();
        Assert.assertTrue(dash.isNotificationPopupVisible(),"❌ Notification popup not visible");
    }



}
