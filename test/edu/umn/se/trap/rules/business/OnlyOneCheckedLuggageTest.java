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
// OnlyOneCheckedLuggageTest.java
package edu.umn.se.trap.rules.business;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.FormDataQuerier;
import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.data.TransportationTypeEnum;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author Dylan
 * 
 */
public class OnlyOneCheckedLuggageTest extends TrapTestFramework
{
    int numCheckedLuggage, numAirTravel;
    String transportation1Date, transportation1Type, transportation1Amount,
            transportation1Currency;
    String transportation2Date, transportation2Type, transportation2Amount,
            transportation2Currency;
    Integer transportation1Number, transportation2Number;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() throws TRAPException
    {
        super.setup(SampleDataEnum.INTERNATIONAL1);

        List<Integer> luggageExpenses = FormDataQuerier.findTransportExpenses(testFormData,
                TransportationTypeEnum.BAGGAGE);
        List<Integer> airExpenses = FormDataQuerier.findTransportExpenses(testFormData,
                TransportationTypeEnum.AIR);
        numCheckedLuggage = luggageExpenses.size();
        numAirTravel = airExpenses.size();
        if (numAirTravel == 0)
        {
            Assert.fail("Need air expenses in sample form for this test");
        }

        // Existing checked luggage
        transportation1Number = luggageExpenses.get(0);
        transportation1Date = String.format(InputFieldKeys.TRANSPORTATION_DATE_FMT,
                transportation1Number);
        transportation1Type = String.format(InputFieldKeys.TRANSPORTATION_TYPE_FMT,
                transportation1Number);
        transportation1Amount = String.format(InputFieldKeys.TRANSPORTATION_AMOUNT_FMT,
                transportation1Number);
        transportation1Currency = String.format(InputFieldKeys.TRANSPORTATION_CURRENCY_FMT,
                transportation1Number);

        // New checked luggage
        transportation2Number = Integer.parseInt(testFormData.get("NUM_TRANSPORTATION")) + 1;
        transportation2Date = String.format(InputFieldKeys.TRANSPORTATION_DATE_FMT,
                transportation2Number);
        transportation2Type = String.format(InputFieldKeys.TRANSPORTATION_TYPE_FMT,
                transportation2Number);
        transportation2Amount = String.format(InputFieldKeys.TRANSPORTATION_AMOUNT_FMT,
                transportation2Number);
        transportation2Currency = String.format(InputFieldKeys.TRANSPORTATION_CURRENCY_FMT,
                transportation2Number);

    }

    @Test
    public void validCheckedLuggage() throws TRAPException
    {
        // Sample form should have correct amount of checked luggage
        saveAndSubmitTestForm();
    }

    @Test
    public void overOneCheckedLuggage() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("Amount of checked luggage exceeds amount");
        testFormData.put(transportation2Date, "20121001");
        testFormData.put(transportation2Type, "BAGGAGE");

        // Don't want to exceed expenses here. Just using an amount I know is within the bounds. May
        // just change to a static value later.
        testFormData.put(transportation2Amount, testFormData.get(transportation1Amount));
        testFormData.put(transportation2Currency, "USD");
        saveAndSubmitTestForm();

    }

    @Test
    public void underOneCheckedLuggage() throws TRAPException
    {
        testFormData.remove(transportation1Date);
        testFormData.remove(transportation1Type);
        testFormData.remove(transportation1Amount);
        testFormData.remove(transportation1Currency);
        saveAndSubmitTestForm();
    }

}
