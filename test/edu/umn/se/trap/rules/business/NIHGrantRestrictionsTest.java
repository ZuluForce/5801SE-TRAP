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
// NIHGrantRestrictionsTest.java
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
 * Class checks that the NIH restrictions are met. Requirement 3.h
 * 
 * @author nagell2008
 */
public class NIHGrantRestrictionsTest extends TrapTestFramework
{

    ReimbursementApp testApp = new ReimbursementApp();

    Map<String, String> foodOnlyNIHGrant = null;
    Map<String, String> foodNIHAndNonNIHGrant = null;

    Map<String, String> allowedTravelNIHGrant = null;
    Map<String, String> unallowedTravelNIHGrant = null;

    /**
     * Setup code for later tests.
     */
    public NIHGrantRestrictionsTest()
    {
        foodOnlyNIHGrant = getLoadableForm(SampleDataEnum.DOMESTIC1);
        foodOnlyNIHGrant.put("GRANT1_ACCOUNT", "654321");

        TestUserGrantDB.UserGrantBuilder ugBuilder = new TestUserGrantDB.UserGrantBuilder();
        ugBuilder.setAccount("654321");
        ugBuilder.setAdmin("Ethan");
        ugBuilder.addAuthorizedPayee("linc001");
        userGrantDB.addUserGrantInfo(ugBuilder);

        TestGrantDB.GrantBuilder grantBuilder = new TestGrantDB.GrantBuilder();
        grantBuilder.setAccount("654321");
        grantBuilder.setAcctype("sponsored");
        grantBuilder.setBalance(34000.00);
        grantBuilder.setFunder("NIH");
        grantBuilder.setOrgType("government");
        grantDB.addGrant(grantBuilder);

        foodNIHAndNonNIHGrant = getLoadableForm(SampleDataEnum.DOMESTIC1);
        foodNIHAndNonNIHGrant.put("GRANT2_ACCOUNT", "654321");
        foodNIHAndNonNIHGrant.put("GRANT2_PERCENT", "20");
        foodNIHAndNonNIHGrant.put("GRANT1_PERCENT", "80");
        foodNIHAndNonNIHGrant.put("NUM_GRANTS", "2");

        allowedTravelNIHGrant = getLoadableForm(SampleDataEnum.INTERNATIONAL1);
        allowedTravelNIHGrant.put("GRANT2_ACCOUNT", "654321");
        allowedTravelNIHGrant.put("GRANT2_PERCENT", "20");
        allowedTravelNIHGrant.put("GRANT1_PERCENT", "80");
        allowedTravelNIHGrant.put("NUM_GRANTS", "2");
        allowedTravelNIHGrant.put("USER_NAME", "linc001");

        unallowedTravelNIHGrant = getLoadableForm(SampleDataEnum.INTERNATIONAL1);
        unallowedTravelNIHGrant.put("GRANT1_ACCOUNT", "654321");
        unallowedTravelNIHGrant.put("GRANT1_PERCENT", "100");
        unallowedTravelNIHGrant.put("NUM_GRANTS", "1");
        unallowedTravelNIHGrant.remove("GRANT2_ACCOUNT");
        unallowedTravelNIHGrant.remove("GRANT2_PERCENT");
        unallowedTravelNIHGrant.put("USER_NAME", "linc001");

        unallowedTravelNIHGrant.remove("DAY1_BREAKFAST_CITY");
        unallowedTravelNIHGrant.remove("DAY1_BREAKFAST_STATE");
        unallowedTravelNIHGrant.remove("DAY1_BREAKFAST_COUNTRY");
        unallowedTravelNIHGrant.remove("DAY1_LUNCH_CITY");
        unallowedTravelNIHGrant.remove("DAY1_LUNCH_STATE");
        unallowedTravelNIHGrant.remove("DAY1_LUNCH_COUNTRY");
        unallowedTravelNIHGrant.remove("DAY2_BREAKFAST_CITY");
        unallowedTravelNIHGrant.remove("DAY2_BREAKFAST_COUNTRY");
        unallowedTravelNIHGrant.remove("DAY2_LUNCH_CITY");
        unallowedTravelNIHGrant.remove("DAY2_LUNCH_COUNTRY");
        unallowedTravelNIHGrant.remove("DAY2_DINNER_COUNTRY");
        unallowedTravelNIHGrant.remove("DAY3_LUNCH_CITY");
        unallowedTravelNIHGrant.remove("DAY3_LUNCH_COUNTRY");
        unallowedTravelNIHGrant.remove("DAY3_DINNER_CITY");
        unallowedTravelNIHGrant.remove("DAY3_DINNER_COUNTRY");
        unallowedTravelNIHGrant.remove("DAY4_BREAKFAST_CITY");
        unallowedTravelNIHGrant.remove("DAY4_BREAKFAST_COUNTRY");
        unallowedTravelNIHGrant.remove("DAY4_LUNCH_CITY");
        unallowedTravelNIHGrant.remove("DAY4_LUNCH_COUNTRY");
        unallowedTravelNIHGrant.remove("DAY4_DINNER_CITY");
        unallowedTravelNIHGrant.remove("DAY4_DINNER_COUNTRY");
        unallowedTravelNIHGrant.remove("DAY5_LUNCH_CITY");
        unallowedTravelNIHGrant.remove("DAY5_LUNCH_COUNTRY");
        unallowedTravelNIHGrant.remove("DAY5_DINNER_CITY");
        unallowedTravelNIHGrant.remove("DAY5_DINNER_COUNTRY");
        unallowedTravelNIHGrant.remove("DAY6_BREAKFAST_CITY");
        unallowedTravelNIHGrant.remove("DAY6_BREAKFAST_COUNTRY");
    }

    /**
     * Tests that a NIH grant will not fund meal expenses
     */
    @Test
    public void foodOnlyNIHGrant()
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
            id = this.saveFormData(foodOnlyNIHGrant, "a test form");
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
            Assert.assertEquals("Unable to claim meal expenses under NIH grants.", e.getMessage());
        }
    }

    /**
     * Checks that meal expenses are reimbursed when a NIH grant and non-NIH grant are present
     */
    @Test
    public void foodNIHAndNonNIHGrant()
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
            id = this.saveFormData(foodNIHAndNonNIHGrant, "a test form");
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

        Assert.assertEquals(true, true);
    }

    /**
     * Checks that only air and public transportation expenses are allowed under a NIH grant
     */
    @Test
    public void allowedTravelNIHGrant()
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
            id = this.saveFormData(allowedTravelNIHGrant, "a test form");
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

        Assert.assertEquals(true, true);

    }

    /**
     * Checks that non-air or non-public transportation costs are not reimbursed.
     */
    @Test
    public void unallowedTravelNIHGrant()
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
            id = this.saveFormData(unallowedTravelNIHGrant, "a test form");
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
            Assert.assertEquals("Transportation expense of $25.0 cannont be funded with $0.0",
                    e.getMessage());
        }
    }

}
