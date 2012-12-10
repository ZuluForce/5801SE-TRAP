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
// TestCurrencyDB.java
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
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.db.CurrencyDB;
import edu.umn.se.trap.db.KeyNotFoundException;

/**
 * Mimics the currency conversion database.
 * 
 * @author ggay
 * 
 */
public class TestCurrencyDB extends CurrencyDB
{
    /** Logger for the TestCurrencyDB */
    private static Logger log = LoggerFactory.getLogger(TestCurrencyDB.class);

    /**
     * This type enumerates the fields of the {@link ArrayList} with the conversion information.
     */
    public static enum CURRENCY_FIELDS
    {
        /** Currency to be converted */
        CURRENCY,

        /** Date of conversion */
        DATE,
    };

    /** Where all the currency data is held */
    Map<Currency, Double> currencyInfo = new HashMap<Currency, Double>();

    /**
     * This type combines the currency code and date into a single key for indexing purposes.
     */
    public class Currency implements Comparable<Currency>
    {
        /** Code used to represent the currency (ie USD') */
        public String currencyCode;

        /** Date that the currency rate applies for */
        public String date;

        /**
         * Constructor. Sets up the object.
         * 
         * @param c - The code for the currency
         * @param d - the date for the currency rate
         */
        public Currency(String c, String d)
        {
            currencyCode = c;
            date = d;
        }

        /**
         * Compares equality of two Currency objects.
         * 
         * @param o - object to compare to.
         * @return a Boolean indicating equality.
         */
        @Override
        public boolean equals(Object o)
        {
            if (o == this)
                return true;
            if (o == null || !(o instanceof Currency))
                return false;
            Currency c = Currency.class.cast(o);
            return currencyCode.equals(c.currencyCode) && date.equals(c.date);
        }

        /**
         * Check the hashcode of a Currency object for equality purposes
         * 
         * @return integer hashcode for this Currency object.
         */
        @Override
        public int hashCode()
        {
            return currencyCode.hashCode() * 3 + date.hashCode() * 5;
        }

        /**
         * Compares two Currency objects for ordering purposes
         * 
         * @param c - Currency to compare to.
         * @return a negative integer, zero, or a positive integer as this object is less than,
         *         equal to, or greater than the specified object.
         */
        @Override
        public int compareTo(Currency c)
        {
            if (c == this)
                return 0;
            int code = currencyCode.compareTo(c.currencyCode);
            if (code != 0)
                return code;
            return date.compareTo(c.date);
        }

    }

