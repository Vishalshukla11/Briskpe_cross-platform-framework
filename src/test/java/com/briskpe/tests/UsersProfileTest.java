package com.briskpe.tests;

import com.briskpe.framework.pages.UsersProfile;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UsersProfileTest extends BaseTest {

    @Test(priority = 1, groups = "requiresLogin")
    @Description("Verify that clicking the profile icon reveals Profile & Setting and Logout buttons.")
    public void verifyProfileAndLogoutButtonsVisibility() {
        test = extent.createTest("Verify Profile & Logout Buttons are Visible");

        UsersProfile usersProfile = new UsersProfile();

        test.info("👤 Clicking profile icon...");
        usersProfile.clickProfileIcon();

        test.info("🔍 Verifying 'Profile & Setting' button visibility...");
        Assert.assertTrue(usersProfile.isProfileAndSettingButtonVisible(), "❌ Profile & Setting button not visible");

        test.info("🔍 Verifying 'Logout' button visibility...");
        Assert.assertTrue(usersProfile.isProfileLogoutButtonVisible(), "❌ Logout button not visible");

        test.pass("✅ Profile & Setting and Logout buttons are visible");
    }

    @Test(priority = 2, groups = "requiresLogin")
    @Description("Verify all user profile menu items are visible after opening the profile settings.")
    public void verifyUserProfileMenuItemsVisibility() {
        test = extent.createTest("Verify User Profile Menu Items");

        UsersProfile usersProfile = new UsersProfile();

        test.info("👤 Opening user profile menu...");
        openUserProfileMenu(usersProfile); // ✅ Reused utility method from BaseTest

        test.info("📂 Clicking 'Profile & Setting' button...");
        Assert.assertTrue(usersProfile.clickProfileAndSettingButton(), "❌ Failed to click Profile & Setting");

        test.info("🔍 Verifying all profile menu items...");
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

        test.pass("✅ All user profile menu items are visible");
    }

    @Test(priority = 3, groups = "requiresLogin")
    @Description("Verify that user is able to log out successfully from the profile menu.")
    public void verifyLogoutFunctionality() {
        test = extent.createTest("Verify Logout Functionality");

        UsersProfile usersProfile = new UsersProfile();

        test.info("👤 Clicking on profile icon...");
        usersProfile.clickProfileIcon();

        test.info("🚪 Clicking 'Logout' button...");
        usersProfile.clickLogoutButton();

        test.pass("✅ Logout action executed successfully");
    }

    @Test(priority = 3, groups = "requiresLogin")
    @Description("Verify that user is able to Navigate to Virtual Accounts page successfully from the profile menu.")
    public void navigateTOVirtualAccountsPage()
    { test = extent.createTest("Verify Virtual Accounts Navigation Functionality");

        UsersProfile usersProfile = new UsersProfile();
        test.info("👤 Opening user profile menu...");
        openUserProfileMenu(usersProfile); // ✅ Reused utility method from BaseTest
        test.info("📂 Clicking 'Profile & Setting' button...");
        Assert.assertTrue(usersProfile.clickProfileAndSettingButton(), "❌ Failed to click Profile & Setting");
        test.info("clicking on Virtual Accounts Button");
        usersProfile.clickVirtualAccounts();
        test.pass("Clicked successsfully on Virtual Accounts Button ");
        Assert.assertTrue(usersProfile.isVirtualAccountsPageVisible(),"❌ Virtual Account page not visible ");
        test.pass("Virtual Account Page visible successfully ");
    }

    @Test(priority = 3, groups = "requiresLogin")
    @Description("Verify that user is able to Create Team member successfully from the profile menu.")
    public void createTeamMember()
    { test = extent.createTest("Verify that user is able to Create Team member successfully from the profile menu.");

        UsersProfile usersProfile = new UsersProfile();
        test.info("👤 Opening user profile menu...");
        openUserProfileMenu(usersProfile); // ✅ Reused utility method from BaseTest
        test.info("📂 Clicking 'Profile & Setting' button...");
        Assert.assertTrue(usersProfile.clickProfileAndSettingButton(), "❌ Failed to click Profile & Setting");
        test.info("clicking on manage Team Button");
        usersProfile.clickManageTeam();
        test.pass("Clicked successsfully on Manage Team  Button ");
        Assert.assertTrue(usersProfile.isManageTeamMemberPageVisible(),"❌Manage Team page not visible ");
        Assert.assertTrue(usersProfile.isAddTeamMemberButtonVisible(), "❌ Add team member Button not Visible");
        usersProfile.clickAddTeamMemberButton();
        test.pass("✅ add team member Button clicked ");
        Assert.assertTrue(usersProfile.isInviteTeamMemberPopupVisible(),"❌ Invite Team member Popup not displayed");
        test.pass("✅ invite team member Popup Displayed");

        test.info("Enter Details Into Invite team member fields ");
        usersProfile.fillInviteTeamMemberFormWithRandomData();
        test.pass("✅ Details Filled successfully");
        usersProfile.clickAddNewTeamMeberInviteButton();
        test.pass("✅ Invite member button clicked ");
        Assert.assertTrue(usersProfile.isInviteTeamMemberSucessfullPopupVisible(),"❌ successfully  Invite Team member Popup not displayed");
        test.pass("✅ Team member Invites successfully");

    }

}
