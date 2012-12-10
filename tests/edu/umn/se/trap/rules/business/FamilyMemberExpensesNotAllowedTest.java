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
// FamilyMemberExpensesTest.java
package edu.umn.se.trap.rules.business;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.FormDataQuerier;
import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author planeman
 * 
 */
public class FamilyMemberExpensesNotAllowedTest extends TrapTestFramework
{
    String incidentalJustField;
    String otherJustField;

    @Before
    public void setup() throws TRAPException
    {
        super.setup(SampleDataEnum.INTERNATIONAL1);
        incidentalJustField = FormDataQuerier.buildFieldStrForFirstIncidental(testFormData,
                InputFieldKeys.INCIDENTAL_JUSTIFICATION_FMT);

        otherJustField = String.format(InputFieldKeys.OTHER_JUSTIFICATION_FMT, 1);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void nonFamilyIncidentalTest() throws Exception
    {
        testFormData.put(incidentalJustField, "Tipped the pretty waitress");
        saveAndSubmitTestForm();
    }

    @Test
    public void familyIncidentalTest() throws Exception
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("Family expenses not allowed for reimbursement.");

        testFormData.put(incidentalJustField, "Bought a doorag for my brother");
        saveAndSubmitTestForm();
    }

    @Test
    public void nonFamilyOtherTest() throws Exception
    {
        testFormData.put(otherJustField, "Bought myself a maserati");
        saveAndSubmitTestForm();
    }

    @Test
    public void familyOtherTest() throws Exception
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("Family expenses not allowed for reimbursement.");

        testFormData.put(otherJustField, "Bought my sister a maserati");
        saveAndSubmitTestForm();
    }

    @Test
    public void goodIncidentalBadOther() throws Exception
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("Family expenses not allowed for reimbursement.");

        testFormData.put(incidentalJustField, "Tipped the pretty waitress");
        testFormData.put(otherJustField, "Bought my sister a maserati");
        saveAndSubmitTestForm();
    }

    @Test
    public void badIncidentalGoodOther() throws Exception
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("Family expenses not allowed for reimbursement.");

        testFormData.put(otherJustField, "Bought myself a maserati");
        testFormData.put(incidentalJustField, "Bought a doorag for my brother");
        saveAndSubmitTestForm();
    }

    @Test
    public void badIncidentalBadOther() throws Exception
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("Family expenses not allowed for reimbursement.");

        testFormData.put(otherJustField, "Bought my sister a maserati");
        testFormData.put(incidentalJustField, "Bought a doorag for my brother");
        saveAndSubmitTestForm();
    }

    @Test
    public void goodIncidentalGoodOther() throws Exception
    {
        testFormData.put(incidentalJustField, "Tipped the pretty waitress");
        testFormData.put(otherJustField, "Bought myself a maserati");
        saveAndSubmitTestForm();
    }

    @Test
    public void testOnlyMatchAtWordBoundary() throws Exception
    {
        testFormData.put(otherJustField, "Tipped person at the restaurant");
        saveAndSubmitTestForm();
    }
}
