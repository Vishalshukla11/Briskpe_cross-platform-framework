package com.briskpe.framework.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Utility class for generating random dummy test data.
 */
public class RandomDataUtils {

    public static String getRandomFirstName() {
        return "FName" + RandomStringUtils.randomAlphabetic(3);
    }

    public static String getRandomMiddleName() {
        return "MName" + RandomStringUtils.randomAlphabetic(2);
    }

    public static String getRandomLastName() {
        return "LName" + RandomStringUtils.randomAlphabetic(4);
    }

    public static String getRandomEmail(String mobile) {
        return  mobile + "@gmail.com";
    }

    public static String getRandomMobileNumber() {
        return "9" + RandomStringUtils.randomNumeric(9);  // Ensures 10-digit Indian number
    }
}
