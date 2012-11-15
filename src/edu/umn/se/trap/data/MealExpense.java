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
public class MealExpense
{
    private Date expenseDate;
    private MealTypeEnum type;
    private String city;
    private String state;
    private String country;

    public MealExpense()
    {
        expenseDate = null;
        type = MealTypeEnum.UNSET;
        city = state = country = null;
    }

    public boolean isEmpty()
    {
        boolean empty = true;

        empty &= expenseDate == null;
        empty &= city == null;
        empty &= state == null;
        empty &= country == null;
        empty &= type == MealTypeEnum.UNSET;

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

    public MealTypeEnum getType()
    {
        return type;
    }

    public void setType(MealTypeEnum type)
    {
        this.type = type;
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

}
