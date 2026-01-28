package com.appointment.common.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class SecureRandomGenerator {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final String NUMERIC = "0123456789";

    private static final int OTP_LENGTH = 6;
    private static final int DEFAULT_LENGTH = 8;

    public static String generateOtp() {
        return generateRandomString(NUMERIC, OTP_LENGTH);
    }

    private static String generateRandomString(String chars, int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = SECURE_RANDOM.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    public static String generateUUID() {
        long timestamp = System.currentTimeMillis(); // e.g., 1720200312511
        String timePart = String.valueOf(timestamp).substring(DEFAULT_LENGTH); // e.g., "0312511" (last 5 digits)
        String randomPart = String.valueOf(100 + new SecureRandom().nextInt(900)); // 3-digit random
        return timePart + randomPart; // e.g., "03125111" → 8 digits
    }
}
