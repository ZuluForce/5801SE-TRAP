// EmailAddressValidator.java
package edu.umn.se.trap.rules.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.exception.InputValidationException;

/**
 * @author Dylan
 * 
 */
public class EmailAddressValidator extends InputValidationRule
{
    private static Logger log = LoggerFactory.getLogger(EmailAddressValidator.class);

    @Override
    public void checkRule(ReimbursementApp app) throws InputValidationException,
            FormProcessorException
    {

    }
}
