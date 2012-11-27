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
// PhoneNumberValidator.java
package edu.umn.se.trap.rules.input;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.exception.InputValidationException;

/**
 * Check that phone numbers are formatted in a specific format (for consistency). This format is
 * ddd-ddd-dddd where d is a positive integer
 * 
 * 
 * @author Dylan
 * 
 */
public class PhoneNumberValidator extends InputValidationRule
{
    /**
     * Check that phone numbers are formatted in a specific format (for consistency). This format is
     * ddd-ddd-dddd where d is a positive integer
     */
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

        if (isValid == false)
        {
            throw new InputValidationException(
                    String.format(
                            "The phone number (%s) is not formatted correctly. The correct format is 123-456-7890.",
                            phoneNumber));
        }

    }

    /**
     * Test the submitted phone number against a regular expression to check for valid format.
     * 
     * @param phoneNumber - phone number associated with a trap user
     * @return - a boolean representing if the phone number format is valid or not
     */
    public boolean isValidPhoneNumber(String phoneNumber)
    {
        String expression = "\\d{3}-\\d{3}-\\d{4}";
        CharSequence input = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}
