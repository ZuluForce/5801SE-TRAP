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
// PrettyPrint.java
package edu.umn.se.trap.util;

import java.util.Map;

/**
 * @author planeman
 * 
 */
public class PrettyPrint
{

    /**
     * Pretty printing of a Java map. Print values out on individual lines as key:value
     * 
     * @param map - Map to create string representation of
     * @return - String representing the map
     */
    public static <K, V> String prettyMap(Map<K, V> map)
    {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<K, V> kv : map.entrySet())
        {
            sb.append(String.format("%s : %s\n", kv.getKey(), kv.getValue()));
        }

        return sb.toString();
    }
}
