// NIHGrantRestrictions.java
package edu.umn.se.trap.rules.business;

import java.util.ArrayList;
import java.util.List;

import edu.umn.se.trap.data.Grant;
import edu.umn.se.trap.data.MealExpense;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.db.GrantDB;
import edu.umn.se.trap.db.GrantDBWrapper;
import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * This rule checks all the requirements of the NIH grants. Specifically, NIH grants do not
 * reimburse for meal expenses and only reimburse for air and public transportation expenses.
 * 
 * @author nagell2008
 * 
 */
public class NIHGrantRestrictions extends BusinessLogicRule
{

    /*
     * (non-Javadoc)
     * @see edu.umn.se.trap.rules.TRAPRule#checkRule(edu.umn.se.trap.data.ReimbursementApp)
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {

        // Holds all available grants to the user
        List<Grant> grants = app.getGrantList();

        // Holds only NIH grants
        List<Grant> nihGrants = new ArrayList<Grant>();

        // Holds the remaining grants
        List<Grant> otherGrants = new ArrayList<Grant>();

        // Temporary variable to hold grant information when needed
        List<Object> grantInfo;

        // Total amount of NIH grant money available
        double nihGrantTotalAvailable = 0;

        // Total amount on non-NIH grant money available
        double otherGrantTotalAvailable = 0;

        // Temporary variable to hold the organization type
        String grantOrganizationType = "";

        // This for-loop separates NIH and non-NIH grants and updates the respective totals
        for (Grant grant : grants)
        {
            try
            {
                grantInfo = GrantDBWrapper.getGrantInfo(grant.getGrantAccount());
            }
            catch (KeyNotFoundException e)
            {
                throw new BusinessLogicException("Could not grab grant information for grant: "
                        + grant.getGrantAccount(), e);
            }
            grantOrganizationType = (String) grantInfo.get(GrantDB.GRANT_FIELDS.ORGANIZATION_TYPE
                    .ordinal());
            if (grantOrganizationType.compareToIgnoreCase("NIH") == 0)
            {
                nihGrants.add(grant);
                try
                {
                    nihGrantTotalAvailable += GrantDBWrapper.getGrantBalance(grant
                            .getGrantAccount());
                }
                catch (KeyNotFoundException e)
                {
                    throw new BusinessLogicException(
                            "Could not grab grant account balance for grant: "
                                    + grant.getGrantAccount(), e);
                }
            }
            else
            {
                otherGrants.add(grant);
                try
                {
                    otherGrantTotalAvailable += GrantDBWrapper.getGrantBalance(grant
                            .getGrantAccount());
                }
                catch (KeyNotFoundException e)
                {
                    throw new BusinessLogicException(
                            "Could not grab grant account balance for grant: "
                                    + grant.getGrantAccount(), e);
                }
            }
        }

        if (nihGrants.size() == 0)
        {
            // No NIH grants found, just return from this business rule
            return;
        }

        // Holds all meal expenses a user is claiming
        List<MealExpense> mealExpenses = app.getMealExpenseList();

        // If there are no non-NIH grants available but there are meal expenses, throw an exception
        // as NIH grants do not allow meal reimbursement
        if (otherGrants.size() == 0 && mealExpenses.size() > 0)
        {
            throw new BusinessLogicException("Unable to claim meal expenses under NIH grants.");
        }

        // Holds transportation expenses
        List<TransportationExpense> transportationExpenses = app.getTransportationExpenseList();

        // Holds only allowed transportation expenses
        List<TransportationExpense> allowedTransportationExpenses = new ArrayList<TransportationExpense>();

        // Holds only other transportation expenses
        List<TransportationExpense> otherTransportationExpenses = new ArrayList<TransportationExpense>();

        // Running total of allowed transportation costs
        double transportationExpenseCost = 0;

        // This loop finds only the allowed transportation costs and adds them to the running total
        // of transportation expenses
        for (TransportationExpense texpense : transportationExpenses)
        {
            switch (texpense.getTransportationType())
            {
            case AIR:
                allowedTransportationExpenses.add(texpense);
                transportationExpenseCost += texpense.getExpenseAmount();
                break;
            case BAGGAGE:
                allowedTransportationExpenses.add(texpense);
                transportationExpenseCost += texpense.getExpenseAmount();
                break;
            case PUBLIC_TRANSPORTATION:
                allowedTransportationExpenses.add(texpense);
                transportationExpenseCost += texpense.getExpenseAmount();
                break;
            default:
                otherTransportationExpenses.add(texpense);
                break;
            }
        }

        // If there are only NIH grants and non-allowed transportation expenses, throw an exception
        if (otherGrants.size() == 0 && otherTransportationExpenses.size() > 0)
        {
            throw new BusinessLogicException(
                    "NIH grants can only reimburse for Air, Baggage or Public Transportation expenses");
        }

        // If there is not enough money in the NIH grant(s) to cover the transportation expenses,
        // throw an exception
        if (transportationExpenseCost > nihGrantTotalAvailable)
        {
            throw new BusinessLogicException("NIH grant(s) do not have enough ($"
                    + nihGrantTotalAvailable + ") funds to cover $" + transportationExpenseCost
                    + " in expenses.");
        }

        // No problems found, continue processing
        return;

    }
}
