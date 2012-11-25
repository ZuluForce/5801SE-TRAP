// USCarriersOnly.java
package edu.umn.se.trap.rules.business;

import java.util.ArrayList;
import java.util.List;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.data.TransportationTypeEnum;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * Check that all air travel expenses are with a US based carrier.
 * 
 * @author Dylan
 * 
 */
public class USCarriersOnly extends BusinessLogicRule
{
    /** List to hold known US carriers */
    List<String> USCarriers = new ArrayList<String>();

    /**
     * Initialize the rule by filling the list of known US carriers
     */
    public USCarriersOnly()
    {
        USCarriers.add("Southwest");
        USCarriers.add("Alaska Airlines");
        USCarriers.add("American");
        USCarriers.add("Delta");
        USCarriers.add("Frontier");
        USCarriers.add("Great Lakes");
        USCarriers.add("Spirit");
        USCarriers.add("Sun Country");
        USCarriers.add("United");
        USCarriers.add("US Airways");
    }

    /**
     * Check that all air travel expenses are with a US based carrier.
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        boolean isUSCarrier;

        // Run through all transportation expenses
        for (TransportationExpense expense : app.getTransportationExpenseList())
        {
            TransportationTypeEnum type = expense.getTransportationType();

            // Only care about air travel for this rul
            if (type == TransportationTypeEnum.AIR)
            {
                isUSCarrier = false;
                String carrier = expense.getTransportationCarrier();

                // Check the carrier in a case insensitive manner against the known US carriers
                for (String USCarrier : USCarriers)
                {
                    if (carrier.compareToIgnoreCase(USCarrier) == 0)
                    {
                        isUSCarrier = true;
                        break;
                    }
                }

                if (!isUSCarrier)
                {
                    throw new BusinessLogicException("Air carrier is not US based.");
                }
            }
        }
    }
}
