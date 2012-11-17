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
 * An object to hold all information related to transportation expenses. Not all fields will be set
 * for every type of transportation expense so it is up to the user of this object to check.
 * 
 * @author planeman
 * 
 */
public class TransportationExpense
{
    private Date transportationDate;
    private String transportationCarrier;
    private Integer transportationMilesTraveled;
    private String transportationRental;
    private String transportationCurrency;
    private TransportationTypeEnum transportationType;

    private Double expenseAmount;
    private Double reimbursementAmount;

    /**
     * Construct the TransportationExpense object.
     */
    public TransportationExpense()
    {
        transportationDate = null;
        transportationCarrier = transportationRental = transportationCurrency = null;

        transportationMilesTraveled = -1;
        expenseAmount = reimbursementAmount = -1.0;

        transportationType = TransportationTypeEnum.NOT_SET;
    }

    /**
     * Get the date this expense occurred on.
     * 
     * @see #setTransportationDate(Date)
     * @return - The date this expense occured on
     */
    public Date getTransportationDate()
    {
        return transportationDate;
    }

    /**
     * Set the date this expense occurred on.
     * 
     * @see #getTransportationDate()
     * @param transportationDate - The date the expense occurred on.
     */
    public void setTransportationDate(Date transportationDate)
    {
        this.transportationDate = transportationDate;
    }

    /**
     * Get the name of the transportation carrier for this expense. This won't be set for some
     * transportation types.
     * 
     * @see #setTransportationCarrier(String)
     * @return - The carrier for this expense.
     */
    public String getTransportationCarrier()
    {
        return transportationCarrier;
    }

    /**
     * Set the name of the transportation carrier for this expense.
     * 
     * @see #getTransportationCarrier()
     * @param transportationCarrier - The carrier for this expense.
     */
    public void setTransportationCarrier(String transportationCarrier)
    {
        this.transportationCarrier = transportationCarrier;
    }

    /**
     * Get the number of miles traveled for this expense
     * 
     * @see #setTransportationMilesTraveled(int)
     * @return - Number of miles travelled.
     */
    public int getTransportationMilesTraveled()
    {
        return transportationMilesTraveled;
    }

    /**
     * Set the number of miles traveled for this expense. This is only relevant for car travel but
     * can be set regardless.
     * 
     * @see #getTransportationMilesTraveled()
     * @param transportationMilesTraveled - The number of miles traveled
     */
    public void setTransportationMilesTraveled(int transportationMilesTraveled)
    {
        this.transportationMilesTraveled = transportationMilesTraveled;
    }

    /**
     * Get the transportation rental flag for this expense.
     * 
     * @see #setTransportationRental(String)
     * @return - The rental flag yes/no string
     */
    public String getTransportationRental()
    {
        return transportationRental;
    }

    /**
     * Set the rental flag for this expense
     * 
     * @see #getTransportationRental()
     * @param transportationRental - the yes/no string that is the rental car flag
     */
    public void setTransportationRental(String transportationRental)
    {
        this.transportationRental = transportationRental;
    }

    /**
     * Get the claimed expense amount for this expense.
     * 
     * @see #setExpenseAmount(Double)
     * @return - The expense amount for this expense
     */
    public Double getExpenseAmount()
    {
        return expenseAmount;
    }

    /**
     * Set the expense amount for this expense. This is the amount claimed for this expense in the
     * submitted form.
     * 
     * @see #getExpenseAmount()
     * @param expenseAmount - The claimed expense amount for this expense
     */
    public void setExpenseAmount(Double expenseAmount)
    {
        this.expenseAmount = expenseAmount;
    }

    /**
     * Get the reimbursement amount for this expense.
     * 
     * @see #setReimbursementAmount(Double)
     * @return - The reimbursement amount for this expense
     */
    public Double getReimbursementAmount()
    {
        return reimbursementAmount;
    }

    /**
     * Set the reimbursement total for this expense. The expenseAmount is the amount listed in the
     * input form, this is the amount that is to be reimbursed after processing the rules.
     * 
     * @param amount - The reimbursement amount
     */
    public void setReimbursementAmount(Double amount)
    {
        reimbursementAmount = amount;
    }

    /**
     * Get the currency type for this expense
     * 
     * @return - The currency type for this expense
     */
    public String getTransportationCurrency()
    {
        return transportationCurrency;
    }

    /**
     * Set the currency type for this expense
     * 
     * @param transportationCurrency - The currency type for this expense
     */
    public void setTransportationCurrency(String transportationCurrency)
    {
        this.transportationCurrency = transportationCurrency;
    }

    /**
     * Get the type of this expense
     * 
     * @return - The type of this transportation expense
     */
    public TransportationTypeEnum getTransportationType()
    {
        return transportationType;
    }

    /**
     * Set the type of this expense
     * 
     * @param transportationType - Type of this transportation expense
     */
    public void setTransportationType(TransportationTypeEnum transportationType)
    {
        this.transportationType = transportationType;
    }

    /**
     * Create a string representation of this object.
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Transportation Expense:\n");
        sb.append(String.format("\tDate: %s\n", transportationDate));
        sb.append(String.format("\tAmount: $%f\n", expenseAmount));
        sb.append(String.format("\tCurrency: %s\n", transportationCurrency));
        sb.append(String.format("\tCarrier: %s\n", transportationCarrier));
        sb.append(String.format("\tRental: %s\n", transportationRental));
        if (transportationType == TransportationTypeEnum.CAR
                && transportationRental.compareTo("no") == 0)
        {
            sb.append(String.format("\tMiles: %d\n", transportationMilesTraveled));
        }
        sb.append(String.format("\tType: %s\n", transportationType));

        return sb.toString();
    }
}
