/*****************************************************************************************
 * Copyright (c) 2012 Dylan Bettermann, Andrew Helgeson, Brian Maurer, Ethan Waytas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ****************************************************************************************/
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
    /**
     * This rule checks that there are no more baggage expense claims than the number of flights.
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        Integer numBaggageExpenses = 0;
        Integer numAirTravelExpenses = 0;

        // Tally up the number of baggage and air travel expenses
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

        // Make sure the # baggage expenses is less or equal to the # of air travel expenses
        if (numBaggageExpenses > numAirTravelExpenses)
        {
            throw new BusinessLogicException(
                    "Cannot claim more baggage expenses than the number of air travel expenses");
        }
    }
}
