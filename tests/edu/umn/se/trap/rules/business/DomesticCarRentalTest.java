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
// DomesticCarRentalRuleTest.java
package edu.umn.se.trap.rules.business;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.FormDataQuerier;
import edu.umn.se.test.frame.TestGrantDB;
import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.InsufficientFundsException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author planeman
 * 
 */
public class DomesticCarRentalTest extends TrapTestFramework
{

    List<Integer> domesticRentalIndexes;
    List<Integer> intlRentalIndexes;

    String domesticCarrierField;
    String intlCarrierField;

    TestGrantDB.GrantBuilder grantModifier;

    @Before
    public void setUp() throws TRAPException, KeyNotFoundException
    {
        super.setup(SampleDataEnum.INTERNATIONAL1);

        domesticRentalIndexes = FormDataQuerier.findRentalExpenses(testFormData, true);
        intlRentalIndexes = FormDataQuerier.findRentalExpenses(testFormData, false);

        Assert.assertTrue(domesticRentalIndexes.size() > 0);
        Assert.assertTrue(intlRentalIndexes.size() > 0);

        domesticCarrierField = String.format(InputFieldKeys.TRANSPORTATION_CARRIER_FMT,
                domesticRentalIndexes.get(0));

        intlCarrierField = String.format(InputFieldKeys.TRANSPORTATION_CARRIER_FMT,
                intlRentalIndexes.get(0));

        grantModifier = grantDB.fillBulderWithGrantInfo("010101010101");
        return;
    }

    @Test
    public void goodDomesticAndForeignRental() throws TRAPException
    {
        // The international sample form has a foreign and domestic (Puerto Rico) rental
        saveAndSubmitTestForm();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void badDomestic() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("Carrier not accepted for domestic car rental");
        testFormData.put(domesticCarrierField, "Charlie Car Rental");

        saveAndSubmitTestForm();
    }

    @Test
    public void domesticDoDOverrideRule() throws TRAPException
    {
        testFormData.put(domesticCarrierField, "Hertz");
        grantModifier.setFunder("DOD");
        grantDB.addGrant(grantModifier);

        saveAndSubmitTestForm();
    }

    @Test
    public void domesticDoDOverrideNotEnoughMoney() throws TRAPException
    {
        exception.expect(InsufficientFundsException.class);
        exception.expectMessage("cannot be reimbursed using DoD grants with");
        testFormData.put(domesticCarrierField, "Hertz");
        grantModifier.setFunder("DOD");
        grantModifier.setBalance(10.0);
        grantDB.addGrant(grantModifier);

        saveAndSubmitTestForm();
    }
}
