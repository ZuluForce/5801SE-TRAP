// DoDForeignExpenseRestrictionTest.java
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
public class DoDForeignExpenseRestrictionTest extends TrapTestFramework
{

    ReimbursementApp testApp = new ReimbursementApp();

    Map<String, String> noForeignExpenses = null;
    Map<String, String> foreignExpenses = null;

    public DoDForeignExpenseRestrictionTest()
    {
        foreignExpenses = getLoadableForm(SampleDataEnum.FOREIGNEXPENSES);

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

        noForeignExpenses = getLoadableForm(SampleDataEnum.FOREIGNEXPENSES);
        noForeignExpenses.put("DAY1_LODGING_CITY", "des moines");
        noForeignExpenses.put("DAY1_LODGING_STATE", "ia");
        noForeignExpenses.put("DAY1_LODGING_COUNTRY", "USA");
        noForeignExpenses.put("DAY1_LODGING_CURRENCY", "USD");

        for (Map.Entry<String, String> entry : noForeignExpenses.entrySet())
        {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

    }

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
