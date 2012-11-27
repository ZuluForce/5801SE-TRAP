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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.MealTypeEnum;
import edu.umn.se.trap.exception.TRAPRuntimeException;

/**
 * A wrapper around a source of expense per diem informaiton. This provides per diems for meals,
 * incidentals and lodging.
 * 
 * @author andrewh
 * 
 */
public class PerDiemDBWrapper
{
    /** Log for this class */
    private static final Logger log = LoggerFactory.getLogger(PerDiemDBWrapper.class);

    /** Underlying db that the wrapper will call */
    private static PerDiemDB perDiemDB;

    /**
     * Get the per diem for a domestic expense given a city and state. If the per diem cannot be
     * found with the city and state it is retried with just the state.
     * 
     * @param city - City for the per diem.
     * @param state - State for the per diem
     * @return - The list of per diems for the given city/state (or just state if the first combo
     *         doesn't work)
     * @throws KeyNotFoundException - If the per diem cannot be found for the given city/state or
     *             just state
     */
    private static List<Double> getDomesticPerDiem(String city, String state)
            throws KeyNotFoundException
    {
        try
        {
            return perDiemDB.getDomesticPerDiem(city, state);
        }
        catch (KeyNotFoundException e)
        {
            log.warn("Couldn't find domestic per diem using city/state ({}/{})", city, state);
        }

        // Retry just with state
        return getDomesticPerDiem(state);
    }

    /**
     * Get the domestic per diems for a given state.
     * 
     * @param state - State to lookup for per diems.
     * @return - The list of per diems for the given states
     * @throws KeyNotFoundException If the per diems cannot be found for the given state
     */
    private static List<Double> getDomesticPerDiem(String state) throws KeyNotFoundException
    {
        return perDiemDB.getDomesticPerDiem(state);
    }

    /**
     * Get the domestic per diem for meals in the given city/state and the specific meal type. If
     * the per diem cannot be found for the city/state combo, just the state will be tried.
     * 
     * @param city - City for the per diem
     * @param state - State for the per diem
     * @param meal - Meal type to get the per diem for
     * @return - The per diem for the specific meal type on in the given city/state or just the
     *         state
     * @throws KeyNotFoundException - If there is no per diem listing for the given state
     */
    public static Double getDomesticPerDiemMeal(String city, String state, MealTypeEnum meal)
            throws KeyNotFoundException
    {
        List<Double> perDiems = getDomesticPerDiem(city, state);
        return getPerDiemMeal(perDiems, meal);
    }

    /**
     * Get the domestic per diem for lodging in the given city/state. If the per diem cannot be
     * found for the city/state, just the state will be tried.
     * 
     * @param city - City for the per diem
     * @param state - State for the per diem
     * @return - The per diem for lodging in the given city/state or just the state
     * @throws KeyNotFoundException If there is no per diem listing for the given state
     */
    public static Double getDomesticPerDiemLodging(String city, String state)
            throws KeyNotFoundException
    {
        List<Double> perDiems = getDomesticPerDiem(city, state);
        return perDiems.get(PerDiemDB.RATE_FIELDS.LODGING_CEILING.ordinal());
    }

    /**
     * Get the domestic per diem for incidnetals in the given city/state. If the per diem cannot be
     * found for the city/state, just the state will be tried.
     * 
     * @param city - City for the per diem
     * @param state - State for the per diem
     * @return - The per diem for incidentals in the given city/state or just the state
     * @throws KeyNotFoundException If there is no per diem listing for the given state
     */
    public static Double getDomesticPerDiemIncidental(String city, String state)
            throws KeyNotFoundException
    {
        List<Double> perDiems = getDomesticPerDiem(city, state);
        return perDiems.get(PerDiemDB.RATE_FIELDS.INCIDENTAL_CEILING.ordinal());
    }

