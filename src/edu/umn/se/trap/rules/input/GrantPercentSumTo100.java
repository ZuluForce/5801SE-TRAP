// GrantPercentSumTo100.java
package edu.umn.se.trap.rules.input;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.Grant;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.exception.InputValidationException;

/**
 * @author Dylan
 * 
 */
public class GrantPercentSumTo100 extends InputValidationRule
{
    /** Logger for the GrantPercentSumTo100 class */
    private static Logger log = LoggerFactory.getLogger(GrantPercentSumTo100.class);

    @Override
    public void checkRule(ReimbursementApp app) throws InputValidationException,
            FormProcessorException
    {
        List<Grant> grants = app.getGrantList();
        Integer grantTotal = 0;

        for (Grant grant : grants)
        {
            grantTotal += grant.getGrantPercentage();
        }

        if (grantTotal != 100)
        {
            throw new InputValidationException(String.format(
                    "Grant percentages do not sum to 100% (%d%%)", grantTotal));
        }
    }

}
