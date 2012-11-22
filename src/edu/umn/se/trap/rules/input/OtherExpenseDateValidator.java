// TransportationDateValidator.java
package edu.umn.se.trap.rules.input;

import java.util.Date;
import java.util.List;

import edu.umn.se.trap.data.OtherExpense;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.InputValidationException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * @author planeman
 * 
 */
public class OtherExpenseDateValidator extends InputValidationRule
{

    /*
     * (non-Javadoc)
     * @see edu.umn.se.trap.rules.TRAPRule#checkRule(edu.umn.se.trap.data.ReimbursementApp)
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        List<OtherExpense> otherExpenses = app.getOtherExpenseList();

        Date arrival = app.getArrivalDatetime();

        for (int i = 0; i < otherExpenses.size(); ++i)
        {
            OtherExpense expense = otherExpenses.get(i);
            Date expenseDate = expense.getExpenseDate();

            // Other expenses can happen before departure or anytime during the trip but are not
            // allowed after arrival.
            if (expenseDate.after(arrival))
            {
                throw new InputValidationException(String.format(
                        "Other expense %d is after trip arrival time", i + 1, expenseDate));
            }
        }
    }
}
