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
import edu.umn.se.trap.exception.TRAPDatabaseException;

/**
 * @author andrewh
 * 
 */
public class PerDiemDBWrapper
{
    private static final Logger log = LoggerFactory.getLogger(PerDiemDBWrapper.class);

    private static PerDiemDB perDiemDB;

    public static List<Double> getDomesticPerDiem(String city, String state)
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

    public static List<Double> getDomesticPerDiem(String state) throws KeyNotFoundException
    {
        return perDiemDB.getDomesticPerDiem(state);
    }

    public static Double getDomesticPerDiemMeal(String city, String state, MealTypeEnum meal)
            throws KeyNotFoundException
    {
        List<Double> perDiems = getDomesticPerDiem(city, state);
        return getPerDiemMeal(perDiems, meal);
    }

    public static Double getDomesticPerDiemLodging(String city, String state)
            throws KeyNotFoundException
    {
        List<Double> perDiems = getDomesticPerDiem(city, state);
        return perDiems.get(PerDiemDB.RATE_FIELDS.LODGING_CEILING.ordinal());
    }

    public static Double getDomesticPerDiemIncidental(String city, String state)
            throws KeyNotFoundException
    {
        List<Double> perDiems = getDomesticPerDiem(city, state);
        return perDiems.get(PerDiemDB.RATE_FIELDS.INCIDENTAL_CEILING.ordinal());
    }

    public static List<Double> getInternationalPerDiem(String city, String country)
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

    public static List<Double> getInternationalPerDiem(String country) throws KeyNotFoundException
    {
        return perDiemDB.getInternationalPerDiem(country);
    }

    public static Double getInternationalPerDiemMeal(String city, String country, MealTypeEnum meal)
            throws KeyNotFoundException
    {
        List<Double> perDiems = getInternationalPerDiem(city, country);
        return getPerDiemMeal(perDiems, meal);
    }

    public static Double getInternationalPerDiemLodging(String city, String country)
            throws KeyNotFoundException
    {
        List<Double> perDiems = getInternationalPerDiem(city, country);
        return perDiems.get(PerDiemDB.RATE_FIELDS.LODGING_CEILING.ordinal());
    }

    public static Double getInternationalPerDiemIncidental(String city, String country)
            throws KeyNotFoundException
    {
        List<Double> perDiems = getInternationalPerDiem(city, country);
        return perDiems.get(PerDiemDB.RATE_FIELDS.INCIDENTAL_CEILING.ordinal());
    }

    private static Double getPerDiemMeal(List<Double> perDiems, MealTypeEnum meal)
            throws KeyNotFoundException
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
            throw new TRAPDatabaseException("Unknown meal type: " + meal.toString());
        }
    }

    public static void setPerDiemDB(PerDiemDB db)
    {
        perDiemDB = db;
    }
}
