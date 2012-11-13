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
