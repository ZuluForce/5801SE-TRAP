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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.IncidentalExpense;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TripDay;
import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.db.PerDiemDBWrapper;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * Add all incidental expenses to the total according to their per diem amount. Incidentals on the
 * first and last day will only be given 75% of their per diem amount.
 * 
 * @author andrewh
 * 
 */
public class IncidentalPerDiem extends BusinessLogicRule
{
    /** Logger for the IncidentalPerDiem class */
    private static final Logger log = LoggerFactory.getLogger(IncidentalExpense.class);

    /**
     * Add all incidental expenses to the total according to their per diem amount. Incidentals on
     * the first and last day will only be given 75% of their per diem amount.
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        Double incidentalTotal = 0.0;

        for (TripDay day : app.getAllTripDays())
        {
            IncidentalExpense expense = day.getIncidentalExpense();
            if (expense == null)
            {
                log.info("No incidental expense on day {}. Skipping per diem check.",
                        day.getDayNumber());
                continue;
            }
            String country = expense.getCountry();
            Double perDiem = null;

            try
            {
                if (country.compareToIgnoreCase("USA") == 0)
                {
                    perDiem = PerDiemDBWrapper.getDomesticPerDiemIncidental(expense.getCity(),
                            expense.getState());
                }
                else
                {
                    perDiem = PerDiemDBWrapper.getInternationalPerDiemIncidental(expense.getCity(),
                            expense.getCountry());
                }
            }
            catch (KeyNotFoundException e)
            {
                log.error("Could not find per diem for the incidental expense:\n{}", expense);
                throw new BusinessLogicException("Could not find per diem for incidental on day "
                        + day.getDayNumber(), e);
            }

            // Make sure the expense is within the per diem
            if (expense.getExpenseAmount() > perDiem)
            {
                throw new BusinessLogicException(String.format(
                        "Incidental expense ($%f) greater than per diem ($%f)",
                        expense.getExpenseAmount(), perDiem));
            }

            // Only 75% on the first and last days
            Double amountToGive = expense.getExpenseAmount();
            if (day.getDayNumber() == 1 || day.getDayNumber() == app.getNumDays())
            {
                amountToGive *= 0.75;
            }

            app.addtoPerDiemTotal(amountToGive, day.getDayNumber());
            incidentalTotal += perDiem;
        }

        log.info("Added ${} in incidentals to the total", incidentalTotal);
    }
}
