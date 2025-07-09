package com.briskpe.tests;

import com.briskpe.framework.core.DriverFactory;
import org.testng.annotations.*;

public class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverFactory.createDriver();     // uses Config.get("platform")
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
