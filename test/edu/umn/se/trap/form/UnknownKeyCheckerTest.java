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
// UnknownKeyCheckerTest.java
package edu.umn.se.trap.form;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.umn.se.trap.exception.InputValidationException;
import edu.umn.se.trap.test.generate.TestDataGenerator;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * Verify that the unknown key checker properly detects invalid keys. This is a unit test and could
 * likely be excluded from the test suite without much reduction in coverage. This test could also
 * be converted to an integration test.
 * 
 * @author andrewh
 * 
 */
public class UnknownKeyCheckerTest
{
    UnknownKeyChecker checker;

    Map<String, String> fullValidForm;
    Map<String, String> invalidForm;

    /**
     * Load a sample form and put an invalid (Epic Meal Time inspired) key value pair into the form.
     */
    @Before
    public void setUp()
    {
        checker = new UnknownKeyChecker();

        fullValidForm = TestDataGenerator.getSampleForm(SampleDataEnum.DOMESTIC1);

        invalidForm = new HashMap<String, String>();
        invalidForm.put("bacon", "secretsauce");
    }

    /**
     * Destruct the key checker.
     */
    @After
    public void tearDown()
    {
        checker = null;
    }

    /**
     * Test that a form with all valid keys is accepted.
     * 
     * @throws InputValidationException When an invalid key is found.
     */
    @Test
    public void validKeyTest() throws InputValidationException
    {
        checker.areFormKeysValid(fullValidForm);
    }

    /**
     * Verify that a form with an invalid key is rejected.
     */
    @Test
    public void invalidKeyTest()
    {
        try
        {
            checker.areFormKeysValid(invalidForm);
            Assert.fail("Invalid form keys passed check");
        }
        catch (InputValidationException ive)
        {
            ; // Good
        }
    }
}
