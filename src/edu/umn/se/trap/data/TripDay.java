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
