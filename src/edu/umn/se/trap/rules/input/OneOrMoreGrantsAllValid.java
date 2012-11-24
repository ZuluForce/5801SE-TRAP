// OneOrMoreGrantsAllValid.java
package edu.umn.se.trap.rules.input;

import java.util.Iterator;
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
public class OneOrMoreGrantsAllValid extends InputValidationRule
{
    /** Logger for the OneOrMoreGrantsAllValid class */
    private static Logger log = LoggerFactory.getLogger(OneOrMoreGrantsAllValid.class);

    @Override
    public void checkRule(ReimbursementApp app) throws InputValidationException,
            FormProcessorException
    {
        List<Grant> submittedGrants = app.getGrantList();
        Iterator iter = submittedGrants.iterator();

        // TODO Change exception message
        if (submittedGrants.isEmpty())
        {
            throw new InputValidationException("No grants were submitted");
        }

        while (iter.hasNext())
        {
            // GrantDBWrapper.getGrantInfo(iter.);
        }

    }
}
