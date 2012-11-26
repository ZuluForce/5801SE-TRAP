// EmailAddressValidator.java
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
public class EmailAddressValidator extends InputValidationRule
{
    /** Logger for the EmailAddressValidator class */
    private static Logger log = LoggerFactory.getLogger(EmailAddressValidator.class);

    @Override
    public void checkRule(ReimbursementApp app) throws InputValidationException,
            FormProcessorException
    {
        // Make sure we have an email address for the user
        String emailAddress = app.getUserInfo().getEmailAddress();
        boolean isValid;

        // TODO Need to look up if this returns null or an empty string
        if (emailAddress == null)
        {
            throw new InputValidationException("Missing email address");
        }

        isValid = isValidEmailAddress(emailAddress);

        // TODO Make a better error message
        // Make sure the email address entered matches the valid email address format
        if (isValid == false)
        {
            throw new InputValidationException(
                    "Email address is not valid. example@example.com is a valid email format.");
        }
    }

    public boolean isValidEmailAddress(String emailAddress)
    {
        String expression = "^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence input = emailAddress;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
