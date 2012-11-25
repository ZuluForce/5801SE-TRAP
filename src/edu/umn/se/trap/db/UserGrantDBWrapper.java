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

import java.util.List;

/**
 * @author andrewh
 * 
 */
public class UserGrantDBWrapper
{
    /** Underlying db that the wrapper will call */
    private static UserGrantDB userGrantDB;

    /**
     * Get user information about a particular grant.
     * 
     * @param accountName - The account name to get user information for
     * @return - A list of strings representing user information about the grant. Use
     *         UserGrantDB.USER_GRANT_FIELDS to extract informaiton from this.
     * @throws KeyNotFoundException If the grant is not found in the db
     */
    private static List<String> getUserGrantInfo(String accountName) throws KeyNotFoundException
    {
        return userGrantDB.getUserGrantInfo(accountName);
    }

    /**
     * Get the admin for a particular account. This is the person who must sign off on
     * 
     * @param accountName - The name of the account to get the admin for
     * @return - The admin for the grant with the specified accountName
     * @throws KeyNotFoundException If the grant is not found in the db
     */
    public static String getGrantAdmin(String accountName) throws KeyNotFoundException
    {
        List<String> grantInfo = getUserGrantInfo(accountName);

        return grantInfo.get(UserGrantDB.USER_GRANT_FIELDS.GRANT_ADMIN.ordinal());
    }

    /**
     * Set the underlying db that is to be called by this wrapper
     * 
     * @param db - The db implementation for the wrapper to call.
     */
    public static void setUserGrantDB(UserGrantDB db)
    {
        userGrantDB = db;
    }
}