    /**
     * Constructor. Sets up the object.
     */
    public TestCurrencyDB()
    {
        Currency entry1 = new Currency("eur", /* Currency to be converted */
        "20121009"); /* Date */
        Double value = 1.5; /*
                             * USD value of 1 unit of that currency (i.e. 1 EURO = 1.5 USD)
                             */
        currencyInfo.put(entry1, value);

        Currency entry2 = new Currency("eur", "20121010");
        value = 1.50;
        currencyInfo.put(entry2, value);
        Currency entry3 = new Currency("eur", "20121011");
        value = 1.51;
        currencyInfo.put(entry3, value);
        Currency entry4 = new Currency("eur", "20121012");
        value = 1.60;
        currencyInfo.put(entry4, value);
        Currency entry5 = new Currency("eur", "20121013");
        value = 1.58;
        currencyInfo.put(entry5, value);
        Currency entry6 = new Currency("eur", "20121014");
        value = 1.56;
        currencyInfo.put(entry6, value);
        Currency entry7 = new Currency("eur", "20121015");
        value = 1.50;
        currencyInfo.put(entry7, value);
        Currency entry8 = new Currency("eur", "20121016");
        value = 1.70;
        currencyInfo.put(entry8, value);
        Currency entry9 = new Currency("eur", "20121017");
        value = 1.69;
        currencyInfo.put(entry9, value);
        Currency entry10 = new Currency("eur", "20121018");
        value = 1.68;
        currencyInfo.put(entry10, value);
        Currency entry11 = new Currency("eur", "20121019");
        value = 1.68;
        currencyInfo.put(entry11, value);
        Currency entry12 = new Currency("eur", "20121020");
        value = 1.69;
        currencyInfo.put(entry12, value);
        Currency entry13 = new Currency("eur", "20121021");
        value = 1.75;
        currencyInfo.put(entry13, value);
        Currency entry14 = new Currency("eur", "20121022");
        value = 1.70;
        currencyInfo.put(entry14, value);
        Currency entry15 = new Currency("eur", "20121023");
        value = 1.69;
        currencyInfo.put(entry15, value);
        Currency entry16 = new Currency("eur", "20121024");
        value = 1.50;
        currencyInfo.put(entry16, value);
        Currency entry17 = new Currency("eur", "20121025");
        value = 1.50;
        currencyInfo.put(entry17, value);
        Currency entry18 = new Currency("eur", "20121026");
        value = 1.51;
        currencyInfo.put(entry18, value);
        Currency entry19 = new Currency("eur", "20121027");
        value = 1.51;
        currencyInfo.put(entry19, value);
        Currency entry20 = new Currency("eur", "20121028");
        value = 1.50;
        currencyInfo.put(entry20, value);
        Currency entry21 = new Currency("eur", "20121029");
        value = 1.55;
        currencyInfo.put(entry21, value);
        Currency entry22 = new Currency("eur", "20121030");
        value = 1.50;
        currencyInfo.put(entry22, value);
        Currency entry23 = new Currency("eur", "20121031");
        value = 1.60;
        currencyInfo.put(entry23, value);
        Currency entry24 = new Currency("eur", "20121101");
        value = 2.00;
        currencyInfo.put(entry24, value);
        Currency entry25 = new Currency("eur", "20121102");
        value = 2.01;
        currencyInfo.put(entry25, value);
        Currency entry26 = new Currency("eur", "20121103");
        value = 2.00;
        currencyInfo.put(entry26, value);
        Currency entry27 = new Currency("eur", "20121104");
        value = 1.98;
        currencyInfo.put(entry27, value);
        Currency entry28 = new Currency("eur", "20121105");
        value = 1.97;
        currencyInfo.put(entry28, value);
        Currency entry29 = new Currency("eur", "20121106");
        value = 1.95;
        currencyInfo.put(entry29, value);
        Currency entry30 = new Currency("eur", "20121107");
        value = 1.80;
        currencyInfo.put(entry30, value);
        Currency entry31 = new Currency("eur", "20121108");
        value = 1.70;
        currencyInfo.put(entry31, value);
        Currency entry32 = new Currency("eur", "20121109");
        value = 1.71;
        currencyInfo.put(entry32, value);
        Currency entry33 = new Currency("eur", "20121110");
        value = 1.69;
        currencyInfo.put(entry33, value);
        Currency entry34 = new Currency("eur", "20121111");
        value = 1.69;
        currencyInfo.put(entry34, value);
        Currency entry35 = new Currency("eur", "20121112");
        value = 1.71;
        currencyInfo.put(entry35, value);
        Currency entry36 = new Currency("eur", "20121113");
        value = 1.69;
        currencyInfo.put(entry36, value);
        Currency entry37 = new Currency("eur", "20121114");
        value = 1.65;
        currencyInfo.put(entry37, value);
        Currency entry38 = new Currency("eur", "20121115");
        value = 1.66;
        currencyInfo.put(entry38, value);
        Currency entry39 = new Currency("eur", "20121116");
        value = 1.75;
        currencyInfo.put(entry39, value);
        Currency entry40 = new Currency("eur", "20121117");
        value = 1.75;
        currencyInfo.put(entry40, value);
        Currency entry41 = new Currency("eur", "20121118");
        value = 2.00;
        currencyInfo.put(entry41, value);
        Currency entry42 = new Currency("eur", "20121119");
        value = 2.10;
        currencyInfo.put(entry42, value);
        Currency entry43 = new Currency("eur", "20121120");
        value = 2.09;
        currencyInfo.put(entry43, value);
        Currency entry44 = new Currency("eur", "20121121");
        value = 2.10;
        currencyInfo.put(entry44, value);
        Currency entry45 = new Currency("eur", "20121122");
        value = 2.08;
        currencyInfo.put(entry45, value);
        Currency entry46 = new Currency("eur", "20121123");
        value = 2.05;
        currencyInfo.put(entry46, value);
        Currency entry47 = new Currency("eur", "20121124");
        value = 2.00;
        currencyInfo.put(entry47, value);
        Currency entry48 = new Currency("eur", "20121125");
        value = 1.98;
        currencyInfo.put(entry48, value);
        Currency entry49 = new Currency("eur", "20121126");
        value = 1.99;
        currencyInfo.put(entry49, value);
        Currency entry50 = new Currency("eur", "20121127");
        value = 1.89;
        currencyInfo.put(entry50, value);
        Currency entry51 = new Currency("eur", "20121128");
        value = 1.80;
        currencyInfo.put(entry51, value);
        Currency entry52 = new Currency("eur", "20121129");
        value = 1.81;
        currencyInfo.put(entry52, value);
        Currency entry53 = new Currency("eur", "20121130");
        value = 1.80;
        currencyInfo.put(entry53, value);

        // Brazilian Real
        Currency brlEntry = new Currency("brl", "20121120");
        value = 2.13;
        currencyInfo.put(brlEntry, value);
        brlEntry = new Currency("brl", "20121121");
        value = 2.10;
        currencyInfo.put(brlEntry, value);
        brlEntry = new Currency("brl", "20121122");
        value = 2.05;
        currencyInfo.put(brlEntry, value);
        brlEntry = new Currency("brl", "20121123");
        value = 2.22;
        currencyInfo.put(brlEntry, value);
        brlEntry = new Currency("brl", "20121124");
        value = 2.00;
        currencyInfo.put(brlEntry, value);
        brlEntry = new Currency("brl", "20121125");
        value = 2.02;
        currencyInfo.put(brlEntry, value);
        brlEntry = new Currency("brl", "20121126");
        value = 1.98;
        currencyInfo.put(brlEntry, value);
        brlEntry = new Currency("brl", "20121127");
        value = 2.08;
        currencyInfo.put(brlEntry, value);
        brlEntry = new Currency("brl", "20121128");
        value = 2.13;
        currencyInfo.put(brlEntry, value);
        brlEntry = new Currency("brl", "20121129");
        value = 2.15;
        currencyInfo.put(brlEntry, value);
        brlEntry = new Currency("brl", "20121130");
        value = 2.10;
        currencyInfo.put(brlEntry, value);
    }

