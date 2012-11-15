/*****************************************************************************************
 * W * Copyright (c) 2012 Dylan Bettermann, Andrew Helgeson, Brian Maurer, Ethan Waytas
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
public class IncidentalExpense
{
    private Date expenseDate;
    private String city;
    private String state;
    private String country;
    private float expenseAmount;
    private String expenseCurrency;
    private String expenseJustification;

    public IncidentalExpense()
    {
        expenseDate = null;
        city = state = country = expenseCurrency = expenseJustification = null;
        expenseAmount = -1;
    }

    public boolean isEmpty()
    {
        boolean empty = true;
        empty &= city == null;
        empty &= state == null;
        empty &= country == null;
        empty &= expenseDate == null;
        empty &= expenseCurrency == null;
        empty &= expenseJustification == null;

        empty &= expenseAmount == -1;

        return empty;
    }

    public Date getExpenseDate()
    {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate)
    {
        this.expenseDate = expenseDate;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public float getExpenseAmount()
    {
        return expenseAmount;
    }

    public void setExpenseAmount(float expenseAmount)
    {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseCurrency()
    {
        return expenseCurrency;
    }

    public void setExpenseCurrency(String expenseCurrency)
    {
        this.expenseCurrency = expenseCurrency;
    }

    public String getExpenseJustification()
    {
        return expenseJustification;
    }

    public void setExpenseJustification(String expenseJustification)
    {
        this.expenseJustification = expenseJustification;
    }

}
