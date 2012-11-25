// AlcoholOnlyAllowedUnderNonSponsored.java
package edu.umn.se.trap.rules.business;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.data.TransportationTypeEnum;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * This rule checks that there are no more baggage expense claims than the number of flights.
 * 
 * @author andrewh
 * 
 */
public class OnlyOneCheckedLuggage extends BusinessLogicRule
{
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        Integer numBaggageExpenses = 0;
        Integer numAirTravelExpenses = 0;

        for (TransportationExpense expense : app.getTransportationExpenseList())
        {
            TransportationTypeEnum type = expense.getTransportationType();
            if (type == TransportationTypeEnum.BAGGAGE)
            {
                ++numBaggageExpenses;
            }
            else if (type == TransportationTypeEnum.AIR)
            {
                ++numAirTravelExpenses;
            }
        }

        if (numBaggageExpenses > numAirTravelExpenses)
        {
            throw new BusinessLogicException(
                    "Cannot claim more baggage expenses than the number of air travel expenses");
        }
    }
}