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

import junit.framework.Assert;

import org.junit.Test;

import edu.umn.se.trap.data.IncidentalExpense;
import edu.umn.se.trap.data.OtherExpense;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.BusinessLogicException;

/**
 * @author planeman
 * 
 */
public class FamilyMemberExpensesTest
{
    IncidentalExpense nonFamilyIncidental;
    IncidentalExpense nonFamilyIncidental2;
    IncidentalExpense familyIncidental;
    OtherExpense nonFamilyOther;
    OtherExpense familyOther;

    ReimbursementApp testApp;

    public FamilyMemberExpensesTest()
    {
        nonFamilyIncidental = new IncidentalExpense();
        nonFamilyIncidental.setExpenseJustification("Tipped the pretty waitress");

        nonFamilyIncidental2 = new IncidentalExpense();
        nonFamilyIncidental2.setExpenseJustification("Tipped person at the restaurant");

        familyIncidental = new IncidentalExpense();
        familyIncidental.setExpenseJustification("Bought a doorag for my brother");

        nonFamilyOther = new OtherExpense();
        nonFamilyOther.setExpenseJustification("Bought myself a maserati");

        familyOther = new OtherExpense();
        familyOther.setExpenseJustification("Bought my sister a maserati");

        testApp = new ReimbursementApp();
        testApp.setNumDays(4); // So that we can add the incidentals
        // testApp.addIncidentalExpense(nonFamilyIncidental, 1);
        // testApp.addIncidentalExpense(familyIncidental, 2);
        // testApp.addOtherExpense(nonFamilyOther);
        // testApp.addOtherExpense(familyOther);
    }

    @Test
    public void nonFamilyIncidentalTest() throws Exception
    {
        FamilyMemberExpensesNotAllowed testRule = new FamilyMemberExpensesNotAllowed();

        try
        {
            testApp.addIncidentalExpense(nonFamilyIncidental, 1);
            testRule.checkRule(testApp);
        }
        catch (BusinessLogicException ble)
        {
            ble.printStackTrace();
            Assert.fail("non family incidental should have been accepted");
        }
    }

    @Test
    public void familyIncidentalTest() throws Exception
    {
        FamilyMemberExpensesNotAllowed testRule = new FamilyMemberExpensesNotAllowed();

        try
        {
            testApp.addIncidentalExpense(familyIncidental, 1);
            testRule.checkRule(testApp);
            Assert.fail("family incidental should not have been accepted");
        }
        catch (BusinessLogicException ble)
        {
            ; // Good
        }
    }

    @Test
    public void nonFamilyOtherTest() throws Exception
    {
        FamilyMemberExpensesNotAllowed testRule = new FamilyMemberExpensesNotAllowed();

        try
        {
            testApp.addOtherExpense(nonFamilyOther);
            testRule.checkRule(testApp);
        }
        catch (BusinessLogicException ble)
        {
            ble.printStackTrace();
            Assert.fail("non family other should have been accepted");
        }
    }

    @Test
    public void familyOtherTest() throws Exception
    {
        FamilyMemberExpensesNotAllowed testRule = new FamilyMemberExpensesNotAllowed();

        try
        {
            testApp.addOtherExpense(familyOther);
            testRule.checkRule(testApp);
            Assert.fail("family other should not have been accepted");
        }
        catch (BusinessLogicException ble)
        {
            ; // Good
        }
    }

    @Test
    public void goodIncidentalBadOther() throws Exception
    {
        FamilyMemberExpensesNotAllowed testRule = new FamilyMemberExpensesNotAllowed();

        // Good incidental, Bad other
        try
        {
            testApp.addOtherExpense(familyOther);
            testApp.addIncidentalExpense(nonFamilyIncidental, 1);
            testRule.checkRule(testApp);
            Assert.fail("Family other expense allowed");
        }
        catch (BusinessLogicException ble)
        {
            ; // Good
        }
    }

    @Test
    public void badIncidentalGoodOther() throws Exception
    {
        FamilyMemberExpensesNotAllowed testRule = new FamilyMemberExpensesNotAllowed();

        // Bad incidental, Good other
        try
        {
            testApp.addOtherExpense(nonFamilyOther);
            testApp.addIncidentalExpense(familyIncidental, 1);
            testRule.checkRule(testApp);
            Assert.fail("Family incidental expense allowed");
        }
        catch (BusinessLogicException ble)
        {
            ; // Good
        }
    }

    @Test
    public void badIncidentalBadOther() throws Exception
    {
        FamilyMemberExpensesNotAllowed testRule = new FamilyMemberExpensesNotAllowed();

        // Bad both
        try
        {
            testApp.addOtherExpense(familyOther);
            testApp.addIncidentalExpense(familyIncidental, 1);
            testRule.checkRule(testApp);
            Assert.fail("Family incidental expense allowed");
        }
        catch (BusinessLogicException ble)
        {
            ; // Good
        }
    }

    @Test
    public void goodIncidentalGoodOther() throws Exception
    {
        FamilyMemberExpensesNotAllowed testRule = new FamilyMemberExpensesNotAllowed();

        // Good both
        try
        {
            testApp.addOtherExpense(nonFamilyOther);
            testApp.addIncidentalExpense(nonFamilyIncidental, 1);
            testRule.checkRule(testApp);
        }
        catch (BusinessLogicException ble)
        {
            Assert.fail("Both good expenses should have been accepted");
        }
    }

    @Test
    public void testOnlyMatchAtWordBoundary() throws Exception
    {
        FamilyMemberExpensesNotAllowed testRule = new FamilyMemberExpensesNotAllowed();

        try
        {
            testApp.addIncidentalExpense(nonFamilyIncidental2, 1);
            testRule.checkRule(testApp);
        }
        catch (BusinessLogicException ble)
        {
            Assert.fail("Non-Family incidental with non word boundary match was rejected");
        }
    }
}
