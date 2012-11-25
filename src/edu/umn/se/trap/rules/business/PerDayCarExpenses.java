package edu.umn.se.trap.rules.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.data.TransportationTypeEnum;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * This rule checks that there are no personal and rental car expenses claimed on the same day.
 * 
 * @author andrewh
 * 
 */
public class PerDayCarExpenses extends BusinessLogicRule
{
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        Map<Date, List<TransportationExpense>> dailyExpenses = new HashMap<Date, List<TransportationExpense>>();

        for (TransportationExpense expense : app.getTransportationExpenseList())
        {
            // We only care about car expenses here
            if (expense.getTransportationType() != TransportationTypeEnum.CAR)
                continue;

            Date eDate = expense.getExpenseDate();

            // Instantiate the expense list for this day if it isn't there
            if (!dailyExpenses.containsKey(eDate))
            {
                dailyExpenses.put(eDate, new ArrayList<TransportationExpense>());
            }

            // Check for a car expense of the opposite on the given date
            List<TransportationExpense> transportExpenses = dailyExpenses.get(eDate);
            for (TransportationExpense seenExpense : transportExpenses)
            {
                if (seenExpense.getTransportationType() != expense.getTransportationType())
                {
                    throw new BusinessLogicException(
                            "Not allowed to claim rental and personal car on day " + eDate);
                }
            }

            // Otherwise we add it to the list
            transportExpenses.add(expense);
        }
    }
}
