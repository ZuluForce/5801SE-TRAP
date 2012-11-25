// ForeignGrantsNoDomesticTravel.java
package edu.umn.se.trap.rules.business;

import java.util.ArrayList;
import java.util.List;

import edu.umn.se.trap.data.Grant;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TRAPConstants;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.db.GrantDB;
import edu.umn.se.trap.db.GrantDBWrapper;
import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * Foreign grants are not allowed to pay for domestic travel within the United States.
 * 
 * @author nagell2008
 * 
 */
public class ForeignGrantsNoDomesticTravel extends BusinessLogicRule
{

    /**
     * Foreign grants are not allowed to pay for domestic travel within the United States.
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        // List of transportation expenses
        List<TransportationExpense> travelExpenses = app.getTransportationExpenseList();

        // List of available grants
        List<Grant> grants = app.getGrantList();

        // List of foreign grants
        List<Grant> foreignGrants = new ArrayList<Grant>();

        // List of domestic grants
        List<Grant> domesticGrants = new ArrayList<Grant>();

        // Temporary variable to hold grant information as needed
        List<Object> grantInfo;

        // Temporary variable to hold the organization type
        String organizationType = "";

        // Total amount of domestic travel expenses claimed
        double domesticTravelClaimedTotal = 0;

        // Total amount of foreign travel expenses claimed
        double foreignTravelClaimedTotal = 0;

        // Total amount of money available from domestic grants
        double domesticTravelAvailableTotal = 0;

        // Total amount of money available from foreign grants
        double foreignTravelAvailableTotal = 0;

        // This for-loop separates foreign and domestic grants and updates the running total
        for (Grant grant : grants)
        {
            try
            {
                grantInfo = GrantDBWrapper.getGrantInfo(grant.getGrantAccount());
            }
            catch (KeyNotFoundException e)
            {
                throw new BusinessLogicException("Could not grab information for grant: "
                        + grant.getGrantAccount(), e);
            }

            organizationType = (String) grantInfo.get(GrantDB.GRANT_FIELDS.ORGANIZATION_TYPE
                    .ordinal());

            if (organizationType.compareToIgnoreCase("foreign") == 0)
            {
                foreignGrants.add(grant);
                try
                {
                    foreignTravelAvailableTotal += GrantDBWrapper.getGrantBalance(grant
                            .getGrantAccount());
                }
                catch (KeyNotFoundException e)
                {
                    throw new BusinessLogicException("Could not grab grant balance for grant: "
                            + grant.getGrantAccount(), e);
                }
            }
            else
            {
                domesticGrants.add(grant);
                try
                {
                    domesticTravelAvailableTotal += GrantDBWrapper.getGrantBalance(grant
                            .getGrantAccount());
                }
                catch (KeyNotFoundException e)
                {
                    throw new BusinessLogicException("Could not grab grant balance for grant: "
                            + grant.getGrantAccount(), e);
                }
            }

        }

        // Updates domestic and foreign travel expenses
        for (TransportationExpense travel : travelExpenses)
        {

            if (travel.getOriginalCurrency().compareToIgnoreCase(TRAPConstants.USD) == 0)
            {
                domesticTravelClaimedTotal += travel.getExpenseAmount();
            }
            else
            {
                foreignTravelClaimedTotal += travel.getExpenseAmount();
            }
        }

        // If there are foreign grants, no domestic grants and domestic travel expenses, throw an
        // exception because foreign grants cannot pay for domestic travel
        if (foreignGrants.size() > 0 && domesticGrants.size() == 0
                && domesticTravelClaimedTotal > 0)
        {
            throw new BusinessLogicException(
                    "Cannot use foreign grants to pay for domestic transporation expenses");
        }

        // If the amount of expenses claimed for domestic travel is greater than the total amount
        // available, throw an exception
        if (domesticTravelClaimedTotal > domesticTravelAvailableTotal)
        {
            throw new BusinessLogicException("Unable to fund transporation expenses ($"
                    + domesticTravelClaimedTotal + ") with available domestic grants");
        }

        // If the amount of foreign expenses is greater than the available funds, throw an exception
        if (foreignTravelClaimedTotal > foreignTravelAvailableTotal)
        {
            throw new BusinessLogicException("Unable to fund transporation expenses ($"
                    + foreignTravelClaimedTotal + ") with available foreign grants");
        }

        return;
    }

}
