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
// AlcoholOnlyAllowedUnderNonSponsored.java
package edu.umn.se.trap.rules.business;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.IncidentalExpense;
import edu.umn.se.trap.data.OtherExpense;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * Checks all incidental and other expense justifications for any mention of family. Expenses for
 * family members cannot be reimbursed.
 * 
 * @author andrewh
 * 
 */
public class FamilyMemberExpensesNotAllowed extends BusinessLogicRule
{
    /** Logger for the class */
    private static final Logger log = LoggerFactory.getLogger(FamilyMemberExpensesNotAllowed.class);

    /** Pattern used to search for family expenses in justifications */
    private static final Pattern FAMILY_PATTERN = Pattern
            .compile(
                    "\\b(father|mother|brother|sister|child|son|daughter|wife|spouse|cousin|aunt|uncle|in-law)\\b",
                    Pattern.CASE_INSENSITIVE);

    /**
     * Checks all incidental and other expense justifications for any mention of family. Expenses
     * for family members cannot be reimbursed.
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        Matcher familyMatch;

        // Check all incidentals
        for (IncidentalExpense incidental : app.getIncidentalExpenseList())
        {
            String justification = incidental.getExpenseJustification();
            familyMatch = FAMILY_PATTERN.matcher(justification);

            if (familyMatch.find())
            {
                String msg = String
                        .format("Family expenses not allowed for reimbursement. Found in incidental justification: %s",
                                justification);
                log.error(msg);
                log.error("Matched text: {}", familyMatch.group());
                throw new BusinessLogicException(msg);
            }
        }

        // Check all other expenses
        for (OtherExpense otherExpense : app.getOtherExpenseList())
        {
            String justification = otherExpense.getExpenseJustification();
            familyMatch = FAMILY_PATTERN.matcher(justification);

            if (familyMatch.find())
            {
                String msg = String
                        .format("Family expenses not allowed for reimbursement. Found in other justification: %s",
                                justification);
                log.error(msg);
                throw new BusinessLogicException(msg);
            }
        }
    }
}
