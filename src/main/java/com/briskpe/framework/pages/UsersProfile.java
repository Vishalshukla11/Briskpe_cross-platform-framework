package com.briskpe.framework.pages;

import com.briskpe.framework.core.DriverFactory;
import com.briskpe.framework.core.Platform;
import com.briskpe.framework.utils.JavaScriptUtils;
import com.briskpe.framework.utils.MouseActionsUtil;
import com.briskpe.framework.utils.WaitUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UsersProfile {

    private final WebDriver driver = DriverFactory.getDriver();
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

    private By getMenuItemLocator(String label) {
        return switch (platform) {
            case WEB, MOBILE_WEB -> By.xpath("//flt-semantics[contains(@aria-label,'" + label + "')]");
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
                clickElement(getProfileAndSettingButtonLocator(), "Profile and Setting Button");
            }
            return true;
        } catch (Exception e) {
            System.out.println("❌ Failed to click Profile And Setting Button: " + e.getMessage());
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

    public void clickAboutBriskpe() {
        clickElement(getAboutBriskpeLocator(), "About BRISKPE");
    }

    public void clickDeleteAccount() {
        clickElement(getDeleteAccountLocator(), "Delete Account");
    }

    public void clickHelpAndSupport() {
        clickElement(getHelpAndSupportLocator(), "Help and Support");
    }

    // ========== VISIBILITY VERIFICATIONS ========== //

    public boolean isProfileIconVisible() {
        return isElementVisible(getProfileIconLocator(), "Profile Icon");
    }

    public boolean isProfileLogoutButtonVisible() {
        return isElementVisible(getProfileLogOutButtonLocator(), "Logout Button");
    }
    public boolean isInsideProfileLogoutButtonVisible() {
        return isElementVisible(getInsideProfileLogOutButtonLocator(), "inside Profile Logout Button");
    }

    public boolean isProfileAndSettingButtonVisible() {
        return isElementVisible(getProfileAndSettingButtonLocator(), "Profile and Setting Button");
    }

    public boolean isProfileScreenVisible() {
        return isElementVisible(getProfileScreenPage(), "Profile Screen");
    }

    public boolean isBusinessProfileVisible() {
        return isElementVisible(getBusinessProfileLocator(), "Business Profile");
    }

    public boolean isVirtualAccountsVisible() {
        return isElementVisible(getVirtualAccountLocator(), "Virtual Accounts");
    }

    public boolean isManageTeamVisible() {
        return isElementVisible(getManageTeamLocator(), "Manage Team");
    }

    public boolean isSettlementTimelineVisible() {
        return isElementVisible(getSettlementTimelineLocator(), "Settlement Timelines");
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
            System.out.println("✅ " + elementName + " clicked");
        } catch (Exception e) {
            System.out.println("❌ Failed to click " + elementName + ": " + e.getMessage());
        }
    }

    private boolean isElementVisible(By locator, String elementName) {
        try {
            WaitUtils.untilVisible(locator, 30);
            boolean visible = driver.findElement(locator).isDisplayed();
            System.out.println("✅ " + elementName + " is visible");
            return true;
        } catch (Exception e) {
            System.out.println("❌ " + elementName + " not visible: " + e.getMessage());
            return false;
        }
    }
}
