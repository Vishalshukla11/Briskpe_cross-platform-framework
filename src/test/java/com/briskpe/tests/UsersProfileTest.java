package com.briskpe.tests;

import com.briskpe.framework.pages.UsersProfile;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for validating User Profile related features.
 */
public class UsersProfileTest extends BaseTest {

    private UsersProfile usersProfile;

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        // Initialize UsersProfile page object before each test
        usersProfile = new UsersProfile();
    }

    /**
     * Common helper to open profile settings menu.
     */
    private void openProfileSettings() {
        getTest().info("👤 Opening user profile menu...");
        openUserProfileMenu(usersProfile);
        getTest().info("📂 Clicking 'Profile & Setting' button...");
        Assert.assertTrue(usersProfile.clickProfileAndSettingButton(), "❌ Failed to click Profile & Setting");
    }

    @Test(priority = 1, groups = "requiresLogin")
    @Description("Verify that clicking the profile icon reveals Profile & Setting and Logout buttons.")
    public void verifyProfileAndLogoutButtonsVisibility() {
        getTest().info("Verify Profile & Logout Buttons are Visible");

        getTest().info("👤 Clicking profile icon...");
        usersProfile.clickProfileIcon();

        getTest().info("🔍 Verifying 'Profile & Setting' button visibility...");
        Assert.assertTrue(usersProfile.isProfileAndSettingButtonVisible(), "❌ Profile & Setting button not visible");

        getTest().info("🔍 Verifying 'Logout' button visibility...");
        Assert.assertTrue(usersProfile.isProfileLogoutButtonVisible(), "❌ Logout button not visible");

        getTest().pass("✅ Profile & Setting and Logout buttons are visible");
    }

    @Test(priority = 2, groups = "requiresLogin")
    @Description("Verify all user profile menu items are visible after opening the profile settings.")
    public void verifyUserProfileMenuItemsVisibility() {
        getTest().info("Verify User Profile Menu Items");

        openProfileSettings();

        getTest().info("🔍 Verifying all profile menu items...");
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

        getTest().pass("✅ All user profile menu items are visible");
    }

    @Test(priority = 3, groups = "requiresLogin")
    @Description("Verify that user is able to log out successfully from the profile menu.")
    public void verifyLogoutFunctionality() {
        getTest().info("Verify Logout Functionality");

        getTest().info("👤 Clicking on profile icon...");
        usersProfile.clickProfileIcon();

        getTest().info("🚪 Clicking 'Logout' button...");
        usersProfile.clickLogoutButton();

        getTest().pass("✅ Logout action executed successfully");
    }

    @Test(priority = 4, groups = "requiresLogin")
    @Description("Verify that user can navigate to Virtual Accounts page successfully from the profile menu.")
    public void verifyNavigationToVirtualAccountsPage() {
        getTest().info("Verify Virtual Accounts Navigation Functionality");

        openProfileSettings();

        getTest().info("📂 Clicking 'Virtual Accounts' button...");
        usersProfile.clickVirtualAccounts();

        getTest().pass("✅ Clicked successfully on Virtual Accounts button");
        Assert.assertTrue(usersProfile.isVirtualAccountsPageVisible(), "❌ Virtual Accounts page not visible");
        getTest().pass("✅ Virtual Accounts page is visible");
    }

    @Test(priority = 5, groups = "requiresLogin")
    @Description("Verify that user is able to create team member successfully from the profile menu.")
    public void verifyCreateTeamMemberFunctionality() {
        getTest().info("Verify Create Team Member Functionality");

        openProfileSettings();

        getTest().info("📂 Clicking 'Manage Team' button...");
        usersProfile.clickManageTeam();
        getTest().pass("✅ Clicked successfully on Manage Team button");

        Assert.assertTrue(usersProfile.isManageTeamMemberPageVisible(), "❌ Manage Team page not visible");
        Assert.assertTrue(usersProfile.isAddTeamMemberButtonVisible(), "❌ Add team member button not visible");

        usersProfile.clickAddTeamMemberButton();
        getTest().pass("✅ Add Team Member button clicked");

        Assert.assertTrue(usersProfile.isInviteTeamMemberPopupVisible(), "❌ Invite Team Member popup not displayed");
        getTest().pass("✅ Invite Team Member popup displayed");

        getTest().info("✍️ Entering details into Invite Team Member fields");
        usersProfile.fillInviteTeamMemberFormWithRandomData();
        getTest().pass("✅ Details filled successfully");

        usersProfile.clickAddNewTeamMemberInviteButton();
        getTest().pass("✅ Invite member button clicked");

        Assert.assertTrue(usersProfile.isInviteTeamMemberSuccessfulPopupVisible(), "❌ Invite Team Member success popup not displayed");
        getTest().pass("✅ Team Member invited successfully");
    }
}
