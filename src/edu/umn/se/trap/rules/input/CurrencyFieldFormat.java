// CurrencyFieldFormat.java
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
public class CurrencyFieldFormat extends InputValidationRule
{

    private static Logger log = LoggerFactory.getLogger(CurrencyFieldFormat.class);
    
    private final static String currencyFieldFormat = new String("USD");
    
    private final static int LENGTH_OF_FIELD = 3;
    

    @Override
    public void checkRule(ReimbursementApp app) throws InputValidationException,
            FormProcessorException
    {
        
        String currencyField = "USD";

    }

}
