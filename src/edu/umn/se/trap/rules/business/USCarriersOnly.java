// USCarriersOnly.java
package edu.umn.se.trap.rules.business;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.data.TransportationTypeEnum;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * @author Dylan
 * 
 */
public class USCarriersOnly extends BusinessLogicRule
{
    private static Logger log = LoggerFactory.getLogger(USCarriersOnly.class);

    List<String> USCarriers = new ArrayList<String>();

    public USCarriersOnly()
    {
        USCarriers.add("Southwest");
        USCarriers.add("Alaska Airlines");
        USCarriers.add("American");
        USCarriers.add("Delta");
        USCarriers.add("Frontier");
        USCarriers.add("Great Lakes");
        USCarriers.add("Spirit");
        USCarriers.add("Sun Country");
        USCarriers.add("United");
        USCarriers.add("US Airways");
    }

    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        List<TransportationExpense> transportationExpenses = app.getTransportationExpenseList();
        boolean isUSCarrier;

        for (TransportationExpense expense : transportationExpenses)
        {
            TransportationTypeEnum type = expense.getTransportationType();
            if (type == TransportationTypeEnum.AIR)
            {
                isUSCarrier = false;
                String carrier = expense.getTransportationCarrier();
                for (String USCarrier : USCarriers)
                {
                    if (carrier.compareToIgnoreCase(USCarrier) == 0)
                    {
                        isUSCarrier = true;
                        break;
                    }
                }
                if (!isUSCarrier)
                {
                    throw new BusinessLogicException("Air carrier is not US based.");
                }
            }
        }
    }
}