    /**
     * Get the per diem for an international expense given a city and country. If the per diem
     * cannot be found with the city and country it is retried with just the country.
     * 
     * @param city - City for the per diem.
     * @param country - Country for the per diem
     * @return - The list of per diems for the given city/country (or just country if the first
     *         combo doesn't work)
     * @throws KeyNotFoundException - If the per diem cannot be found for the given city/country or
     *             just country
     */
    private static List<Double> getInternationalPerDiem(String city, String country)
            throws KeyNotFoundException
    {
        try
        {
            return perDiemDB.getInternationalPerDiem(city, country);
        }
        catch (KeyNotFoundException e)
        {
            log.warn("Couldn't find international per diem with city/country ({}/{})", city,
                    country);
        }

        // Retry with just the country
        return getInternationalPerDiem(country);
    }

    /**
     * Get the per diem for an international expense given a country.
     * 
     * @param country - Country for the per diem
     * @return - The list of per diems for the given country
     * @throws KeyNotFoundException - If the per diem cannot be found for the given country
     */
    public static List<Double> getInternationalPerDiem(String country) throws KeyNotFoundException
    {
        return perDiemDB.getInternationalPerDiem(country);
    }

    /**
     * Get the international per diem for meals in the given city/country and the specific meal
     * type. If the per diem cannot be found for the city/country combo, just the country will be
     * tried.
     * 
     * @param city - City for the per diem
     * @param country - Country for the per diem
     * @param meal - Meal type to get the per diem for
     * @return - The per diem for the specific meal type on in the given city/country or just the
     *         country
     * @throws KeyNotFoundException - If there is no per diem listing for the given country
     */
    public static Double getInternationalPerDiemMeal(String city, String country, MealTypeEnum meal)
            throws KeyNotFoundException
    {
        List<Double> perDiems = getInternationalPerDiem(city, country);
        return getPerDiemMeal(perDiems, meal);
    }

    /**
     * Get the international per diem for lodging in the given city/country. If the per diem cannot
     * be found for the city/country combo, just the country will be tried.
     * 
     * @param city - City for the per diem
     * @param country - Country for the per diem
     * @return - The per diem for the lodging in the given city/country or just the country
     * @throws KeyNotFoundException - If there is no per diem listing for the given country
     */
    public static Double getInternationalPerDiemLodging(String city, String country)
            throws KeyNotFoundException
    {
        List<Double> perDiems = getInternationalPerDiem(city, country);
        return perDiems.get(PerDiemDB.RATE_FIELDS.LODGING_CEILING.ordinal());
    }

    /**
     * Get the international per diem for incidentals in the given city/country. If the per diem
     * cannot be found for the city/country combo, just the country will be tried.
     * 
     * @param city - City for the per diem
     * @param country - Country for the per diem
     * @return - The per diem for the incidentals in the given city/country or just the country
     * @throws KeyNotFoundException - If there is no per diem listing for the given country
     */
    public static Double getInternationalPerDiemIncidental(String city, String country)
            throws KeyNotFoundException
    {
        List<Double> perDiems = getInternationalPerDiem(city, country);
        return perDiems.get(PerDiemDB.RATE_FIELDS.INCIDENTAL_CEILING.ordinal());
    }

    /**
     * Method that picks the specific meal per diem out of a list of per diems.
     * 
     * @param perDiems - The list of per diems taken from the underlying db
     * @param meal - The specific meal type to get
     * @return - The per diem for the meal type requested
     */
    private static Double getPerDiemMeal(List<Double> perDiems, MealTypeEnum meal)
    {
        switch (meal)
        {
        case BREAKFAST:
            return perDiems.get(PerDiemDB.RATE_FIELDS.BREAKFAST_RATE.ordinal());
        case LUNCH:
            return perDiems.get(PerDiemDB.RATE_FIELDS.LUNCH_RATE.ordinal());
        case DINNER:
            return perDiems.get(PerDiemDB.RATE_FIELDS.DINNER_RATE.ordinal());
        default:
            throw new TRAPRuntimeException("Unknown meal type: " + meal.toString());
        }
    }

    /**
     * Set the underlying db that is to be called by this wrapper
     * 
     * @param db - The db implementation for the wrapper to call.
     */
    public static void setPerDiemDB(PerDiemDB db)
    {
        perDiemDB = db;
    }
}
