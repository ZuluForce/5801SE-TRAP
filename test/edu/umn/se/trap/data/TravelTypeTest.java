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
// TravelTypeTest.java
package edu.umn.se.trap.data;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.MissingFieldException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author andrewh
 * 
 */
public class TravelTypeTest extends TrapTestFramework
{

    @Before
    public void setup() throws TRAPException
    {
        super.setup(SampleDataEnum.DOMESTIC1);
    }

    @Test
    public void cseSponsored() throws TRAPException
    {
        saveAndSubmitTestForm();
    }

    @Test
    public void dtcSponsored() throws TRAPException
    {
        testFormData.remove(InputFieldKeys.TRAVEL_TYPE_CSE_SPONSORED);
        testFormData.put(InputFieldKeys.TRAVEL_TYPE_DTC_SPONSORED, TRAPConstants.STR_YES);
        saveAndSubmitTestForm();
    }

    @Test
    public void nonSponsored() throws TRAPException
    {
        testFormData.remove(InputFieldKeys.TRAVEL_TYPE_CSE_SPONSORED);
        testFormData.put(InputFieldKeys.TRAVEL_TYPE_NONSPONSORED, TRAPConstants.STR_YES);
        saveAndSubmitTestForm();
    }

    @Test
    public void allTypes()
    {
        testFormData.put(InputFieldKeys.TRAVEL_TYPE_CSE_SPONSORED, TRAPConstants.STR_YES);
        testFormData.put(InputFieldKeys.TRAVEL_TYPE_DTC_SPONSORED, TRAPConstants.STR_YES);
        testFormData.put(InputFieldKeys.TRAVEL_TYPE_NONSPONSORED, TRAPConstants.STR_YES);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void noType() throws TRAPException
    {
        exception.expect(MissingFieldException.class);
        exception.expectMessage("Must have at least one Travel Type");

        testFormData.remove(InputFieldKeys.TRAVEL_TYPE_CSE_SPONSORED);
        testFormData.remove(InputFieldKeys.TRAVEL_TYPE_DTC_SPONSORED);
        testFormData.remove(InputFieldKeys.TRAVEL_TYPE_NONSPONSORED);

        saveAndSubmitTestForm();
    }
}
