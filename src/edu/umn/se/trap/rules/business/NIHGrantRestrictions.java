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
        // going with funding organization

        List<Grant> grants = app.getGrantList();

        List<Grant> nihGrants = new ArrayList<Grant>();
        List<Grant> otherGrants = new ArrayList<Grant>();
        List<Object> grantInfo;

        double nihGrantTotalAvailable = 0;
        double otherGrantTotalAvailable = 0;

        String grantOrganizationType = "";

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

        List<MealExpense> mealExpenses = app.getMealExpenseList();

        if (otherGrants.size() == 0 && mealExpenses.size() > 0)
        {
            throw new BusinessLogicException("Unable to claim meal expenses under NIH grants.");
        }

        List<TransportationExpense> transportationExpenses = app.getTransportationExpenseList();

        List<TransportationExpense> allowedTransportationExpenses = new ArrayList<TransportationExpense>();

        double transportationExpenseCost = 0;

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
                break;
            }
        }

        if (transportationExpenseCost > nihGrantTotalAvailable)
        {
            throw new BusinessLogicException("NIH grant(s) do not have enough ($"
                    + nihGrantTotalAvailable + ") funds to cover $" + transportationExpenseCost
                    + " in expenses.");
        }

        return;

    }
}
