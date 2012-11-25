// AlcoholOnlyAllowedUnderNonSponsored.java
package edu.umn.se.trap.rules.business;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.umn.se.trap.data.Grant;
import edu.umn.se.trap.data.IncidentalExpense;
import edu.umn.se.trap.data.OtherExpense;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.db.GrantDBWrapper;
import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * This class checks that any alcohol related expenses that are claimed have available non-sponsored
 * grants to charge.
 * 
 * @author nagell2008
 * 
 */
public class AlcoholOnlyAllowedUnderNonSponsored extends BusinessLogicRule
{

    /**
     * List of keywords to look for that relate to alcohol.
     */
    private static final Pattern ALCOHOL_PATTERN = Pattern.compile(
            "(alcohol|whiskey|rum|vodka|tequila|beer|wine)", Pattern.CASE_INSENSITIVE);

    /**
     * This class checks that any alcohol related expenses that are claimed have available
     * non-sponsored grants to charge.
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        // Hold a list of all the grants
        List<Grant> grants = app.getGrantList();

        // A list of the other expenses
        List<OtherExpense> otherExpenses = app.getOtherExpenseList();

        // A list of the incidental expenses
        List<IncidentalExpense> incidentalExpenses = app.getIncidentalExpenseList();

        // Use for comparison to check grant types
        String grantType = "";

        // Used in matching keywords
        Matcher alcoholMatch;

        // Total amount of money available to charge non-sponsored grants
        double totalNonSponsoredAmount = 0;

        // Loop through the grants looking for non-sponsored type grants
        for (Grant grant : grants)
        {
            try
            {
                grantType = GrantDBWrapper.getGrantType(grant.getGrantAccount());
            }
            catch (KeyNotFoundException e)
            {
                throw new BusinessLogicException("Failed to grab grant information for grant: "
                        + grant.getGrantAccount(), e);
            }

            if (grantType.compareToIgnoreCase("non-sponsored") == 0)
            {
                try
                {
                    totalNonSponsoredAmount += GrantDBWrapper.getGrantBalance(grant
                            .getGrantAccount());
                }
                catch (KeyNotFoundException e)
                {
                    throw new BusinessLogicException(
                            "Could not get grant balance from database for grant: "
                                    + grant.getGrantAccount());
                }
            }
        }

        // Loop through all the other expenses and see if the justification field mentions alcohol
        for (OtherExpense expense : otherExpenses)
        {
            alcoholMatch = ALCOHOL_PATTERN.matcher(expense.getExpenseJustification());

            if (alcoholMatch.find())
            {
                if (expense.getExpenseAmount() > totalNonSponsoredAmount)
                {
                    throw new BusinessLogicException("Alcohol expense of $"
                            + expense.getExpenseAmount() + " is not covered by $"
                            + totalNonSponsoredAmount + " in Non-Sponsored grants");
                }
            }
        }

        for (IncidentalExpense expense : incidentalExpenses)
        {
            alcoholMatch = ALCOHOL_PATTERN.matcher(expense.getExpenseJustification());

            if (alcoholMatch.find())
            {
                if (expense.getExpenseAmount() > totalNonSponsoredAmount)
                {
                    throw new BusinessLogicException("Alcohol expense of $"
                            + expense.getExpenseAmount() + " is not covered by $"
                            + totalNonSponsoredAmount + " in Non-Sponsored grants");
                }
            }

        }

        // All checks passed, keep processing
        return;
    }
}
