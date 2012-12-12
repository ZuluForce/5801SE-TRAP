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
// USCarriersOnlyTest.java
package edu.umn.se.trap.rules.business;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.FormDataQuerier;
import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.data.TransportationTypeEnum;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author Dylan
 * 
 */
public class USCarriersOnlyTest extends TrapTestFramework
{
    String airCarrier;

    @SuppressWarnings("javadoc")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Loads a sample form and makes sure it contains air expenses to verify.
     * 
     * @throws TRAPException When form saving fails
     */
    @Before
    public void setup() throws TRAPException
    {
        super.setup(SampleDataEnum.INTERNATIONAL1);

        List<Integer> airExpenses = FormDataQuerier.findTransportExpenses(testFormData,
                TransportationTypeEnum.AIR);
        if (airExpenses.size() == 0)
        {
            Assert.fail("Need air expenses in sample form for this test");
        }

        airCarrier = String.format(InputFieldKeys.TRANSPORTATION_CARRIER_FMT, airExpenses.get(0));

    }

    /**
     * Verify that a valid US air carrier is accepted during form processing
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void validUSCarrier() throws TRAPException
    {
        testFormData.put(airCarrier, "American");
        this.saveFormData(testFormData, testFormId);
        submitFormData(testFormId);
    }

    /**
     * Verify that a non-valid air carrier is not accepted during form processing
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void invalidCarrier() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("Air carrier is not US based.");
        testFormData.put(airCarrier, "Dylan's Party Plane");
        this.saveFormData(testFormData, testFormId);
        submitFormData(testFormId);
    }

}
