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
 * @author planeman
 * 
 */
public class UnknownKeyCheckerTest
{
    UnknownKeyChecker checker;

    Map<String, String> fullValidForm;
    Map<String, String> invalidForm;

    @Before
    public void setUp()
    {
        checker = new UnknownKeyChecker();

        fullValidForm = TestDataGenerator.getSampleForm(SampleDataEnum.DOMESTIC1);

        invalidForm = new HashMap<String, String>();
        invalidForm.put("bacon", "secretsauce");
    }

    @After
    public void tearDown()
    {
        checker = null;
    }

    @Test
    public void validKeyTest() throws InputValidationException
    {
        checker.areFormKeysValid(fullValidForm);
    }

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
