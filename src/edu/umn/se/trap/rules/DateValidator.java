// DateValidator.java
package edu.umn.se.trap.rules;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.InputValidationException;

/**
 * @author planeman
 * 
 */
public class DateValidator extends InputValidationRule
{
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private final static SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyyMMdd HHmmss");

    /*
     * (non-Javadoc)
     * @see edu.umn.se.trap.rules.TRAPRule#checkRule(edu.umn.se.trap.data.ReimbursementApp)
     */
    @Override
    public void checkRule(ReimbursementApp app)
    {
        // TODO Auto-generated method stub

    }

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

}
