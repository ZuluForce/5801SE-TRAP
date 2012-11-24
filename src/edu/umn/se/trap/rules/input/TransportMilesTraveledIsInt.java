// TransportMilesTraveledIsInt.java
package edu.umn.se.trap.rules.input;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.exception.InputValidationException;

/**
 * @author Dylan
 * 
 */
public class TransportMilesTraveledIsInt extends InputValidationRule
{
    private static Logger log = LoggerFactory.getLogger(TransportMilesTraveledIsInt.class);

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
            // TODO Not quite sure if this is right.
            if (transportationExpense.getTransportationMilesTraveled() <= 0)
            {
                throw new InputValidationException(
                        "Transportation miles traveled must be a positive integer");
            }
        }
    }

}
