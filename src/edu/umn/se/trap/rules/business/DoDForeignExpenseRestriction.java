// DoDForeignExpenseRestriction.java
package edu.umn.se.trap.rules.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.umn.se.trap.data.Grant;
import edu.umn.se.trap.data.IncidentalExpense;
import edu.umn.se.trap.data.LodgingExpense;
import edu.umn.se.trap.data.MealExpense;
import edu.umn.se.trap.data.OtherExpense;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TRAPConstants;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.data.TripDay;
import edu.umn.se.trap.db.GrantDBWrapper;
import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.db.PerDiemDBWrapper;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.rules.input.DateValidator;

/**
 * @author planeman
 * 
 */
public class DoDForeignExpenseRestriction extends BusinessLogicRule
{

    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        List<Date> foreignDates = new ArrayList<Date>();

        List<Grant> dodGrants = null;
        try
        {
            dodGrants = GrantDBWrapper.getDODGrants(app.getGrantList());
        }
        catch (KeyNotFoundException e)
        {
            throw new FormProcessorException("Failed to get DoD grant list");
        }

        // Find the total funding available for DoD and non-DoD grants
        Double totalDoDFunds = 0.0;
        Double nonDoDFunds = 0.0;
        for (Grant grant : app.getGrantList())
        {
            try
            {
                if (dodGrants.contains(grant))
                {
                    totalDoDFunds += GrantDBWrapper.getGrantBalance(grant.getGrantAccount());
                }
                else
                {
                    nonDoDFunds += GrantDBWrapper.getGrantBalance(grant.getGrantAccount());
                }
            }
            catch (KeyNotFoundException notFound)
            {
                throw new FormProcessorException("Grant balance not found for account: " + notFound);
            }
        }

        // First check all per diem expenses to determine what days are 'foreign'
        for (TripDay day : app.getAllTripDays())
        {
            Boolean foreignDay, foreignExpense;
            foreignDay = foreignExpense = false;

            String country = null;

            // Check the incidental for the day
            IncidentalExpense incidentalExpense = day.getIncidentalExpense();
            if (incidentalExpense != null)
            {
                foreignExpense = false;

                country = incidentalExpense.getCountry();

                foreignExpense = (country.compareToIgnoreCase(TRAPConstants.USA) != 0);
                if (foreignExpense)
                {
                    // Check that the expense amount fits within the non dod funds
                    if (incidentalExpense.getExpenseAmount() > nonDoDFunds)
                    {
                        throw new BusinessLogicException(
                                "Foreign incidental expense cannot be funded under DoD grants. Not enough $ in non-DoD grants");
                    }
                }

                // Keep track if this day has foreign expenses
                foreignDay |= foreignExpense;
            }

            // Check the lodging for the day
            LodgingExpense lodgingExpense = day.getLodgingExpense();
            if (lodgingExpense != null)
            {
                foreignExpense = false;

                country = lodgingExpense.getCountry();
                foreignExpense = (country.compareToIgnoreCase(TRAPConstants.USA) != 0);
                if (foreignExpense)
                {
                    // Check that the expense amount fits within the non dod funds
                    if (lodgingExpense.getExpenseAmount() > nonDoDFunds)
                    {
                        throw new BusinessLogicException(
                                "Foreign lodging expense cannot be funded under DoD grants. Not enough $ in non-DoD grants");
                    }
                }

                // Keep track if this day has foreign expenses
                foreignDay |= foreignExpense;
            }

            // Check the meal expenses for the day
            for (MealExpense expense : day.getMealExpenses())
            {
                foreignExpense = false;

                country = expense.getCountry();
                foreignExpense = (country.compareToIgnoreCase(TRAPConstants.USA) != 0);
                if (foreignExpense)
                {
                    // Meals run on a per diem so get that first
                    Double perDiem = Double.MAX_VALUE;
                    try
                    {
                        perDiem = PerDiemDBWrapper.getInternationalPerDiemMeal(expense.getCity(),
                                expense.getState(), expense.getType());
                    }
                    catch (KeyNotFoundException e)
                    {
                        throw new FormProcessorException(String.format(
                                "Cannot find per diem for %s meal on %s", expense.getType()
                                        .toString(), expense.getExpenseDate()));
                    }

                    // Check that the expense amount fits within the non dod funds
                    if (perDiem > nonDoDFunds)
                    {
                        throw new BusinessLogicException(
                                "Foreign meal expense cannot be funded under DoD grants. Not enough $ in non-DoD grants");
                    }
                }

                // Keep track if this day has foreign expenses
                foreignDay |= foreignExpense;
            }

            // If there was something foreign then add it to the foreignDates list
            if (foreignDay)
            {
                Date baseDate = app.getDepartureDatetime();
                Date foreignDate = DateValidator
                        .advanceDateInDays(baseDate, day.getDayNumber() - 1);

                foreignDates.add(foreignDate);
            }
        }

        // Check that there are no transportation expenses on a foreign date
        for (TransportationExpense texpense : app.getTransportationExpenseList())
        {
            boolean foreignCurrency = (texpense.getExpenseCurrency().compareToIgnoreCase(
                    TRAPConstants.USD) != 0);
            if (foreignDates.contains(texpense.getExpenseDate()) || foreignCurrency)
            {
                if (texpense.getExpenseAmount() > nonDoDFunds)
                {
                    throw new BusinessLogicException(
                            "Foreign transportation expense cannot be funded under DoD grant and there are insufficient funds available elsewhere.");
                }
            }
        }

        // Check that there are no transportation expenses on a foreign date
        for (OtherExpense otherExpense : app.getOtherExpenseList())
        {
            boolean foreignCurrency = (otherExpense.getExpenseCurrency().compareToIgnoreCase(
                    TRAPConstants.USD) != 0);

            if (foreignDates.contains(otherExpense.getExpenseDate()) || foreignCurrency)
            {
                if (otherExpense.getExpenseAmount() > nonDoDFunds)
                {
                    throw new BusinessLogicException(
                            "Foreign other expense cannot be funded under DoD grant and there are insufficient funds available elsewhere.");
                }
            }
        }
    }
}
