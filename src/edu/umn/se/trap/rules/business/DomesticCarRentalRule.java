// DomesticCarRentalRule.java
package edu.umn.se.trap.rules.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TRAPConstants;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.data.TransportationTypeEnum;
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

    /**
     * Check that all domestic car rentals use a an accepted carrier.
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
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

}
