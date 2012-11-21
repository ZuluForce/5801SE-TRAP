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
 * @author andrewh
 * 
 */
public class PerDiemDBWrapper
{
    private static final PerDiemDB perDiemDB = new PerDiemDB();

    public static List<Double> getDomesticPerDiem(String city, String state)
            throws KeyNotFoundException
    {
        return perDiemDB.getDomesticPerDiem(city, state);
    }

    public static List<Double> getDomesticPerDiem(String state) throws KeyNotFoundException
    {
        return perDiemDB.getDomesticPerDiem(state);
    }

    public static List<Double> getInternationalPerDiem(String city, String country)
            throws KeyNotFoundException
    {
        return perDiemDB.getInternationalPerDiem(city, country);
    }

    public static List<Double> getInternationalPerDiem(String country) throws KeyNotFoundException
    {
        return perDiemDB.getInternationalPerDiem(country);
    }

}
