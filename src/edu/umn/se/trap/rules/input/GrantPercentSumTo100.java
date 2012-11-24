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
    private static Logger log = LoggerFactory.getLogger(GrantPercentSumTo100.class);

    @Override
    public void checkRule(ReimbursementApp app) throws InputValidationException,
            FormProcessorException
    {
        List<Grant> grants = app.getGrantList();
        int grantTotal = 0;

        // TODO Reword exception message
        if (grants.isEmpty())
        {
            throw new InputValidationException("Missing grants");
        }

        for (Grant grant : grants)
        {
            grantTotal += grant.getGrantPercentage();
        }

        // TODO put inside for loop?
        if (grantTotal > 100)
        {
            throw new InputValidationException("Total reimbursement exceeds total expenses");
        }

        // TODO what if grants do not sum to 100%? Leave it?
    }

}
