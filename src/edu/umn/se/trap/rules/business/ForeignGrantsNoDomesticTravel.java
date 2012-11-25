// ForeignGrantsNoDomesticTravel.java
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
 * Foreign grants are not allowed to pay for domestic travel within the United States.
 * 
 * @author nagell2008
 * 
 */
public class ForeignGrantsNoDomesticTravel extends BusinessLogicRule
{

    /**
     * Checks all expenses and ensures that domestic expenses can be funded under non-foreign
     * grants. If not, an exception will be thrown.
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        List<Date> domesticDates = new ArrayList<Date>();

        List<Grant> foreignGrants = null;
        try
        {
            foreignGrants = GrantDBWrapper.getForeignGrants(app.getGrantList());
        }
        catch (KeyNotFoundException e)
        {
            throw new FormProcessorException("Failed to get foreign grant list");
        }

        // Find the total funding available for DoD and non-DoD grants
        Double foreignFunds = 0.0;
        Double nonForeignFunds = 0.0;
        for (Grant grant : app.getGrantList())
        {
            try
            {
                if (foreignGrants.contains(grant))
                {
                    foreignFunds += GrantDBWrapper.getGrantBalance(grant.getGrantAccount());
                }
                else
                {
                    nonForeignFunds += GrantDBWrapper.getGrantBalance(grant.getGrantAccount());
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
            Boolean domesticDay, domesticExpense;
            domesticDay = domesticExpense = false;

            String country = null;

            // Check the incidental for the day
            IncidentalExpense incidentalExpense = day.getIncidentalExpense();
            if (incidentalExpense != null)
            {
                domesticExpense = false;

                country = incidentalExpense.getCountry();

                domesticExpense = (country.compareToIgnoreCase(TRAPConstants.USA) == 0);
                if (domesticExpense)
                {
                    // Check that the expense amount fits within the non dod funds
                    if (incidentalExpense.getExpenseAmount() > nonForeignFunds)
                    {
                        throw new BusinessLogicException(
                                "Domestic incidental expense cannot be funded under foreign grants. Not enough $ in non-foreign grants");
                    }
                }

                // Keep track if this day has foreign expenses
                domesticDay |= domesticExpense;
            }

            // Check the lodging for the day
            LodgingExpense lodgingExpense = day.getLodgingExpense();
            if (lodgingExpense != null)
            {
                domesticExpense = false;

                country = lodgingExpense.getCountry();
                domesticExpense = (country.compareToIgnoreCase(TRAPConstants.USA) == 0);
                if (domesticExpense)
                {
                    // Check that the expense amount fits within the non dod funds
                    if (lodgingExpense.getExpenseAmount() > nonForeignFunds)
                    {
                        throw new BusinessLogicException(
                                "Domestic lodging expense cannot be funded under foreign grants. Not enough $ in non-foreign grants");
                    }
                }

                // Keep track if this day has foreign expenses
                domesticDay |= domesticExpense;
            }

            // Check the meal expenses for the day
            for (MealExpense expense : day.getMealExpenses())
            {
                domesticExpense = false;

                country = expense.getCountry();
                domesticExpense = (country.compareToIgnoreCase(TRAPConstants.USA) == 0);
                if (domesticExpense)
                {
                    // Meals run on a per diem so get that first
                    Double perDiem = Double.MAX_VALUE;
                    try
                    {
                        perDiem = PerDiemDBWrapper.getDomesticPerDiemMeal(expense.getCity(),
                                expense.getState(), expense.getType());
                    }
                    catch (KeyNotFoundException e)
                    {
                        throw new FormProcessorException(String.format(
                                "Cannot find per diem for %s meal on %s", expense.getType()
                                        .toString(), expense.getExpenseDate()));
                    }

                    // Check that the expense amount fits within the non dod funds
                    if (perDiem > nonForeignFunds)
                    {
                        throw new BusinessLogicException(
                                "Domestic meal expense cannot be funded under foreign grants. Not enough $ in non-foreign grants");
                    }
                }

                // Keep track if this day has foreign expenses
                domesticDay |= domesticExpense;
            }

            // If there was something foreign then add it to the foreignDates list
            if (domesticDay)
            {
                Date baseDate = app.getDepartureDatetime();
                Date foreignDate = DateValidator
                        .advanceDateInDays(baseDate, day.getDayNumber() - 1);

                domesticDates.add(foreignDate);
            }
        }

        // Check that there are no transportation expenses on a foreign date
        for (TransportationExpense texpense : app.getTransportationExpenseList())
        {
            boolean domesticCurrency = (texpense.getExpenseCurrency().compareToIgnoreCase(
                    TRAPConstants.USD) == 0);
            if (domesticDates.contains(texpense.getExpenseDate()) || domesticCurrency)
            {
                if (texpense.getExpenseAmount() > nonForeignFunds)
                {
                    throw new BusinessLogicException(
                            "Domestic transportation expense cannot be funded under foreign grant and there are insufficient funds available elsewhere.");
                }
            }
        }

        // Check that there are no transportation expenses on a foreign date
        for (OtherExpense otherExpense : app.getOtherExpenseList())
        {
            boolean domesticCurrency = (otherExpense.getExpenseCurrency().compareToIgnoreCase(
                    TRAPConstants.USD) == 0);

            if (domesticDates.contains(otherExpense.getExpenseDate()) || domesticCurrency)
            {
                if (otherExpense.getExpenseAmount() > nonForeignFunds)
                {
                    throw new BusinessLogicException(
                            "Domestic other expense cannot be funded under foreign grant and there are insufficient funds available elsewhere.");
                }
            }
        }
    }

}
