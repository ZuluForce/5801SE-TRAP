/**
 * 
 */
package edu.umn.se.trap.db;

import java.util.List;

/**
 * @author planeman
 * 
 */
public class UserGrantDBWrapper extends UserGrantDB
{
    private final UserGrantDB userGrantDB;

    public UserGrantDBWrapper()
    {
        userGrantDB = new UserGrantDB();
    }

    @Override
    public boolean equals(Object obj)
    {
        return userGrantDB.equals(obj);
    }

    @Override
    public List<String> getUserGrantInfo(String arg0) throws KeyNotFoundException
    {
        return userGrantDB.getUserGrantInfo(arg0);
    }

    @Override
    public int hashCode()
    {
        return userGrantDB.hashCode();
    }

    @Override
    public String toString()
    {
        return userGrantDB.toString();
    }

}
