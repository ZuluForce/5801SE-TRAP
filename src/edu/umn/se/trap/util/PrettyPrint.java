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
