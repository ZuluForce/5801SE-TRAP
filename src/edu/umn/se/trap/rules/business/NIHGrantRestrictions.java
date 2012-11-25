// NIHGrantRestrictions.java
package edu.umn.se.trap.rules.business;

import java.util.ArrayList;
import java.util.List;

import edu.umn.se.trap.data.Grant;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TransportationExpense;
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

    /**
     * This rule checks all the requirements of the NIH grants. Specifically, NIH grants do not
     * reimburse for meal expenses and only reimburse for air and public transportation expenses.
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {

        // Holds only NIH grants
        List<Grant> nihGrants;
        try
        {
            nihGrants = GrantDBWrapper.getNIHGrants(app.getGrantList());
        }
        catch (KeyNotFoundException e1)
        {
            throw new BusinessLogicException("Failed to get the NIH grants");
        }

        // Holds the remaining grants
        List<Grant> otherGrants = app.getGrantList();

        // Remove all the NIH grants to leave only non-NIH grants
        otherGrants.removeAll(nihGrants);

        // Total amount of non-NIH grant money available
        double nonNIHGrantTotalAvailable = 0;

        // This for-loop updates the non-NIH grant totals
        for (Grant grant : otherGrants)
        {
            try
            {
                nonNIHGrantTotalAvailable += GrantDBWrapper
                        .getGrantBalance(grant.getGrantAccount());
            }
            catch (KeyNotFoundException e)
            {
                throw new BusinessLogicException("Could not grab grant account balance for grant: "
                        + grant.getGrantAccount(), e);
            }
        }

        // If there are no non-NIH grants available but there are meal expenses, throw an exception
        // as NIH grants do not allow meal reimbursement
        if (otherGrants.size() == 0 && app.getMealExpenseList().size() > 0)
        {
            throw new BusinessLogicException("Unable to claim meal expenses under NIH grants.");
        }

        // Holds transportation expenses
        List<TransportationExpense> transportationExpenses = app.getTransportationExpenseList();

        // Holds only other transportation expenses
        List<TransportationExpense> otherTransportationExpenses = new ArrayList<TransportationExpense>();

        // This loop finds only the allowed transportation costs and adds them to the running total
        // of transportation expenses
        for (TransportationExpense texpense : transportationExpenses)
        {
            switch (texpense.getTransportationType())
            {
            case AIR:
            case BAGGAGE:
            case PUBLIC_TRANSPORTATION:
                break;
            default:
                // If the expense (one that a NIH grant does not allow) cannot be covered by a
                // non-NIH grant, throw an exception
                otherTransportationExpenses.add(texpense);
                if (texpense.getExpenseAmount() > nonNIHGrantTotalAvailable)
                {
                    throw new BusinessLogicException("Transportation expense of $"
                            + texpense.getExpenseAmount() + " cannont be funded with $"
                            + nonNIHGrantTotalAvailable);
                }
                break;
            }
        }

        // If there are only NIH grants and non-allowed transportation expenses, throw an exception
        if (otherGrants.size() == 0 && otherTransportationExpenses.size() > 0)
        {
            throw new BusinessLogicException(
                    "NIH grants can only reimburse for Air, Baggage or Public Transportation expenses");
        }

        // No problems found, continue processing
        return;

    }
}
