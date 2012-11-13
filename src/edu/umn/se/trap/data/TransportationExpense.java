/**
 * 
 */
package edu.umn.se.trap.data;

import java.util.Date;

/**
 * @author planeman
 * 
 */
public class TransportationExpense
{
    private Date transportationDate;
    private String transportationCarrier;
    private int transportationMilesTraveled;
    private String transportationRental;
    private float transportationAmount;
    private String transportationCurrency;
    private TransportationTypeEnum transportationType;

    public Date getTransportationDate()
    {
        return transportationDate;
    }

    public void setTransportationDate(Date transportationDate)
    {
        this.transportationDate = transportationDate;
    }

    public String getTransportationCarrier()
    {
        return transportationCarrier;
    }

    public void setTransportationCarrier(String transportationCarrier)
    {
        this.transportationCarrier = transportationCarrier;
    }

    public int getTransportationMilesTraveled()
    {
        return transportationMilesTraveled;
    }

    public void setTransportationMilesTraveled(int transportationMilesTraveled)
    {
        this.transportationMilesTraveled = transportationMilesTraveled;
    }

    public String getTransportationRental()
    {
        return transportationRental;
    }

    public void setTransportationRental(String transportationRental)
    {
        this.transportationRental = transportationRental;
    }

    public float getTransportationAmount()
    {
        return transportationAmount;
    }

    public void setTransportationAmount(float transportationAmount)
    {
        this.transportationAmount = transportationAmount;
    }

    public String getTransportationCurrency()
    {
        return transportationCurrency;
    }

    public void setTransportationCurrency(String transportationCurrency)
    {
        this.transportationCurrency = transportationCurrency;
    }

    public TransportationTypeEnum getTransportationType()
    {
        return transportationType;
    }

    public void setTransportationType(TransportationTypeEnum transportationType)
    {
        this.transportationType = transportationType;
    }

}
