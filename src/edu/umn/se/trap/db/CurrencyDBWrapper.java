package edu.umn.se.trap.db;

/**
 * @author planeman
 * 
 */
public class CurrencyDBWrapper extends CurrencyDB
{
    private final CurrencyDB currencyDB;

    public CurrencyDBWrapper()
    {
        currencyDB = new CurrencyDB();
    }

    @Override
    public boolean equals(Object obj)
    {
        return currencyDB.equals(obj);
    }

    @Override
    public Double getConversion(String currency, String date) throws KeyNotFoundException
    {
        return currencyDB.getConversion(currency, date);
    }

    @Override
    public int hashCode()
    {
        return currencyDB.hashCode();
    }

    @Override
    public String toString()
    {
        return currencyDB.toString();
    }

}
