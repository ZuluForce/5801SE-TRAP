// USCarriersOnly.java
package edu.umn.se.trap.rules.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TransportationCarrierEnum;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * @author Dylan
 * 
 */
public class USCarriersOnly extends BusinessLogicRule
{
    private static Logger log = LoggerFactory.getLogger(USCarriersOnly.class);

    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        List<TransportationExpense> transportationExpenses = app.getTransportationExpenseList();

        // TODO Do I need to check for empty here?
        if (transportationExpenses.isEmpty())
        {
            throw new BusinessLogicException("Missing transportation expenses");
        }

        for (TransportationExpense transportationExpense : transportationExpenses)
        {
            if (transportationExpense.getTransportationType().toString() == "AIR")
            {
                // TODO Not sure if this is going to work. Can switch to a for loop if it does not.
                try
                {
                    TransportationCarrierEnum.valueOf(transportationExpense
                            .getTransportationCarrier());
                }
                catch (Exception e)
                {
                    throw new BusinessLogicException(
                            "Air carrier is not recognized or not a US based carrier.");
                }
            }
        }

    }
}
