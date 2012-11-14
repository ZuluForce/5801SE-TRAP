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
package edu.umn.se.trap.db;

import java.util.List;

/**
 * @author planeman
 * 
 */
public class PerDiemDBWrapper extends PerDiemDB
{
    private final PerDiemDB perDiemDB;

    public PerDiemDBWrapper()
    {
        perDiemDB = new PerDiemDB();
    }

    @Override
    public boolean equals(Object obj)
    {
        return perDiemDB.equals(obj);
    }

    @Override
    public List<Double> getDomesticPerDiem(String city, String state) throws KeyNotFoundException
    {
        return perDiemDB.getDomesticPerDiem(city, state);
    }

    @Override
    public List<Double> getDomesticPerDiem(String state) throws KeyNotFoundException
    {
        return perDiemDB.getDomesticPerDiem(state);
    }

    @Override
    public List<Double> getInternationalPerDiem(String city, String country)
            throws KeyNotFoundException
    {
        return perDiemDB.getInternationalPerDiem(city, country);
    }

    @Override
    public List<Double> getInternationalPerDiem(String country) throws KeyNotFoundException
    {
        return perDiemDB.getInternationalPerDiem(country);
    }

    @Override
    public int hashCode()
    {
        return perDiemDB.hashCode();
    }

    @Override
    public String toString()
    {
        return perDiemDB.toString();
    }

}
