package com.appointment.common.constants;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final int LAST_LOGIN_REPORT_COUNT_FACTOR = 15;

    public static final int MOST_ACTIVE_USERS_COUNT_FACTOR = 5;

    public static final int MOST_ACTIVE_RESOURCES_COUNT_FACTOR = 5;

    public static final String USER_UUID_PREFIX = "USR-";
    // Fields Min/Max Values
    public static final int DEFAULT_MIN_SIZE = 1;

    public static final int DEFAULT_MAX_SIZE = 255;

    public static final int DESCRIPTION_MAX_SIZE = 5000;

    public static final int PASSWORD_MAX_SIZE = 30;

    public static final long FILE_MAX_SIZE_MB = 10 * 1024 * 1024; // 10 MB

    public static final String CONTENT_TYPE_PDF = "application/pdf";
    public static final String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String CONTENT_TYPE_TXT = "text/plain";
    public static final String CONTENT_TYPE_CSV  = "text/csv";
    public static final String CONTENT_TYPE_XLS  = "application/vnd.ms-excel";


    public static final String CONTENT_TYPE_DOC = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    public static final String CONTENT_TYPE_PNG = "image/png";
    public static final String CONTENT_TYPE_JPG_AND_JPEG = "image/jpeg";

    public static final List<String> ALLOWED_FILE_EXTENSIONS_FOR_DOCUMENTS =
            Arrays.asList(CONTENT_TYPE_PDF, CONTENT_TYPE_XLSX, CONTENT_TYPE_TXT, CONTENT_TYPE_DOC, CONTENT_TYPE_PNG, CONTENT_TYPE_JPG_AND_JPEG);

    public static final List<String> ALLOWED_FILE_EXTENSIONS_FOR_FLIGHT_MANIFEST =
            Arrays.asList(CONTENT_TYPE_CSV, CONTENT_TYPE_XLSX, CONTENT_TYPE_XLSX);

    public static final Long TEN_MB_IN_BYTES = 10000000L;

    public static final String CONTENT_RESPONSE_ATTACHMENT = "attachment";

    public static final String HEX_COLOR_PREFIX = "#";

    public static final Long OBJECT_STORAGE_PRE_SIGNED_URL_EXPIRY_TIME = 30000L; // 2 minutes

    public static final BigDecimal ZERO = BigDecimal.ZERO;

    public static final Long MONTHS_IN_MILLIS = 2592000000L;
}
