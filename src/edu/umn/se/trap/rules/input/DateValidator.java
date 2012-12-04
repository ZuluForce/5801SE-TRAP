/*****************************************************************************************
 * Copyright (c) 2012 Dylan Bettermann, Andrew Helgeson, Brian Maurer, Ethan Waytas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 ****************************************************************************************/
// DateValidator.java
package edu.umn.se.trap.rules.input;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.exception.InputValidationException;

/**
 * Check that arrival and departure datetimes come in the correct order. Check that the reported
 * number of days corresponds with the length between the departure and arrival datetimes
 * (inclusive). Check that the trip arrival time is before the submission time for the trip.
 * 
 * @author andrewh
 * 
 */
public class DateValidator extends InputValidationRule
{
    /** Logger for the DateValidator class */
    private static Logger log = LoggerFactory.getLogger(DateValidator.class);

    /** TRAP format for a date */
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    /** TRAP format for a datetime */
    private final static SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyyMMdd HHmmss");

    /** Used in finding the number of days in a range using the Date object */
    private static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;

    /**
     * Check that arrival and departure datetimes come in the correct order. Check that the reported
     * number of days corresponds with the length between the departure and arrival datetimes
     * (inclusive). Check that the trip arrival time is before the submission time for the trip.
     */
    @Override
    public void checkRule(ReimbursementApp app) throws InputValidationException,
            FormProcessorException
    {
        // Make sure we have arrival and departure times
        Date arrival = app.getArrivalDatetime();
        Date departure = app.getDepartureDatetime();

        if (arrival == null || departure == null)
        {
            throw new InputValidationException("Missing departure or arrival datetime");
        }

        // Check the order of the arrival and departure dates
        if (arrival.before(departure))
        {
            throw new InputValidationException("Departure datetime after the arrival datetime");
        }

        // Check that the report number of days is in line with the arrival and departure datetimes
        int num_days = getDaySpan(departure, arrival);

        if (num_days != app.getNumDays())
        {
            throw new InputValidationException(
                    String.format(
                            "Declared number of days (%d) doesn't match with departure and arrival dates (%d)",
                            app.getNumDays(), num_days));
        }

        // Check that the dates come before form submission
        String submitTime = app.getSubmissionTime();
        if (submitTime == null)
        {
            throw new FormProcessorException(
                    "No form submission datetime present. Cannot complete form processing");
        }
        if (arrival.after(convertToDatetime(submitTime)))
        {
            throw new InputValidationException(
                    "Trip arrival time comes after form submission time.");
        }
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
            dateFormat.setLenient(false);
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
            datetimeFormat.setLenient(false);
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
        dateFormat.setLenient(false);
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
        datetimeFormat.setLenient(false);
        return datetimeFormat.format(datetime);
    }

    /**
     * Get the number of days between start and end with the option of rounding up.
     * 
     * @param start - The start date for the range
     * @param end - The end date for the range
     * @return - The number of days between start and end. In the case that the start and end are
     *         equal, 0 will be returned
     */
    public static Integer getDaySpan(Date start, Date end)
    {
        if (start.equals(end))
            return 0;

        Date _start = getStartOfDay(start);
        Date _end = getStartOfDay(end);

        log.debug("Computing number of days between {} and {}", _start, _end);
        float days = ((_end.getTime() - _start.getTime()) / DAY_IN_MILLIS) + 1;

        return (int) days;
    }

    /**
     * Given a date, return the very beginning of that day.
     * 
     * @param d - The date for which you want to get the start of day for.
     * @return - A Date representing the start of the day for the given date.
     */
    public static Date getStartOfDay(Date d)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    /**
     * Get the start of the next day after d.
     * 
     * @param d - A date
     * @return - The start of the day after the given day d
     */
    public static Date getStartOfNextDay(Date d)
    {
        d = advanceDateInDays(d, 1);
        return getStartOfDay(d);
    }
}
