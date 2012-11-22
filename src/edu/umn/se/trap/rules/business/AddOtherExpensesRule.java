// AddOtherExpensesRule.java
package edu.umn.se.trap.rules.business;

import edu.umn.se.trap.data.OtherExpense;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.TRAPException;

/**
 * @author planeman
 * 
 */
public class AddOtherExpensesRule extends BusinessLogicRule
{

    /*
     * (non-Javadoc)
     * @see edu.umn.se.trap.rules.TRAPRule#checkRule(edu.umn.se.trap.data.ReimbursementApp)
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        for (OtherExpense expense : app.getOtherExpenseList())
        {
            app.addToReimbursementTotal(expense.getExpenseAmount());
        }
    }

}
