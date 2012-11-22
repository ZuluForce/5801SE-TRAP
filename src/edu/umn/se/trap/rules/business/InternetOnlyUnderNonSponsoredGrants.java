// InternetOnlyUnderNonSponsoredGrants.java
package edu.umn.se.trap.rules.business;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.umn.se.trap.data.Grant;
import edu.umn.se.trap.data.OtherExpense;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.db.GrantDBWrapper;
import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * This class checks that any internet related expenses that are claimed have available
 * non-sponsored grants to charge.
 * 
 * @author nagell2008
 * 
 */
public class InternetOnlyUnderNonSponsoredGrants extends BusinessLogicRule
{

    /**
     * List of keywords to look for that relate to alcohol.
     */
    private static final Pattern INTERNET_PATTERN = Pattern.compile("(internet|wireless|wifi)",
            Pattern.CASE_INSENSITIVE);

    /**
     * This class checks that any internet related expenses that are claimed have available
     * non-sponsored grants to charge.
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        // Hold a list of all the grants
        List<Grant> grants = app.getGrantList();

        // A list of the other expenses
        List<OtherExpense> otherExpenses = app.getOtherExpenseList();

        // Holds only expenses that mention internet (or other keywords) in the justification field
        List<OtherExpense> internetExpenses = new ArrayList<OtherExpense>();

        // Holds grants that have type non-sponsored
        List<Grant> nonSponsoredGrants = new ArrayList<Grant>();

        // Use for comparison to check grant types
        String grantType = "";

        // Used in matching keywords
        Matcher internet_match;

        // Total amount that a user is claiming in internet expenses
        double totalInternetCharge = 0;

        // Total amount of money available to charge non-sponsored grants
        double totalNonSponsoredAmount = 0;

        // Loop through all the other expenses and see if the justification field mentions internet
        for (OtherExpense expense : otherExpenses)
        {
            internet_match = INTERNET_PATTERN.matcher(expense.getExpenseJustification());

            if (internet_match.find())
            {
                internetExpenses.add(expense);
            }
        }

        // If no expenses related to the internet were found, it is safe to return out of the rule
        if (internetExpenses.size() == 0)
        {
            return;
        }

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
                nonSponsoredGrants.add(grant);
            }
        }

        // No grants found of type non-sponsored, but internet expenses were found. Need to throw an
        // error in this case.
        if (nonSponsoredGrants.size() == 0)
        {
            throw new BusinessLogicException(
                    "Found internet expenses, but there are no non-sponsored grants available");
        }

        // Loop through the internet expenses and add up the total
        for (OtherExpense expense : internetExpenses)
        {
            totalInternetCharge += expense.getExpenseAmount();
        }

        // Loop through the internet expenses and add up the total
        for (Grant grant : nonSponsoredGrants)
        {
            try
            {
                totalNonSponsoredAmount += GrantDBWrapper.getGrantBalance(grant.getGrantAccount());
            }
            catch (KeyNotFoundException e)
            {
                throw new BusinessLogicException("Unable to find the grant balance for grant: "
                        + grant.getGrantAccount(), e);
            }
        }

        // If the total charge for internet expenses is greater than the amount available from
        // non-sponsored grants, throw an error
        if (totalInternetCharge > totalNonSponsoredAmount)
        {
            throw new BusinessLogicException("Unable to fund internet expenses ($"
                    + totalInternetCharge + ") with available non-sponsored grants");
        }

        return;
    }

}
