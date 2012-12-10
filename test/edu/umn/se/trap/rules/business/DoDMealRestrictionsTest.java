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
