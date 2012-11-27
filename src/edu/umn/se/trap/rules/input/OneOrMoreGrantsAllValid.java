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
// OneOrMoreGrantsAllValid.java
package edu.umn.se.trap.rules.input;

import java.util.List;

import edu.umn.se.trap.data.Grant;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.db.GrantDBWrapper;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.exception.InputValidationException;

/**
 * Check that at least one grant was submitted for processing. Check that every grant submitted is a
 * valid grant account.
 * 
 * @author Dylan
 * 
 */
public class OneOrMoreGrantsAllValid extends InputValidationRule
{

    /**
     * Check that at least one grant was submitted for processing. Check that every grant submitted
     * is a valid grant account.
     */
    @Override
    public void checkRule(ReimbursementApp app) throws InputValidationException,
            FormProcessorException
    {
        List<Grant> grants = app.getGrantList();

        if (grants.isEmpty())
        {
            throw new InputValidationException(
                    "No grants provided. At least one grant needs to be provided for processing");
        }

        for (Grant grant : grants)
        {
            if (!GrantDBWrapper.isValidGrant(grant.getGrantAccount()))
            {
                throw new InputValidationException(String.format("Grant account %s is not valid",
                        grant.getGrantAccount()));
            }
        }
    }
}
