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
// AlcoholOnlyAllowedUnderNonSponsoredGrant.java
package edu.umn.se.trap.rules.business;

import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.FormDataQuerier;
import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * This class tests the alcohol requirement 3.d.
 * 
 * @author nagell2008
 * 
 */
public class AlcoholOnlyFundedByNonSponsoredGrantTest extends TrapTestFramework
{

    Map<String, String> goodAlcoholOther = null;
    Map<String, String> badAlcoholOther = null;

    Map<String, String> goodAlcoholIncidental = null;
    Map<String, String> badAlcoholIncidental = null;

    String incidentalJustField;
    String incidentalAmntField;
    String otherJustField;
    String otherAmntField;

    /**
     * Some quick setup that will be used later. Specifically, and incidental and other expense.
     * 
     * @throws TRAPException - Something bad happened during setup
     */
    @Before
    public void setup() throws TRAPException
    {
        super.setup(SampleDataEnum.INTERNATIONAL1);

        incidentalJustField = FormDataQuerier.buildFieldStrForFirstIncidental(testFormData,
                InputFieldKeys.INCIDENTAL_JUSTIFICATION_FMT);
        incidentalAmntField = FormDataQuerier.buildFieldStrForFirstIncidental(testFormData,
                InputFieldKeys.INCIDENTAL_AMOUNT_FMT);

        otherJustField = String.format(InputFieldKeys.OTHER_JUSTIFICATION_FMT, 1);
        otherAmntField = String.format(InputFieldKeys.OTHER_AMOUNT_FMT, 1);
    }

    /**
     * Expected exception, if any
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Alcohol other expense with a non-sponsored grant that has enough money to fund the expense.
     * 
     * @throws TRAPException - Test fails if exception is thrown
     */
    @Test
    public void alcoholOtherEnoughFunding() throws TRAPException
    {
        testFormData.put(otherJustField, "A Whiskey on the rocks");
        testFormData.put(otherAmntField, "1.00");

        saveAndSubmitTestForm();
    }

    /**
     * Alcohol expense with a non-sponsored grant, but the expense is too high and cannot be funded.
     * 
     * @throws TRAPException - Test passes if exception is thrown
     */
    @Test
    public void alcoholOtherLowFunding() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("Alcohol expense of");

        testFormData.put(otherJustField, "A martini shaken and not stirred");
        testFormData.put(otherAmntField, "100000000.00");

        saveAndSubmitTestForm();
    }

    /**
     * Alcohol expense charged as an incidental. The non-sponsored grant does not have enough money.
     * 
     * @throws TRAPException - Test passes if exception is thrown with a specific message
     */
    @Test
    public void alcoholIncidentalLowFunding() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("Alcohol expense of");

        testFormData.put(incidentalJustField,
                "I needed a beer to not die after Ponder's horrible game");
        testFormData.put(incidentalAmntField, "100000000.00");

        saveAndSubmitTestForm();
    }

    /**
     * Alcohol expense charged as an incidental. The non-sponsored grant does have enough money.
     * 
     * @throws TRAPException - - Test fails if exception is thrown
     */
    @Test
    public void alcoholIncidentalEnoughFunding() throws TRAPException
    {
        testFormData.put(incidentalJustField, "A little bit of wine for the heart. Ya know..");
        testFormData.put(incidentalAmntField, "1.00");

        saveAndSubmitTestForm();
    }

}
