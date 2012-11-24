// AlcoholOnlyAllowedUnderNonSponsored.java
package edu.umn.se.trap.rules.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.LodgingExpense;
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
public class LodgingPerDiem extends BusinessLogicRule
{
    /** Logger for the PerDiemLodgingCeiling class */
    private static final Logger log = LoggerFactory.getLogger(LodgingPerDiem.class);

    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        for (TripDay day : app.getAllTripDays())
        {
            LodgingExpense expense = day.getLodgingExpense();
            if (expense == null)
            {
                log.info("No lodging expense on day {}. Skipping per diem check.",
                        day.getDayNumber());
                continue;
            }
            String country = expense.getCountry();

            Double perDiem = null;

            try
            {
                if (country.compareToIgnoreCase("USA") == 0)
                {
                    perDiem = PerDiemDBWrapper.getDomesticPerDiemLodging(expense.getCity(),
                            expense.getState());
                }
                else
                {
                    perDiem = PerDiemDBWrapper.getInternationalPerDiemLodging(expense.getCity(),
                            expense.getCountry());
                }
            }
            catch (KeyNotFoundException notFound)
            {
                log.error("Could not find lodging per diem for expense:\n{}", expense);
                throw new BusinessLogicException(
                        "Could not find per diem for lodging expense on day " + day.getDayNumber(),
                        notFound);
            }

            // Make sure the claimed expense amount is no more than the per diem
            if (expense.getExpenseAmount() > perDiem)
            {
                throw new BusinessLogicException(String.format(
                        "Loding expense ($%f) is greater than the perDiem ($%f)",
                        expense.getExpenseAmount(), perDiem));
            }

            // Add expense amount to the day total (and reimbursement total)
            app.addtoPerDiemTotal(expense.getExpenseAmount(), day.getDayNumber());
        }

    }
}
