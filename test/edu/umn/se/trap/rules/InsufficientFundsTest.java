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
// InsufficientFundsTest.java
package edu.umn.se.trap.rules;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.test.generate.TestDataGenerator;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author andrewh
 * 
 */
public class InsufficientFundsTest extends TrapTestFramework
{

    public InsufficientFundsTest()
    {
        super();
    }

    @Test
    public void testExtremelyLargeExpense() throws TRAPException
    {
        setValidUser(); // Sets a known user as the current one in TRAP
        Map<String, String> form = TestDataGenerator.getSampleForm(SampleDataEnum.DOMESTIC1);
        form.put("OTHER2_AMOUNT", new Double(Double.MAX_VALUE).toString());

        Integer id = this.saveFormData(form, "a very expensive trip");

        try
        {
            submitFormData(id);
            Assert.fail("Very expensive reimbursement should not have passed");
        }
        catch (TRAPException e)
        {
            ; // Good
        }
    }
}
