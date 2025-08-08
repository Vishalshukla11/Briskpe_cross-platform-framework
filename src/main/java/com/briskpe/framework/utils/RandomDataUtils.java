package com.briskpe.framework.utils;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Utility class for generating random dummy test data,
 * such as names, emails, and mobile numbers.
 */
public class RandomDataUtils {

    private static final String FIRST_NAME_PREFIX = "FName";
    private static final String MIDDLE_NAME_PREFIX = "MName";
    private static final String LAST_NAME_PREFIX = "LName";

    /**
     * Generates a random first name with a fixed prefix and 3 random alphabets.
     *
     * @return randomized first name string
     */
    public static String getRandomFirstName() {
        return FIRST_NAME_PREFIX + RandomStringUtils.randomAlphabetic(3);
    }

    /**
     * Generates a random middle name with a fixed prefix and 2 random alphabets.
     *
     * @return randomized middle name string
     */
    public static String getRandomMiddleName() {
        return MIDDLE_NAME_PREFIX + RandomStringUtils.randomAlphabetic(2);
    }

    /**
     * Generates a random last name with a fixed prefix and 4 random alphabets.
     *
     * @return randomized last name string
     */
    public static String getRandomLastName() {
        return LAST_NAME_PREFIX + RandomStringUtils.randomAlphabetic(4);
    }

    /**
     * Generates an email address by concatenating given mobile string with a fixed domain.
     *
     * @param mobile the mobile number to include in the email address
     * @return generated email string
     */
    public static String getRandomEmail(String mobile) {
        return mobile + "@gmail.com";
    }

    /**
     * Generates a random 10-digit Indian mobile number starting with '9'.
     *
     * @return generated mobile number string
     */
    public static String getRandomMobileNumber() {
        return "9" + RandomStringUtils.randomNumeric(9);
    }

    /**
     * Generates a random email address with a random username and fixed domain.
     *
     * @return generated random email string
     */
    public static String getRandomEmail() {
        String username = RandomStringUtils.randomAlphanumeric(8).toLowerCase();
        return username + "@gmail.com";
    }

    /**
     * Generates a generic random alphabetic string of specified length.
     *
     * @param length length of string desired
     * @return random alphabetic string
     */
    public static String getRandomAlphabetic(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }
}
