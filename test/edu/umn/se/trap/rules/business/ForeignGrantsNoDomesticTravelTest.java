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
// ForeignGrantsNoDomesticTravelTest.java
package edu.umn.se.trap.rules.business;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TestGrantDB;
import edu.umn.se.test.frame.TestUserGrantDB;
import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * This class tests that foreign grants do not pay for domestic things, as per requirement 3.g
 * 
 * @author nagell2008
 */
public class ForeignGrantsNoDomesticTravelTest extends TrapTestFramework
{

    String incidentalJustField;
    String incidentalAmntField;

    /**
     * Some form setup
     * 
     * @throws TRAPException - Something bad happened during setup
     */
    public ForeignGrantsNoDomesticTravelTest() throws TRAPException
    {
        super.setup(SampleDataEnum.SHORT_INTL);

        TestUserGrantDB.UserGrantBuilder ugBuilder = new TestUserGrantDB.UserGrantBuilder();
        ugBuilder.setAccount("8675309");
        ugBuilder.setAdmin("Ethan");
        ugBuilder.addAuthorizedPayee("linc001");
        userGrantDB.addUserGrantInfo(ugBuilder);

        TestGrantDB.GrantBuilder grantBuilder = new TestGrantDB.GrantBuilder();
        grantBuilder.setAccount("8675309");
        grantBuilder.setAcctype("sponsored");
        grantBuilder.setBalance(34000.00);
        grantBuilder.setFunder(null);
        grantBuilder.setOrgType("foreign");
        grantDB.addGrant(grantBuilder);

        testFormData.put("GRANT1_ACCOUNT", "8675309");
        testFormData.put("GRANT1_PERCENT", "100");

        testFormData.remove("DAY1_LODGING_CITY");
        testFormData.remove("DAY1_LODGING_COUNTRY");
        testFormData.remove("DAY1_LODGING_AMOUNT");
        testFormData.remove("DAY1_LODGING_CURRENCY");
    }

