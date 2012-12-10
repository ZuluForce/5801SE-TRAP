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
// CurrencyAbbreviationTest.java
package edu.umn.se.trap.rules.input;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.MissingFieldException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.exception.TRAPRuntimeException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * TODO: Requirement
 * 
 * @author andrewh
 * 
 */
public class CurrencyAbbreviationTest extends TrapTestFramework
{

    String incidental1CurrencyField;

    /**
     * Load a sample form and build the currency field string that we will have to modify.
     * 
     * @throws TRAPException When saving the form fails
     */
    @Before
    public void setup() throws TRAPException
    {
        this.setup(SampleDataEnum.INTERNATIONAL1);

        incidental1CurrencyField = String.format(InputFieldKeys.INCIDENTAL_CURRENCY_FMT, 1);
    }

    /**
     * Verify that a valid currency is accepted in processing.
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void validCurrency() throws TRAPException
    {
        // The default international form has all valid currencies
        saveAndSubmitTestForm();
    }

    @SuppressWarnings("javadoc")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Verify that form processing fails when the currency is missing.
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void missingCurrency() throws TRAPException
    {
        exception.expect(MissingFieldException.class);
        exception.expectMessage("Missing " + incidental1CurrencyField);
        testFormData.remove(incidental1CurrencyField);
        saveAndSubmitTestForm();
    }

    /**
     * Verify that form processing fails when the code for a currency is incomplete/truncated.
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void truncatedCurrency() throws TRAPException
    {
        exception.expect(TRAPRuntimeException.class);
        exception.expectMessage("currency");
        testFormData.put(incidental1CurrencyField, "US");
        saveAndSubmitTestForm();
    }

    /**
     * Verify that form processing fails when there is an extra character in the currency code. In
     * this case the extra character is a space.
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void extraCharacterSpace() throws TRAPException
    {
        exception.expect(TRAPRuntimeException.class);
        exception.expectMessage("currency");
        testFormData.put(incidental1CurrencyField, "USD ");
        saveAndSubmitTestForm();
    }

    /**
     * Verify that form processing fails when there is an extra character in the currency code. In
     * this case the extra character is an alpha-numeric.
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void extraCharacter() throws TRAPException
    {
        exception.expect(TRAPRuntimeException.class);
        exception.expectMessage("currency");

        // United States Development League
        testFormData.put(incidental1CurrencyField, "USDL");
        saveAndSubmitTestForm();
    }
}
