// TransportationDateValidator.java
package edu.umn.se.trap.rules.input;

import java.util.Date;
import java.util.List;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.data.TransportationTypeEnum;
import edu.umn.se.trap.exception.InputValidationException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * @author planeman
 * 
 */
public class TransportationDateValidator extends InputValidationRule
{

    /*
     * (non-Javadoc)
     * @see edu.umn.se.trap.rules.TRAPRule#checkRule(edu.umn.se.trap.data.ReimbursementApp)
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        // All transportations in the trip
        List<TransportationExpense> transportExpenses = app.getTransportationExpenseList();

        Date departure = app.getDepartureDatetime();
        Date arrival = app.getArrivalDatetime();

        for (int i = 0; i < transportExpenses.size(); ++i)
        {
            TransportationExpense expense = transportExpenses.get(i);
            Date expenseDate = expense.getExpenseDate();

            // Air travel expenses are allowed to come before the beginning of the trip.
            if (expense.getTransportationType() != TransportationTypeEnum.AIR
                    && expenseDate.before(departure))
            {
                throw new InputValidationException(
                        String.format(
                                "Transportation expense %d comes before departure datetime and isn't air travel",
                                i + 1));
            }

            // All transportation expenses must be before the arrival time
            if (expenseDate.after(arrival))
            {
                throw new InputValidationException(String.format(
                        "Transportation expense %d is after trip arrival time", i + 1, expenseDate));
            }
        }
    }
}
