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
// FormDataConverterTest.java
package edu.umn.se.trap.form;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.test.generate.TestDataGenerator;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * Test the form data converter's ability to convert a raw input form into a Reimbursement
 * Application.
 * 
 * @author andrewh
 * 
 */
public class FormDataConverterTest
{
    private static Logger log = LoggerFactory.getLogger(FormDataConverterTest.class);

    /**
     * Verify that the form data converter can convert the sample domestic form.
     */
    @Test
    public void testSampleForm1()
    {
        Map<String, String> form = TestDataGenerator.getSampleForm(SampleDataEnum.DOMESTIC1);
        try
        {
            ReimbursementApp app = FormDataConverter.formToReimbursementApp(form);
            log.info("ReimbursementApp:\n{}", app);
            Assert.assertNotNull(app);
        }
        catch (TRAPException te)
        {
            te.printStackTrace();
            Assert.fail("The data converter threw an exception on valid input");
        }
    }
}
