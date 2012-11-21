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
// TripDay.java
package edu.umn.se.trap.data;

import java.util.ArrayList;
import java.util.List;

import edu.umn.se.trap.exception.InputValidationException;

/**
 * @author planeman
 * 
 */
public class TripDay
{
    Integer dayNumber;

    List<MealExpense> mealExpenses;
    LodgingExpense lodgingExpense;
    IncidentalExpense incidentalExpense;

    Double dayTotal;

    public TripDay(Integer dayNumber)
    {
        this.dayNumber = dayNumber;

        mealExpenses = new ArrayList<MealExpense>();

        lodgingExpense = null;
        incidentalExpense = null;

        dayTotal = 0.0;
    }

    public Integer getDayNumber()
    {
        return dayNumber;
    }

    public Double getDayTotal()
    {
        return dayTotal;
    }

    public void addToDayTotal(Double amount)
    {
        dayTotal += amount;
    }

    public List<MealExpense> getMealExpenses()
    {
        return mealExpenses;
    }

    public void addMealExpenses(MealExpense mealExpense) throws InputValidationException
    {
        if (mealExpenses.size() >= 3)
        {
            throw new InputValidationException("More than 3 meal expenses claimed for day "
                    + dayNumber);
        }

        mealExpenses.add(mealExpense);
    }

    public LodgingExpense getLodgingExpense()
    {
        return lodgingExpense;
    }

    public void setLodgingExpense(LodgingExpense expense) throws InputValidationException
    {
        if (lodgingExpense != null)
        {
            throw new InputValidationException("Cannot have more than one lodging expense on day "
                    + dayNumber);
        }

        lodgingExpense = expense;
    }

    public IncidentalExpense getIncidentalExpense()
    {
        return incidentalExpense;
    }

    public void setIncidentalExpense(IncidentalExpense expense) throws InputValidationException
    {
        if (incidentalExpense != null)
        {
            throw new InputValidationException("Cannot have more than one incidental on day "
                    + dayNumber);
        }
        incidentalExpense = expense;
    }

    public List<PerDiemExpense> getAllExpenses()
    {
        List<PerDiemExpense> expenses = new ArrayList<PerDiemExpense>();

        expenses.addAll(mealExpenses);
        expenses.add(incidentalExpense);
        expenses.add(lodgingExpense);

        return expenses;
    }
}
