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
// TestPartialExpenses.java
package edu.umn.se.trap.data;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.MissingFieldException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * This is an extra test that isn't for a specific requirement. Theses tests verify that partially
 * filled expenses (ie some fields are missing) are rejected by the data converter.
 * 
 * @author andrewh
 * 
 */
public class TestPartialExpenses extends TrapTestFramework
{
    // This day in the international sample has all three meals, lodging and incidental
    Integer targetDay = 4;

    /**
     * Load a sample form.
     * 
     * @throws TRAPException When form saving fails
     */
    @Before
    public void setup() throws TRAPException
    {
        this.setup(SampleDataEnum.INTERNATIONAL1);
    }

    @SuppressWarnings("javadoc")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Verify that a partial breakfast expense is detected and rejected.
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void partialBreakfast() throws TRAPException
    {
        String breakfastCity = String.format(InputFieldKeys.BREAKFAST_COUNTRY_FMT, targetDay);
        exception.expect(MissingFieldException.class);
        exception.expectMessage("Missing " + breakfastCity);

        testFormData.remove(breakfastCity);

        saveAndSubmitTestForm();
    }

    /**
     * Verify that a partial lunch expense is detected and rejected.
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void partialLunch() throws TRAPException
    {
        String lunchCity = String.format(InputFieldKeys.LUNCH_COUNTRY_FMT, targetDay);
        exception.expect(MissingFieldException.class);
        exception.expectMessage("Missing " + lunchCity);

        testFormData.remove(lunchCity);

        saveAndSubmitTestForm();
    }

    /**
     * Verify that a partial dinner expense is detected and rejected
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void partialDinner() throws TRAPException
    {
        String dinnerCity = String.format(InputFieldKeys.DINNER_COUNTRY_FMT, targetDay);
        exception.expect(MissingFieldException.class);
        exception.expectMessage("Missing " + dinnerCity);

        testFormData.remove(dinnerCity);

        saveAndSubmitTestForm();
    }

    /**
     * Verify that a partial lodging expense is detected and rejected
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void partialLodging() throws TRAPException
    {
        String lodgingAmount = String.format(InputFieldKeys.LODGING_AMOUNT_FMT, targetDay);
        exception.expect(MissingFieldException.class);
        exception.expectMessage("Missing " + lodgingAmount);

        testFormData.remove(lodgingAmount);

        saveAndSubmitTestForm();
    }
}
