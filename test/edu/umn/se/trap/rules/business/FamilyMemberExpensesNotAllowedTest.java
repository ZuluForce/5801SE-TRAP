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
 * @author andrewh
 * 
 */
public class FamilyMemberExpensesNotAllowedTest extends TrapTestFramework
{
    String incidentalJustField;
    String otherJustField;

    /**
     * Load a sample form.
     * 
     * @throws TRAPException When form saving fails
     */
    @Before
    public void setup() throws TRAPException
    {
        super.setup(SampleDataEnum.INTERNATIONAL1);
        incidentalJustField = FormDataQuerier.buildFieldStrForFirstIncidental(testFormData,
                InputFieldKeys.INCIDENTAL_JUSTIFICATION_FMT);

        otherJustField = String.format(InputFieldKeys.OTHER_JUSTIFICATION_FMT, 1);
    }

    @SuppressWarnings("javadoc")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Verify that a non-family incidental is accepted. In this case, it is for someone that is not
     * yourself, but is also not a family member.
     * 
     * @throws Exception When form processing fails
     */
    @Test
    public void nonFamilyIncidentalTest() throws Exception
    {
        testFormData.put(incidentalJustField, "Tipped the pretty waitress");
        saveAndSubmitTestForm();
    }

    /**
     * Verify that the incidental expense is not accepted when it is for a family member. In this
     * case, it is a purchase for a brother.
     * 
     * @throws Exception When form processing fails
     */
    @Test
    public void familyIncidentalTest() throws Exception
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("Family expenses not allowed for reimbursement.");

        testFormData.put(incidentalJustField, "Bought a doorag for my brother");
        saveAndSubmitTestForm();
    }

    /**
     * Verify that a non-family other expense is accepted. In this case, it is for yourself.
     * 
     * @throws Exception When form processing fails.
     */
    @Test
    public void nonFamilyOtherTest() throws Exception
    {
        testFormData.put(otherJustField, "Bought myself a maserati");
        saveAndSubmitTestForm();
    }

    /**
     * Verify that the other expense is not accepted when it is for a family member. In this case,
     * it is a purchase for a sister.
     * 
     * @throws Exception When form processing fails
     */
    @Test
    public void familyOtherTest() throws Exception
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("Family expenses not allowed for reimbursement.");

        testFormData.put(otherJustField, "Bought my sister a maserati");
        saveAndSubmitTestForm();
    }

    /**
     * Verify that the family member 'other' expense is rejected when the form also contains valid,
     * non-family incidental expenses.
     * 
     * @throws Exception When form processing fails.
     */
    @Test
    public void goodIncidentalBadOther() throws Exception
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("Family expenses not allowed for reimbursement.");

        testFormData.put(incidentalJustField, "Tipped the pretty waitress");
        testFormData.put(otherJustField, "Bought my sister a maserati");
        saveAndSubmitTestForm();
    }

    /**
     * Verify that the family member incidental expense is rejected when the form also contains
     * valid, non-family 'other' expenses.
     * 
     * @throws Exception When form processing fails.
     */
    @Test
    public void badIncidentalGoodOther() throws Exception
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("Family expenses not allowed for reimbursement.");

        testFormData.put(otherJustField, "Bought myself a maserati");
        testFormData.put(incidentalJustField, "Bought a doorag for my brother");
        saveAndSubmitTestForm();
    }

    /**
     * Verify that both incidental and 'other' expenses are rejected if they contain a family member
     * in the justification.
     * 
     * @throws Exception When form processing fails
     */
    @Test
    public void badIncidentalBadOther() throws Exception
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("Family expenses not allowed for reimbursement.");

        testFormData.put(otherJustField, "Bought my sister a maserati");
        testFormData.put(incidentalJustField, "Bought a doorag for my brother");
        saveAndSubmitTestForm();
    }

    /**
     * Verify that both incidental and 'other' expenses are accepted if they do not contain a family
     * member in the justification.
     * 
     * @throws Exception When form processing fails.
     */
    @Test
    public void goodIncidentalGoodOther() throws Exception
    {
        testFormData.put(incidentalJustField, "Tipped the pretty waitress");
        testFormData.put(otherJustField, "Bought myself a maserati");
        saveAndSubmitTestForm();
    }

    /**
     * Verify that substrings are not matched when checking for family members. For example, when
     * looking for "son", TRAP should not consider "person" a match.
     * 
     * @throws Exception When form processing fails.
     */
    @Test
    public void testOnlyMatchAtWordBoundary() throws Exception
    {
        testFormData.put(otherJustField, "Tipped person at the restaurant");
        saveAndSubmitTestForm();
    }
}
