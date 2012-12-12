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
// DoDForeignExpenseRestrictionTest.java
package edu.umn.se.trap.rules.business;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TestGrantDB;
import edu.umn.se.test.frame.TestUserGrantDB;
import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * This class tests requirement 3.i. Specifically, it tests the foreign expense restrictions
 * enforced by DoD grants.
 * 
 * @author nagell2008
 * 
 */
public class DoDForeignExpenseRestrictionTest extends TrapTestFramework
{

    ReimbursementApp testApp = new ReimbursementApp();

    Map<String, String> noForeignExpenses = null;
    Map<String, String> foreignExpenses = null;

    /**
     * Setup code for various forms.
     * 
     * @throws TRAPException - Something bad happened during setup
     */
    public DoDForeignExpenseRestrictionTest() throws TRAPException
    {

        super.setup(SampleDataEnum.SHORT_INTL);

        foreignExpenses = getLoadableForm(SampleDataEnum.SHORT_INTL);

        TestUserGrantDB.UserGrantBuilder ugBuilder = new TestUserGrantDB.UserGrantBuilder();
        ugBuilder.setAccount("8675309");
        ugBuilder.setAdmin("Ethan");
        ugBuilder.addAuthorizedPayee("linc001");
        userGrantDB.addUserGrantInfo(ugBuilder);

        TestGrantDB.GrantBuilder grantBuilder = new TestGrantDB.GrantBuilder();
        grantBuilder.setAccount("8675309");
        grantBuilder.setAcctype("sponsored");
        grantBuilder.setBalance(34000.00);
        grantBuilder.setFunder("DoD");
        grantBuilder.setOrgType("government");
        grantDB.addGrant(grantBuilder);

        noForeignExpenses = getLoadableForm(SampleDataEnum.SHORT_INTL);
        noForeignExpenses.put("DAY1_LODGING_CITY", "des moines");
        noForeignExpenses.put("DAY1_LODGING_STATE", "ia");
        noForeignExpenses.put("DAY1_LODGING_COUNTRY", "USA");
        noForeignExpenses.put("DAY1_LODGING_CURRENCY", "USD");

        testFormData.remove("DAY1_LODGING_CITY");
        testFormData.remove("DAY1_LODGING_COUNTRY");
        testFormData.remove("DAY1_LODGING_AMOUNT");
        testFormData.remove("DAY1_LODGING_CURRENCY");

    }

    /**
     * Expected exception.
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Checks to see that a foreign travel expense is not refunded under a DoD grant.
     * 
     * @throws TRAPException - Test passes on when an exception is thrown
     */
    @Test
    public void foreignExpenseTravel() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);

        testFormData.put("TRANSPORTATION1_DATE", "20121125");
        testFormData.put("TRANSPORTATION1_TYPE", "AIR");
        testFormData.put("TRANSPORTATION1_CARRIER", "American");
        testFormData.put("TRANSPORTATION1_AMOUNT", "725.50");
        testFormData.put("TRANSPORTATION1_CURRENCY", "BRL");

        testFormData.put("NUM_TRANSPORTATION", "1");

        testFormData.put("GRANT1_ACCOUNT", "8675309");
        testFormData.put("GRANT1_PERCENT", "100");

        saveAndSubmitTestForm();
    }

    /**
     * Checks that a foreign incidental expenses is not refunded.
     * 
     * @throws TRAPException - Test passes when exception is thrown
     */
    @Test
    public void foreignExpenseIncidental() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);

        testFormData.put("DAY1_INCIDENTAL_COUNTRY", "Brasil");
        testFormData.put("DAY1_INCIDENTAL_JUSTIFICATION", "I dislike...unicorns");
        testFormData.put("DAY1_INCIDENTAL_AMOUNT", "5.00");
        testFormData.put("DAY1_INCIDENTAL_CURRENCY", "BRL");

        saveAndSubmitTestForm();
    }

    /**
     * Checks that a foreign other expense is not refunded.
     * 
     * @throws TRAPException - Test passes when exception is thrown
     */
    @Test
    public void foreignExpenseOther() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);

        testFormData.put("OTHER1_DATE", "20121125");
        testFormData.put("OTHER1_JUSTIFICATION", "Conference Registration");
        testFormData.put("OTHER1_AMOUNT", "450");
        testFormData.put("OTHER1_CURRENCY", "BRL");

        testFormData.put("NUM_OTHER_EXPENSES", "1");

        testFormData.put("GRANT1_ACCOUNT", "8675309");
        testFormData.put("GRANT1_PERCENT", "100");

        saveAndSubmitTestForm();

    }

    /**
     * A foreign expense with an unknown city/country.
     * 
     * @throws TRAPException - Test passes when exception is thrown
     */
    @Test
    public void foreignExpensesUnknownCity() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);

        testFormData.put("DAY1_LODGING_CITY", "Blah");
        testFormData.put("DAY1_LODGING_COUNTRY", "Blahistan");
        testFormData.put("DAY1_LODGING_AMOUNT", "156.10");
        testFormData.put("DAY1_LODGING_CURRENCY", "BRL");

        saveAndSubmitTestForm();
    }

    /**
     * Tests a meal expenses with an unknown city/country.
     * 
     * @throws TRAPException - Test passes when exception is thrown
     */
    @Test
    public void foreignExpenseMealUnknownCity() throws TRAPException
    {
        exception.expect(FormProcessorException.class);

        testFormData.put("DAY1_BREAKFAST_CITY", "Blah");
        testFormData.put("DAY1_BREAKFAST_COUNTRY", "Blahistan");

        saveAndSubmitTestForm();
    }

    /**
     * Tests that a DoD grant can fund non-foreign expenses.
     */
    @Test
    public void noForeignExpenses()
    {
        try
        {
            setUser("linc001");
        }
        catch (TRAPException e1)
        {
            e1.printStackTrace();
            Assert.fail("Failed to set user: " + e1.getMessage());
        }
        int id = -1;

        try
        {
            id = this.saveFormData(noForeignExpenses, "a test form");
        }
        catch (TRAPException e)
        {
            e.printStackTrace();
            Assert.fail("Failed to save form: " + e.getMessage());
        }

        try
        {
            submitFormData(id);
        }
        catch (TRAPException e)
        {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

    }

    /**
     * Checks that a DoD grant will not fund foreign expenses.
     */
    @Test
    public void foreignExpenses()
    {
        try
        {
            setUser("linc001");
        }
        catch (TRAPException e1)
        {
            e1.printStackTrace();
            Assert.fail("Failed to set user: " + e1.getMessage());
        }
        int id = -1;

        try
        {
            id = this.saveFormData(foreignExpenses, "a test form");
        }
        catch (TRAPException e)
        {
            e.printStackTrace();
            Assert.fail("Failed to save form: " + e.getMessage());
        }

        try
        {
            submitFormData(id);
        }
        catch (TRAPException e)
        {
            e.printStackTrace();
            Assert.assertEquals(
                    "Foreign lodging expense cannot be funded under DoD grants. Not enough $ in non-DoD grants",
                    e.getMessage());
        }

    }
}
