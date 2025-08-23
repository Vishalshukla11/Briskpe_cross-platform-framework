package com.briskpe.framework.pages;

import com.briskpe.framework.base.BasePage;
import com.briskpe.framework.core.DriverFactory;
import com.briskpe.framework.core.DriverManager;
import com.briskpe.framework.core.Platform;
import com.briskpe.framework.utils.JavaScriptUtils;
import com.briskpe.framework.utils.MouseActionsUtil;
import com.briskpe.framework.utils.RandomDataUtils;
import com.briskpe.framework.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UsersProfile extends BasePage {

    private static final Logger logger = Logger.getLogger(UsersProfile.class.getName());

    private final WebDriver driver = DriverManager.getDriver();
    private final Platform platform = Platform.fromString(System.getProperty("platform", "WEB"));

    // ========== LOCATORS ========== //

    private By getProfileIconLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[contains(@aria-label,'Profile')]");
            case ANDROID, IOS -> AppiumBy.flutterKey("user_profile_icon");
        };
    }

    private By getProfileLogOutButtonLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[contains(text(),'Log Out')]");
            case ANDROID, IOS -> AppiumBy.flutterKey("logout_button");
        };
    }

    private By getInsideProfileLogOutButtonLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[contains(text(),'Log out')]");
            case ANDROID, IOS -> AppiumBy.flutterKey("logout_button");
        };
    }

    private By getProfileAndSettingButtonLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[contains(text(),'Profile and Settings')]");
            case ANDROID, IOS -> AppiumBy.flutterKey("profile_settings_button");
        };
    }

    private By getProfileScreenPage() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier='screen_profile_menu']");
            case ANDROID, IOS -> AppiumBy.flutterKey("profile_username");
        };
    }

    private By getDeleteAccountLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[contains(text(),'Delete Account')]");
            case ANDROID, IOS -> AppiumBy.flutterKey("profile_username");
        };
    }

    private By getVirtualAccountsPageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier='screen_virtualaccount_list']");
            case ANDROID, IOS -> AppiumBy.flutterKey("screen_virtualaccount_list");
        };
    }

    private By getManageTeamPageLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier='screen_teammember_list']");
            case ANDROID, IOS -> AppiumBy.flutterKey("screen_teammember_list");
        };
    }

    private By getAddTeamMemberLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier='btn_add_member']//flt-semantics");
            case ANDROID, IOS -> AppiumBy.flutterKey("btn_add_member");
        };
    }

    private By getInviteMemberPopupLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[contains(@aria-label,'Invite Member')]");
            case ANDROID, IOS -> AppiumBy.flutterKey("btn_close");
        };
    }

    private By getInviteButtonLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[@flt-semantics-identifier='btn_invite']//flt-semantics");
            case ANDROID, IOS -> AppiumBy.flutterKey("btn_invite");
        };
    }

    private By getBusinessProfileLocator() {
        return getMenuItemLocator("Business Profile");
    }

    private By getVirtualAccountLocator() {
        return getMenuItemLocator("Virtual Accounts");
    }

    private By getManageTeamLocator() {
        return getMenuItemLocator("Manage Team");
    }

    private By getSettlementTimelineLocator() {
        return getMenuItemLocator("Settlement Timelines");
    }

    private By getReferAndEarnLocator() {
        return getMenuItemLocator("Refer and Earn");
    }

    private By getAboutBriskpeLocator() {
        return getMenuItemLocator("About BRISKPE");
    }

    private By getHelpAndSupportLocator() {
        return getMenuItemLocator("Help and Support");
    }

    private By getFirstNameField() {
        return getInviteTeamMemberField("firstName");
    }

    private By getMiddleNameField() {
        return getInviteTeamMemberField("middleName");
    }

    private By getLastNameField() {
        return getInviteTeamMemberField("lastName");
    }

    private By getEmailField() {
        return getInviteTeamMemberField("email");
    }

    private By getMobileNumberField() {
        return getInviteTeamMemberField("mobileNumber");
    }

    private By getInviteTeamMemberSuccessfullyPopupLocator() {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//span[contains(text(),'Invite sent on registered email successfully!')]");
            case ANDROID, IOS -> AppiumBy.flutterKey("screen_virtualaccount_list");
        };
    }

    private By getMenuItemLocator(String label) {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[contains(@aria-label,'" + label + "')]");
            case ANDROID, IOS -> AppiumBy.flutterKey(label);
        };
    }

    private By getInviteTeamMemberField(String label) {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//input[contains(@aria-label,'" + label + "')]");
            case ANDROID, IOS -> AppiumBy.flutterKey(label);
        };
    }

    // ========== ACTION METHODS ========== //

    public void clickProfileIcon() {
        clickElement(getProfileIconLocator(), "Profile Icon");
    }

    public void clickLogoutButton() {
        clickElement(getProfileLogOutButtonLocator(), "Logout Button");
    }

    public boolean clickProfileAndSettingButton() {
        try {
            if (platform == Platform.WEB || platform == Platform.MOBILE_WEB) {
                MouseActionsUtil mouseActionsUtil = new MouseActionsUtil(driver);
                mouseActionsUtil.moveMouseToElement(getProfileAndSettingButtonLocator());
                mouseActionsUtil.doubleClickAtCurrentCursorPosition();
            } else {
                clickElement(getProfileAndSettingButtonLocator(), "Profile and Settings Button");
            }
            logger.info("Clicked Profile and Settings button successfully.");
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to click Profile And Setting Button: " + e.getMessage(), e);
            return false;
        }
    }

    public void clickBusinessProfile() {
        clickElement(getBusinessProfileLocator(), "Business Profile");
    }

    public void clickVirtualAccounts() {
        clickElement(getVirtualAccountLocator(), "Virtual Accounts");
    }

    public void clickManageTeam() {
        clickElement(getManageTeamLocator(), "Manage Team");
    }

    public void clickSettlementTimeline() {
        clickElement(getSettlementTimelineLocator(), "Settlement Timelines");
    }

    public void clickReferAndEarn() {
        clickElement(getReferAndEarnLocator(), "Refer and Earn");
    }

    public void clickAddTeamMemberButton() {
        clickElement(getAddTeamMemberLocator(), "Add Team Member Button");
    }

    public void clickAboutBriskpe() {
        clickElement(getAboutBriskpeLocator(), "About BRISKPE");
    }

    public void clickDeleteAccount() {
        clickElement(getDeleteAccountLocator(), "Delete Account");
    }

    public void clickHelpAndSupport() {
        clickElement(getHelpAndSupportLocator(), "Help and Support");
    }

    public void clickAddNewTeamMemberInviteButton() {
        clickElement(getInviteButtonLocator(), "Invite Button");
    }

    // ========== VISIBILITY VERIFICATIONS ========== //

    public boolean isProfileIconVisible() {
        return isElementVisible(getProfileIconLocator(), "Profile Icon");
    }

    public boolean isProfileLogoutButtonVisible() {
        return isElementVisible(getProfileLogOutButtonLocator(), "Logout Button");
    }

    public boolean isInsideProfileLogoutButtonVisible() {
        return isElementVisible(getInsideProfileLogOutButtonLocator(), "Inside Profile Logout Button");
    }

    public boolean isProfileAndSettingButtonVisible() {
        return isElementVisible(getProfileAndSettingButtonLocator(), "Profile and Settings Button");
    }

    public boolean isProfileScreenVisible() {
        return isElementVisible(getProfileScreenPage(), "Profile Screen");
    }

    public boolean isAddTeamMemberButtonVisible() {
        return isElementVisible(getAddTeamMemberLocator(), "Add Team Member Button");
    }

    public boolean isBusinessProfileVisible() {
        return isElementVisible(getBusinessProfileLocator(), "Business Profile");
    }

    public boolean isVirtualAccountsVisible() {
        return isElementVisible(getVirtualAccountLocator(), "Virtual Accounts");
    }

    public boolean isVirtualAccountsPageVisible() {
        return isElementVisible(getVirtualAccountsPageLocator(), "Virtual Accounts Page");
    }

    public boolean isManageTeamVisible() {
        return isElementVisible(getManageTeamLocator(), "Manage Team");
    }

    public boolean isManageTeamMemberPageVisible() {
        return isElementVisible(getManageTeamPageLocator(), "Manage Team Member Page");
    }

    public boolean isInviteTeamMemberPopupVisible() {
        return isElementVisible(getInviteMemberPopupLocator(), "Invite Member Popup");
    }

    public boolean isSettlementTimelineVisible() {
        return isElementVisible(getSettlementTimelineLocator(), "Settlement Timelines");
    }

    public boolean isInviteTeamMemberSuccessfulPopupVisible() {
        return isElementVisible(getInviteTeamMemberSuccessfullyPopupLocator(), "Invite Team Member Successful Popup");
    }

    public boolean isReferAndEarnVisible() {
        return isElementVisible(getReferAndEarnLocator(), "Refer and Earn");
    }

    public boolean isAboutBriskpeVisible() {
        return isElementVisible(getAboutBriskpeLocator(), "About BRISKPE");
    }

    public boolean isDeleteAccountVisible() {
        return isElementVisible(getDeleteAccountLocator(), "Delete Account");
    }

    public boolean isHelpAndSupportVisible() {
        return isElementVisible(getHelpAndSupportLocator(), "Help and Support");
    }

    // ========== REUSABLE HELPERS ========== //

    private void clickElement(By locator, String elementName) {
        try {
            WaitUtils.untilVisible(locator, 30);
            if (platform == Platform.WEB || platform == Platform.MOBILE_WEB) {
                JavaScriptUtils.jsClick(locator, driver);
            } else {
                driver.findElement(locator).click();
            }
            logger.info(elementName + " clicked successfully.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to click " + elementName + ": " + e.getMessage(), e);
        }
    }

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

    private void enterText(By locator, String text, String fieldName) {
        try {
            WaitUtils.untilVisible(locator, 20);
            WebElement element = driver.findElement(locator);
            element.clear();
            element.sendKeys(text);
            logger.info("Entered '" + text + "' into " + fieldName);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to enter text in " + fieldName + ": " + e.getMessage(), e);
        }
    }

    public void fillInviteTeamMemberFormWithRandomData() {
        try {
            String firstName = RandomDataUtils.getRandomFirstName();
            String middleName = RandomDataUtils.getRandomMiddleName();
            String lastName = RandomDataUtils.getRandomLastName();
            String mobile = RandomDataUtils.getRandomMobileNumber();
            String email = RandomDataUtils.getRandomEmail(mobile);

            enterText(getFirstNameField(), firstName, "First Name");
            enterText(getMiddleNameField(), middleName, "Middle Name");
            enterText(getLastNameField(), lastName, "Last Name");
            enterText(getEmailField(), email, "Email");
            enterText(getMobileNumberField(), mobile, "Mobile Number");

            logger.info("Invite Team Member form filled with random data.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to fill team member form: " + e.getMessage(), e);
        }
    }
}
