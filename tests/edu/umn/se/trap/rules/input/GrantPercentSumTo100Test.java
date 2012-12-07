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
// GrantPercentSumTo100Test.java
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

/**
 * @author andrewh
 * 
 */
public class GrantPercentSumTo100Test extends TrapTestFramework
{
    LoadedSampleForm formData;
    Integer formId;

    String grant1Name, grant1Percent;
    String grant2Name, grant2Percent;

    @Before
    public void setup() throws TRAPException
    {
        setValidUser();
        formData = getLoadableForm(SampleDataEnum.DOMESTIC1);
        formId = this.saveFormData(formData, "test form");

        grant1Name = String.format(InputFieldKeys.GRANT_ACCOUNT_FMT, 1);
        grant1Percent = String.format(InputFieldKeys.GRANT_PERCENT_FMT, 1);

        grant2Name = String.format(InputFieldKeys.GRANT_ACCOUNT_FMT, 2);
        grant2Percent = String.format(InputFieldKeys.GRANT_PERCENT_FMT, 2);
    }

    @Test
    public void singleGrantEqualTo100() throws TRAPException
    {
        formData.put(grant1Percent, "100");
        this.saveFormData(formData, formId);
        submitFormData(formId);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void singleGrantLessThan100() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Grant percentages do not sum to 100%");

        formData.put(grant1Percent, "80");
        this.saveFormData(formData, formId);
        submitFormData(formId);
    }

    @Test
    public void singleGrantGreaterThan100() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Grant percentages do not sum to 100%");

        formData.put(grant1Percent, "120");
        this.saveFormData(formData, formId);
        submitFormData(formId);
    }

    @Test
    public void singleGrantEqualTo0() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Grant percentages do not sum to 100%");

        formData.put(grant1Percent, "00");
        this.saveFormData(formData, formId);
        submitFormData(formId);
    }

    @Test
    public void multiGrantLessThan100() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Grant percentages do not sum to 100%");

        formData.put(InputFieldKeys.NUM_GRANTS, "2");
        formData.put(grant1Percent, "60");

        formData.put(grant2Name, "UMN_SUPER_PAC");
        formData.put(grant2Percent, "20");

        this.saveFormData(formData, formId);
        submitFormData(formId);
    }

    @Test
    public void multiGrantGreaterThan100() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Grant percentages do not sum to 100%");

        formData.put(InputFieldKeys.NUM_GRANTS, "2");
        formData.put(grant1Percent, "90");

        formData.put(grant2Name, "UMN_SUPER_PAC");
        formData.put(grant2Percent, "20");

        this.saveFormData(formData, formId);
        submitFormData(formId);
    }

    @Test
    public void multiGrantEqualTo0() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Grant percentages do not sum to 100%");

        formData.put(InputFieldKeys.NUM_GRANTS, "2");
        formData.put(grant1Percent, "00");

        formData.put(grant2Name, "UMN_SUPER_PAC");
        formData.put(grant2Percent, "00");

        this.saveFormData(formData, formId);
        submitFormData(formId);
    }

    @Test
    public void multiGrantEqualTo100() throws TRAPException
    {
        formData.put(InputFieldKeys.NUM_GRANTS, "2");
        formData.put(grant1Percent, "60");

        formData.put(grant2Name, "UMN_SUPER_PAC");
        formData.put(grant2Percent, "40");

        this.saveFormData(formData, formId);
        submitFormData(formId);
    }
}
