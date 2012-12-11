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
// TransportationDateValidator.java
package edu.umn.se.trap.rules.input;

import java.util.Date;
import java.util.List;

import edu.umn.se.trap.data.OtherExpense;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.InputValidationException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * Validate the date of other expenses. Other expenses may occur any time during or before the trip
 * but not after the arrival day.
 * 
 * @author andrewh
 * 
 */
public class OtherExpenseDateValidator extends InputValidationRule
{

    /**
     * Validate the date of other expenses. Other expenses may occur any time during or before the
     * trip but not after the arrival day.
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        List<OtherExpense> otherExpenses = app.getOtherExpenseList();

        Date arrival = app.getArrivalDatetime();
        arrival = DateValidator.getStartOfNextDay(arrival);

        for (int i = 0; i < otherExpenses.size(); ++i)
        {
            OtherExpense expense = otherExpenses.get(i);
            Date expenseDate = expense.getExpenseDate();

            // Other expenses can happen before departure or anytime during the trip but are not
            // allowed after arrival.
            if (expenseDate.after(arrival) || expenseDate.equals(arrival))
            {
                throw new InputValidationException(String.format(
                        "Other expense %d is after trip arrival time", i + 1, expenseDate));
            }
        }
    }
}
