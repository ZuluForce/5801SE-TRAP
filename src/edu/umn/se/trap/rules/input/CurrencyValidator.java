// CurrencyValidator.java
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
public class CurrencyValidator extends InputValidationRule
{

    private static Logger log = LoggerFactory.getLogger(CurrencyValidator.class);

    // TODO Need to figure out a formatter for this one
    // blah blah

    @Override
    public void checkRule(ReimbursementApp app) throws InputValidationException,
            FormProcessorException
    {

        // if null

        // if not numbers

        // if currency not recognized/supported <- ??
    }

}
