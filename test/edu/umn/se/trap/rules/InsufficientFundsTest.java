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
// InsufficientFundsTest.java
package edu.umn.se.trap.rules;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.InsufficientFundsException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.form.OutputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * Verify that a reimbursement application is rejected when there are insufficient funds in one or
 * more grants to satisfy the funding partitioning defined by the form.
 * 
 * @author andrewh
 * 
 */
public class InsufficientFundsTest extends TrapTestFramework
{

    /**
     * Load a sample form.
     * 
     * @throws TRAPException When form saving fails.
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
     * Verify that the application is rejected when the reimbursement total is above the amount in a
     * grant.
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void extremelyLargeExpense() throws TRAPException
    {
        exception.expect(InsufficientFundsException.class);
        exception.expectMessage("Insufficient funds in grant");
        testFormData.put("OTHER2_AMOUNT", new Double(Double.MAX_VALUE).toString());

        saveAndSubmitTestForm();
    }

    /**
     * Test that the reimbursement is completed and excepted when the reimbursement total is equal
     * to the amount in the grant.
     * 
     * @throws TRAPException When form processing fails.
     */
    @Test
    public void equalToGrantFunds() throws TRAPException
    {
        String other2Amnt = String.format(InputFieldKeys.OTHER_AMOUNT_FMT, 2);

        Double newAmount = Double.parseDouble(testFormData.get(other2Amnt));
        newAmount += 248953.76; // This will put the reimbursement total up to 250000

        testFormData.put(other2Amnt, newAmount.toString());

        saveAndSubmitTestForm();

        Map<String, String> output = getCompletedForm(testFormId);
        Double total = Double.parseDouble(output.get(OutputFieldKeys.TOTAL_REIMBURSEMENT));
        Assert.assertEquals(250000.0, total);
    }
}
