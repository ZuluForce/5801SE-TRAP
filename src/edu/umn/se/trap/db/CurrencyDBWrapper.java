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

/**
 * A wrapper around a source of currency information. This provides currency conversion rates for
 * particular dates.
 * 
 * @author andrewh
 * 
 */
public class CurrencyDBWrapper
{
    /** Underlying db that the wrapper will call */
    private static CurrencyDB currencyDB;

    /**
     * Get the conversion rate for a particular currency on the given date.
     * 
     * @param currency - The currency to get the conversion rate for
     * @param date - The date to get the conversion rate for
     * @return - The conversion rate for the currency on the given date
     * @throws KeyNotFoundException When the currency is not found in the db
     */
    public static Double getConversion(String currency, String date) throws KeyNotFoundException
    {
        return currencyDB.getConversion(currency, date);
    }

    /**
     * Set the underlying db that is to be called by this wrapper
     * 
     * @param db - The db implementation for the wrapper to call.
     */
    public static void setCurrencyDB(CurrencyDB db)
    {
        currencyDB = db;
    }
}
