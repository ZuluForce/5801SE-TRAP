/**
 * 
 */
package edu.umn.se.trap.db;

import java.util.List;

/**
 * @author planeman
 * 
 */
public class UserDBWrapper
{
    private final UserDB userDB;

    public UserDBWrapper()
    {
        userDB = new UserDB();
    }

    @Override
    public boolean equals(Object obj)
    {
        return userDB.equals(obj);
    }

    public List<String> getUserInfo(String userName) throws KeyNotFoundException
    {
        return userDB.getUserInfo(userName);
    }

    @Override
    public int hashCode()
    {
        return userDB.hashCode();
    }

    @Override
    public String toString()
    {
        return userDB.toString();
    }

}
