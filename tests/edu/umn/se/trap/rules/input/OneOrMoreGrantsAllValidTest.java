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
// OneOrMoreGrantsAllValidTest.java
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
 * Test for requirement 1.*
 * 
 * @author andrewh
 * 
 */
public class OneOrMoreGrantsAllValidTest extends TrapTestFramework
{
    /**
     * Load a sample form.
     * 
     * @throws TRAPException When the form cannot be saved
     */
    @Before
    public void setup() throws TRAPException
    {
        this.setup(SampleDataEnum.DOMESTIC1);
    }

    @SuppressWarnings("javadoc")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Test that one valid grant with 100% is not rejected.
     * 
     * @throws TRAPException When form submission fails
     */
    @Test
    public void oneValidGrant() throws TRAPException
    {
        saveAndSubmitTestForm();
    }

    // Currently this cannot be tested because the rule for checking that all grants sum to 100%
    // throws an exception before the grant validity rolls around.
    // @Test
    // public void noGrants() throws TRAPException
    // {
    // exception.expect(InputValidationException.class);
    // exception.expectMessage("No grants provided");
    // formData.put(InputFieldKeys.NUM_GRANTS, "0");
    // this.saveFormData(formData, formId);
    // submitFormData(formId);
    // }

    /**
     * Test that one valid and one invalid grant gets rejected.
     * 
     * @throws TRAPException When form submission fails
     */
    @Test
    public void oneValidOneInvalid() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Grant account black_hole is not valid");

        String newGrantName = String.format(InputFieldKeys.GRANT_ACCOUNT_FMT, 2);
        String newGrantPercent = String.format(InputFieldKeys.GRANT_PERCENT_FMT, 2);
        testFormData.put(InputFieldKeys.NUM_GRANTS, "2");
        testFormData.put(newGrantName, "black_hole");
        testFormData.put(newGrantPercent, "0");

        saveAndSubmitTestForm();
    }

    /**
     * Test that two invalid grants get rejected.
     * 
     * @throws TRAPException When form submission fails
     */
    @Test
    public void twoInvalid() throws TRAPException
    {
        exception.expect(InputValidationException.class);

        String firstGrantName = String.format(InputFieldKeys.GRANT_ACCOUNT_FMT, 1);
        String newGrantName = String.format(InputFieldKeys.GRANT_ACCOUNT_FMT, 2);
        String newGrantPercent = String.format(InputFieldKeys.GRANT_PERCENT_FMT, 2);

        testFormData.put(InputFieldKeys.NUM_GRANTS, "2");
        testFormData.put(firstGrantName, "rabbit_hole");
        testFormData.put(newGrantName, "black_hole");
        testFormData.put(newGrantPercent, "0");

        saveAndSubmitTestForm();
    }
}
