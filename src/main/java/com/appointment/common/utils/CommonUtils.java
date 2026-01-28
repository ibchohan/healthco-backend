package com.appointment.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;

@Slf4j(topic = "CommonUtils")
@Component
public class CommonUtils {

    public static boolean isNull(Object o) {
        return o == null;
    }

    public static ExampleMatcher getGeneralMatcher() {
        return ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();
    }

    public static boolean isNullOrEmpty(String value) {
        return isNull(value) || value.isEmpty();
    }

    public static Boolean isPastTimestamp(long epochMilli) {
        return System.currentTimeMillis() > epochMilli;
    }

    public static Instant getCurrentTimestamp() {
        return Instant.ofEpochMilli(System.currentTimeMillis());
    }

    public static Instant convertEpochToInstant(Long epochMilli) {
        if (isNull(epochMilli)) return null;
        return Instant.ofEpochMilli(epochMilli);
    }

    public static Long convertInstantToEpoch(Instant instant) {
        if (isNull(instant)) return null;
        return instant.toEpochMilli();
    }

    public static Double roundValueTo2Digits(Double amount) {
        // Create a BigDecimal object
        BigDecimal bd = new BigDecimal(amount.toString());
        // Round to two decimal places using RoundingMode.HALF_UP
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static BigDecimal roundValueTo2Digits(BigDecimal amount) {
        if (amount == null) return BigDecimal.ZERO;
        return amount.setScale(2, RoundingMode.HALF_UP);
    }

    public static Long getStartOfMonthEpoch(YearMonth period) {
        LocalDate startOfMonth = period.atDay(1);
        return startOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static Long getEndOfMonthEpoch(YearMonth period) {
        LocalDate endOfMonth = period.atEndOfMonth();
        return endOfMonth.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static Double calculatePercentage(long part, long total) {
        return Math.round((part * 1.0 / total) * 100 * 100.0) / 100.0;
    }
    public static String getExtension(String filename) {
        if (filename == null) return "";
        int idx = filename.lastIndexOf('.');
        if (idx == -1) return "";
        return filename.substring(idx).toLowerCase(Locale.ROOT);
    }
}
