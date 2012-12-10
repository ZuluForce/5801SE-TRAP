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
// TestPerDiemDB.java
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
import edu.umn.se.trap.db.PerDiemDB;

/**
 * Mimics the database of meal/incidental per diems.
 * 
 * @author ggay
 * 
 */
public class TestPerDiemDB extends PerDiemDB
{
    private static Logger log = LoggerFactory.getLogger(TestPerDiemDB.class);

    /**
     * This type enumerates the fields of the {@link ArrayList} with the per diem information.
     */
    public static enum RATE_FIELDS
    {
        BREAKFAST_RATE, /* Breakfast rate in USD */
        LUNCH_RATE, /* Lunch rate in USD */
        DINNER_RATE, /* Dinner rate in USD */
        INCIDENTAL_CEILING, /* Incidental ceiling in USD */
        LODGING_CEILING; /* Lodging ceiling in USD */
    };

    Map<Location, List<Double>> perDiemInfo = new HashMap<Location, List<Double>>();

    /**
     * This type combines the city, state, and country into a single key for indexing purposes.
     */
    public static class Location implements Comparable<Location>
    {
        public String city;
        public String state;
        public String country;

        /**
         * Constructor. Sets up the object.
         */
        public Location(String ci, String s, String co)
        {
            city = ci;
            state = s;
            country = co;
        }

        /**
         * Compares equality of two Location objects.
         * 
         * @param object to compare to.
         * @return a Boolean indicating equality.
         */
        @Override
        public boolean equals(Object o)
        {
            if (o == this)
                return true;
            if (o == null || !(o instanceof Location))
                return false;
            Location l = Location.class.cast(o);
            return city.equals(l.city) && state.equals(l.state) && country.equals(l.country);
        }

        /**
         * Check the hashcode of a Location object for equality purposes
         * 
         * @return integer hashcode for this Location object.
         */
        @Override
        public int hashCode()
        {
            return city.hashCode() * 3 + state.hashCode() * 5 + country.hashCode() * 7;
        }

        /**
         * Compares two Location objects for ordering purposes
         * 
         * @param Location to compare to.
         * @return a negative integer, zero, or a positive integer as this object is less than,
         *         equal to, or greater than the specified object.
         */
        @Override
        public int compareTo(Location l)
        {
            if (l == this)
                return 0;
            int c = city.compareTo(l.city);
            if (c != 0)
                return c;
            int s = state.compareTo(l.state);
            if (s != 0)
                return s;
            return country.compareTo(l.country);
        }

        /**
         * Create a string representation of this location.
         */
        @Override
        public String toString()
        {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("City = %s\n", city));
            sb.append(String.format("State = %s\n", state));
            sb.append(String.format("Country = %s\n", country));

