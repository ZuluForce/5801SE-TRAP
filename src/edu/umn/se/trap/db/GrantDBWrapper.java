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
public class GrantDBWrapper
{
    private static final GrantDB grantDB = new GrantDB();

    public static List<Object> getGrantInfo(String accountName) throws KeyNotFoundException
    {
        return grantDB.getGrantInfo(accountName);
    }

    public static Double getGrantBalance(String accountName) throws KeyNotFoundException
    {
        List<Object> grantInfo = getGrantInfo(accountName);

        return (Double) grantInfo.get(GrantDB.GRANT_FIELDS.ACCOUNT_BALANCE.ordinal());
    }

    public static void updateAccountBalance(String accountName, Double newBalance)
            throws KeyNotFoundException
    {
        grantDB.updateAccountBalance(accountName, newBalance);
    }

}
