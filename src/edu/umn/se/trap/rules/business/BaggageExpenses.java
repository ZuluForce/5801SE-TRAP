// BaggageExpenses.java
package edu.umn.se.trap.rules.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.data.TransportationTypeEnum;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * Add all baggage expenses to the total. This rule also checks that no baggage expense is greater
 * than $25 and throws an error if it is.
 * 
 * @author planeman
 * 
 */
public class BaggageExpenses extends BusinessLogicRule
{
    /** Log for the BaggageExpenses rule class */
    private static final Logger log = LoggerFactory.getLogger(BaggageExpenses.class);

    /** Constant representing the max allowable amount for a single baggage expense */
    private static final Double maxBaggageClaim = 25.0;

    /**
     * Add all baggage expenses to the total. This rule also checks that no baggage expense is
     * greater than $25 and throws an error if it is.
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        Double baggageTotal = 0.0;

        for (TransportationExpense expense : app.getTransportationExpenseList())
        {
            TransportationTypeEnum type = expense.getTransportationType();
            Double amount = expense.getExpenseAmount();

            // Only concerned with baggage claims here
            if (type == TransportationTypeEnum.BAGGAGE)
            {
                // The reimbursement amount for this expense should not have been set yet. Safety
                // check.
                if (amount >= 0.0)
                {
                    log.error("Transportation expense reimbursement amount already set:\n{}",
                            expense);
                    throw new FormProcessorException(
                            "Transportation baggage expense reimbursement amount set before expected");
                }

                // The assumption is that no carrier will charge more than $25 for a bag
                if (amount > maxBaggageClaim)
                {
                    throw new BusinessLogicException(String.format(
                            "Baggage claim for $%f is above maximum allowable $%f", amount,
                            maxBaggageClaim));
                }

                // Add expense to total
                expense.reimburseFullAmount();
                app.addToReimbursementTotal(expense.getExpenseAmount());

                baggageTotal += expense.getExpenseAmount();
            }
        }

        log.info("Added ${} in baggage expenses to the total", baggageTotal);
    }

}
