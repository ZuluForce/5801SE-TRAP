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
// TransportationDateValidator.java
package edu.umn.se.trap.rules.input;

import java.util.Date;
import java.util.List;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.data.TransportationTypeEnum;
import edu.umn.se.trap.exception.InputValidationException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * Verifies the date of all transportation expenses. All transportation expenses must occur from the
 * day of departure to the day of arrival. Only air travel expenses may occur before the departure
 * day of the trip.
 * 
 * @author planeman
 * 
 */
public class TransportationDateValidator extends InputValidationRule
{

    /**
     * Verifies the date of all transportation expenses. All transportation expenses must occur from
     * the day of departure to the day of arrival. Only air travel expenses may occur before the
     * departure day of the trip.
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        // All transportations in the trip
        List<TransportationExpense> transportExpenses = app.getTransportationExpenseList();

        Date departure = app.getDepartureDatetime();
        Date arrival = app.getArrivalDatetime();

        // We allow any transportation expense for the duration of the
        // departure and arrival dates since we don't have time info.
        departure = DateValidator.getStartOfDay(departure);
        arrival = DateValidator.getStartOfNextDay(arrival);

        for (int i = 0; i < transportExpenses.size(); ++i)
        {
            TransportationExpense expense = transportExpenses.get(i);
            Date expenseDate = expense.getExpenseDate();

            // Air travel expenses are allowed to come before the beginning of the trip.
            if (expense.getTransportationType() != TransportationTypeEnum.AIR
                    && expenseDate.before(departure))
            {
                throw new InputValidationException(
                        String.format(
                                "Transportation expense %d comes before departure datetime and isn't air travel",
                                i + 1));
            }

            // All transportation expenses must be before or on the day of arrival
            if (expenseDate.after(arrival))
            {
                throw new InputValidationException(String.format(
                        "Transportation expense %d is after trip arrival time", i + 1, expenseDate));
            }
        }
    }
}
