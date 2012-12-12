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
// LodgingPerDiemTest.java
package edu.umn.se.trap.rules.business;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.FormDataQuerier;
import edu.umn.se.test.frame.TestPerDiemDB;
import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author Dylan
 * 
 */
public class LodgingPerDiemTest extends TrapTestFramework
{

    String lodgingCity, lodgingState, lodgingAmount;

    TestPerDiemDB.PerDiemBuilder builder;

    @SuppressWarnings("javadoc")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Load a sample form and make sure it has lodging expenses.
     * 
     * @throws TRAPException When form saving fails
     * @throws KeyNotFoundException When the per diem amount is not found in the database
     */
    @Before
    public void setup() throws TRAPException, KeyNotFoundException
    {
        super.setup(SampleDataEnum.DOMESTIC1);

        List<Integer> lodgingExpenses = FormDataQuerier.findLodgingExpenses(testFormData);
        if (lodgingExpenses.size() == 0)
        {
            Assert.fail("Needed lodging expenses in sample form for this test");
        }

        lodgingCity = String.format(InputFieldKeys.LODGING_CITY_FMT, lodgingExpenses.get(0));
        lodgingState = String.format(InputFieldKeys.LODGING_STATE_FMT, lodgingExpenses.get(0));
        lodgingAmount = String.format(InputFieldKeys.LODGING_AMOUNT_FMT, lodgingExpenses.get(0));

        builder = perDiemDB.fillBuilderWithDomesticInfo(testFormData.get(lodgingCity),
                testFormData.get(lodgingState));

        builder.addRate(TestPerDiemDB.RATE_FIELDS.LODGING_CEILING, 500.00);

    }

    /**
     * Verify that a single lodging expense under the per diem limit is accepted
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void oneLodgingExpenseLessThanLimit() throws TRAPException
    {
        testFormData.put(lodgingAmount, "1");
        saveAndSubmitTestForm();
    }

    /**
     * Verify that a single lodging expense equal to the per diem limit is accepted
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void oneLodgingExpenseEqualToLimit() throws TRAPException
    {
        testFormData.put(lodgingAmount, "500");
        saveAndSubmitTestForm();
    }

    /**
     * Verify that a single lodging expense that is over the per diem limit is rejected
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void oneLodgingExpenseMoreThanLimit() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("is greater than the perDiem");

        testFormData.put(lodgingAmount, "1000000");
        saveAndSubmitTestForm();
    }

}
