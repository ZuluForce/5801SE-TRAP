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

import edu.umn.se.trap.data.MealExpense;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TRAPConstants;
import edu.umn.se.trap.data.TripDay;
import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.db.PerDiemDBWrapper;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * Add all meal expenses to the total according to their per diem amount. Meals on the first and
 * last day will only be given 75% of their per diem amount.
 * 
 * @author andrewh
 * 
 */
public class MealPerDiem extends BusinessLogicRule
{
    /** Logger for the MealPerDiem class */
    private static final Logger log = LoggerFactory.getLogger(MealPerDiem.class);

    /**
     * Add all meal expenses to the total according to their per diem amount. Meals on the first and
     * last day will only be given 75% of their per diem amount.
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        Double mealTotal = 0.0;

        for (TripDay day : app.getAllTripDays())
        {
            for (MealExpense meal : day.getMealExpenses())
            {
                String country = meal.getCountry();
                Double perDiem = null;

                try
                {
                    // Get the per diem amount
                    if (country.compareToIgnoreCase(TRAPConstants.USA) == 0)
                    {
                        perDiem = PerDiemDBWrapper.getDomesticPerDiemMeal(meal.getCity(),
                                meal.getState(), meal.getType());
                    }
                    else
                    {
                        perDiem = PerDiemDBWrapper.getInternationalPerDiemMeal(meal.getCity(),
                                meal.getCountry(), meal.getType());
                    }
                }
                catch (KeyNotFoundException e)
                {
                    log.error("Failed to find per diem for meal expense:\n{}", meal);
                    throw new BusinessLogicException("Failed to find per diem for meal expense", e);
                }

                // Only 75% on first and last days
                if (day.getDayNumber() == 1 || day.getDayNumber() == app.getNumDays())
                {
                    perDiem *= 0.75;
                }

                // Add the per diem to the day's total
                app.addtoPerDiemTotal(perDiem, day.getDayNumber());

                mealTotal += perDiem;
            }
        }

        log.info("Added ${} in meal expenses to the total", mealTotal);
    }
}
