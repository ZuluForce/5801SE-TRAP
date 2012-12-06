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
import edu.umn.se.trap.test.generate.LoadedSampleForm;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;
import edu.umn.se.trap.util.Pair;

/**
 * @author andrewh
 * 
 */
public class PhoneNumberValidatorTest extends TrapTestFramework
{

    LoadedSampleForm formData;
    Integer formId;

    @Before
    public void setup() throws TRAPException
    {
        Pair<Integer, LoadedSampleForm> formInfo = basicTrapSetup(SampleDataEnum.DOMESTIC1);
        formData = formInfo.getRight();
        formId = formInfo.getLeft();
    }

    @Test
    public void validPhoneNumber() throws TRAPException
    {
        // The sample form has a valid phone number in it
        submitFormData(formId);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void invalidIntlNumber() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("is not formatted correctly");
        formData.put(InputFieldKeys.EMERGENCY_PHONE, "1-952-123-4567");
        this.saveFormData(formData, formId);
        submitFormData(formId);
    }

    @Test
    public void missingAreaCode() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("is not formatted correctly");
        formData.put(InputFieldKeys.EMERGENCY_PHONE, "123-4567");
        this.saveFormData(formData, formId);
        submitFormData(formId);
    }

    @Test
    public void areaCodeTooLong() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("is not formatted correctly");
        formData.put(InputFieldKeys.EMERGENCY_PHONE, "9521-123-4567");
        this.saveFormData(formData, formId);
        submitFormData(formId);
    }

    @Test
    public void secondSetTooShort() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("is not formatted correctly");
        formData.put(InputFieldKeys.EMERGENCY_PHONE, "952-12-4567");
        this.saveFormData(formData, formId);
        submitFormData(formId);
    }

    @Test
    public void secondSetTooLong() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("is not formatted correctly");
        formData.put(InputFieldKeys.EMERGENCY_PHONE, "952-1234-4567");
        this.saveFormData(formData, formId);
        submitFormData(formId);
    }

    @Test
    public void missingLastSet() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("is not formatted correctly");
        formData.put(InputFieldKeys.EMERGENCY_PHONE, "952-123");
        this.saveFormData(formData, formId);
        submitFormData(formId);
    }

    @Test
    public void lastSetTooShort() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("is not formatted correctly");
        formData.put(InputFieldKeys.EMERGENCY_PHONE, "952-123-456");
        this.saveFormData(formData, formId);
        submitFormData(formId);
    }

    @Test
    public void lastSetTooLong() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("is not formatted correctly");
        formData.put(InputFieldKeys.EMERGENCY_PHONE, "952-123-45678");
        this.saveFormData(formData, formId);
        submitFormData(formId);
    }

    @Test
    public void emptyPhoneNumber() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("is not formatted correctly");
        formData.put(InputFieldKeys.EMERGENCY_PHONE, " ");
        this.saveFormData(formData, formId);
        submitFormData(formId);
    }
}
