// PhoneNumberValidator.java
package edu.umn.se.trap.rules.input;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /** TRAP format for a phone number */

    @Override
    public void checkRule(ReimbursementApp app) throws InputValidationException,
            FormProcessorException
    {
        String phoneNumber = app.getUserInfo().getEmergencycontactPhone();
        boolean isValid;

        if (phoneNumber == null)
        {
            throw new InputValidationException("Missing emergency contact phone number");
        }

        isValid = isValidPhoneNumber(phoneNumber);

        // TODO Write a better exception message.
        if (isValid == false)
        {
            throw new InputValidationException(String.format(
                    "The phone number (%s) is not formatted correctly.", phoneNumber));
        }

    }

    public boolean isValidPhoneNumber(String phoneNumber)
    {
        String expression = "\\d{3}-\\d{3}-\\d{4}";
        CharSequence input = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
