// AddTransportationExpenses.java
package edu.umn.se.trap.rules.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TRAPConstants;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.data.TransportationTypeEnum;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * This rule adds all transportation expenses not covered elsewhere. The ones this rule does not
 * handler are as follows: mileage and baggage.
 * 
 * @author planeman
 * 
 */
public class AddTransportationExpenses extends BusinessLogicRule
{
    /** Logger for the AddTransportationExpenses class */
    private static Logger log = LoggerFactory.getLogger(AddTransportationExpenses.class);

    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        Double transportTotal = 0.0;

        for (TransportationExpense expense : app.getTransportationExpenseList())
        {
            TransportationTypeEnum type = expense.getTransportationType();
            Double amount = expense.getExpenseAmount();
            String rental = expense.getTransportationRental();

            // Baggage is already handled by another rule
            if (type == TransportationTypeEnum.BAGGAGE)
                continue;

            // Skip any personal car expenses since those are handled by the mileage rule
            if (type == TransportationTypeEnum.CAR
                    && rental.compareToIgnoreCase(TRAPConstants.STR_NO) == 0)
            {
                continue;
            }

            if (expense.getReimbursementAmount() >= 0.0)
            {
                log.error("Transportation expense reimbursement amount set before expected");
                throw new FormProcessorException(
                        "Transportation expense reimbursement amount set before expected");
            }

            expense.setReimbursementAmount(amount);
            app.addToReimbursementTotal(amount);

            transportTotal += amount;
        }

        log.info("Added ${} in transport expenses to the total", transportTotal);
    }
}
