package com.briskpe.framework.utils;

import java.util.Random;

/**
 * Utility class to generate valid and invalid mobile numbers following defined digit rules.
 * Valid mobile numbers start with digits 5-9.
 * Invalid mobile numbers start with digits 0-4.
 */
public class MobileNumberUtils {

    private static final Random random = new Random();
    private static final int MOBILE_NUMBER_LENGTH = 10;

    private static final int VALID_FIRST_DIGIT_MIN = 5;
    private static final int VALID_FIRST_DIGIT_MAX = 9;

    private static final int INVALID_FIRST_DIGIT_MIN = 0;
    private static final int INVALID_FIRST_DIGIT_MAX = 4;

    /**
     * Generates a valid 10-digit mobile number starting with digits 5-9.
     *
     * @return generated valid mobile number as string
     */
    public static String generateValidMobileNumber() {
        String mobileNumber = generateMobileNumberInRange(VALID_FIRST_DIGIT_MIN, VALID_FIRST_DIGIT_MAX);
        return mobileNumber;
    }

    /**
     * Generates an invalid 10-digit mobile number starting with digits 0-4.
     *
     * @return generated invalid mobile number as string
     */
    public static String generateInvalidMobileNumber() {
        String mobileNumber = generateMobileNumberInRange(INVALID_FIRST_DIGIT_MIN, INVALID_FIRST_DIGIT_MAX);
        return mobileNumber;
    }

    /**
     * Internal helper to generate a mobile number with the first digit in the specified inclusive range.
     *
     * @param digitMin minimum digit for the first digit inclusive
     * @param digitMax maximum digit for the first digit inclusive
     * @return generated mobile number as string
     */
    private static String generateMobileNumberInRange(int digitMin, int digitMax) {
        int firstDigit = digitMin + random.nextInt(digitMax - digitMin + 1);
        StringBuilder mobileNumber = new StringBuilder();
        mobileNumber.append(firstDigit);
        for (int i = 1; i < MOBILE_NUMBER_LENGTH; i++) {
            mobileNumber.append(random.nextInt(10));
        }
        return mobileNumber.toString();
    }
}
