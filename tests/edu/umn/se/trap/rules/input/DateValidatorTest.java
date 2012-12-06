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
import edu.umn.se.trap.test.generate.LoadedSampleForm;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author andrewh
 * 
 */
public class DateValidatorTest extends TrapTestFramework
{

    Integer formId;
    LoadedSampleForm testForm;

    String trans1Field;
    String departureDTField;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() throws TRAPException
    {
        setValidUser();
        testForm = getLoadableForm(SampleDataEnum.DOMESTIC1);
        formId = this.saveFormData(testForm, "a test form");

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
            String transport1Date = testForm.get(trans1Field);
            submitFormData(formId);

            Map<String, String> output = getCompletedForm(formId);
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
            String departureDatetime = testForm.get(departureDTField);
            submitFormData(formId);

            Map<String, String> output = getCompletedForm(formId);
            String departureDatetimeOutput = output.get(InputFieldKeys.DEPARTURE_DATETIME);

            Assert.assertTrue(departureDatetime.equals(departureDatetimeOutput));
        }
        catch (TRAPException te)
        {
            Assert.fail(te.getMessage());
        }
    }

    @Test
    public void nonNumericCharacters() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid date format");
        testForm.put(trans1Field, "202d12411s08");
        submitFormData(formId);
    }

    @Test
    public void tooManyDigits() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid date format");
        testForm.put(trans1Field, "2021241108");
        submitFormData(formId);
    }

    @Test
    public void negativeDateYear() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid date format");
        testForm.put(trans1Field, "-20121108");
        submitFormData(formId);
    }

    @Test
    public void outOfRangeMonth() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid date format");
        testForm.put(trans1Field, "20121308");
        submitFormData(formId);
    }

    @Test
    public void outOfRangeDay() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid date format");
        testForm.put(trans1Field, "20121100");
        submitFormData(formId);
    }

    @Test
    public void outOfRangeDayForCertainMonth() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid date format");
        testForm.put(trans1Field, "20121131");
        submitFormData(formId);
    }

    @Test
    public void negativeDatetimeYear() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid datetime format");
        testForm.put(departureDTField, "-20121112 235900");
        submitFormData(formId);
    }

    @Test
    public void outOfRangeMothDatetime() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid datetime format");
        testForm.put(departureDTField, "20121312 235900");
        submitFormData(formId);
    }

    @Test
    public void outOfRangeDayDatetime() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid datetime format");
        testForm.put(departureDTField, "20121100 235900");
        submitFormData(formId);
    }

    @Test
    public void outOfRangeDayForMonthDatetime() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid datetime format");
        testForm.put(departureDTField, "20121131 235900");
        submitFormData(formId);
    }

    @Test
    public void outOfRangeHourDatetime() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid datetime format");
        testForm.put(departureDTField, "20121130 255900");
        submitFormData(formId);
    }

    @Test
    public void outOfRangeMinutesDatetime() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid datetime format");
        testForm.put(departureDTField, "20121130 236100");
        submitFormData(formId);
    }

    @Test
    public void outOfRangeSecondsDatetime() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid datetime format");
        testForm.put(departureDTField, "20121130 235970");
        submitFormData(formId);
    }

    @Test
    public void zeroDaySpan()
    {
        Date date1 = new Date();
        Date date2 = new Date();

        int span = DateValidator.getDaySpan(date1, date2);
        Assert.assertEquals(0, span);
    }
}
