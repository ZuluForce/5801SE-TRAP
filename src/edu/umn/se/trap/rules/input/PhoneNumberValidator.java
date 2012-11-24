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
    /** Logger for the PhoneNumberValidator class */
    private static Logger log = LoggerFactory.getLogger(PhoneNumberValidator.class);

    // TODO Figure out what to use for a formatter
    /** TRAP format for a phone number */
    private final static String phoneNumberRegex = "\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d";

    @Override
    public void checkRule(ReimbursementApp app) throws InputValidationException,
            FormProcessorException
    {

        // TODO Need phone number. From userInfo object?
        String phoneNumber = app.getUserInfo().getEmergencycontactPhone();

        if (phoneNumber == null)
        {
            throw new InputValidationException("Missing phone number");
        }

        // TODO Write a better exception message.
        if (!phoneNumberRegex.matches(phoneNumber))
        {
            throw new InputValidationException("The phone number is not formatted correctly.");
        }

    }
}
