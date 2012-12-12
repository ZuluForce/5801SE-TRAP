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
// InternetOnlyUnderNonSponsoredGrants.java
package edu.umn.se.trap.rules.business;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * Checks that a user only appropriately claims internet expenses. Requirement 3.e
 * 
 * @author nagell2008
 */
public class InternetOnlyUnderNonSponsoredGrant extends TrapTestFramework
{

    ReimbursementApp testApp = new ReimbursementApp();

    Map<String, String> goodInternetOther = null;
    Map<String, String> badInternetOther = null;

    Map<String, String> goodInternetIncidental = null;
    Map<String, String> badInternetIncidental = null;

    /**
     * Setup code for tests
     * 
     * @throws TRAPException - Something bad happened
     */
    public InternetOnlyUnderNonSponsoredGrant() throws TRAPException
    {

        super.setup(SampleDataEnum.SHORT_INTL);

        goodInternetOther = getLoadableForm(SampleDataEnum.DOMESTIC1);
        goodInternetOther.put("OTHER3_DATE", "20121003");
        goodInternetOther.put("OTHER3_JUSTIFICATION", "Purchased some wifi");
        goodInternetOther.put("OTHER3_AMOUNT", "23.00");
        goodInternetOther.put("OTHER3_CURRENCY", "USD");

        badInternetOther = getLoadableForm(SampleDataEnum.DOMESTIC1);
        badInternetOther.put("OTHER3_DATE", "20121003");
        badInternetOther.put("OTHER3_JUSTIFICATION", "Purchased some wifi");
        badInternetOther.put("OTHER3_AMOUNT", "23.00");
        badInternetOther.put("OTHER3_CURRENCY", "USD");

        goodInternetIncidental = getLoadableForm(SampleDataEnum.DOMESTIC1);
        goodInternetIncidental.put("DAY1_INCIDENTAL_CITY", "Minneapolis");
        goodInternetIncidental.put("DAY1_INCIDENTAL_STATE", "MN");
        goodInternetIncidental.put("DAY1_INCIDENTAL_COUNTRY", "USA");
        goodInternetIncidental.put("DAY1_INCIDENTAL_JUSTIFICATION", "Purchased some wifi");
        goodInternetIncidental.put("DAY1_INCIDENTAL_AMOUNT", "4.00");
        goodInternetIncidental.put("DAY1_INCIDENTAL_CURRENCY", "USD");

        badInternetIncidental = getLoadableForm(SampleDataEnum.DOMESTIC1);
        badInternetIncidental.put("DAY1_INCIDENTAL_CITY", "Minneapolis");
        badInternetIncidental.put("DAY1_INCIDENTAL_STATE", "MN");
        badInternetIncidental.put("DAY1_INCIDENTAL_COUNTRY", "USA");
        badInternetIncidental.put("DAY1_INCIDENTAL_JUSTIFICATION", "Purchased some wifi");
        badInternetIncidental.put("DAY1_INCIDENTAL_AMOUNT", "4.00");
        badInternetIncidental.put("DAY1_INCIDENTAL_CURRENCY", "USD");

    }

    /**
     * Expected exception
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Checks that if a non-sponsored grant is present, but the internet cost is too high to be
     * refunded.
     * 
     * @throws TRAPException - Test passes on exception
     */
    @Test
    public void testGoodInterNetOtherExpenseReallyExpensive() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);

        testFormData.put("GRANT1_ACCOUNT", "umn_super_pac");
        testFormData.put("GRANT1_PERCENT", "100");

        testFormData.put("OTHER1_DATE", "20121103");
        testFormData.put("OTHER1_JUSTIFICATION", "Bought some wifi");
        testFormData.put("OTHER1_AMOUNT", "450000.00");
        testFormData.put("OTHER1_CURRENCY", "USD");

        testFormData.put("NUM_OTHER_EXPENSES", "1");

        saveAndSubmitTestForm();
    }

    /**
     * Checks that a valid internet expense will not be refunded if it costs too much.
     * 
     * @throws TRAPException - Test passes on exception
     */
    @Test
    public void testGoodInterNetIncidentalExpenseReallyExpensive() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);

        testFormData.put("GRANT1_ACCOUNT", "umn_super_pac");
        testFormData.put("GRANT1_PERCENT", "100");

        testFormData.put("DAY1_INCIDENTAL_CITY", "Minneapolis");
        testFormData.put("DAY1_INCIDENTAL_STATE", "MN");
        testFormData.put("DAY1_INCIDENTAL_COUNTRY", "USA");
        testFormData.put("DAY1_INCIDENTAL_JUSTIFICATION", "I love wifi");
        testFormData.put("DAY1_INCIDENTAL_AMOUNT", "5.00");
        testFormData.put("DAY1_INCIDENTAL_CURRENCY", "USD");

        saveAndSubmitTestForm();

    }

    /**
     * Checks that a user cannot claim an internet expense without a non-sponsored grant.
     */
    @Test
    public void testBadInternetOtherExpense()
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
            id = this.saveFormData(badInternetOther, "a test form");
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
            Assert.assertEquals(true, true);
        }

    }

    /**
     * Checks that a user can claim an internet expense with a non-sponsored grant present.
     */
    @Test
    public void testGoodInternetOtherExpense()
    {
        goodInternetOther.put("NUM_GRANTS", "2");
        goodInternetOther.put("GRANT2_ACCOUNT", "99999");
        goodInternetOther.put("GRANT2_PERCENT", "23");
        goodInternetOther.put("GRANT1_PERCENT", "77");

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
            id = this.saveFormData(goodInternetOther, "a test form");
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
            Assert.fail("Failed to submit form: " + e.getMessage());
        }

        Assert.assertEquals(true, true);

    }

    /**
     * Checks that an incidental internet expense cannot be funded with no non-sponsored grants
     * available.
     */
    @Test
    public void testBadInternetIncidentalExpense()
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
            id = this.saveFormData(badInternetIncidental, "a test form");
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
            Assert.assertEquals(e.getMessage(), true, true);
        }

    }

    /**
     * Checks that a good internet incidental expense is refunded, non-sponsored grant present.
     */
    @Test
    public void testGoodInternetIncidentalExpense()
    {

        goodInternetIncidental.put("NUM_GRANTS", "2");
        goodInternetIncidental.put("GRANT2_ACCOUNT", "99999");
        goodInternetIncidental.put("GRANT2_PERCENT", "23");
        goodInternetIncidental.put("GRANT1_PERCENT", "77");

        testApp.setNumDays(5);

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
            id = this.saveFormData(goodInternetIncidental, "a test form");
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
            Assert.fail("Failed to submit form: " + e.getMessage());
        }

        Assert.assertEquals(true, true);

    }

}
