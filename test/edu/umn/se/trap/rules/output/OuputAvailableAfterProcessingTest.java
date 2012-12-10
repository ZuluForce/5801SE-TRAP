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
// OuputAvailableAfterProcessingTest.java
package edu.umn.se.trap.rules.output;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.test.generate.LoadedSampleForm;
import edu.umn.se.trap.test.generate.TestDataGenerator;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * For requirement 5.a and 5.b
 * 
 * @author andrewh
 * 
 */
public class OuputAvailableAfterProcessingTest extends TrapTestFramework
{

    @Before
    public void setup() throws TRAPException
    {
        super.setup(SampleDataEnum.DOMESTIC1);
    }

    @Test
    public void submitAndCheckOutput() throws TRAPException
    {
        saveAndSubmitTestForm();
    }

    @Test
    public void originalFormAvailableAfterError() throws TRAPException
    {
        setValidUser(SampleDataEnum.INTERNATIONAL1);
        try
        {
            submitAndCheckOutput();
        }
        catch (TRAPException error)
        {
            // What we want
            LoadedSampleForm originalForm = TestDataGenerator
                    .getSampleForm(SampleDataEnum.DOMESTIC1);

            setValidUser(SampleDataEnum.DOMESTIC1); // Have to change back so I can get the form
            Map<String, String> savedForm = getSavedFormData(testFormId);
            Assert.assertEquals(originalForm, savedForm);
        }
    }
}
