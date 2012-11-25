// DoDGrantRestrictions.java
package edu.umn.se.trap.rules.business;

import java.util.ArrayList;
import java.util.List;

import edu.umn.se.trap.data.Grant;
import edu.umn.se.trap.data.MealExpense;
import edu.umn.se.trap.data.MealTypeEnum;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TRAPConstants;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.data.TransportationTypeEnum;
import edu.umn.se.trap.db.GrantDB;
import edu.umn.se.trap.db.GrantDBWrapper;
import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.db.PerDiemDBWrapper;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * @author nagell2008
 * 
 */
public class DoDGrantRestrictions extends BusinessLogicRule
{

    /*
     * (non-Javadoc)
     * @see edu.umn.se.trap.rules.TRAPRule#checkRule(edu.umn.se.trap.data.ReimbursementApp)
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {

        List<Grant> grants = app.getGrantList();

        List<Grant> dodGrants = new ArrayList<Grant>();
        List<Grant> otherGrants = new ArrayList<Grant>();
        List<Object> grantInfo;

        double dodGrantTotalAvailable = 0;
        double otherGrantTotalAvailable = 0;

        String grantOrganizationType = "";

        for (Grant grant : grants)
        {
            try
            {
                grantInfo = GrantDBWrapper.getGrantInfo(grant.getGrantAccount());
            }
            catch (KeyNotFoundException e)
            {
                throw new BusinessLogicException("Could not grab grant information for grant: "
                        + grant.getGrantAccount(), e);
            }
            grantOrganizationType = (String) grantInfo.get(GrantDB.GRANT_FIELDS.ORGANIZATION_TYPE
                    .ordinal());
            if (grantOrganizationType.compareToIgnoreCase("DoD") == 0)
            {
                dodGrants.add(grant);
                try
                {
                    dodGrantTotalAvailable += GrantDBWrapper.getGrantBalance(grant
                            .getGrantAccount());
                }
                catch (KeyNotFoundException e)
                {
                    throw new BusinessLogicException(
                            "Could not grab grant account balance for grant: "
                                    + grant.getGrantAccount(), e);
                }
            }
            else
            {
                otherGrants.add(grant);
                try
                {
                    otherGrantTotalAvailable += GrantDBWrapper.getGrantBalance(grant
                            .getGrantAccount());
                }
                catch (KeyNotFoundException e)
                {
                    throw new BusinessLogicException(
                            "Could not grab grant account balance for grant: "
                                    + grant.getGrantAccount(), e);
                }
            }
        }

        if (dodGrants.size() == 0)
        {
            // No DoD grants to charge, so it is safe to return
            return;
        }

        List<TransportationExpense> transportationExpenses = app.getTransportationExpenseList();
        List<TransportationExpense> allowedCarRentalTransportationExpenses = new ArrayList<TransportationExpense>();
        List<TransportationExpense> otherCarRentalTransportationExpenses = new ArrayList<TransportationExpense>();
        List<TransportationExpense> allowedAirTravelExpenses = new ArrayList<TransportationExpense>();
        List<TransportationExpense> otherAirTravelExpenses = new ArrayList<TransportationExpense>();
        double carRentalTotalExpense = 0;
        double airTotalExpense = 0;

        for (TransportationExpense texpense : transportationExpenses)
        {
            if (texpense.getTransportationRental().compareToIgnoreCase("yes") == 0)
            {
                if (texpense.getTransportationCarrier().compareToIgnoreCase("Hertz") == 0)
                {
                    allowedCarRentalTransportationExpenses.add(texpense);
                    carRentalTotalExpense += texpense.getExpenseAmount();
                }
                else
                {
                    otherCarRentalTransportationExpenses.add(texpense);
                }

                continue;
            }

            if (texpense.getTransportationType() == TransportationTypeEnum.AIR)
            {
                if (texpense.getOriginalCurrency().compareToIgnoreCase(TRAPConstants.USD) == 0)
                {
                    allowedAirTravelExpenses.add(texpense);
                    airTotalExpense += texpense.getExpenseAmount();

                }
                else
                {
                    otherAirTravelExpenses.add(texpense);
                }
            }
        }

        /*
         * Only DoD grants are available, no claimable Hertz car rentals, but there are other car
         * expense claims, throw an exception
         */
        if (otherGrants.size() == 0 && allowedCarRentalTransportationExpenses.size() == 0
                && otherCarRentalTransportationExpenses.size() > 0)
        {
            throw new BusinessLogicException("DoD grants can only reimburse for Hertz rental cars");
        }

        /*
         * If there is not enough money for car rental expenses, throw an exception.
         */
        if (carRentalTotalExpense > dodGrantTotalAvailable)
        {
            throw new BusinessLogicException("Car rental charges of $" + carRentalTotalExpense
                    + " are not covered by DoD grants ($" + dodGrantTotalAvailable
                    + " total available)");
        }

        if (otherGrants.size() == 0 && allowedAirTravelExpenses.size() == 0
                && otherAirTravelExpenses.size() > 0)
        {
            throw new BusinessLogicException(
                    "DoD grants can only reimburse for domestic air travel");
        }

        if (airTotalExpense > dodGrantTotalAvailable)
        {
            throw new BusinessLogicException("Air travel charges of $" + airTotalExpense
                    + " are not covered by DoD grants ($" + dodGrantTotalAvailable
                    + " total available)");
        }

        List<MealExpense> mealExpenses = app.getMealExpenseList();
        List<MealExpense> allowedMealExpenses = new ArrayList<MealExpense>();
        double mealExpensesTotal = 0;

        for (MealExpense me : mealExpenses)
        {
            if (me.getType() == MealTypeEnum.LUNCH || me.getType() == MealTypeEnum.DINNER)
            {
                allowedMealExpenses.add(me);

                String country = me.getCountry();
                double perDiem = 0;

                try
                {
                    if (country.compareToIgnoreCase("USA") == 0)
                    {
                        perDiem = PerDiemDBWrapper.getDomesticPerDiemMeal(me.getCity(),
                                me.getState(), me.getType());
                    }
                    else
                    {
                        perDiem = PerDiemDBWrapper.getInternationalPerDiemMeal(me.getCity(),
                                me.getCountry(), me.getType());
                    }
                }
                catch (KeyNotFoundException e)
                {
                    throw new BusinessLogicException("Failed to find per diem for meal expense", e);
                }

                mealExpensesTotal += perDiem;
            }
        }

        if (allowedMealExpenses.size() == 0 && otherGrants.size() == 0)
        {
            throw new BusinessLogicException(
                    "There appear to only be DoD grants with only breakfast charges. DoD grants do not reimburse for breakfast charges.");
        }

        if (mealExpensesTotal > dodGrantTotalAvailable)
        {
            throw new BusinessLogicException("Meal charges of $" + mealExpensesTotal
                    + " are not covered by DoD grants ($" + dodGrantTotalAvailable
                    + " total available)");
        }

        if (mealExpensesTotal + carRentalTotalExpense + airTotalExpense > dodGrantTotalAvailable)
        {
            throw new BusinessLogicException("Total car rental, meal and air travel charges of $"
                    + (mealExpensesTotal + carRentalTotalExpense + airTotalExpense)
                    + " are not covered by DoD grants ($" + dodGrantTotalAvailable
                    + " total available)");
        }

        return;

    }
}
