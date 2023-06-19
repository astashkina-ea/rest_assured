package homework_2.utils;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Calendar;

public class DateTimeUtils {
    public static long getCurrentTimeInSeconds() {
        return Calendar.getInstance().toInstant().getLong(ChronoField.INSTANT_SECONDS);
    }

    public static long getIsoFormatTimeToSeconds(String dateTimeInIso) {
        return DateTimeFormatter.ISO_INSTANT.parse(dateTimeInIso).getLong(ChronoField.INSTANT_SECONDS);
    }
}