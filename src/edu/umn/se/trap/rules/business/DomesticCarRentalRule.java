// DomesticCarRentalRule.java
package edu.umn.se.trap.rules.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.umn.se.trap.data.Grant;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TRAPConstants;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.data.TransportationTypeEnum;
import edu.umn.se.trap.db.GrantDBWrapper;
import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * Checks that all domestic transportation expenses for rental cars use an accepted carrier.
 * Currently the list of accepted carriers is just 'National Travel'.
 * 
 * @author planeman
 * 
 */
public class DomesticCarRentalRule extends BusinessLogicRule
{
    /** List of accepted carriers for domestic car rental */
    public static final List<String> acceptedCarriers = new ArrayList<String>(
            Arrays.asList("National Travel"));

    /** Allowed car rental for DoD grants */
    public static final String dodAllowedCarRental = "Hertz";

    /**
     * Check that all domestic car rentals use a an accepted carrier.
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {

        List<Grant> dodGrants;
        try
        {
            dodGrants = GrantDBWrapper.getDODGrants(app.getGrantList());
        }
        catch (KeyNotFoundException e)
        {
            throw new BusinessLogicException("Failed to get a list of DoD grants");
        }

        for (TransportationExpense expense : app.getTransportationExpenseList())
        {
            // Check if it is domestic by looking at the currency
            String currency = expense.getExpenseCurrency();
            if (currency.compareToIgnoreCase(TRAPConstants.USD) != 0)
                continue;

            // Check if the transport expense is a rental car
            String rentalStr = expense.getTransportationRental();
            Boolean rental = rentalStr.compareToIgnoreCase(TRAPConstants.STR_YES) == 0;

            if (rental)
            {
                // Make sure it is under a car expense type. This is check in other places as
                // well but it doesn't hurt to do it again
                TransportationTypeEnum type = expense.getTransportationType();
                if (type != TransportationTypeEnum.CAR)
                {
                    throw new BusinessLogicException(
                            "Transportation rental claimed under non car expense");
                }

                // Check the carrier in the list of accepted domestic rental companies
                String carrier = expense.getTransportationCarrier();
                if (!isCarrierAccepted(carrier))
                {
                    throw new BusinessLogicException("Carrier not accept for domestic car rental: "
                            + carrier);
                }

                if (!isDODCarrierAccepted(carrier, dodGrants, expense))
                {
                    throw new BusinessLogicException("Carrier not accept for DOD car rental: "
                            + carrier);
                }
            }
        }
    }

    /**
     * Check if the given carrier name is in the list of accepted rental carriers.
     * 
     * @param carrier - The carrier name to check for acceptance
     * @return - Whether or not the given carrier is in the list of accepted rental carriers.
     */
    private boolean isCarrierAccepted(String carrier)
    {
        for (String acceptedCarrier : acceptedCarriers)
        {
            if (acceptedCarrier.compareToIgnoreCase(carrier) == 0)
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Check if the given carrier name is allowed under the DoD grants
     * 
     * @param carrier - Car rental carrier
     * @param dodGrants - List of only DoD grants
     * @param expense - The current expense to check
     * @return - True if the carrier is allowed by the DoD, False otherwise
     * @throws BusinessLogicException - When a carrier is not accepted by a DoD grant, or relative
     *             database information could not be found
     */
    private boolean isDODCarrierAccepted(String carrier, List<Grant> dodGrants,
            TransportationExpense expense) throws BusinessLogicException
    {
        // Running total of available funds in DoD grants
        double dodGrantTotal = 0;

        // Loop through all the grants and check that the carrier
        for (Grant grant : dodGrants)
        {
            if (dodAllowedCarRental.compareToIgnoreCase(carrier) != 0)
            {
                return false;
            }

            try
            {
                dodGrantTotal += GrantDBWrapper.getGrantBalance(grant.getGrantAccount());
            }
            catch (KeyNotFoundException e)
            {
                throw new BusinessLogicException("Unable to find the account balance for grant: "
                        + grant.getGrantAccount(), e);
            }

        }

        // The expense amount is greater than the total amount in the DoD grants
        if (expense.getExpenseAmount() > dodGrantTotal)
        {
            throw new BusinessLogicException("Car rental expense of $" + expense.getExpenseAmount()
                    + " cannot be reimbursed using DoD grants with $" + dodGrantTotal
                    + " available funds.");
        }

        return true;
    }

}
