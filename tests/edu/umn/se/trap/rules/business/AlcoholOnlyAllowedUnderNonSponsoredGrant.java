// AlcoholOnlyAllowedUnderNonSponsoredGrant.java
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
public class AlcoholOnlyAllowedUnderNonSponsoredGrant extends TrapTestFramework
{

    OtherExpense alcoholOther;
    IncidentalExpense alcoholIncidental;

    ReimbursementApp testApp = new ReimbursementApp();

    AlcoholOnlyAllowedUnderNonSponsored rule = new AlcoholOnlyAllowedUnderNonSponsored();

    public AlcoholOnlyAllowedUnderNonSponsoredGrant()
    {
        alcoholOther = new OtherExpense();

        alcoholOther.setExpenseJustification("Purchased some whiskey");
        alcoholOther.setExpenseCurrency("USD");
        alcoholOther.setExpenseAmount(23.00);

        alcoholIncidental = new IncidentalExpense();

        alcoholIncidental.setCity("Minneapolis");
        alcoholIncidental.setState("MN");
        alcoholIncidental.setCountry("USA");
        alcoholIncidental.setExpenseAmount(23.00);
        alcoholIncidental.setExpenseCurrency("USD");
        alcoholIncidental.setExpenseJustification("Purchased some whiskey");

    }

    @Test
    public void testBadAlcoholOtherExpense()
    {
        getLoadableForm(SampleDataEnum.DOMESTIC1);

        testApp.addOtherExpense(alcoholOther);

        try
        {
            rule.checkRule(testApp);
            Assert.fail("Alcohol expense accepted when no non-sponsored grant present");
        }
        catch (TRAPException e)
        {
            // e.printStackTrace();
            // The test has passed, exception thrown when a non-sponsored grant is not present.
            Assert.assertEquals(true, true);
        }

    }

    @Test
    public void testGoodAlcoholOtherExpense()
    {
        getLoadableForm(SampleDataEnum.DOMESTIC1);

        Grant nonSponsored = new Grant();

        nonSponsored.setGrantAccount("99999");
        nonSponsored.setGrantPercentage(23);

        testApp.addGrant(nonSponsored);

        testApp.addOtherExpense(alcoholOther);

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
            testApp.addIncidentalExpense(alcoholIncidental, 2);
        }
        catch (InputValidationException e1)
        {
            e1.printStackTrace();
            Assert.fail("Failed to add incidental expense: " + e1.getMessage());
        }

        try
        {
            rule.checkRule(testApp);
            Assert.fail("Alcohol expense accepted when no non-sponsored grant present");
        }
        catch (TRAPException e)
        {
            // e.printStackTrace();
            // The test has passed, exception thrown when a non-sponsored grant is not present.
            Assert.assertEquals(true, true);
        }

    }

    @Test
    public void testGoodAlcoholIncidentalExpense()
    {

        Grant nonSponsored = new Grant();

        nonSponsored.setGrantAccount("99999");
        nonSponsored.setGrantPercentage(23);

        testApp.addGrant(nonSponsored);

        try
        {
            testApp.setNumDays(5);
            testApp.addIncidentalExpense(alcoholIncidental, 2);
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
