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
// TestUserGrantDB.java
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
package edu.umn.se.test.frame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.db.UserGrantDB;

/**
 * Mimics the DB table between the association between the users and the grants.
 * 
 * @author ggay
 * 
 */

public class TestUserGrantDB extends UserGrantDB
{
    /** Logger for the TestUserGrantDB */
    private static final Logger log = LoggerFactory.getLogger(TestUserGrantDB.class);

    /**
     * This type enumerates the fields of the {@link ArrayList} with the user information.
     */
    public static enum USER_GRANT_FIELDS
    {
        ACCOUNT_NUMBER, /* Grant account number */
        GRANT_ADMIN, /*
                      * Users (X500 numbers) who can be reimbursed under the grant, without
                      * permission, and approves other's requests
                      */
        AUTHORIZED_PAYEES, /*
                            * List of users (X500 numbers) who can be reimbursed under a grant, with
                            * permission from an account admin
                            */
    };

    Map<String, List<String>> userInfo = new HashMap<String, List<String>>();

    public static class UserGrantBuilder
    {
        private String account;
        private String admin;
        private final List<String> authorizedPayees;

        public UserGrantBuilder()
        {
            authorizedPayees = new ArrayList<String>();
            account = admin = "";
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
         * @return the admin
         */
        public String getAdmin()
        {
            return admin;
        }

        /**
         * @param admin the admin to set
         */
        public void setAdmin(String admin)
        {
            this.admin = admin;
        }

        /**
         * @return the authorizedPayee
         */
        public List<String> getAuthorizedPayes()
        {
            return authorizedPayees;
        }

        public String getAuthorizedPayeesString()
        {
            if (authorizedPayees.size() == 0)
            {
                return "";
            }

            StringBuilder sb = new StringBuilder();
            for (String payee : authorizedPayees)
            {
                sb.append(payee + ",");
            }

            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }

        /**
         * 
         * @param payee payee to add to authorized list
         */
        public void addAuthorizedPayee(String payee)
        {
            authorizedPayees.add(payee);
        }
    }

    /**
     * Constructor. Sets up the object.
     */
    public TestUserGrantDB()
    {
        ArrayList<String> user = new ArrayList<String>();
        user.add("010101010101");
        user.add("heimd001");
        user.add("linc001, gayxx067");

        userInfo.put(user.get(USER_GRANT_FIELDS.ACCOUNT_NUMBER.ordinal()), user);

        UserGrantBuilder builder = new UserGrantBuilder();
        builder.setAccount("umn_super_pac");
        builder.setAdmin("helge206");
        addUserGrantInfo(builder);

        UserGrantBuilder newBuilder = new UserGrantBuilder();
        newBuilder.setAccount("99999");
        newBuilder.setAdmin("linc001");
        addUserGrantInfo(newBuilder);

    }

    public void addUserGrantInfo(UserGrantBuilder builder)
    {
        ArrayList<String> user = new ArrayList<String>();
        user.add(builder.getAccount());
        user.add(builder.getAdmin());
        user.add(builder.getAuthorizedPayeesString());

        userInfo.put(builder.getAccount(), user);
    }

    /**
     * Gets the user's information as a list of strings.
     * 
     * @param accountName the account number of the grant to be retrieved from the system.
     * @return a list containing the users paid under a grant. This contains the X500 id of the
     *         account number, account admin name, and a string of users paid under the grant
     *         (comma-seperated string). A null or empty list returned from this method should be
     *         treated as an invalid account number.
     * @throws KeyNotFoundException if the specified account number could not be found in the
     *             database.
     */
    @Override
    public List<String> getUserGrantInfo(String accountName) throws KeyNotFoundException
    {
        log.info("User grant info request for account {}", accountName);

        List<String> userInfo = this.userInfo.get(accountName.toLowerCase());
        if (userInfo == null)
        {
            throw new KeyNotFoundException("Could not find account, " + accountName
                    + ", in user DB.");
        }
        return userInfo;
    }
}
