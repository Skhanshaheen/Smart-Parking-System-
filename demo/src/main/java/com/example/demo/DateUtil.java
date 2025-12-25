package com.example.demo;
import java.text.SimpleDateFormat;
import java.text.ParsePosition;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Date utility class.
 */
public class DateUtil {
    public static SimpleDateFormat dateFormatter;
    private static String longDatePattern;
    private static String shortDatePattern;

    static {
        shortDatePattern = "dd-MM-yyyy";
        longDatePattern = "EEEE, d MMMM yyyy";
        dateFormatter = new SimpleDateFormat(shortDatePattern);
        dateFormatter.setLenient(false);
    }

    public static String convertDateToLongString(Date date) {
        dateFormatter.applyPattern(longDatePattern);
        String dateString = dateFormatter.format(date);
        dateFormatter.applyPattern(shortDatePattern);
        return dateString;
    }

    public static String convertDateToShortString(Date date) {
        return dateFormatter.format(date);
    }

    public static Date convertStringToDate(String dateString) {
        dateString = dateString.trim();
        ParsePosition posn = new ParsePosition(0);
        Date date = dateFormatter.parse(dateString, posn);
        int endIndex = posn.getIndex();

        String message = "Date string <" + dateString + "> not recognised";
        if (date == null) {
            throw new RuntimeException(message + ".");
        } else if (endIndex != dateString.length()) {
            throw new RuntimeException(message + " because it contains extra characters after a date.");
        } else {
            return date;
        }
    }

    public static int daysBetween(Date startDate, Date endDate) {
        boolean outOfOrder;
        Date temp;

        if (startDate.compareTo(endDate) <= 0) {
            outOfOrder = false;
        } else {
            outOfOrder = true;
            temp = startDate;
            startDate = endDate;
            endDate = temp;
        }

        int daysBetween = 0;

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar prevYearCalendar = (Calendar) calendar.clone();
        calendar.add(Calendar.YEAR, 1);

        while (!calendar.getTime().after(endDate)) {
            if (isLeapYear(prevYearCalendar.get(Calendar.YEAR)) &&
                prevYearCalendar.get(Calendar.MONTH) < Calendar.MARCH) {
                daysBetween += 366;
            } else {
                daysBetween += 365;
            }
            prevYearCalendar = (Calendar) calendar.clone();
            calendar.add(Calendar.YEAR, 1);
        }
        calendar = prevYearCalendar;

        while (calendar.getTime().before(endDate)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }

        if (outOfOrder) daysBetween = -daysBetween;
        return daysBetween;
    }

    public static Date incrementDate(Date date, int noOfDays) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, noOfDays);
        return calendar.getTime();
    }

    public static boolean isLeapYear(int year) {
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.isLeapYear(year);
    }

    public static boolean isValidDateString(String dateString) {
        try {
            convertStringToDate(dateString);
            return true;
        } catch (RuntimeException ex) {
            return false;
        }
    }

    public static Date nextDate(Date date) {
        return incrementDate(date, 1);
    }
}