    /**
     * Rule for expected exception
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Checks that a foreign grant cannot fund domestic travel
     * 
     * @throws TRAPException - Test passes when exception thrown
     */
    @Test
    public void foreignGrantTravelExpenses() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);

        testFormData.put("TRANSPORTATION1_DATE", "20121001");
        testFormData.put("TRANSPORTATION1_TYPE", "AIR");
        testFormData.put("TRANSPORTATION1_CARRIER", "American");
        testFormData.put("TRANSPORTATION1_AMOUNT", "725.50");
        testFormData.put("TRANSPORTATION1_CURRENCY", "USD");

        testFormData.put("NUM_TRANSPORTATION", "1");

        saveAndSubmitTestForm();
    }

    /**
     * Checks that a foreign grant cannot fund a domestic incidental expense.
     * 
     * @throws TRAPException - Test passes on exception
     */
    @Test
    public void foreignGrantIncidentalExpenses() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);

        testFormData.put("DAY1_INCIDENTAL_CITY", "Minneapolis");
        testFormData.put("DAY1_INCIDENTAL_STATE", "MN");
        testFormData.put("DAY1_INCIDENTAL_COUNTRY", "USA");
        testFormData.put("DAY1_INCIDENTAL_JUSTIFICATION", "I dislike...unicorns");
        testFormData.put("DAY1_INCIDENTAL_AMOUNT", "5.00");
        testFormData.put("DAY1_INCIDENTAL_CURRENCY", "USD");

        saveAndSubmitTestForm();
    }

    /**
     * Tests that a foreign incidental passes
     * 
     * @throws TRAPException - Test fails on exception
     */
    @Test
    public void foreignGrantForeignIncidentalExpenses() throws TRAPException
    {
        // exception.expect(BusinessLogicException.class);

        testFormData.put("DAY1_INCIDENTAL_COUNTRY", "Brazil");
        testFormData.put("DAY1_INCIDENTAL_JUSTIFICATION", "I dislike...unicorns");
        testFormData.put("DAY1_INCIDENTAL_AMOUNT", "5.00");
        testFormData.put("DAY1_INCIDENTAL_CURRENCY", "BRL");

        saveAndSubmitTestForm();
    }

    /**
     * Checks that a foreign grant cannot fund domestic lodging
     * 
     * @throws TRAPException - Test passes on exception
     */
    @Test
    public void foreignGrantLodgingExpenses() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);

        testFormData.put("DAY1_LODGING_CITY", "Lawrence");
        testFormData.put("DAY1_LODGING_STATE", "KS");
        testFormData.put("DAY1_LODGING_COUNTRY", "USA");
        testFormData.put("DAY1_LODGING_AMOUNT", "86.31");
        testFormData.put("DAY1_LODGING_CURRENCY", "USD");

        saveAndSubmitTestForm();
    }

    /**
     * Checks that foreign transportation costs are refunded under a foreign grant
     * 
     * @throws TRAPException - Test fails on exception
     */
    @Test
    public void foreignGrantCarRentalExpenses() throws TRAPException
    {
        // exception.expect(BusinessLogicException.class);

        testFormData.put("TRANSPORTATION1_DATE", "20121125");
        testFormData.put("TRANSPORTATION1_TYPE", "CAR");
        testFormData.put("TRANSPORTATION1_RENTAL", "YES");
        testFormData.put("TRANSPORTATION1_CARRIER", "National Traveler");
        testFormData.put("TRANSPORTATION1_AMOUNT", "125.00");
        testFormData.put("TRANSPORTATION1_CURRENCY", "BRL");

        testFormData.put("NUM_TRANSPORTATION", "1");

        saveAndSubmitTestForm();
    }

    /**
     * Checks that a foreign grant cannot fund domestic transportation mileage expenses
     * 
     * @throws TRAPException - Test passes on exception
     */
    @Test
    public void foreignGrantTransportationMileageExpenses() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);

        testFormData.put("TRANSPORTATION1_DATE", "20121125");
        testFormData.put("TRANSPORTATION1_TYPE", "GAS");
        testFormData.put("TRANSPORTATION1_AMOUNT", "22.50");
        testFormData.put("TRANSPORTATION1_CURRENCY", "USD");

        testFormData.put("NUM_TRANSPORTATION", "1");

        saveAndSubmitTestForm();

    }

    /**
     * Checks that a foreign grant cannot fund a personal car domestically. Does a lodging check to
     * get to the car check.
     * 
     * @throws TRAPException - Test passes on exception
     */
    @Test
    public void foreignGrantPersonalCarExpenses() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);

        testFormData.put("TRANSPORTATION1_DATE", "20121125");
        testFormData.put("TRANSPORTATION1_TYPE", "CAR");
        testFormData.put("TRANSPORTATION1_RENTAL", "NO");
        testFormData.put("TRANSPORTATION1_MILES_TRAVELED", "50");
        testFormData.put("TRANSPORTATION1_AMOUNT", "0.00");
        testFormData.put("TRANSPORTATION1_CURRENCY", "USD");

        testFormData.put("DAY1_LODGING_CITY", "Lawrence");
        testFormData.put("DAY1_LODGING_STATE", "KS");
        testFormData.put("DAY1_LODGING_COUNTRY", "USA");
        testFormData.put("DAY1_LODGING_AMOUNT", "86.31");
        testFormData.put("DAY1_LODGING_CURRENCY", "USD");

        testFormData.put("NUM_TRANSPORTATION", "1");

        saveAndSubmitTestForm();

    }

    /**
     * Checks that a non-sponsored grant can fund a domestic expense with a foreign grant present
     * 
     * @throws TRAPException - Test fails on exception
     */
    @Test
    public void foreignGrantNSTransportationExpenseCovered() throws TRAPException
    {
        testFormData.put("GRANT2_ACCOUNT", "umn_super_pac");
        testFormData.put("GRANT2_PERCENT", "50");
        testFormData.put("GRANT1_PERCENT", "50");
        testFormData.put("NUM_GRANTS", "2");

        testFormData.put("TRANSPORTATION1_DATE", "20121125");
        testFormData.put("TRANSPORTATION1_TYPE", "GAS");
        testFormData.put("TRANSPORTATION1_AMOUNT", "22.50");
        testFormData.put("TRANSPORTATION1_CURRENCY", "USD");

        testFormData.put("NUM_TRANSPORTATION", "1");

        saveAndSubmitTestForm();
    }

    /**
     * Checks that a foreign grant cannot fund a domestic meal
     * 
     * @throws TRAPException - Test passes on exception
     */
    @Test
    public void foreignGrantMealExpense() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);

        testFormData.put("DAY1_LUNCH_CITY", "Des Moines");
        testFormData.put("DAY1_LUNCH_STATE", "IA");
        testFormData.put("DAY1_LUNCH_COUNTRY", "USA");

        saveAndSubmitTestForm();
    }

    /**
     * Checks that a domestic other expense is not funded under a foreign grant
     * 
     * @throws TRAPException - Test passes on exception
     */
    @Test
    public void foreignGrantOtherExpense() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);

        testFormData.put("OTHER1_DATE", "20121103");
        testFormData.put("OTHER1_JUSTIFICATION", "Conference Registration");
        testFormData.put("OTHER1_AMOUNT", "450");
        testFormData.put("OTHER1_CURRENCY", "USD");

        testFormData.put("NUM_OTHER_EXPENSES", "1");

        saveAndSubmitTestForm();

    }

    /**
     * Simple test of a regular, default form
     * 
     * @throws TRAPException - Test fails on exception
     */
    @Test
    public void foreignGrantSubmit() throws TRAPException
    {
        saveAndSubmitTestForm();
    }

    /**
     * Checks that two grants do not conflict
     * 
     * @throws TRAPException - Test fails on exception
     */
    @Test
    public void foreignGrantWithNonSponsoredGrant() throws TRAPException
    {
        testFormData.put("GRANT2_ACCOUNT", "umn_super_pac");
        testFormData.put("GRANT2_PERCENT", "50");
        testFormData.put("GRANT1_PERCENT", "50");
        testFormData.put("NUM_GRANTS", "2");

        saveAndSubmitTestForm();

    }

    /**
     * Checks that a non-sponsored grant can cover an other expense when a foreign grant is present.
     * 
     * @throws TRAPException - Test fails on exception
     */
    @Test
    public void foreignGrantNSCoveredOtherExpense() throws TRAPException
    {
        testFormData.put("GRANT2_ACCOUNT", "umn_super_pac");
        testFormData.put("GRANT2_PERCENT", "50");
        testFormData.put("GRANT1_PERCENT", "50");
        testFormData.put("NUM_GRANTS", "2");

        testFormData.put("OTHER1_DATE", "20121103");
        testFormData.put("OTHER1_JUSTIFICATION", "Conference Registration");
        testFormData.put("OTHER1_AMOUNT", "450");
        testFormData.put("OTHER1_CURRENCY", "USD");

        testFormData.put("NUM_OTHER_EXPENSES", "1");

        saveAndSubmitTestForm();

    }

    /**
     * Sample test submit
     * 
     * @throws TRAPException - Test fails on exception
     */
    @Test
    public void noForeignGrant() throws TRAPException
    {
        testFormData.put("GRANT1_ACCOUNT", "umn_super_pac");
        testFormData.put("GRANT1_PERCENT", "100");

        saveAndSubmitTestForm();
    }

    /**
     * Checks that a foreign grant does not refund an incidental expense with a non-sponsored grant
     * present
     * 
     * @throws TRAPException - Test fails on exception
     */
    @Test
    public void foreignGrantIncidentalDomesticNS() throws TRAPException
    {
        // exception.expect(BusinessLogicException.class);

        testFormData.put("DAY1_INCIDENTAL_CITY", "Minneapolis");
        testFormData.put("DAY1_INCIDENTAL_STATE", "MN");
        testFormData.put("DAY1_INCIDENTAL_COUNTRY", "USA");
        testFormData.put("DAY1_INCIDENTAL_JUSTIFICATION",
                "Tipped cart person at airport to drive me to gate");
        testFormData.put("DAY1_INCIDENTAL_AMOUNT", "5.00");
        testFormData.put("DAY1_INCIDENTAL_CURRENCY", "USD");

        testFormData.put("GRANT2_ACCOUNT", "umn_super_pac");
        testFormData.put("GRANT2_PERCENT", "50");
        testFormData.put("GRANT1_PERCENT", "50");
        testFormData.put("NUM_GRANTS", "2");

        saveAndSubmitTestForm();

    }

    /**
     * Checks that a foreign grant will not refund a domestic incidental expense
     * 
     * @throws TRAPException - Test passes on exception
     */
    @Test
    public void foreignGrantIncidentalDomestic() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);

        testFormData.put("DAY1_INCIDENTAL_CITY", "Minneapolis");
        testFormData.put("DAY1_INCIDENTAL_STATE", "MN");
        testFormData.put("DAY1_INCIDENTAL_COUNTRY", "USA");
        testFormData.put("DAY1_INCIDENTAL_JUSTIFICATION",
                "Tipped cart person at airport to drive me to gate");
        testFormData.put("DAY1_INCIDENTAL_AMOUNT", "5.00");
        testFormData.put("DAY1_INCIDENTAL_CURRENCY", "USD");

        saveAndSubmitTestForm();

    }

}
