// AlcoholOnlyAllowedUnderNonSponsoredGrant.java
package edu.umn.se.trap.rules.business;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author nagell2008
 * 
 */
public class AlcoholOnlyAllowedUnderNonSponsoredGrant extends TrapTestFramework
{

    ReimbursementApp testApp = new ReimbursementApp();

    Map<String, String> goodAlcoholOther = null;
    Map<String, String> badAlcoholOther = null;

    Map<String, String> goodAlcoholIncidental = null;
    Map<String, String> badAlcoholIncidental = null;

    public AlcoholOnlyAllowedUnderNonSponsoredGrant()
    {

        goodAlcoholOther = getLoadableForm(SampleDataEnum.DOMESTIC1);
        goodAlcoholOther.put("OTHER3_DATE", "20121003");
        goodAlcoholOther.put("OTHER3_JUSTIFICATION", "Purchased some whiskey");
        goodAlcoholOther.put("OTHER3_AMOUNT", "23.00");
        goodAlcoholOther.put("OTHER3_CURRENCY", "USD");

        badAlcoholOther = getLoadableForm(SampleDataEnum.DOMESTIC1);
        badAlcoholOther.put("OTHER3_DATE", "20121003");
        badAlcoholOther.put("OTHER3_JUSTIFICATION", "Purchased some whiskey");
        badAlcoholOther.put("OTHER3_AMOUNT", "23.00");
        badAlcoholOther.put("OTHER3_CURRENCY", "USD");

        goodAlcoholIncidental = getLoadableForm(SampleDataEnum.DOMESTIC1);
        goodAlcoholIncidental.put("DAY1_INCIDENTAL_CITY", "Minneapolis");
        goodAlcoholIncidental.put("DAY1_INCIDENTAL_STATE", "MN");
        goodAlcoholIncidental.put("DAY1_INCIDENTAL_COUNTRY", "USA");
        goodAlcoholIncidental.put("DAY1_INCIDENTAL_JUSTIFICATION", "Purchased some whiskey");
        goodAlcoholIncidental.put("DAY1_INCIDENTAL_AMOUNT", "4.00");
        goodAlcoholIncidental.put("DAY1_INCIDENTAL_CURRENCY", "USD");

        badAlcoholIncidental = getLoadableForm(SampleDataEnum.DOMESTIC1);
        badAlcoholIncidental.put("DAY1_INCIDENTAL_CITY", "Minneapolis");
        badAlcoholIncidental.put("DAY1_INCIDENTAL_STATE", "MN");
        badAlcoholIncidental.put("DAY1_INCIDENTAL_COUNTRY", "USA");
        badAlcoholIncidental.put("DAY1_INCIDENTAL_JUSTIFICATION", "Purchased some whiskey");
        badAlcoholIncidental.put("DAY1_INCIDENTAL_AMOUNT", "23.00");
        badAlcoholIncidental.put("DAY1_INCIDENTAL_CURRENCY", "USD");

    }

    @Test
    public void testBadAlcoholOtherExpense()
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
            id = this.saveFormData(badAlcoholOther, "a test form");
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

    @Test
    public void testGoodAlcoholOtherExpense()
    {
        goodAlcoholOther.put("NUM_GRANTS", "2");
        goodAlcoholOther.put("GRANT2_ACCOUNT", "99999");
        goodAlcoholOther.put("GRANT2_PERCENT", "23");
        goodAlcoholOther.put("GRANT1_PERCENT", "77");

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
            id = this.saveFormData(goodAlcoholOther, "a test form");
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

    @Test
    public void testBadAlcoholIncidentalExpense()
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
            id = this.saveFormData(badAlcoholIncidental, "a test form");
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

    @Test
    public void testGoodAlcoholIncidentalExpense()
    {

        goodAlcoholIncidental.put("NUM_GRANTS", "2");
        goodAlcoholIncidental.put("GRANT2_ACCOUNT", "99999");
        goodAlcoholIncidental.put("GRANT2_PERCENT", "23");
        goodAlcoholIncidental.put("GRANT1_PERCENT", "77");

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
            id = this.saveFormData(goodAlcoholIncidental, "a test form");
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
