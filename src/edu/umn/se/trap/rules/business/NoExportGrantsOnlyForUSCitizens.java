// NoExportGrantsOnlyForUSCitizens.java
package edu.umn.se.trap.rules.business;

import java.util.List;

import edu.umn.se.trap.data.Grant;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.UserInfo;
import edu.umn.se.trap.db.GrantDB;
import edu.umn.se.trap.db.GrantDBWrapper;
import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.exception.BusinessLogicException;
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

        // Temporary variable to hold grant information as needed
        List<Object> grantInfo;

        // This loop checks that if a user is not a United States citizen who is using a noExport
        // grant, throw an exception
        for (Grant grant : app.getGrantList())
        {
            try
            {
                grantInfo = GrantDBWrapper.getGrantInfo(grant.getGrantAccount());
            }
            catch (KeyNotFoundException e)
            {
                throw new BusinessLogicException("Could not grab grant information from grant: "
                        + grant.getGrantAccount(), e);
            }

            organizationType = (String) grantInfo.get(GrantDB.GRANT_FIELDS.ORGANIZATION_TYPE
                    .ordinal());

            if (organizationType.compareToIgnoreCase("noExport") == 0)
            {
                if (currentUserCitizenShip.compareToIgnoreCase("United States") == 0)
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
