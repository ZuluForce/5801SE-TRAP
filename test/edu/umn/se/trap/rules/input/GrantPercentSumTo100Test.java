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
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * TODO: Requirement<br/>
 * This test verifies requirement 1.*
 * 
 * @author andrewh
 * 
 */
public class GrantPercentSumTo100Test extends TrapTestFramework
{
    String grant1Name, grant1Percent;
    String grant2Name, grant2Percent;

    /**
     * Load a sample form.
     * 
     * @throws TRAPException when saving the form fails
     */
    @Before
    public void setup() throws TRAPException
    {
        this.setup(SampleDataEnum.DOMESTIC1);

        grant1Name = String.format(InputFieldKeys.GRANT_ACCOUNT_FMT, 1);
        grant1Percent = String.format(InputFieldKeys.GRANT_PERCENT_FMT, 1);

        grant2Name = String.format(InputFieldKeys.GRANT_ACCOUNT_FMT, 2);
        grant2Percent = String.format(InputFieldKeys.GRANT_PERCENT_FMT, 2);
    }

    /**
     * Verify a single valid grant summing to 100% is accepted
     * 
     * @throws TRAPException when form submission fails
     */
    @Test
    public void singleGrantEqualTo100() throws TRAPException
    {
        testFormData.put(grant1Percent, "100");
        saveAndSubmitTestForm();
    }

    @SuppressWarnings("javadoc")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Verify a single valid grant with less than 100% is rejected
     * 
     * @throws TRAPException when form submission fails
     */
    @Test
    public void singleGrantLessThan100() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Grant percentages do not sum to 100%");

        testFormData.put(grant1Percent, "80");
        saveAndSubmitTestForm();
    }

    /**
     * Verify a single grant with greater than 100% is rejected
     * 
     * @throws TRAPException when form submission fails
     */
    @Test
    public void singleGrantGreaterThan100() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Grant percentages do not sum to 100%");

        testFormData.put(grant1Percent, "120");
        saveAndSubmitTestForm();
    }

    /**
     * Verify a single grant with 0% is rejected.
     * 
     * @throws TRAPException when form submission fails
     */
    @Test
    public void singleGrantEqualTo0() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Grant percentages do not sum to 100%");

        testFormData.put(grant1Percent, "00");
        saveAndSubmitTestForm();
    }

    /**
     * Verify multiple grants with less than 100% is rejected
     * 
     * @throws TRAPException when form submission fails
     */
    @Test
    public void multiGrantLessThan100() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Grant percentages do not sum to 100%");

        testFormData.put(InputFieldKeys.NUM_GRANTS, "2");
        testFormData.put(grant1Percent, "60");

        testFormData.put(grant2Name, "UMN_SUPER_PAC");
        testFormData.put(grant2Percent, "20");

        saveAndSubmitTestForm();
    }

    /**
     * Verify multiple grants summing to over 100% is rejected.
     * 
     * @throws TRAPException when form submission fails
     */
    @Test
    public void multiGrantGreaterThan100() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Grant percentages do not sum to 100%");

        testFormData.put(InputFieldKeys.NUM_GRANTS, "2");
        testFormData.put(grant1Percent, "90");

        testFormData.put(grant2Name, "UMN_SUPER_PAC");
        testFormData.put(grant2Percent, "20");

        saveAndSubmitTestForm();
    }

    /**
     * Verify multiple grants equal to 0% is rejected
     * 
     * @throws TRAPException when form submission fails
     */
    @Test
    public void multiGrantEqualTo0() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Grant percentages do not sum to 100%");

        testFormData.put(InputFieldKeys.NUM_GRANTS, "2");
        testFormData.put(grant1Percent, "00");

        testFormData.put(grant2Name, "UMN_SUPER_PAC");
        testFormData.put(grant2Percent, "00");

        saveAndSubmitTestForm();
    }

    /**
     * Verify multiple grants equal to 100% is accepted.
     * 
     * @throws TRAPException when form submission fails
     */
    @Test
    public void multiGrantEqualTo100() throws TRAPException
    {
        testFormData.put(InputFieldKeys.NUM_GRANTS, "2");
        testFormData.put(grant1Percent, "60");

        testFormData.put(grant2Name, "UMN_SUPER_PAC");
        testFormData.put(grant2Percent, "40");

        saveAndSubmitTestForm();
    }
}
