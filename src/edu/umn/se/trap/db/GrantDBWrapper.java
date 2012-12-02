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
package edu.umn.se.trap.db;

import java.util.ArrayList;
import java.util.List;

import edu.umn.se.trap.data.Grant;

/**
 * A wrapper around a source of grant information. This provides information on grant organization
 * types, funding organizations, account types, approvers, and available funds.
 * 
 * @author andrewh
 * 
 */
public class GrantDBWrapper
{
    /** Underlying db that the wrapper will call */
    private static GrantDB grantDB;

    /** Government organization type */
    public static final String ORG_TYPE_GOV = "government";

    /** noExport organization type */
    public static final String ORG_TYPE_NOEXPORT = "noExport";

    /** Foreign organization type */
    public static final String ORG_TYPE_FOREIGN = "foreign";

    /** DoD funding organization */
    public static final String FUNDING_ORG_DOD = "DOD";

    /** NIH funding organization */
    public static final String FUNDING_ORG_NIH = "NIH";

    /** Sponsored account type */
    public static final String SPONSORED_ACCT = "sponsored";

    /** Non-Sponsored account type */
    public static final String NONSPONSORED_ACCT = "non-sponsored";

    /**
     * Get the grant info object list from the underlying database. This is made private since it
     * directly exposes the underlying db and we don't want other people using it.
     * 
     * @param accountName - The account to get info for
     * @return - The object list of information for the grant.
     */
    private static List<Object> getGrantInfo(String accountName) throws KeyNotFoundException
    {
        return grantDB.getGrantInfo(accountName);
    }

    /**
     * Get the balance for a particular account.
     * 
     * @param accountName - The grant account to get the balance for.
     * @return - The balance for the account
     * @throws KeyNotFoundException - When the grant cannot be found in the db
     */
    public static Double getGrantBalance(String accountName) throws KeyNotFoundException
    {
        List<Object> grantInfo = getGrantInfo(accountName);

        return (Double) grantInfo.get(GrantDB.GRANT_FIELDS.ACCOUNT_BALANCE.ordinal());
    }

    /**
     * Get the account type for the grant. (ie sponsored or non-sponsored)
     * 
     * @param accountName - The grant account to get the account type for.
     * @return - The account type for the grant.
     * @throws KeyNotFoundException When the grant cannot be found in the db
     */
    public static String getGrantAccountType(String accountName) throws KeyNotFoundException
    {
        List<Object> grantInfo = getGrantInfo(accountName);

        return (String) grantInfo.get(GrantDB.GRANT_FIELDS.ACCOUNT_TYPE.ordinal());
    }

    /**
     * Get the grant organization type. (ie government, noExport ...)
     * 
     * @param accountName - The grant account to get the organization type for
     * @return - The organization type for the grant.
     * @throws KeyNotFoundException When the grant cannot be found in the db
     */
    public static String getGrantOrganizationType(String accountName) throws KeyNotFoundException
    {
        List<Object> grantInfo = getGrantInfo(accountName);

        return (String) grantInfo.get(GrantDB.GRANT_FIELDS.ORGANIZATION_TYPE.ordinal());
    }

    /**
     * Get the grant funding organization (ie DoD, NIH, ...)
     * 
     * @param accountName - the grant account to get the funding organization for.
     * @return - The funding organization for the grant.
     * @throws KeyNotFoundException When the grant cannot be found in the db
     */
    public static String getGrantFundingOrganization(String accountName)
            throws KeyNotFoundException
    {
        List<Object> grantInfo = getGrantInfo(accountName);

        return (String) grantInfo.get(GrantDB.GRANT_FIELDS.FUNDING_ORGANIZATION.ordinal());
    }

