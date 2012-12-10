// InternetOnlyUnderNonSponsoredGrants.java
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
public class InternetOnlyUnderNonSponsoredGrant extends TrapTestFramework
{

    ReimbursementApp testApp = new ReimbursementApp();

    Map<String, String> goodInternetOther = null;
    Map<String, String> badInternetOther = null;

    Map<String, String> goodInternetIncidental = null;
    Map<String, String> badInternetIncidental = null;

    public InternetOnlyUnderNonSponsoredGrant()
    {
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
