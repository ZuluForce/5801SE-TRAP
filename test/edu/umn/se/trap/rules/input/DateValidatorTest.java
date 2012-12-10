/*****************************************************************************************
 * Copyright (c) 2012 Dylan Bettermann, Andrew Helgeson, Brian Maurer, Ethan Waytas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ****************************************************************************************/
// DateValidatorTest.java
package edu.umn.se.trap.rules.input;

import java.util.Date;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.InputValidationException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * TODO: Requirement<br/>
 * 
 * @author andrewh
 * 
 */
public class DateValidatorTest extends TrapTestFramework
{
    String trans1Field;
    String departureDTField;

    @SuppressWarnings("javadoc")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Load a sample form and build the strings for the fields we will modify.
     * 
     * @throws TRAPException When saving the sample form fails.
     */
    @Before
    public void setup() throws TRAPException
    {
        this.setup(SampleDataEnum.DOMESTIC1);

        trans1Field = String.format(InputFieldKeys.TRANSPORTATION_DATE_FMT, 1);
        departureDTField = InputFieldKeys.DEPARTURE_DATETIME;
    }

    /**
     * Test that the date validator correctly parses a form with known valid dates.
     */
    @Test
    public void validDate()
    {
        try
        {
            String transport1Date = testFormData.get(trans1Field);
            submitFormData(testFormId);

            Map<String, String> output = getCompletedForm(testFormId);
            String transport1DateOutput = output.get(trans1Field);

            Assert.assertTrue(transport1DateOutput.equals(transport1Date));
        }
        catch (TRAPException te)
        {
            Assert.fail(te.getMessage());
        }
    }

    /**
     * Test that the date validator correctly parses a form with known valid datetimes.
     */
    @Test
    public void validDateTime()
    {
        try
        {
            String departureDatetime = testFormData.get(departureDTField);
            submitFormData(testFormId);

            Map<String, String> output = getCompletedForm(testFormId);
            String departureDatetimeOutput = output.get(InputFieldKeys.DEPARTURE_DATETIME);

            Assert.assertTrue(departureDatetime.equals(departureDatetimeOutput));
        }
        catch (TRAPException te)
        {
            Assert.fail(te.getMessage());
        }
    }

    /**
     * Verify that form processing fails when a date has non-numeric characters.
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void nonNumericCharacters() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid date format");
        testFormData.put(trans1Field, "202d12411s08");
        submitFormData(testFormId);
    }

    /**
     * Verify that form processing fails when a date has too many digits
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void tooManyDigits() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid date format");
        testFormData.put(trans1Field, "2021241108");
        submitFormData(testFormId);
    }

    /**
     * Verify that form processing fails when you have a negative year
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void negativeDateYear() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid date format");
        testFormData.put(trans1Field, "-20121108");
        submitFormData(testFormId);
    }

    /**
     * Verify that form processing fails with an invalid month number.
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void outOfRangeMonth() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid date format");
        testFormData.put(trans1Field, "20121308");
        submitFormData(testFormId);
    }

    /**
     * Verify that form processing fails when you have an invalid day number (ie 0). If the world
     * were run by computer scientists they things may have been different.
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void outOfRangeDay() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid date format");
        testFormData.put(trans1Field, "20121100");
        submitFormData(testFormId);
    }

    /**
     * Verify that form processing fails with a day that is out of range for the month it is in.
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void outOfRangeDayForCertainMonth() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid date format");
        testFormData.put(trans1Field, "20121131");
        submitFormData(testFormId);
    }

    /**
     * Verify that form processing fails with a negative datetime year.
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void negativeDatetimeYear() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid datetime format");
        testFormData.put(departureDTField, "-20121112 235900");
        submitFormData(testFormId);
    }

    /**
     * Verify that form processing fails with an out of range month for a datetime.
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void outOfRangeMothDatetime() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid datetime format");
        testFormData.put(departureDTField, "20121312 235900");
        submitFormData(testFormId);
    }

    /**
     * Verify that form processing fails with an our of range day for a datetime.
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void outOfRangeDayDatetime() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid datetime format");
        testFormData.put(departureDTField, "20121100 235900");
        submitFormData(testFormId);
    }

    /**
     * Verify that form processing fails with an out of rane day for the given month in a datetime.
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void outOfRangeDayForMonthDatetime() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid datetime format");
        testFormData.put(departureDTField, "20121131 235900");
        submitFormData(testFormId);
    }

    /**
     * Verify that form processing fails for a datetime with an invalid hour
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void outOfRangeHourDatetime() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid datetime format");
        testFormData.put(departureDTField, "20121130 255900");
        submitFormData(testFormId);
    }

    /**
     * Verify that form processing fails for a datetime with an invalid minutes value.
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void outOfRangeMinutesDatetime() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid datetime format");
        testFormData.put(departureDTField, "20121130 236100");
        submitFormData(testFormId);
    }

    /**
     * Verify that form processing fails for a datetime with an invalid seconds value.
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void outOfRangeSecondsDatetime() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid datetime format");
        testFormData.put(departureDTField, "20121130 235970");
        submitFormData(testFormId);
    }

    /**
     * Verify that two days on the same day (right now) produce a day span of 0.
     */
    @Test
    public void zeroDaySpan()
    {
        Date date1 = new Date();
        Date date2 = new Date();

        int span = DateValidator.getDaySpan(date1, date2);
        Assert.assertEquals(0, span);
    }
}
