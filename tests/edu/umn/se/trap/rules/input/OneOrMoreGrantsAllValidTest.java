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
import edu.umn.se.trap.test.generate.LoadedSampleForm;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;
import edu.umn.se.trap.util.Pair;

/**
 * @author andrewh
 * 
 */
public class OneOrMoreGrantsAllValidTest extends TrapTestFramework
{
    LoadedSampleForm formData;
    Integer formId;

    @Before
    public void setup() throws TRAPException
    {
        Pair<Integer, LoadedSampleForm> setupData = basicTrapSetup(SampleDataEnum.DOMESTIC1);
        formData = setupData.getRight();
        formId = setupData.getLeft();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void oneValidGrant() throws TRAPException
    {
        submitFormData(formId);
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

    @Test
    public void oneValidOneInvalid() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Grant account black_hole is not valid");

        String newGrantName = String.format(InputFieldKeys.GRANT_ACCOUNT_FMT, 2);
        String newGrantPercent = String.format(InputFieldKeys.GRANT_PERCENT_FMT, 2);
        formData.put(InputFieldKeys.NUM_GRANTS, "2");
        formData.put(newGrantName, "black_hole");
        formData.put(newGrantPercent, "0");

        this.saveFormData(formData, formId);
        submitFormData(formId);
    }

    @Test
    public void twoInvalid() throws TRAPException
    {
        exception.expect(InputValidationException.class);

        String firstGrantName = String.format(InputFieldKeys.GRANT_ACCOUNT_FMT, 1);
        String newGrantName = String.format(InputFieldKeys.GRANT_ACCOUNT_FMT, 2);
        String newGrantPercent = String.format(InputFieldKeys.GRANT_PERCENT_FMT, 2);

        formData.put(InputFieldKeys.NUM_GRANTS, "2");
        formData.put(firstGrantName, "rabbit_hole");
        formData.put(newGrantName, "black_hole");
        formData.put(newGrantPercent, "0");

        this.saveFormData(formData, formId);
        submitFormData(formId);
    }
}
