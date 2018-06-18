package com.blog.Util;

import java.util.*;
import java.text.*;

/**
 * Итилита по конвертации данные в читабельный вид
 */
public class FormatUtil {
    /** Pattern dd.MM.yyyy */
    public static DateFormat dateFormat;
    /** Pattern dd.MM.yyyy HH:mm:ss */
    public static DateFormat dateTimeFormat;

    static {
        dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    }

    /**
     * Format dd.MM.yyyy
     * @param date date
     * @return formatted value
     */
    public static synchronized String formatDate(Date date) {
        if (date != null) {
            return dateFormat.format(date);
        } else {
            return "null";
        }
    }
    
    /**
     * Format dd.MM.yyyy HH:mm:ss
     * @param date date
     * @return formatted value
     */
    public static synchronized String formatDateTime(Date date) {
        if (date != null) {
            return dateTimeFormat.format(date);
        } else {
            return "null";
        }
    }
    
}
