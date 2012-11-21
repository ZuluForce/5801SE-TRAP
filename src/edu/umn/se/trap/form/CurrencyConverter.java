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
// CurrencyConverter.java
package edu.umn.se.trap.form;

import java.util.Date;

import edu.umn.se.trap.db.CurrencyDBWrapper;
import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.exception.TRAPRuntimeException;
import edu.umn.se.trap.rules.DateValidator;

/**
 * @author planeman
 * 
 */
public class CurrencyConverter
{
    /**
     * Convert the given amount of money to USD from the specified currency on the date provided.
     * 
     * @param amount - The amount to convert
     * @param currency - The currency for the amount
     * @param date - The date to use when looking up the conversion rate
     * @return - The amount converted to USD according to the conversion rate for currency on the
     *         given date.
     */
    public static Double convertToUSD(Double amount, String currency, Date date)
    {
        return convertToUSD(amount, currency, DateValidator.dateToString(date));
    }

    /**
     * Convert the given amount of money to USD from the specified currency on the date provided.
     * 
     * @param amount - The amount to convert
     * @param currency - The currency for the amount
     * @param date - The date to use when looking up the conversion rate
     * @return - The amount converted to USD according to the conversion rate for currency on the
     *         given date.
     */
    public static Double convertToUSD(Double amount, String currency, String date)
    {
        try
        {
            Double conversionRate = CurrencyDBWrapper.getConversion(currency, date);
            return amount * conversionRate;
        }
        catch (KeyNotFoundException noKey)
        {
            throw new TRAPRuntimeException(String.format("Failed to convert currency %s on %s",
                    currency, date));
        }
    }
}
