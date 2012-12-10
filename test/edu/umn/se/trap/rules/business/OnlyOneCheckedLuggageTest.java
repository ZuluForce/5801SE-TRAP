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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * @author Dylan
 * 
 */
public class OnlyOneCheckedLuggageTest extends TrapTestFramework
{
    private static final Logger log = LoggerFactory.getLogger(OnlyOneCheckedLuggageTest.class);

    int numAirTravel;

    int baseNumberForNewExpenses;
    List<String> newDateFields;
    List<String> newTypeFields;
    List<String> newCurrencyFields;
    List<String> newAmountFields;

    @Before
    public void setup() throws TRAPException
    {
        super.setup(SampleDataEnum.INTERNATIONAL1);

        List<Integer> airExpenses = FormDataQuerier.findTransportExpenses(testFormData,
                TransportationTypeEnum.AIR);
        numAirTravel = airExpenses.size();
        if (numAirTravel == 0)
        {
            Assert.fail("Need air expenses in sample form for this test");
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

    @Test
    public void validCheckedLuggage() throws TRAPException
    {
        addNewExpenses(1, 25.0);
        saveAndSubmitTestForm();
    }

    @Test
    public void overOneCheckedLuggage() throws TRAPException
    {
        addNewExpenses(2, 25.0, 20.0);
        saveAndSubmitTestForm();

    }

    @Test
    public void underOneCheckedLuggage() throws TRAPException
    {
        // The base form has no checked luggage
        saveAndSubmitTestForm();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void moreLuggageThanAirTravel() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);
        exception
                .expectMessage("Cannot claim more baggage expenses than the number of air travel expenses");
        addNewExpenses(numAirTravel + 1, 25.0, 20.0, 25.0);
        saveAndSubmitTestForm();
    }

    @Test
    public void baggageGreaterThanTwentyFive() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("is above maximum allowable");

        addNewExpenses(1, 30.0);
        saveAndSubmitTestForm();
    }

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
