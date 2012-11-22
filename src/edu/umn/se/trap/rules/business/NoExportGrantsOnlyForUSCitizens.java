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
 * @author nagell2008
 * 
 */
public class NoExportGrantsOnlyForUSCitizens extends BusinessLogicRule
{

    /*
     * (non-Javadoc)
     * @see edu.umn.se.trap.rules.TRAPRule#checkRule(edu.umn.se.trap.data.ReimbursementApp)
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {

        UserInfo currentUser = app.getUserInfo();

        String currentUserCitizenShip = currentUser.getCitizenship();
        String organizationType = "";

        List<Object> grantInfo;

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
