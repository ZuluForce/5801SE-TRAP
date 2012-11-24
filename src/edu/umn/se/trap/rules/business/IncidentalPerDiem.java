// AlcoholOnlyAllowedUnderNonSponsored.java
package edu.umn.se.trap.rules.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.IncidentalExpense;
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
public class IncidentalPerDiem extends BusinessLogicRule
{
    /** Logger for the IncidentalPerDiem class */
    private static final Logger log = LoggerFactory.getLogger(IncidentalExpense.class);

    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {

        for (TripDay day : app.getAllTripDays())
        {
            IncidentalExpense expense = day.getIncidentalExpense();
            if (expense == null)
            {
                log.info("No incidental expense on day {}. Skipping per diem check.",
                        day.getDayNumber());
                continue;
            }
            String country = expense.getCountry();
            Double perDiem = null;

            try
            {
                if (country.compareToIgnoreCase("USA") == 0)
                {
                    perDiem = PerDiemDBWrapper.getDomesticPerDiemIncidental(expense.getCity(),
                            expense.getState());
                }
                else
                {
                    perDiem = PerDiemDBWrapper.getInternationalPerDiemIncidental(expense.getCity(),
                            expense.getCountry());
                }
            }
            catch (KeyNotFoundException e)
            {
                log.error("Could not find per diem for the incidental expense:\n{}", expense);
                throw new BusinessLogicException("Could not find per diem for incidental on day "
                        + day.getDayNumber(), e);
            }

            // Make sure the expense is within the per diem
            if (expense.getExpenseAmount() > perDiem)
            {
                throw new BusinessLogicException(String.format(
                        "Incidental expense ($%f) greater than per diem ($%f)",
                        expense.getExpenseAmount(), perDiem));
            }

            app.addtoPerDiemTotal(expense.getExpenseAmount(), day.getDayNumber());
        }

    }
}
