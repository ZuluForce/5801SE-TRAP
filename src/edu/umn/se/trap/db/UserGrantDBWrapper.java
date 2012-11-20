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
public class UserGrantDBWrapper
{
    private static final UserGrantDB userGrantDB = new UserGrantDB();

    public static List<String> getUserGrantInfo(String accountName) throws KeyNotFoundException
    {
        return userGrantDB.getUserGrantInfo(accountName);
    }

    public static String getGrantAdmin(String accountName) throws KeyNotFoundException
    {
        List<String> grantInfo = userGrantDB.getUserGrantInfo(accountName);

        return grantInfo.get(UserGrantDB.USER_GRANT_FIELDS.GRANT_ADMIN.ordinal());
    }
}
