// AlcoholOnlyAllowedUnderNonSponsored.java
package edu.umn.se.trap.rules.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.MealExpense;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TripDay;
import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.db.PerDiemDBWrapper;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * 
 * @author andrewh
 * 
 */
public class MealPerDiem extends BusinessLogicRule
{
    private static final Logger log = LoggerFactory.getLogger(MealPerDiem.class);

    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        for (TripDay day : app.getAllTripDays())
        {
            for (MealExpense meal : day.getMealExpenses())
            {
                String country = meal.getCountry();
                Double perDiem = null;

                try
                {
                    // Get the per diem amount
                    if (country.compareToIgnoreCase("USA") == 0)
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

                // Add the per diem to the day's total
                app.addtoPerDiemTotal(perDiem, day.getDayNumber());
            }
        }
    }
}
