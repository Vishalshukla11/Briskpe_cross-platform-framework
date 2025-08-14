package com.briskpe.tests;

import com.briskpe.framework.core.Config;
import com.briskpe.framework.pages.LoginPage;
import com.briskpe.framework.pages.SignUp;
import com.briskpe.framework.utils.JavaScriptUtils;
import com.briskpe.framework.utils.WaitUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * SignUpTest contains tests related to signup or login flows.
 */
@Slf4j
public class SignUpTest extends BaseTest {


    @Test(priority = 1)
    public void shouldCompleteManualOnboarfding() throws InterruptedException {
        getTest().info("🔐 Starting login flow with valid credentials");

        LoginPage loginPage = new LoginPage();
        SignUp signUp = new SignUp();
        String mobileNumber = Config.get("mobileNo");
        String otp = Config.get("OTP");


        getTest().info("🔹 Checking Login tab visibility");
        Assert.assertTrue(loginPage.isLoginTabDisplayed(), "❌ Login tab not visible");
        signUp.clickSignuptextLink();
        signUp.isSignUpPageVisible();
        signUp.enterMobileNumber();
        signUp.enterEmail();
        signUp.clickWhatsappCheckbox();
        signUp.clickTnCCheckbox();
        signUp.isGetOtpButtonClickble();
        signUp.clickGetOtpButton();
        VerifyOtpScreen();
        Thread.sleep(7000);
signUp.isSelectAccountTypePageVisible();

    }


}
