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
// TestGrantDB.java
package edu.umn.se.test.frame;

/**
 * Copyright (c) 2012, Ian De Silva, Gregory Gay All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer. - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution. - Neither the name of the University of Minnesota nor
 * the names of its contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.umn.se.trap.db.GrantDB;
import edu.umn.se.trap.db.KeyNotFoundException;

/**
 * Mimics the grant database.
 * 
 * @author ggay
 * 
 */
public class TestGrantDB extends GrantDB
{
    /**
     * This type enumerates the fields of the {@link ArrayList} with the grant information.
     */
    public static enum GRANT_FIELDS
    {
        /** Account number */
        ACCOUNT_NUMBER,
        /** Account type (Sponsored vs Non-sponsored) */
        ACCOUNT_TYPE,
        /** Funding organization */
        FUNDING_ORGANIZATION,
        /** Organization type (i.e., government, industry, noExport, ngo, foreign) */
        ORGANIZATION_TYPE,
        /** Account balance */
        ACCOUNT_BALANCE;
    };

    /**
     * This class is used to contain information about a grant and is used in adding new grants to
     * the db (from externally). This is simpler than having to fiddle with the enumerations and
     * indexing into some list.
     * 
     * @author andrewh
     * 
     */
    public static class GrantBuilder
    {
        String account;
        String accType;
        String orgType;
        String funder;
        Double balance;

        /**
         * Initializes the grant builder. All strings are set to empty string and the balance is set
         * to 0.0
         */
        public GrantBuilder()
        {
            account = accType = orgType = funder = "";
            balance = 0.0;
        }

        /**
         * @return the account
         */
        public String getAccount()
        {
            return account;
        }

        /**
         * @param account the account to set
         */
        public void setAccount(String account)
        {
            this.account = account;
        }

        /**
         * @return the acctype
         */
        public String getAcctype()
        {
            return accType;
        }

        /**
         * @param acctype the acctype to set
         */
        public void setAcctype(String acctype)
        {
            accType = acctype;
        }

        /**
         * @return the orgType
         */
        public String getOrgType()
        {
            return orgType;
        }

        /**
         * @param orgType the orgType to set
         */
        public void setOrgType(String orgType)
        {
            this.orgType = orgType;
        }

        /**
         * @return the funder
         */
        public String getFunder()
        {
            return funder;
        }

        /**
         * @param funder the funder to set
         */
        public void setFunder(String funder)
        {
            this.funder = funder;
        }

        /**
         * @return the balance
         */
        public Double getBalance()
        {
            return balance;
        }

        /**
         * @param balance the balance to set
         */
        public void setBalance(Double balance)
        {
            this.balance = balance;
        }
    }

    Map<String, List<Object>> grantInfo = new HashMap<String, List<Object>>();

    /**
     * Constructor. Sets up the object.
     */
    public TestGrantDB()
    {
        /* Example 1: Sponsored Grant */
        ArrayList<Object> grant = new ArrayList<Object>();
        grant.add("010101010101"); /* Account number */
        grant.add("sponsored"); /* Account type */
        grant.add("DARPA"); /* Funding organization */
        grant.add("government"); /*
                                  * Organization type (i.e., government, industry)
                                  */
        grant.add((double) 250000); /* Account balance */

        grantInfo.put((String) grant.get(GRANT_FIELDS.ACCOUNT_NUMBER.ordinal()), grant);

        /* Example 2: Non-sponsored funds */
        ArrayList<Object> grant2 = new ArrayList<Object>();
        grant2.add("99999"); /* Account number */
        grant2.add("non-sponsored"); /* Account type */
        grant2.add(null); /* Funding organization */
        grant2.add("mixed"); /* Organization type */
        grant2.add((double) 98000); /* Account balance */

        grantInfo.put((String) grant2.get(GRANT_FIELDS.ACCOUNT_NUMBER.ordinal()), grant2);

        GrantBuilder builder = new GrantBuilder();
        builder.setAccount("umn_super_pac");
        builder.setAcctype("non-sponsored");
        builder.setFunder(null);
        builder.setOrgType("industry");
        builder.setBalance(13370.0);

        addGrant(builder);

    }

