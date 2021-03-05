package com.example.greber_trialbook;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Helper class for static string formatting functions.
 */
public class Format {
    static String date(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA).format(date);
    }
}