    /**
     * Add a currency conversion rate to the database.
     * 
     * @param currency - The currency code
     * @param date - The date the rate applies for
     * @param rate - The conversion rate
     */
    public void addConversionRate(String currency, String date, Double rate)
    {
        Currency entry = new Currency(currency, date);
        currencyInfo.put(entry, rate);
    }

    /**
     * Remove a currency conversion rate from the database.
     * 
     * @param currency - The currency code to look for
     * @param date - The date for the conversion rate. when put together with the currency code this
     *            uniquely defines a rate.
     * @throws KeyNotFoundException - When the (currency, date) pair cannot be found in the database
     *             for removal
     */
    public void removeConversionRate(String currency, String date) throws KeyNotFoundException
    {
        ArrayList<String> conversion = new ArrayList<String>();
        conversion.add(currency.toLowerCase());
        conversion.add(date);

        Double value = currencyInfo.remove(conversion);
        if (value == null)
        {
            throw new KeyNotFoundException("Could not find either currency, " + currency
                    + " or date " + date + " in currency DB.");
        }
    }

    /**
     * Gets the USD value of a currency given a date.
     * 
     * @param currency the currency to be converted.
     * @param date the date of the transaction
     * @return The USD value for one unit of the specified currency on the given date
     * @throws KeyNotFoundException if the specified currency or date could not be found in the
     *             database.
     */
    @Override
    public Double getConversion(String currency, String date) throws KeyNotFoundException
    {
        log.info("Request for conversion rate. Currency={}, Date={}", currency, date);

        Currency conversion = new Currency(currency.toLowerCase(), date);
        Double value = currencyInfo.get(conversion);
        if (value == null)
        {
            throw new KeyNotFoundException("Could not find either currency, " + currency
                    + " or date " + date + " in currency DB.");
        }
        return value;
    }
}
