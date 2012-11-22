// PhoneNumberValidator.java
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
public class PhoneNumberValidator extends InputValidationRule
{

    private static Logger log = LoggerFactory.getLogger(PhoneNumberValidator.class);

    // TODO Figure out what to use for a formatter
    private final static String phoneNumberFormat = new String();

    @Override
    public void checkRule(ReimbursementApp app) throws InputValidationException,
            FormProcessorException
    {

        // TODO Need phone number. From userInfo object?
        // String phoneNumber = app.getUserInfo()
        String phoneNumber = "1234567890";

        if (phoneNumber == null)
        {
            throw new InputValidationException("Missing phone number");
        }

        // TODO This depends on figuring out the phoneNumber formatter
        if (phoneNumber == "blah")
        {
            throw new InputValidationException(
                    "The phone number is not formatted correctly. Please use the format dddddddddd where d is any number");
        }

    }
}
