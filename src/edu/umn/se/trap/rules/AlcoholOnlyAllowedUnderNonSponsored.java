// AlcoholOnlyAllowedUnderNonSponsored.java
package edu.umn.se.trap.rules;

import java.util.List;

import edu.umn.se.trap.data.Grant;
import edu.umn.se.trap.data.OtherExpense;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.TRAPException;

/**
 * @author nagell2008
 * 
 */
public class AlcoholOnlyAllowedUnderNonSponsored extends BusinessLogicRule
{

    /*
     * (non-Javadoc)
     * @see edu.umn.se.trap.rules.TRAPRule#checkRule(edu.umn.se.trap.data.ReimbursementApp)
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        List<Grant> grants = app.getGrantList();

        List<OtherExpense> otherExpenses = app.getOtherExpenseList();

        return;
    }

}
