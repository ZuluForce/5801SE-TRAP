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
// MealPerDiemTest.java
package edu.umn.se.trap.rules.business;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.FormDataQuerier;
import edu.umn.se.test.frame.TestPerDiemDB;
import edu.umn.se.test.frame.TestPerDiemDB.PerDiemBuilder;
import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.form.OutputFieldKeys;
import edu.umn.se.trap.rules.FinalizeRule;
import edu.umn.se.trap.test.generate.LoadedSampleForm;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * Check for requirement 2.c and 2.m
 * 
 * @author Dylan
 * 
 */
public class MealPerDiemTest extends TrapTestFramework
{

    @SuppressWarnings("javadoc")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    String day1MealCountry;

    String day1Total;
    String lastDayTotal;

    String grant1Amnt;
    String grant2Amnt;

    // The per diem for Minneapolis MN
    PerDiemBuilder TCPerDiemInfo;

    // The per diem for Puerto RIco
    PerDiemBuilder PRPerDiemInfo;

    /**
     * Load a sample form.
     * 
     * @throws TRAPException When form saving fails
     * @throws KeyNotFoundException When the per diems cannot be found in the database
     */
    @Before
    public void setup() throws TRAPException, KeyNotFoundException
    {
        setup(SampleDataEnum.INTERNATIONAL1);

        // This incidental must be on the first day which is assumed true for the international form
        day1MealCountry = String.format(InputFieldKeys.BREAKFAST_COUNTRY_FMT, 1);
        day1Total = String.format(OutputFieldKeys.DAY_TOTAL_FMT, 1);
        lastDayTotal = FormDataQuerier.buildFieldStrForLastIncidental(testFormData,
                OutputFieldKeys.DAY_TOTAL_FMT);
        grant1Amnt = String.format(OutputFieldKeys.GRANT_CHARGE_FMT, 1);
        grant2Amnt = String.format(OutputFieldKeys.GRANT_CHARGE_FMT, 2);

        TCPerDiemInfo = perDiemDB.fillBuilderWithDomesticInfo("Minneapolis", "MN");
        PRPerDiemInfo = perDiemDB.fillBuilderWithIntlInfo("", "puerto rico");
    }

    /**
     * Verify that only 75% of claimed meal expense on the first day is reimbursed.
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void seventyFivePercentOnFirstDay() throws TRAPException
    {
        // Get the original per diem for the breakfast meal
        Double mealAmount = TCPerDiemInfo.getRates().get(
                TestPerDiemDB.RATE_FIELDS.BREAKFAST_RATE.ordinal());

        // Set the per diem to 0 now
        TCPerDiemInfo.addBreakfastRate(0.0);
        perDiemDB.addRateToDB(TCPerDiemInfo);

        mealAmount *= 0.75;

        // Get the expected output and more importantly the expected total
        LoadedSampleForm expected = this.getExpectedOutput(testFormData);
        Double expectedTotal = Double
                .parseDouble(expected.get(OutputFieldKeys.TOTAL_REIMBURSEMENT));

        // Create a new total with 75% of the incidental cost subtracted
        expected.put(OutputFieldKeys.TOTAL_REIMBURSEMENT,
                getDiffAsString(expectedTotal, mealAmount));

        // Update the day1 total
        Double d1Total = Double.parseDouble(expected.get(day1Total));
        expected.put(day1Total, getDiffAsString(d1Total, mealAmount));

        // Update the grant totals. We hardcode their percentages here so don't change them in the
        // file!!
        Double grant1Total = Double.parseDouble(expected.get(grant1Amnt));
        expected.put(grant1Amnt, getDiffAsString(grant1Total, mealAmount * .75));

        Double grant2Total = Double.parseDouble(expected.get(grant2Amnt));
        expected.put(grant2Amnt, getDiffAsString(grant2Total, mealAmount * .25));

        saveAndSubmitTestForm();
        Map<String, String> result = getCompletedForm(testFormId);

        Assert.assertTrue(doOutputsMatch(result, expected));
    }

    /**
     * Verify that only 75% of claimed meal expense on the last day is reimbursed.
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void seventyFivePercentOnLastDay() throws TRAPException
    {
        // Get the original per diem for the breakfast meal
        Double mealAmount = PRPerDiemInfo.getRates().get(
                TestPerDiemDB.RATE_FIELDS.BREAKFAST_RATE.ordinal());

        // Set the rate for the breakfast to 0
        PRPerDiemInfo.addBreakfastRate(0.0);
        perDiemDB.addRateToDB(PRPerDiemInfo);

        mealAmount *= 0.75;

        // Get the expected output and more importantly the expected total
        LoadedSampleForm expected = this.getExpectedOutput(testFormData);
        Double expectedTotal = Double
                .parseDouble(expected.get(OutputFieldKeys.TOTAL_REIMBURSEMENT));

        // Create a new total with 75% of the incidental cost subtracted
        expected.put(OutputFieldKeys.TOTAL_REIMBURSEMENT,
                getDiffAsString(expectedTotal, mealAmount));

        // Update the last total
        Double lastDTotal = Double.parseDouble(expected.get(lastDayTotal));
        expected.put(lastDayTotal, getDiffAsString(lastDTotal, mealAmount));

        // Update the grant totals. We hardcode their percentages here so don't change them in the
        // file!!
        Double grant1Total = Double.parseDouble(expected.get(grant1Amnt));
        expected.put(grant1Amnt, getDiffAsString(grant1Total, mealAmount * .75));

        Double grant2Total = Double.parseDouble(expected.get(grant2Amnt));
        expected.put(grant2Amnt, getDiffAsString(grant2Total, mealAmount * .25));

        saveAndSubmitTestForm();
        Map<String, String> result = getCompletedForm(testFormId);
        Assert.assertTrue(doOutputsMatch(result, expected));
    }

    /**
     * Verify that a per diem with an unknown location (to the db) is rejected.
     * 
     * @throws TRAPException When form processing fails
     * @throws KeyNotFoundException When the per diem cannot be removed from the db
     */
    @Test
    public void unknownPerDiemLocation() throws TRAPException, KeyNotFoundException
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("Failed to find per diem for meal expense");

        perDiemDB.removeRateFromDB(TCPerDiemInfo);

        saveAndSubmitTestForm();
    }

    private String getDiffAsString(Double amount1, Double amount2)
    {
        Double diff = amount1 - amount2;

        return FinalizeRule.formatDoubleAsCurrencyNoComma(diff);
    }
}
