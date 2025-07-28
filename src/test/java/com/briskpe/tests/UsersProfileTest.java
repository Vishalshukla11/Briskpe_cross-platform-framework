package com.briskpe.tests;

import com.briskpe.framework.pages.UsersProfile;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UsersProfileTest extends BaseTest {

    @Test(priority = 1)
    @Description("Verify that clicking the profile icon reveals Profile & Setting and Logout buttons.")
    public void verifyProfileAndLogoutButtonsVisibility() {
        test = extent.createTest("Verify Profile & Logout Buttons are Visible");

        UsersProfile usersProfile = new UsersProfile();

        test.info("Step 1: Click profile icon");
        usersProfile.clickProfileIcon();

        test.info("Step 2: Verify Profile & Setting button is visible");
        Assert.assertTrue(usersProfile.isProfileAndSettingButtonVisible(), "❌ Profile & Setting button not visible");

        test.info("Step 3: Verify Logout button is visible");
        Assert.assertTrue(usersProfile.isProfileLogoutButtonVisible(), "❌ Logout button not visible");

        test.pass("✅ Profile and Logout buttons are visible");
    }

    @Test(priority = 2)
    @Description("Verify all user profile menu items are visible after opening the profile settings.")
    public void verifyUserProfileMenuItemsVisibility() {
        test = extent.createTest("Verify User Profile Menu Items");

        UsersProfile usersProfile = new UsersProfile();

        test.info("Step 1: Open user profile menu");
        openUserProfileMenu(usersProfile); // ✅ Utility method in BaseTest

        test.info("Step 2: Click on Profile & Setting button");
        Assert.assertTrue(usersProfile.clickProfileAndSettingButton(), "❌ Failed to click Profile & Setting");

        test.info("Step 3: Verify all profile screen options");
        Assert.assertTrue(usersProfile.isProfileScreenVisible(), "❌ Profile screen not visible");
        Assert.assertTrue(usersProfile.isBusinessProfileVisible(), "❌ Business Profile not visible");
        Assert.assertTrue(usersProfile.isVirtualAccountsVisible(), "❌ Virtual Accounts not visible");
        Assert.assertTrue(usersProfile.isManageTeamVisible(), "❌ Manage Team not visible");
        Assert.assertTrue(usersProfile.isSettlementTimelineVisible(), "❌ Settlement Timeline not visible");
        Assert.assertTrue(usersProfile.isReferAndEarnVisible(), "❌ Refer & Earn not visible");
        Assert.assertTrue(usersProfile.isAboutBriskpeVisible(), "❌ About BriskPe not visible");
        Assert.assertTrue(usersProfile.isDeleteAccountVisible(), "❌ Delete Account not visible");
        Assert.assertTrue(usersProfile.isHelpAndSupportVisible(), "❌ Help & Support not visible");
        Assert.assertTrue(usersProfile.isInsideProfileLogoutButtonVisible(), "❌ Logout inside profile not visible");

        test.pass("✅ All profile menu items are visible");
    }

    @Test(priority = 3)
    @Description("Verify that user is able to log out successfully from the profile menu.")
    public void verifyLogoutFunctionality() {
        test = extent.createTest("Verify Logout Functionality");

        UsersProfile usersProfile = new UsersProfile();

        test.info("Step 1: Click on profile icon");
        usersProfile.clickProfileIcon();

        test.info("Step 2: Click on Logout button");
        usersProfile.clickLogoutButton();

        test.pass("✅ Logout action executed successfully");
    }
}
