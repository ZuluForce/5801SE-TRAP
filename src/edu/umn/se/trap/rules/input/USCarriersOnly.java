// USCarriersOnly.java
package edu.umn.se.trap.rules.input;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TransportationCarrierEnum;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.exception.InputValidationException;

/**
 * @author Dylan
 * 
 */
public class USCarriersOnly extends InputValidationRule
{
    private static Logger log = LoggerFactory.getLogger(USCarriersOnly.class);

    @Override
    public void checkRule(ReimbursementApp app) throws InputValidationException,
            FormProcessorException
    {
        List<TransportationExpense> transportationExpenses = app.getTransportationExpenseList();

        if (transportationExpenses.isEmpty())
        {
            throw new InputValidationException("Missing transportation expenses");
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
                    throw new InputValidationException(
                            "Air carrier is not recognized or not a US based carrier.");
                }
            }
        }

    }
}
