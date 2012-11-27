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
// DoDGrantRestrictions.java
package edu.umn.se.trap.rules.business;

import java.util.ArrayList;
import java.util.List;

import edu.umn.se.trap.data.Grant;
import edu.umn.se.trap.data.MealExpense;
import edu.umn.se.trap.data.MealTypeEnum;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TRAPConstants;
import edu.umn.se.trap.db.GrantDBWrapper;
import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.db.PerDiemDBWrapper;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * This rule checks all the requirements of being reimbursed under a DoD grant. Specifically, DoD
 * grants do not fund breakfast.
 * 
 * @author nagell2008
 * 
 */
public class DoDMealRestrictions extends BusinessLogicRule
{

    /**
     * This rule checks all the requirements of being reimbursed under a DoD grant. Specifically,
     * DoD grants do not fund breakfast.
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        // Holds DoD grants
        List<Grant> dodGrants;
        try
        {
            // Fill with DoD grants
            dodGrants = GrantDBWrapper.getDODGrants(app.getGrantList());
        }
        catch (KeyNotFoundException e1)
        {
            throw new BusinessLogicException("Failed to get a list of available DoD grants");
        }

        // Keeps track of the total amount of funds available in the DoD grants
        double nonDoDGrantTotalAvailable = 0;

        // Holds non-DoD grants
        List<Grant> nonDoDGrants = app.getGrantList();

        // Remove all the DoD grants from all the grants to leave only non-DoD grants
        nonDoDGrants.removeAll(dodGrants);

        // This loop adds up the total amount available from the non-DoD grants
        for (Grant grant : nonDoDGrants)
        {
            try
            {
                nonDoDGrantTotalAvailable += GrantDBWrapper
                        .getGrantBalance(grant.getGrantAccount());
            }
            catch (KeyNotFoundException e)
            {
                throw new BusinessLogicException("Could not grab grant balance from grant: "
                        + grant.getGrantAccount(), e);
            }
        }

        // Holds meal expenses
        List<MealExpense> mealExpenses = app.getMealExpenseList();

        // Holds only allowed meal expenses
        List<MealExpense> allowedMealExpenses = new ArrayList<MealExpense>();

        /*
         * This for-loop breaks apart allowed meal expenses vs. non-allowed meal expenses. DoD
         * grants only allow Lunch and Dinner claims.
         */
        for (MealExpense me : mealExpenses)
        {
            if (me.getType() == MealTypeEnum.BREAKFAST)
            {

                // Country where the meal was claimed
                String country = me.getCountry();
                double perDiem = 0;

                try
                {
                    // See if the country is USA
                    if (country.compareToIgnoreCase(TRAPConstants.USA) == 0)
                    {
                        perDiem = PerDiemDBWrapper.getDomesticPerDiemMeal(me.getCity(),
                                me.getState(), me.getType());

                        // Add an allowed meal expense
                        allowedMealExpenses.add(me);
                    }
                }
                catch (KeyNotFoundException e)
                {
                    throw new BusinessLogicException("Failed to find per diem for meal expense", e);
                }

                // If the per diem is greater than the total amount of non-DoD grants
                if (perDiem > nonDoDGrantTotalAvailable)
                {
                    throw new BusinessLogicException(
                            String.format(
                                    "Meal per diem of $%.2f is greater than $%.2f in available non-DoD grants",
                                    perDiem, nonDoDGrantTotalAvailable));
                }
            }
        }

        // All meal checks under DoD grants have passed
        return;

    }
}
