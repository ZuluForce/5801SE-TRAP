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
// OnlyOneCheckedLuggageTest.java
package edu.umn.se.trap.rules.business;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.FormDataQuerier;
import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.data.TRAPConstants;
import edu.umn.se.trap.data.TransportationTypeEnum;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.rules.FinalizeRule;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * TODO: Requirement
 * 
 * @author Dylan
 * 
 */
public class OnlyOneCheckedLuggageTest extends TrapTestFramework
{
    int numAirTravel;

    int baseNumberForNewExpenses;
    List<String> newDateFields;
    List<String> newTypeFields;
    List<String> newCurrencyFields;
    List<String> newAmountFields;

    /**
     * Load a sample international form.
     * 
     * The setup also builds the fields necessary to add new transportation baggage expenses to the
     * form.
     * 
     * @throws TRAPException When form saving fails
     */
    @Before
    public void setup() throws TRAPException
    {
        super.setup(SampleDataEnum.INTERNATIONAL1);

        List<Integer> airExpenses = FormDataQuerier.findTransportExpenses(testFormData,
                TransportationTypeEnum.AIR);
        numAirTravel = airExpenses.size();
        if (numAirTravel == 0)
        {
            Assert.fail("Need air expenses in the sample form for this test");
        }

        newDateFields = new ArrayList<String>();
        newTypeFields = new ArrayList<String>();
        newCurrencyFields = new ArrayList<String>();
        newAmountFields = new ArrayList<String>();

        /* Build all the fields we may need to create new baggage expenses */
        for (int i = 9; i <= (9 + numAirTravel); ++i)
        {
            newDateFields.add(String.format(InputFieldKeys.TRANSPORTATION_DATE_FMT, i));
            newTypeFields.add(String.format(InputFieldKeys.TRANSPORTATION_TYPE_FMT, i));
            newCurrencyFields.add(String.format(InputFieldKeys.TRANSPORTATION_CURRENCY_FMT, i));
            newAmountFields.add(String.format(InputFieldKeys.TRANSPORTATION_AMOUNT_FMT, i));
        }
    }

    /**
     * Verify that one baggage expense is accepted.
     * 
     * @throws TRAPException When form processing fails.
     */
    @Test
    public void oneCheckedLuggage() throws TRAPException
    {
        addNewExpenses(1, 25.0);
        saveAndSubmitTestForm();
    }

    /**
     * Verify that two baggage expenses are accepted.
     * 
     * @throws TRAPException When form processing fails.
     */
    @Test
    public void twoCheckedLuggage() throws TRAPException
    {
        addNewExpenses(2, 25.0, 20.0);
        saveAndSubmitTestForm();

    }

    /**
     * Verify that a form with no baggage expenses is accepted.
     * 
     * @throws TRAPException When form processing fails.
     */
    @Test
    public void noCheckedLuggage() throws TRAPException
    {
        // The base form has no checked luggage
        saveAndSubmitTestForm();
    }

    @SuppressWarnings("javadoc")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Verify that the form is rejected when there are more baggage expenses than air travel.
     * 
     * @throws TRAPException When form processing fails.
     */
    @Test
    public void moreLuggageThanAirTravel() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);
        exception
                .expectMessage("Cannot claim more baggage expenses than the number of air travel expenses");
        addNewExpenses(numAirTravel + 1, 25.0, 20.0, 25.0);
        saveAndSubmitTestForm();
    }

    /**
     * Verify that the form is rejected when the baggage expense is greater than $25.
     * 
     * @throws TRAPException When form processing fails.
     */
    @Test
    public void baggageGreaterThanTwentyFive() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("is above maximum allowable");

        addNewExpenses(1, 30.0);
        saveAndSubmitTestForm();
    }

    /**
     * This is a utility method to add new transportation expenses using the strings we built
     * earlier. This will also update the reimbursement total and num_transportation fields
     * according to the number of expenses added and what their expense amount is.
     * 
     * @param howmany - How many new baggage expenses to add.
     * @param amounts - The amounts for each of the baggage expenses.
     */
    private void addNewExpenses(int howmany, double... amounts)
    {
        for (int i = 0; i < howmany; ++i)
        {
            testFormData.put(newDateFields.get(i), "20121129");
            testFormData.put(newCurrencyFields.get(i), TRAPConstants.USD);
            testFormData.put(newTypeFields.get(i), TransportationTypeEnum.BAGGAGE.toString());
            testFormData.put(newAmountFields.get(i),
                    FinalizeRule.formatDoubleAsCurrencyNoComma(amounts[0]));
        }

        // Update the total number of expenses
        Integer numTransExpenses = Integer.parseInt(testFormData
                .get(InputFieldKeys.NUMBER_TRANSPORTATION_EXPENSES));

        numTransExpenses += howmany;
        testFormData
                .put(InputFieldKeys.NUMBER_TRANSPORTATION_EXPENSES, numTransExpenses.toString());
    }
}
