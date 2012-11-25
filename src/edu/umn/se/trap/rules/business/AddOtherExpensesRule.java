// AddOtherExpensesRule.java
package edu.umn.se.trap.rules.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.OtherExpense;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * @author planeman
 * 
 */
public class AddOtherExpensesRule extends BusinessLogicRule
{
    private static final Logger log = LoggerFactory.getLogger(AddOtherExpensesRule.class);

    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        Double otherTotal = 0.0;

        for (OtherExpense expense : app.getOtherExpenseList())
        {
            Double amount = expense.getExpenseAmount();
            if (expense.getReimbursementAmount() >= 0.0)
            {
                log.error("Other expense reimbursement amount set before expected");
                throw new FormProcessorException(
                        "Other expense had reimbursement amount set before expected");
            }

            app.addToReimbursementTotal(amount);
            expense.setReimbursementAmount(amount);

            otherTotal += amount;
        }

        log.info("Added ${} in other expenses to the total", otherTotal);
    }
}
