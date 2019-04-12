package com.github.kubenext.uaa.utils;

import org.apache.commons.lang.RandomStringUtils;

/**
 * @author shangjin.li
 */
public class RandomUtil {

    private static final int DEF_COUNT = 20;

    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(DEF_COUNT);
    }

    public static String generateActivationKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

    public static String generateResetKey() {
        return RandomStringUtils.randomNumeric(DEF_COUNT);
    }

}