    /**
     * Add a grant to the database
     * 
     * @param builder - Builder containing information about the grant to add.
     */
    public void addGrant(GrantBuilder builder)
    {
        ArrayList<Object> grant = new ArrayList<Object>();
        grant.add(builder.getAccount()); /* Account number */
        grant.add(builder.getAcctype()); /* Account type */
        grant.add(builder.getFunder()); /* Funding organization */
        grant.add(builder.getOrgType()); /* Organization type */
        grant.add((double) builder.getBalance()); /* Account balance */

        grantInfo.put((String) grant.get(GRANT_FIELDS.ACCOUNT_NUMBER.ordinal()), grant);
    }

    /**
     * Remove a grant from the database
     * 
     * @param accountName - Account name for the grant to remove
     * @throws KeyNotFoundException When no grant by the given name is found.
     */
    public void removeGrant(String accountName) throws KeyNotFoundException
    {
        List<Object> grantInfo = this.grantInfo.remove(accountName.toLowerCase());
        if (grantInfo == null)
        {
            throw new KeyNotFoundException("Could not find funding source, " + accountName
                    + ", in grant DB.");
        }
    }

    /**
     * Fill a builder object with information about a particular grant.
     * 
     * @param accountName - The account name of the grant to get information for.
     * @return A grant builder object filled with information about the appropriate grant.
     * @throws KeyNotFoundException When no grant is found with the given account name.
     */
    public GrantBuilder fillBulderWithGrantInfo(String accountName) throws KeyNotFoundException
    {
        List<Object> grantInfo = getGrantInfo(accountName);
        GrantBuilder builder = new GrantBuilder();

        builder.setAccount(accountName);
        builder.setAcctype((String) grantInfo.get(GRANT_FIELDS.ACCOUNT_TYPE.ordinal()));
        builder.setFunder((String) grantInfo.get(GRANT_FIELDS.FUNDING_ORGANIZATION.ordinal()));
        builder.setOrgType((String) grantInfo.get(GRANT_FIELDS.ORGANIZATION_TYPE.ordinal()));
        builder.setBalance((Double) grantInfo.get(GRANT_FIELDS.ACCOUNT_BALANCE.ordinal()));

        return builder;
    }

    /**
     * Gets the grant information as a list of strings.
     * 
     * @param accountName the funding account number to be retrieved.
     * @return a list containing the grant information. This contains the account type, funding
     *         organization, organization type, and account balance.
     * @throws KeyNotFoundException if the specified user name could not be found in the database.
     */
    @Override
    public List<Object> getGrantInfo(String accountName) throws KeyNotFoundException
    {

        List<Object> grantInfo = this.grantInfo.get(accountName.toLowerCase());
        if (grantInfo == null)
        {
            throw new KeyNotFoundException("Could not find funding source, " + accountName
                    + ", in grant DB.");
        }
        return grantInfo;
    }

    /**
     * Update the account balance for a given account.
     * 
     * @param accountName the account number to be updated.
     * @param newBalance the new balance of the account.
     * @throws KeyNotFoundException if the specified user name could not be found in the database.
     */
    @Override
    public void updateAccountBalance(String accountName, Double newBalance)
            throws KeyNotFoundException
    {
        List<Object> grantInfo = this.grantInfo.get(accountName.toLowerCase());
        if (grantInfo == null)
        {
            throw new KeyNotFoundException("Could not find funding source, " + accountName
                    + ", in grant DB.");
        }

        grantInfo.set(GRANT_FIELDS.ACCOUNT_BALANCE.ordinal(), newBalance);
        this.grantInfo.put(accountName, grantInfo);
    }
}
