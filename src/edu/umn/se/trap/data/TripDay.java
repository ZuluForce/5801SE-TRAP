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
// TripDay.java
package edu.umn.se.trap.data;

import java.util.ArrayList;
import java.util.List;

import edu.umn.se.trap.exception.InputValidationException;

/**
 * The TripDay class contains all the PerDiemExpenses for one day in the trip. This is convenient
 * for particular rules which work on a per day notion.
 * 
 * @author andrewh
 * 
 */
public class TripDay
{
    /** Day on the trip. The first day has a day number of 1 */
    Integer dayNumber;

    /** List of meal expenses for this day */
    List<MealExpense> mealExpenses;

    /** The lodging expense for this day */
    LodgingExpense lodgingExpense;

    /** The incidental expense for this day */
    IncidentalExpense incidentalExpense;

    /** Total reimbursement amount for this day */
    Double dayTotal;

    /**
     * Constructor for a TripDay. Initializes all internal values. Day is set to the provided
     * number, meal expenses list created, lodging/incidental expenses are set to null and the
     * dayTotal is set to 0.
     * 
     * @param dayNumber - The number of the day in the trip this TripDay is to represnet. The first
     *            day of the trip should be 1.
     */
    public TripDay(Integer dayNumber)
    {
        this.dayNumber = dayNumber;

        mealExpenses = new ArrayList<MealExpense>();

        lodgingExpense = null;
        incidentalExpense = null;

        dayTotal = 0.0;
    }

    /**
     * Get the day of on the trip of this TripDay.
     * 
     * @return - The day of the trip
     */
    public Integer getDayNumber()
    {
        return dayNumber;
    }

    /**
     * Get the total reimbursement amount for this day of the trip. This is only the total for per
     * diem expenses.
     * 
     * @return - The total for all per diem expenses on this day of the trip.
     */
    public Double getDayTotal()
    {
        return dayTotal;
    }

    /**
     * Add to the reimbursement total for the per diem expenses on this day of the trip.
     * 
     * @param amount - The amount to add for this day of the trip.
     */
    public void addToDayTotal(Double amount)
    {
        dayTotal += amount;
    }

    /**
     * Get the list of meal expenses for this day.
     * 
     * @return - The list of meal expenses for this day
     */
    public List<MealExpense> getMealExpenses()
    {
        return mealExpenses;
    }

    /**
     * Add a meal expense to the day's list.
     * 
     * @param mealExpense - the meal expense to add
     * @throws InputValidationException When there are already 3 meal expenses set for this day.
     */
    public void addMealExpenses(MealExpense mealExpense) throws InputValidationException
    {
        if (mealExpenses.size() >= 3)
        {
            throw new InputValidationException("More than 3 meal expenses claimed for day "
                    + dayNumber);
        }

        mealExpenses.add(mealExpense);
    }

    /**
     * Get the lodging expense for this day. It is null if not set.
     * 
     * @return - The lodging expense for this day
     */
    public LodgingExpense getLodgingExpense()
    {
        return lodgingExpense;
    }

    /**
     * Set the lodging expense for this day
     * 
     * @param expense - The lodging expense to set for this day
     * @throws InputValidationException When a lodging expense has already been set
     */
    public void setLodgingExpense(LodgingExpense expense) throws InputValidationException
    {
        if (lodgingExpense != null)
        {
            throw new InputValidationException("Cannot have more than one lodging expense on day "
                    + dayNumber);
        }

        lodgingExpense = expense;
    }

    /**
     * Get the incidental expense for this day. It is null if not set.
     * 
     * @return - The incidental expense for this day.
     */
    public IncidentalExpense getIncidentalExpense()
    {
        return incidentalExpense;
    }

    /**
     * Set the incidental expense for this day of the trip.
     * 
     * @param expense - The incidental expense to set
     * @throws InputValidationException If the day's incidental expense has already been set
     */
    public void setIncidentalExpense(IncidentalExpense expense) throws InputValidationException
    {
        if (incidentalExpense != null)
        {
            throw new InputValidationException("Cannot have more than one incidental on day "
                    + dayNumber);
        }
        incidentalExpense = expense;
    }

    /**
     * Get all the expenses for this day. If an incidental or lodging expense is not set (ie null)
     * it will not be added to the list.
     * 
     * @return - the list of all expenses for this day
     */
    public List<PerDiemExpense> getAllExpenses()
    {
        List<PerDiemExpense> expenses = new ArrayList<PerDiemExpense>();

        if (lodgingExpense != null)
            expenses.add(lodgingExpense);
        if (incidentalExpense != null)
            expenses.add(incidentalExpense);
        expenses.addAll(mealExpenses);

        return expenses;
    }
}
