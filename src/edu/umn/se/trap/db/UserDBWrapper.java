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
 * A wrapper around a source of user information. This provides information such as first names,
 * last names, citizenship, and employment status given a username.
 * 
 * @author andrewh
 * 
 */
public class UserDBWrapper
{
    /** Underlying db that the wrapper will call */
    private static UserDB userDB;

    /**
     * Get a list of Strings from the db representing different things about the user.
     * 
     * @param userName - The username to get information for
     * @return - A list of strings with information about the user. Use this list with the
     *         UserDB.USER_FIELDS enum to extract information.
     * @throws KeyNotFoundException If the user cannot be found in the db
     */
    public static List<String> getUserInfo(String userName) throws KeyNotFoundException
    {
        return userDB.getUserInfo(userName);
    }

    /**
     * Set the underlying db that is to be called by this wrapper
     * 
     * @param db - The db implementation for the wrapper to call.
     */
    public static void setUserDB(UserDB db)
    {
        userDB = db;
    }

    /**
     * Check if the given username is present in the db. Presence in the db is assumed to mean it is
     * valid.
     * 
     * @param userName - The username to lookup in the db.
     * @return - True if the username is in the db, false otherwise
     */
    public static boolean isValidUser(String userName)
    {
        try
        {
            userDB.getUserInfo(userName);
            return true;
        }
        catch (KeyNotFoundException e)
        {
            return false;
        }
    }
}
