/*****************************************************************************************
 * Copyright (c) 2012 Dylan Bettermann, Andrew Helgeson, Brian Maurer, Ethan Waytas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 ****************************************************************************************/
// EmailAddressValidator.java
package edu.umn.se.trap.rules.input;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.exception.InputValidationException;

/**
 * Check that email addresses are in a specific format (for consistency). This format is
 * example@example.com.
 * 
 * @author Dylan
 * 
 */
public class EmailAddressValidator extends InputValidationRule
{
    /**
     * Check that email addresses are in a specific format (for consistency). This format is
     * example@example.com.
     */
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

    /**
     * Test the submitted email address against a regular expression to check for valid format.
     * 
     * @param emailAddress - email address associated with a trap user
     * @return - a boolean representing if the email address format is valid or not
     */
    public boolean isValidEmailAddress(String emailAddress)
    {
        String expression = "^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence input = emailAddress;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
