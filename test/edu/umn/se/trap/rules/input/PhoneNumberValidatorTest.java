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
// PhoneNumberValidatorTest.java
package edu.umn.se.trap.rules.input;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.InputValidationException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * TODO: Fill in proper requirement<br/>
 * Test for requirement 1.*
 * 
 * @author andrewh
 * 
 */
public class PhoneNumberValidatorTest extends TrapTestFramework
{
    /**
     * Load a sample form
     * 
     * @throws TRAPException - When saving the form fails
     */
    @Before
    public void setup() throws TRAPException
    {
        this.setup(SampleDataEnum.DOMESTIC1);
    }

    /**
     * Test that a valid phone number is accepted.
     * 
     * @throws TRAPException - When the form fails submission
     */
    @Test
    public void validPhoneNumber() throws TRAPException
    {
        // The sample form has a valid phone number in it
        saveAndSubmitTestForm();
    }

    @SuppressWarnings("javadoc")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Test that an international number with a leading 1 is not accepted.
     * 
     * @throws TRAPException When the form fails submission
     */
    @Test
    public void invalidIntlNumber() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("is not formatted correctly");
        testFormData.put(InputFieldKeys.EMERGENCY_PHONE, "1-952-123-4567");
        saveAndSubmitTestForm();
    }

    /**
     * Test that a missing area code is caught.
     * 
     * @throws TRAPException When the form fails submission
     */
    @Test
    public void missingAreaCode() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("is not formatted correctly");
        testFormData.put(InputFieldKeys.EMERGENCY_PHONE, "123-4567");
        saveAndSubmitTestForm();
    }

    /**
     * Test that too long of an area code is caught.
     * 
     * @throws TRAPException When the form fails submission
     */
    @Test
    public void areaCodeTooLong() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("is not formatted correctly");
        testFormData.put(InputFieldKeys.EMERGENCY_PHONE, "9521-123-4567");
        saveAndSubmitTestForm();
    }

    /**
     * Test that when the second set of numbers is too short it is caught.
     * 
     * @throws TRAPException When the form fails submission
     */
    @Test
    public void secondSetTooShort() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("is not formatted correctly");
        testFormData.put(InputFieldKeys.EMERGENCY_PHONE, "952-12-4567");
        saveAndSubmitTestForm();
    }

    /**
     * Test that when the second set of numbers is too long it is caught.
     * 
     * @throws TRAPException When the form fails submission
     */
    @Test
    public void secondSetTooLong() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("is not formatted correctly");
        testFormData.put(InputFieldKeys.EMERGENCY_PHONE, "952-1234-4567");
        saveAndSubmitTestForm();
    }

    /**
     * Test that when the last set of numbers is missing it is caught
     * 
     * @throws TRAPException When the form fails submission
     */
    @Test
    public void missingLastSet() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("is not formatted correctly");
        testFormData.put(InputFieldKeys.EMERGENCY_PHONE, "952-123");
        saveAndSubmitTestForm();
    }

    /**
     * The last set of numbers is too short.
     * 
     * @throws TRAPException When the form fails submission
     */
    @Test
    public void lastSetTooShort() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("is not formatted correctly");
        testFormData.put(InputFieldKeys.EMERGENCY_PHONE, "952-123-456");
        saveAndSubmitTestForm();
    }

    /**
     * The last set of numbers is too long
     * 
     * @throws TRAPException When the form fails submission
     */
    @Test
    public void lastSetTooLong() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("is not formatted correctly");
        testFormData.put(InputFieldKeys.EMERGENCY_PHONE, "952-123-45678");
        saveAndSubmitTestForm();
    }

    /**
     * The phone number is empty.
     * 
     * @throws TRAPException When the form fails submission
     */
    @Test
    public void emptyPhoneNumber() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("is not formatted correctly");
        testFormData.put(InputFieldKeys.EMERGENCY_PHONE, " ");
        saveAndSubmitTestForm();
    }
}
