/*****************************************************************************************
 * Copyright (c) 2012 Dylan Bettermann, Andrew Helgeson, Brian Maurer, Ethan Waytas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 ****************************************************************************************/
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
    /**
     * This rule checks that there are no personal and rental car expenses claimed on the same day.
     */
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
                if (seenExpense.getTransportationRental() != expense.getTransportationRental())
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
