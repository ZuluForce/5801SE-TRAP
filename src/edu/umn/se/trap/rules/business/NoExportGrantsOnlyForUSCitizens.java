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
// NoExportGrantsOnlyForUSCitizens.java
package edu.umn.se.trap.rules.business;

import edu.umn.se.trap.data.Grant;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TRAPConstants;
import edu.umn.se.trap.data.UserInfo;
import edu.umn.se.trap.db.GrantDBWrapper;
import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * NoExport grants can only be used by users who are United States citizens
 * 
 * @author nagell2008
 * 
 */
public class NoExportGrantsOnlyForUSCitizens extends BusinessLogicRule
{

    /**
     * NoExport grants can only be used by users who are United States citizens
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        // Current user of the TRAP system
        UserInfo currentUser = app.getUserInfo();

        // Citizenship of the current user
        String currentUserCitizenShip = currentUser.getCitizenship();

        // Temporary variable to hold a funds organization type
        String organizationType = "";

        // This loop checks that if a user is not a United States citizen who is using a noExport
        // grant, throw an exception
        for (Grant grant : app.getGrantList())
        {
            try
            {
                organizationType = GrantDBWrapper.getGrantOrganizationType(grant.getGrantAccount());
            }
            catch (KeyNotFoundException e)
            {
                e.printStackTrace();
                throw new FormProcessorException("Cannot get organization type for account: "
                        + grant.getGrantAccount());
            }

            // Check if the grant is of type noExport
            if (organizationType.compareToIgnoreCase(GrantDBWrapper.ORG_TYPE_NOEXPORT) == 0)
            {
                // If the user is a United States citizen, continue. Otherwise, throw an exception
                if (currentUserCitizenShip.compareToIgnoreCase(TRAPConstants.USA_LONG) == 0)
                {
                    continue;
                }
                else
                {
                    throw new BusinessLogicException("User has citizenship: "
                            + currentUserCitizenShip + " and is trying to claim a no-export grant");
                }
            }

        }

        return;
    }
}
