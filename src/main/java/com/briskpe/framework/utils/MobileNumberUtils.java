package com.briskpe.framework.utils;

import com.briskpe.framework.core.Config;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Random;

public class MobileNumberUtils {

    public static String generateValidMobileNumber() {
        Random random = new Random();
        int firstDigit = 5 + random.nextInt(5); // 5 to 9
        StringBuilder mobileNumber = new StringBuilder(String.valueOf(firstDigit));
        for (int i = 0; i < 9; i++) {
            mobileNumber.append(random.nextInt(10));
        }
        String validNumber = mobileNumber.toString();
        Config.set("valid.mobile", validNumber);
        return validNumber;
    }

    public static String generateInvalidMobileNumber() {
        Random random = new Random();
        int firstDigit = random.nextInt(5); // 0 to 4
        StringBuilder mobileNumber = new StringBuilder(String.valueOf(firstDigit));
        for (int i = 0; i < 9; i++) {
            mobileNumber.append(random.nextInt(10));
        }
        String invalidNumber = mobileNumber.toString();
        Config.set("invalid.mobile", invalidNumber);
        return invalidNumber;
    }
}
