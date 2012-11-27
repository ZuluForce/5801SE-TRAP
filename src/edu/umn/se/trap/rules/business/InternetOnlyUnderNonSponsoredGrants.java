/*****************************************************************************************
 * Copyright (c) 2012 Dylan Bettermann, Andrew Helgeson, Brian Maurer, Ethan Waytas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ****************************************************************************************/
// InternetOnlyUnderNonSponsoredGrants.java
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

        // A list of the incidental expenses
        List<IncidentalExpense> incidentalExpenses = app.getIncidentalExpenseList();

        // Use for comparison to check grant types
        String grantType = "";

        // Used in matching keywords
        Matcher internetMatch;

        // Total amount of money available to charge non-sponsored grants
        double totalNonSponsoredAmount = 0;

        // Loop through the grants looking for non-sponsored type grants
        for (Grant grant : grants)
        {
            try
            {
                grantType = GrantDBWrapper.getGrantAccountType(grant.getGrantAccount());
            }
            catch (KeyNotFoundException e)
            {
                throw new BusinessLogicException("Failed to grab grant information for grant: "
                        + grant.getGrantAccount(), e);
            }

            // If the grant is non-sponsored, update the running grant total
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
            // Get the justification and prepare to search through it
            internetMatch = INTERNET_PATTERN.matcher(expense.getExpenseJustification());

            // If a match is found, see if the expense is greater than the available amount
            if (internetMatch.find())
            {
                if (expense.getExpenseAmount() > totalNonSponsoredAmount)
                {
                    throw new BusinessLogicException("Internet related expense of $"
                            + expense.getExpenseAmount() + " is not covered by $"
                            + totalNonSponsoredAmount + " in Non-Sponsored grants");
                }
            }
        }

        for (IncidentalExpense expense : incidentalExpenses)
        {
            // Get the justification and prepare to search through it
            internetMatch = INTERNET_PATTERN.matcher(expense.getExpenseJustification());

            // If a match is found, see if the expense is greater than the available amount
            if (internetMatch.find())
            {
                if (expense.getExpenseAmount() > totalNonSponsoredAmount)
                {
                    throw new BusinessLogicException("Internet related expense of $"
                            + expense.getExpenseAmount() + " is not covered by $"
                            + totalNonSponsoredAmount + " in Non-Sponsored grants");
                }
            }
        }

        // All checks passed, keep processing
        return;

    }
}
