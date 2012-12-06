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
// GrantPercentSumTo100.java
package edu.umn.se.trap.rules.input;

import java.util.List;

import edu.umn.se.trap.data.Grant;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.exception.InputValidationException;

/**
 * Checks that the grant reimbursement percentages equal 100% when summed. No more no less.
 * 
 * @author Dylan
 * 
 */
public class GrantPercentSumTo100 extends InputValidationRule
{
    /**
     * Checks that the grant reimbursement amount does not exceed the total expense amount.
     */
    @Override
    public void checkRule(ReimbursementApp app) throws InputValidationException,
            FormProcessorException
    {
        List<Grant> grants = app.getGrantList();
        Integer grantTotal = 0;

        for (Grant grant : grants)
        {
            grantTotal += grant.getGrantPercentage();
        }

        if (grantTotal != 100)
        {
            throw new InputValidationException(String.format(
                    "Grant percentages do not sum to 100%% (%d%%)", grantTotal));
        }
    }

}
