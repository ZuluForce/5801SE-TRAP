// AddOtherExpensesRule.java
package edu.umn.se.trap.rules.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.OtherExpense;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * Add all other expenses to the reimbursement total.
 * 
 * @author planeman
 * 
 */
public class AddOtherExpensesRule extends BusinessLogicRule
{
    /** Log for the AddOtherExpensesRule */
    private static final Logger log = LoggerFactory.getLogger(AddOtherExpensesRule.class);

    /**
     * Add all other expenses to the reimbursement total.
     */
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