            return sb.toString();
        }
    }

    public static class PerDiemBuilder
    {
        private List<Double> rates;
        String city = "";
        String state = "";
        String country = "";

        public PerDiemBuilder()
        {
            rates = new ArrayList<Double>();
        }

        public void setCity(String city)
        {
            this.city = city;
        }

        public void setState(String state)
        {
            this.state = state;
        }

        public void setCountry(String country)
        {
            this.country = country;
        }

        public Location getLocation()
        {
            return new Location(city.toLowerCase(), state.toLowerCase(), country.toLowerCase());
        }

        public void addRate(RATE_FIELDS field, Double rate)
        {
            while (rates.size() < field.ordinal())
            {
                rates.add(0.0);
            }
            rates.set(field.ordinal(), rate);
        }

        public void addBreakfastRate(Double rate)
        {
            addRate(RATE_FIELDS.BREAKFAST_RATE, rate);
        }

        public void addLunchRate(Double rate)
        {
            addRate(RATE_FIELDS.LUNCH_RATE, rate);
        }

        public void addDinnerRate(Double rate)
        {
            addRate(RATE_FIELDS.DINNER_RATE, rate);
        }

        public void addIncidentalRate(Double rate)
        {
            addRate(RATE_FIELDS.INCIDENTAL_CEILING, rate);
        }

        public void addLodgingRate(Double rate)
        {
            addRate(RATE_FIELDS.LODGING_CEILING, rate);
        }

        public void setRates(List<Double> rates)
        {
            this.rates = rates;
        }

        public List<Double> getRates()
        {
            return rates;
        }
    }

    /**
     * Constructor. Sets up the object.
     */
    public TestPerDiemDB()
    {
        /* Example 1: Domestic */

        Location location = new Location("minneapolis", /* City. */
        "mn", /* State. Only applicable for domestic travel. */
        "united states"); /* Country */
        ArrayList<Double> rates = new ArrayList<Double>();
        rates.add(7.0); /* Breakfast rate in USD */
        rates.add(12.0); /* Lunch rate in USD */
        rates.add(26.0); /* Dinner rate in USD */
        rates.add(5.0); /* Incidental ceiling in USD */
        rates.add(150.0); /* Lodging ceiling in USD */

        perDiemInfo.put(location, rates);

        /* Example 2: International, specific city */

        Location location2 = new Location("zurich", "", "switzerland");
        ArrayList<Double> rates2 = new ArrayList<Double>();
        rates2.add(12.0); /* Breakfast rate in USD */
        rates2.add(25.0); /* Lunch rate in USD */
        rates2.add(50.0); /* Dinner rate in USD */
        rates2.add(20.0); /* Incidental ceiling in USD */
        rates2.add(225.0); /* Lodging ceiling in USD */

        perDiemInfo.put(location2, rates2);

        /* Example 3: International, country rate */

        Location location3 = new Location("", "", "switzerland");
        ArrayList<Double> rates3 = new ArrayList<Double>();
        rates3.add(10.0); /* Breakfast rate in USD */
        rates3.add(20.0); /* Lunch rate in USD */
        rates3.add(40.0); /* Dinner rate in USD */
        rates3.add(20.0); /* Incidental ceiling in USD */
        rates3.add(250.0); /* Hotel ceiling in USD */

        perDiemInfo.put(location3, rates3);

        Location location4 = new Location("des moines", "ia", "united states");
        ArrayList<Double> rates4 = new ArrayList<Double>();
        rates4.add(7.0); /* Breakfast rate in USD */
        rates4.add(11.0); /* Lunch rate in USD */
        rates4.add(23.0); /* Dinner rate in USD */
        rates4.add(0.0); /* Incidental ceiling in USD */
        rates4.add(150.0); /* Hotel ceiling in USD */

        perDiemInfo.put(location4, rates4);

        // NOTE, I'm using the same rates here. If the rates change for 1,
        // even at runtime, they will all change!

        // Same rates for Kansas City, MO
        Location location5 = new Location("kansas city", "mo", "united states");
        perDiemInfo.put(location5, rates4);

        // Same rates for Lawrence, KS
        Location location6 = new Location("lawrence", "ks", "united states");
        perDiemInfo.put(location6, rates4);

        Location location7 = new Location("sao paulo", "", "brazil");
        ArrayList<Double> rates7 = new ArrayList<Double>();
        rates7.add(15.0); /* Breakfast rate in USD */
        rates7.add(20.0); /* Lunch rate in USD */
        rates7.add(25.0); /* Dinner rate in USD */
        rates7.add(20.0); /* Incidental ceiling in USD */
        rates7.add(175.0); /* Hotel ceiling in USD */
        perDiemInfo.put(location7, rates7);

        Location location8 = new Location("", "", "brazil");
        ArrayList<Double> rates8 = new ArrayList<Double>();
        rates8.add(12.0); /* Breakfast rate in USD */
        rates8.add(25.0); /* Lunch rate in USD */
        rates8.add(40.0); /* Dinner rate in USD */
        rates8.add(20.0); /* Incidental ceiling in USD */
        rates8.add(125.0); /* Hotel ceiling in USD */
        perDiemInfo.put(location8, rates8);

        Location location9 = new Location("", "", "puerto rico");
        ArrayList<Double> rates9 = new ArrayList<Double>();
        rates9.add(15.0); /* Breakfast rate in USD */
        rates9.add(25.0); /* Lunch rate in USD */
        rates9.add(40.0); /* Dinner rate in USD */
        rates9.add(10.0); /* Incidental ceiling in USD */
        rates9.add(200.0); /* Hotel ceiling in USD */
        perDiemInfo.put(location9, rates9);

        Location location10 = new Location("jacksonville", "fl", "united states");
        ArrayList<Double> rates10 = new ArrayList<Double>();
        rates10.add(10.0); /* Breakfast rate in USD */
        rates10.add(22.0); /* Lunch rate in USD */
        rates10.add(35.0); /* Dinner rate in USD */
        rates10.add(0.0); /* Incidental ceiling in USD */
        rates10.add(120.0); /* Hotel ceiling in USD */
        perDiemInfo.put(location10, rates10);
    }

    /**
     * Add a per diem entry to the database.
     * 
     * @param builder
     */
    public void addRateToDB(PerDiemBuilder builder)
    {
        perDiemInfo.put(builder.getLocation(), builder.getRates());
    }

    /**
     * Remove a rate from the per diem database
     * 
     * @param builder - The builder with information about the rate to remove. Really only need
     *            enough info to construct a location.
     * @throws KeyNotFoundException When the location cannot be found in the database.
     */
    public void removeRateFromDB(PerDiemBuilder builder) throws KeyNotFoundException
    {
        this.removeRateFromDB(builder.getLocation());
    }

    /**
     * Remove a rate from the per diem database
     * 
     * @param location - The location that the rates are indexed under
     * @throws KeyNotFoundException When the location cannot be found in the database.
     */
    public void removeRateFromDB(Location location) throws KeyNotFoundException
    {
        List<Double> removed = perDiemInfo.remove(location);
        if (removed == null)
        {
            System.out.println("Location = " + location.toString());
            throw new KeyNotFoundException("Could not find location for removal");
        }
    }

    /**
     * Fill a per diem builder with the information in the database for the given city/state. If
     * that isn't found then the state alone is tried.
     * 
     * @param city - City for per diems
     * @param state - State for per diems
     * @return - The PerDiemBuilder object with values filled from the database entry
     * @throws KeyNotFoundException When the per diem for the given city/state or just state cannot
     *             be found.
     */
    public PerDiemBuilder fillBuilderWithDomesticInfo(String city, String state)
            throws KeyNotFoundException
    {
        List<Double> rates;
        try
        {
            rates = this.getDomesticPerDiem(city, state);
        }
        catch (KeyNotFoundException notFound)
        {
            rates = this.getDomesticPerDiem(state);
        }

        PerDiemBuilder builder = new PerDiemBuilder();
        builder.setCity(city);
        builder.setState(state);
        builder.setCountry("united states");

        builder.setRates(rates);

        return builder;
    }

    /**
     * Fill a per diem builder with the information in the database for the given city/country. If
     * that isn't found then the country alone is tried.
     * 
     * @param city - City for per diems
     * @param country - Country for per diems
     * @return - The PerDiemBuilder object with values filled from the database entry
     * @throws KeyNotFoundException When the per diem for the given city/country or just country
     *             cannot be found.
     */
    public PerDiemBuilder fillBuilderWithIntlInfo(String city, String country)
            throws KeyNotFoundException
    {
        List<Double> rates;
        try
        {
            rates = this.getInternationalPerDiem(city, country);
        }
        catch (KeyNotFoundException notFound)
        {
            rates = this.getInternationalPerDiem(country);
        }

        PerDiemBuilder builder = new PerDiemBuilder();
        builder.setCity(city);
        builder.setCountry(country);

        builder.setRates(rates);

        return builder;
    }

    /**
     * Gets domestic per diem rates as a list of strings.
     * 
     * @param city the name of the city where a meal was purchased.
     * @param state the state where the meal was purchased.
     * @return a list containing the per diem rates, broken down by type. This contains breakfast,
     *         lunch, dinner, and incidentals - all in USD. A null or empty list returned from this
     *         method should be treated as an invalid city/state pairing.
     * @throws KeyNotFoundException if the specified city/state pairing could not be found in the
     *             database.
     */
    @Override
    public List<Double> getDomesticPerDiem(String city, String state) throws KeyNotFoundException
    {
        log.info("Request for domestic per diem. city={}, state={}", city, state);

        Location location = new Location(city.toLowerCase(), state.toLowerCase(), "united states");
        List<Double> rateInfo = perDiemInfo.get(location);
        if (rateInfo == null)
        {
            throw new KeyNotFoundException("Could not find city, " + city + ", in the database.");
        }
        return rateInfo;
    }

    /**
     * Gets domestic per diem rates as a list of strings.
     * 
     * @param state the state where the meal was purchased.
     * @return a list containing the per diem rates, broken down by type. This contains breakfast,
     *         lunch, dinner, incidentals, and lodging - all in USD. A null or empty list returned
     *         from this method should be treated as an invalid state input. The {@link RATE_FIELDS}
     *         enum is provided to help you index the list.
     * @throws KeyNotFoundException if the specified state could not be found in the database.
     */
    @Override
    public List<Double> getDomesticPerDiem(String state) throws KeyNotFoundException
    {
        log.info("Request for domestic per diem. state={}", state);

        Location location = new Location("", state.toLowerCase(), "united states");
        List<Double> rateInfo = perDiemInfo.get(location);
        if (rateInfo == null)
        {
            throw new KeyNotFoundException("Could not find state, " + state + ", in the database.");
        }
        return rateInfo;
    }

    /**
     * Gets international per diem rates as a list of strings.
     * 
     * @param city the name of the city where a meal was purchased.
     * @param country the country where the meal was purchased.
     * @return a list containing the per diem rates, broken down by type. This contains breakfast,
     *         lunch, dinner, incidentals, and lodging - all in USD. A null or empty list returned
     *         from this method should be treated as an invalid city/country pairing. The
     *         {@link RATE_FIELDS} enum is provided to help you index the list.
     * @throws KeyNotFoundException if the specified city/country pairing could not be found in the
     *             database.
     */
    @Override
    public List<Double> getInternationalPerDiem(String city, String country)
            throws KeyNotFoundException
    {
        log.info("Request for international per diem. city={}, country={}", city, country);
        Location location = new Location(city.toLowerCase(), "", country.toLowerCase());
        List<Double> rateInfo = perDiemInfo.get(location);
        if (rateInfo == null)
        {
            throw new KeyNotFoundException("Could not find city, " + city + ", in the database.");
        }
        return rateInfo;
    }

    /**
     * Gets international per diem rates as a list of strings.
     * 
     * @param country the country where the meal was purchased.
     * @return a list containing the per diem rates, broken down by type. This contains breakfast,
     *         lunch, dinner, and incidentals - all in USD. A null or empty list returned from this
     *         method should be treated as an invalid country input. The {@link RATE_FIELDS} enum is
     *         provided to help you index the list.
     * @throws KeyNotFoundException if the specified country could not be found in the database.
     */
    @Override
    public List<Double> getInternationalPerDiem(String country) throws KeyNotFoundException
    {
        log.info("Request for international per diem. country={}", country);

        Location location = new Location("", "", country.toLowerCase());
        List<Double> rateInfo = perDiemInfo.get(location);
        if (rateInfo == null)
        {
            throw new KeyNotFoundException("Could not find country, " + country
                    + ", in the database.");
        }
        return rateInfo;
    }
}
