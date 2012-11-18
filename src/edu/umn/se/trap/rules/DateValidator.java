// DateValidator.java
package edu.umn.se.trap.rules;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.InputValidationException;

/**
 * @author planeman
 * 
 */
public class DateValidator extends InputValidationRule
{
    /** TRAP format for a date */
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    /** TRAP format for a datetime */
    private final static SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyyMMdd HHmmss");

    @Override
    public void checkRule(ReimbursementApp app)
    {
        // TODO Auto-generated method stub

    }

    /**
     * Convert a string into a Date object using the standard date format for TRAP.
     * 
     * @param rawDate - The string in trap date format
     * @return - A Date object constructed from the input
     * @throws InputValidationException - If the input does not conform to TRAP's date format
     */
    public static Date convertToDate(String rawDate) throws InputValidationException
    {
        try
        {
            return dateFormat.parse(rawDate);
        }
        catch (ParseException pe)
        {
            throw new InputValidationException("Invalid date format", pe);
        }
    }

    /**
     * Convert a string into a Date object using the standard datetime format for TRAP.
     * 
     * @param rawDatetime - The string in trap datetime format
     * @return - A Date object constructed from the input
     * @throws InputValidationException - If the input does not conform to TRAP's datetime format
     */
    public static Date convertToDatetime(String rawDatetime) throws InputValidationException
    {
        try
        {
            return datetimeFormat.parse(rawDatetime);
        }
        catch (ParseException pe)
        {
            throw new InputValidationException("Invalid datetime format", pe);
        }
    }

    /**
     * Find the Date that is a certain number of days ahead of the given base date.
     * 
     * @param baseDate - The starting date.
     * @param numDays - The number of days to advance.
     * @return - The baseDate advance by numDays.
     */
    public static Date advanceDateInDays(Date baseDate, int numDays)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(baseDate);
        cal.add(Calendar.DATE, numDays);
        return cal.getTime();
    }

    /**
     * Returns a string formatted in the standard TRAP date format for a given date.
     * 
     * @param date - The date to stringify
     * @return - The string representation of the date in TRAP's date format
     */
    public static String dateToString(Date date)
    {
        return dateFormat.format(date);
    }

    /**
     * Returns a string formatted in the standard TRAP datetime format for a given date.
     * 
     * @param datetime - The date to stringify
     * @return - The string representation of the datetime in TRAP's datetime format
     */
    public static String datetimeToString(Date datetime)
    {
        return datetimeFormat.format(datetime);
    }

}
