/*****************************************************************************************
 * Copyright (c) 2012 Dylan Bettermann, Andrew Helgeson, Brian Maurer, Ethan Waytas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ****************************************************************************************/
// UnknownKeyChecker.java
package edu.umn.se.trap.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.umn.se.trap.exception.InputValidationException;

/**
 * This class checks form data (a map) for any unknown keys.
 * 
 * @author planeman
 * 
 */
public class UnknownKeyChecker
{
    /** list of patterns for all known keys */
    private final List<Pattern> validKeyPatterns;

    /**
     * Constructor for the key checker. This compiles a regex for each known key.
     */
    public UnknownKeyChecker()
    {
        validKeyPatterns = new ArrayList<Pattern>();

        // Add all the patterns
        // Main trip times
        validKeyPatterns.add(Pattern.compile("ARRIVAL_DATETIME"));
        validKeyPatterns.add(Pattern.compile("DEPARTURE_DATETIME"));

        // Funding types
        validKeyPatterns.add(Pattern.compile("TRAVEL_TYPE_CSE_SPONSORED"));
        validKeyPatterns.add(Pattern.compile("TRAVEL_TYPE_DTC_SPONSORED"));
        validKeyPatterns.add(Pattern.compile("TRAVEL_TYPE_NONSPONSORED"));

        // User Information Fields
        validKeyPatterns.add(Pattern.compile("EMERGENCY_CONTACT_NAME"));
        validKeyPatterns.add(Pattern.compile("EMERGENCY_CONTACT_PHONE"));

        // User Information
        validKeyPatterns.add(Pattern.compile("USER_NAME"));

        // Conference Information Fields
        validKeyPatterns.add(Pattern.compile("JUSTIFICATION_CONFERENCE_TITLE"));
        validKeyPatterns.add(Pattern.compile("JUSTIFICATION_PRESENTED"));
        validKeyPatterns.add(Pattern.compile("JUSTIFICATION_PRESENTATION_TITLE"));
        validKeyPatterns.add(Pattern.compile("JUSTIFICATION_PRESENTATION_ABSTRACT"));
        validKeyPatterns.add(Pattern.compile("JUSTIFICATION_PRESENTATION_ACKNOWLEDGEMENT"));
        validKeyPatterns.add(Pattern.compile("JUSTIFICATION_NONSPONSORED"));
        validKeyPatterns.add(Pattern.compile("JUSTIFICATION_SPONSORED"));

        // Meal Expenses

        // Breakfast
        validKeyPatterns.add(Pattern.compile("DAY[1-9][0-9]*_BREAKFAST_CITY"));
        validKeyPatterns.add(Pattern.compile("DAY[1-9][0-9]*_BREAKFAST_STATE"));
        validKeyPatterns.add(Pattern.compile("DAY[1-9][0-9]*_BREAKFAST_COUNTRY"));

        // Lunch
        validKeyPatterns.add(Pattern.compile("DAY[1-9][0-9]*_LUNCH_CITY"));
        validKeyPatterns.add(Pattern.compile("DAY[1-9][0-9]*_LUNCH_STATE"));
        validKeyPatterns.add(Pattern.compile("DAY[1-9][0-9]*_LUNCH_COUNTRY"));

        // Dinner
        validKeyPatterns.add(Pattern.compile("DAY[1-9][0-9]*_DINNER_CITY"));
        validKeyPatterns.add(Pattern.compile("DAY[1-9][0-9]*_DINNER_STATE"));
        validKeyPatterns.add(Pattern.compile("DAY[1-9][0-9]*_DINNER_COUNTRY"));

        // Incidental
        validKeyPatterns.add(Pattern.compile("DAY[1-9][0-9]*_INCIDENTAL_CITY"));
        validKeyPatterns.add(Pattern.compile("DAY[1-9][0-9]*_INCIDENTAL_STATE"));
        validKeyPatterns.add(Pattern.compile("DAY[1-9][0-9]*_INCIDENTAL_COUNTRY"));
        validKeyPatterns.add(Pattern.compile("DAY[1-9][0-9]*_INCIDENTAL_AMOUNT"));
        validKeyPatterns.add(Pattern.compile("DAY[1-9][0-9]*_INCIDENTAL_CURRENCY"));
        validKeyPatterns.add(Pattern.compile("DAY[1-9][0-9]*_INCIDENTAL_JUSTIFICATION"));

        // Lodging
        validKeyPatterns.add(Pattern.compile("DAY[1-9][0-9]*_LODGING_CITY"));
        validKeyPatterns.add(Pattern.compile("DAY[1-9][0-9]*_LODGING_STATE"));
        validKeyPatterns.add(Pattern.compile("DAY[1-9][0-9]*_LODGING_COUNTRY"));
        validKeyPatterns.add(Pattern.compile("DAY[1-9][0-9]*_LODGING_AMOUNT"));
        validKeyPatterns.add(Pattern.compile("DAY[1-9][0-9]*_LODGING_CURRENCY"));

        // Transportation
        validKeyPatterns.add(Pattern.compile("NUM_TRANSPORTATION"));
        validKeyPatterns.add(Pattern.compile("TRANSPORTATION[1-9][0-9]*_DATE"));
        validKeyPatterns.add(Pattern.compile("TRANSPORTATION[1-9][0-9]*_TYPE"));
        validKeyPatterns.add(Pattern.compile("TRANSPORTATION[1-9][0-9]*_RENTAL"));
        validKeyPatterns.add(Pattern.compile("TRANSPORTATION[1-9][0-9]*_CARRIER"));
        validKeyPatterns.add(Pattern.compile("TRANSPORTATION[1-9][0-9]*_MILES_TRAVELED"));
        validKeyPatterns.add(Pattern.compile("TRANSPORTATION[1-9][0-9]*_AMOUNT"));
        validKeyPatterns.add(Pattern.compile("TRANSPORTATION[1-9][0-9]*_CURRENCY"));

        // Other
        validKeyPatterns.add(Pattern.compile("NUM_OTHER_EXPENSES"));
        validKeyPatterns.add(Pattern.compile("OTHER[1-9][0-9]*_DATE"));
        validKeyPatterns.add(Pattern.compile("OTHER[1-9][0-9]*_JUSTIFICATION"));
        validKeyPatterns.add(Pattern.compile("OTHER[1-9][0-9]*_AMOUNT"));
        validKeyPatterns.add(Pattern.compile("OTHER[1-9][0-9]*_CURRENCY"));

        // Grant Fields
        validKeyPatterns.add(Pattern.compile("NUM_GRANTS"));
        validKeyPatterns.add(Pattern.compile("GRANT[1-9][0-9]*_ACCOUNT"));
        validKeyPatterns.add(Pattern.compile("GRANT[1-9][0-9]*_PERCENT"));

        // Expense Fields
        validKeyPatterns.add(Pattern.compile("NUM_DAYS"));
    }

    /**
     * Check all keys in the map for validity. Validity means that every key in the map matches a
     * pattern for a know key name.
     * 
     * @param formData - the map to check
     * @return - True if every key matched a pattern and false if at least one key did not match a
     *         pattern.
     * @throws InputValidationException
     */
    public void areFormKeysValid(Map<String, String> formData) throws InputValidationException
    {
        Matcher keyMatcher;

        // Every key:value in the data map
        for (Map.Entry<String, String> formPair : formData.entrySet())
        {
            boolean foundMatch = false;
            // Each one must be checked against all patterns until a match is found
            for (Pattern fieldPattern : validKeyPatterns)
            {
                // Run the regex pattern agains the key
                keyMatcher = fieldPattern.matcher(formPair.getKey());
                if (keyMatcher.find())
                {
                    foundMatch = true;
                    break;
                }
            }

            if (!foundMatch)
                throw new InputValidationException("Unkown key in form data: " + formPair.getKey());
        }

        // All keys matched a pattern
    }
}
