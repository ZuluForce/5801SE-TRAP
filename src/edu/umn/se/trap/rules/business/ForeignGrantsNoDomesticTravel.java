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
        List<TransportationExpense> travelExpenses = app.getTransportationExpenseList();

        List<Grant> grants = app.getGrantList();

        List<Grant> foreignGrants = new ArrayList<Grant>();
        List<Grant> domesticGrants = new ArrayList<Grant>();
        List<Object> grantInfo;

        String organizationType = "";

        double domesticTravelClaimedTotal = 0;
        double foreignTravelClaimedTotal = 0;

        double domesticTravelAvailableTotal = 0;
        double foreignTravelAvailableTotal = 0;

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

        if (foreignGrants.size() > 0 && domesticGrants.size() == 0
                && domesticTravelClaimedTotal > 0)
        {
            throw new BusinessLogicException(
                    "Cannot use foreign grants to pay for domestic transporation expenses");
        }

        if (domesticTravelClaimedTotal > domesticTravelAvailableTotal)
        {
            throw new BusinessLogicException("Unable to fund transporation expenses ($"
                    + domesticTravelClaimedTotal + ") with available domestic grants");
        }

        if (foreignTravelClaimedTotal > foreignTravelAvailableTotal)
        {
            throw new BusinessLogicException("Unable to fund transporation expenses ($"
                    + foreignTravelClaimedTotal + ") with available foreign grants");
        }

        return;
    }

}
