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
 * This rule checks all the requirements of being reimbursed under a DoD grant. Specifically, DoD
 * grants do not fund breakfast, only fund car rental through Hertz, and air travel must be
 * domestic.
 * 
 * @author nagell2008
 * 
 */
public class DoDGrantRestrictions extends BusinessLogicRule
{

    /**
     * This rule checks all the requirements of being reimbursed under a DoD grant. Specifically,
     * DoD grants do not fund breakfast, only fund car rental through Hertz, and air travel must be
     * domestic.
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        // Holds all available grants
        List<Grant> grants = app.getGrantList();

        // Holds DoD grants
        List<Grant> dodGrants = new ArrayList<Grant>();

        // Holds non-DoD grants
        List<Grant> otherGrants = new ArrayList<Grant>();

        // Temporary variable to hold grant information
        List<Object> grantInfo;

        // Keeps track of the total amount of funds available in the DoD grants
        double dodGrantTotalAvailable = 0;

        // Keeps track of the total amount of funds available in the non-DoD grants
        double otherGrantTotalAvailable = 0;

        // Temporary variable to hold the organization type of a grant
        String grantOrganizationType = "";

        /*
         * This loops breaks apart the DoD grants and the non-DoD grants
         */
        for (Grant grant : grants)
        {
            try
            {
                // Hold information related to the current grant
                grantInfo = GrantDBWrapper.getGrantInfo(grant.getGrantAccount());
            }
            catch (KeyNotFoundException e)
            {
                throw new BusinessLogicException("Could not grab grant information for grant: "
                        + grant.getGrantAccount(), e);
            }
            // Grab the organization type
            grantOrganizationType = (String) grantInfo.get(GrantDB.GRANT_FIELDS.ORGANIZATION_TYPE
                    .ordinal());

            // If the grant is DoD, we add it to the DoD grant list and update the total funds
            // available, other wise we do similar for non-DoD grants.
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

        // Hold all the transportation expenses
        List<TransportationExpense> transportationExpenses = app.getTransportationExpenseList();

        // Holds only allowed car rental expenses
        List<TransportationExpense> allowedCarRentalTransportationExpenses = new ArrayList<TransportationExpense>();

        // Holds car rental expenses that a DoD grant will not pay for
        List<TransportationExpense> otherCarRentalTransportationExpenses = new ArrayList<TransportationExpense>();

        // Holds air expenses that are domestic
        List<TransportationExpense> allowedAirTravelExpenses = new ArrayList<TransportationExpense>();

        // Holds air expenses that a DoD grant will not pay for
        List<TransportationExpense> otherAirTravelExpenses = new ArrayList<TransportationExpense>();

        // Total amount of allowed car rental expenses
        double carRentalTotalExpense = 0;

        // Total amount of air travel expenses
        double airTotalExpense = 0;

        /*
         * This for-loop has two purposes. First: If an expense is for a car rental, it is checked
         * that the carrier is Hertz and then added to the list (and the total amount is updated)
         * Second: If an expense is for air travel, it is checked that the currency that paid for
         * the ticket is in USD. At the moment, this is the only way to confirm that the travel was
         * domestic.
         */
        for (TransportationExpense texpense : transportationExpenses)
        {
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
         * Only DoD grants are available, no claimable air travel expenses, but there are other air
         * travel expense claims.
         */
        if (otherGrants.size() == 0 && allowedAirTravelExpenses.size() == 0
                && otherAirTravelExpenses.size() > 0)
        {
            throw new BusinessLogicException(
                    "DoD grants can only reimburse for domestic air travel");
        }

        /*
         * If there is not enough money for air travel expenses in the DoD grants, throw an
         * exception.
         */
        if (airTotalExpense > dodGrantTotalAvailable)
        {
            throw new BusinessLogicException("Air travel charges of $" + airTotalExpense
                    + " are not covered by DoD grants ($" + dodGrantTotalAvailable
                    + " total available)");
        }

        // Holds meal expenses
        List<MealExpense> mealExpenses = app.getMealExpenseList();

        // Holds only allowed meal expenses
        List<MealExpense> allowedMealExpenses = new ArrayList<MealExpense>();

        // Holds other meal expenses not reimbursable under a DoD grant.
        List<MealExpense> otherMealExpenses = new ArrayList<MealExpense>();

        // Running total of allowed meal expenses
        double mealExpensesTotal = 0;

        /*
         * This for-loop breaks apart allowed meal expenses vs. non-allowed meal expenses. DoD
         * grants only allow Lunch and Dinner claims.
         */
        for (MealExpense me : mealExpenses)
        {
            if (me.getType() == MealTypeEnum.LUNCH || me.getType() == MealTypeEnum.DINNER)
            {

                // Country where the meal was claimed
                String country = me.getCountry();
                double perDiem = 0;

                try
                {
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

                mealExpensesTotal += perDiem;
            }
            else
            {
                otherMealExpenses.add(me);
            }
        }

        /*
         * Only DoD grants are available, no claimable meal expenses, but there are other meal
         * travel expense claims.
         */
        if (allowedMealExpenses.size() == 0 && otherGrants.size() == 0
                && otherMealExpenses.size() > 0)
        {
            throw new BusinessLogicException(
                    "There appear to only be DoD grants with only breakfast charges. DoD grants do not reimburse for breakfast charges.");
        }

        /*
         * If there is not enough money for meal expenses in the DoD grants, throw an exception.
         */
        if (mealExpensesTotal > dodGrantTotalAvailable)
        {
            throw new BusinessLogicException("Meal charges of $" + mealExpensesTotal
                    + " are not covered by DoD grants ($" + dodGrantTotalAvailable
                    + " total available)");
        }

        /*
         * If there is not enough money for meal, travel and car rental expenses in the DoD grants,
         * throw an exception.
         */
        if ((mealExpensesTotal + airTotalExpense) > dodGrantTotalAvailable)
        {
            throw new BusinessLogicException("Total meal and air travel charges of $"
                    + (mealExpensesTotal + airTotalExpense) + " are not covered by DoD grants ($"
                    + dodGrantTotalAvailable + " total available)");
        }

        // All checks under DoD grants have passed
        return;

    }
}
