package com.briskpe.tests;

import com.briskpe.framework.core.Config;
import com.briskpe.framework.pages.LoginPage;
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

   public void completeManualOnbaording()
   {
       LoginPage loginPage = new LoginPage();
       String mobileNumber = Config.get("mobileNo");
     
       

   }

}
