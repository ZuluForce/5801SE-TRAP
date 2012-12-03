// InternetOnlyUnderNonSponsoredGrants.java
package edu.umn.se.trap.rules.business;

import junit.framework.Assert;

import org.junit.Test;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.data.Grant;
import edu.umn.se.trap.data.IncidentalExpense;
import edu.umn.se.trap.data.OtherExpense;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.InputValidationException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author nagell2008
 * 
 */
public class InternetOnlyUnderNonSponsoredGrant extends TrapTestFramework
{

    OtherExpense internetOther;
    IncidentalExpense internetIncidental;

    ReimbursementApp testApp = new ReimbursementApp();

    InternetOnlyUnderNonSponsoredGrants rule = new InternetOnlyUnderNonSponsoredGrants();

    public InternetOnlyUnderNonSponsoredGrant()
    {
        internetOther = new OtherExpense();

        internetOther.setExpenseJustification("Purchased some wifi");
        internetOther.setExpenseCurrency("USD");
        internetOther.setExpenseAmount(23.00);

        internetIncidental = new IncidentalExpense();

        internetIncidental.setCity("Minneapolis");
        internetIncidental.setState("MN");
        internetIncidental.setCountry("USA");
        internetIncidental.setExpenseAmount(23.00);
        internetIncidental.setExpenseCurrency("USD");
        internetIncidental.setExpenseJustification("Purchased some wifi");

    }

    @Test
    public void testBadInternetOtherExpense()
    {
        getLoadableForm(SampleDataEnum.DOMESTIC1);

        testApp.addOtherExpense(internetOther);

        try
        {
            rule.checkRule(testApp);
            Assert.fail("Internet expense accepted when no non-sponsored grant present");
        }
        catch (TRAPException e)
        {
            // e.printStackTrace();
            // The test has passed, exception thrown when a non-sponsored grant is not present.
            Assert.assertEquals(true, true);
        }

    }

    @Test
    public void testGoodInternetOtherExpense()
    {
        getLoadableForm(SampleDataEnum.DOMESTIC1);

        Grant nonSponsored = new Grant();

        nonSponsored.setGrantAccount("99999");
        nonSponsored.setGrantPercentage(23);

        testApp.addGrant(nonSponsored);

        testApp.addOtherExpense(internetOther);

        try
        {
            rule.checkRule(testApp);
        }
        catch (TRAPException e)
        {
            e.printStackTrace();
            Assert.fail("Message: " + e.getMessage());
        }

    }

    @Test
    public void testBadAlcoholIncidentalExpense()
    {
        getLoadableForm(SampleDataEnum.DOMESTIC1);

        try
        {
            testApp.setNumDays(5);
            testApp.addIncidentalExpense(internetIncidental, 2);
        }
        catch (InputValidationException e1)
        {
            e1.printStackTrace();
            Assert.fail("Failed to add incidental expense: " + e1.getMessage());
        }

        try
        {
            rule.checkRule(testApp);
            Assert.fail("Internet expense accepted when no non-sponsored grant present");
        }
        catch (TRAPException e)
        {
            // e.printStackTrace();
            // The test has passed, exception thrown when a non-sponsored grant is not present.
            Assert.assertEquals(true, true);
        }

    }

    @Test
    public void testGoodInternetIncidentalExpense()
    {

        Grant nonSponsored = new Grant();

        nonSponsored.setGrantAccount("99999");
        nonSponsored.setGrantPercentage(23);

        testApp.addGrant(nonSponsored);

        try
        {
            testApp.setNumDays(5);
            testApp.addIncidentalExpense(internetIncidental, 2);
        }
        catch (InputValidationException e1)
        {
            e1.printStackTrace();
            Assert.fail("Failed to add incidental expense: " + e1.getMessage());
        }

        try
        {
            rule.checkRule(testApp);
        }
        catch (TRAPException e)
        {
            e.printStackTrace();
            Assert.fail("Message: " + e.getMessage());
        }

    }

}