    /**
     * Update the balance in the given account to have a specific newBalance.
     * 
     * @param accountName - The grant account to set the balance for.
     * @param newBalance - The balance to set in the grant.
     * @throws KeyNotFoundException If the grant is not found in the db
     */
    public static void updateAccountBalance(String accountName, Double newBalance)
            throws KeyNotFoundException
    {
        grantDB.updateAccountBalance(accountName, newBalance);
    }

    /**
     * Filter the given list of grants to just NIH grants.
     * 
     * @param grants - the unfiltered list of grants
     * @return - The list of NIH grants from the original grants parameter
     * @throws KeyNotFoundException When a grant cannot be found in the db
     */
    public static List<Grant> getDODGrants(List<Grant> grants) throws KeyNotFoundException
    {
        return getGrantsOfCertainType(grants, SPONSORED_ACCT, FUNDING_ORG_DOD);
    }

    /**
     * Filter the given list of grants to just DoD grants.
     * 
     * @param grants - the unfiltered list of grants
     * @return - The list of DoD grants from the original grants parameter
     * @throws KeyNotFoundException When a grant cannot be found in the db
     */
    public static List<Grant> getNIHGrants(List<Grant> grants) throws KeyNotFoundException
    {
        return getGrantsOfCertainType(grants, SPONSORED_ACCT, FUNDING_ORG_NIH);
    }

    /**
     * Filter the given list of grants to just those that have a certain account type (ie sponsored
     * or non-sponsored) and funding organization (DOD or NIH).
     * 
     * @param grants - The unfiltered list of grants
     * @param acctType - The type of the account to filter on.
     * @param fundingOrg - The funding organization to filter on.
     * @return - A list of grants filtered to those with the appropriate account type and funding
     *         organization.
     * @throws KeyNotFoundException When a grant cannot be found in the db
     */
    public static List<Grant> getGrantsOfCertainType(List<Grant> grants, String acctType,
            String fundingOrg) throws KeyNotFoundException
    {
        List<Grant> filteredGrants = new ArrayList<Grant>();

        for (Grant grant : grants)
        {
            String grantAcctType = getGrantAccountType(grant.getGrantAccount());
            String grantFundingOrg = getGrantFundingOrganization(grant.getGrantAccount());

            // Check if this grant is of the specified type
            boolean isOfType = true;

            if (grantAcctType == null)
                isOfType = false;
            else
                isOfType &= (grantAcctType.compareToIgnoreCase(acctType) == 0);

            if (grantFundingOrg == null)
                isOfType = false;
            else
                isOfType &= (grantFundingOrg.compareToIgnoreCase(fundingOrg) == 0);

            if (isOfType)
            {
                filteredGrants.add(grant);
            }
        }

        return filteredGrants;
    }

    /**
     * Filter the list of grants to just the foreign grants.
     * 
     * @param grants - The full list of grants to be filtered.
     * @return - The list of foreign grants from the original list.
     * @throws KeyNotFoundException If the grant cannot be found in the db
     */
    public static List<Grant> getForeignGrants(List<Grant> grants) throws KeyNotFoundException
    {
        List<Grant> foreignGrants = new ArrayList<Grant>();
        for (Grant grant : grants)
        {
            String orgType = getGrantOrganizationType(grant.getGrantAccount());

            if (orgType.compareToIgnoreCase(ORG_TYPE_FOREIGN) == 0)
            {
                foreignGrants.add(grant);
            }
        }

        return foreignGrants;
    }

    /**
     * Set the underlying db that is to be called by this wrapper
     * 
     * @param db - The db implementation for the wrapper to call.
     */
    public static void setGrantDB(GrantDB db)
    {
        grantDB = db;
    }

    /**
     * Check if a grant is present in the database.
     * 
     * @param accountName - The account name to check for presence in the db
     * @return - True if the grant account is present in the db, false otherwise.
     */
    public static boolean isValidGrant(String accountName)
    {
        try
        {
            grantDB.getGrantInfo(accountName);
            return true;
        }
        catch (KeyNotFoundException e)
        {
            return false;
        }
    }
}
