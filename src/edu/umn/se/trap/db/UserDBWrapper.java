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
 * @author planeman
 * 
 */
public class UserDBWrapper
{
    private static final UserDB userDB = new UserDB();

    @Override
    public boolean equals(Object obj)
    {
        return userDB.equals(obj);
    }

    public static List<String> getUserInfo(String userName) throws KeyNotFoundException
    {
        return userDB.getUserInfo(userName);
    }

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

    @Override
    public int hashCode()
    {
        return userDB.hashCode();
    }

    @Override
    public String toString()
    {
        return userDB.toString();
    }

}
