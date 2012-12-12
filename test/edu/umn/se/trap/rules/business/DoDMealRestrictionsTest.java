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
// DoDMealRestrictionsTest.java
package edu.umn.se.trap.rules.business;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import edu.umn.se.test.frame.TestGrantDB;
import edu.umn.se.test.frame.TestUserGrantDB;
import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * This class checks that DoD grants do not reimburse for breakfast expenses
 * 
 * @author nagell2008
 * 
 */
public class DoDMealRestrictionsTest extends TrapTestFramework
{

    ReimbursementApp testApp = new ReimbursementApp();

    Map<String, String> breakfastExpenses = null;
    Map<String, String> noBreakfastExpenses = null;

    Map<String, String> carAllowedExpenses = null;
    Map<String, String> carNotAllowedExpenses = null;

    /**
     * Setup some form information.
     */
    public DoDMealRestrictionsTest()
    {

        breakfastExpenses = getLoadableForm(SampleDataEnum.DOMESTIC1);
        breakfastExpenses.put("GRANT1_ACCOUNT", "8675309");
        breakfastExpenses.put("DAY2_BREAKFAST_CITY", "Lawrence");
        breakfastExpenses.put("DAY2_BREAKFAST_STATE", "KS");
        breakfastExpenses.put("DAY2_BREAKFAST_COUNTRY", "USA");

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

        noBreakfastExpenses = getLoadableForm(SampleDataEnum.DOMESTIC1);

    }

    /**
     * Checks that a DoD grant does not reimburse for a breakfast expense
     */
    @Test
    public void breakfastExpenses()
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
            id = this.saveFormData(breakfastExpenses, "a test form");
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
                    "Meal per diem of $7.00 is greater than $0.00 in available non-DoD grants",
                    e.getMessage());
        }

    }

    /**
     * Form without any breakfast expenses, only a DoD grant.
     */
    @Test
    public void noBreakfastExpenses()
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
            id = this.saveFormData(noBreakfastExpenses, "a test form");
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

}
