/*****************************************************************************************
 * Copyright (c) 2012 Dylan Bettermann, Andrew Helgeson, Brian Maurer, Ethan Waytas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 ****************************************************************************************/
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

    public TransportationExpense()
    {
        transportationDate = null;
        transportationCarrier = transportationRental = transportationCurrency = null;

        transportationMilesTraveled = -1;
        transportationAmount = -1;

        transportationType = TransportationTypeEnum.NOT_SET;
    }

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
