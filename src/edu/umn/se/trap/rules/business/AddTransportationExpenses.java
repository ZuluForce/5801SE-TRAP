// AddTransportationExpenses.java
package edu.umn.se.trap.rules.business;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TRAPConstants;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.data.TransportationTypeEnum;
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
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        for (TransportationExpense expense : app.getTransportationExpenseList())
        {
            TransportationTypeEnum type = expense.getTransportationType();
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

            expense.setExpenseAmount(expense.getExpenseAmount());
            app.addToReimbursementTotal(expense.getExpenseAmount());
        }
    }
}
