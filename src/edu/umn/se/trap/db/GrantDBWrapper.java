package edu.umn.se.trap.db;

import java.util.List;

/**
 * @author planeman
 * 
 */
public class GrantDBWrapper extends GrantDB
{
    private final GrantDB grantDB;

    public GrantDBWrapper()
    {
        grantDB = new GrantDB();
    }

    @Override
    public boolean equals(Object obj)
    {
        return grantDB.equals(obj);
    }

    @Override
    public List<Object> getGrantInfo(String accountName) throws KeyNotFoundException
    {
        return grantDB.getGrantInfo(accountName);
    }

    @Override
    public int hashCode()
    {
        return grantDB.hashCode();
    }

    @Override
    public String toString()
    {
        return grantDB.toString();
    }

    @Override
    public void updateAccountBalance(String accountName, Double newBalance)
            throws KeyNotFoundException
    {
        grantDB.updateAccountBalance(accountName, newBalance);
    }

}
