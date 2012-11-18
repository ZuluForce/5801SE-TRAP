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
 * A data object containing all information pertaining to one meal expense.
 * 
 * @author planeman
 * 
 */
public class MealExpense
{
    /** The date the expense occurred */
    private Date expenseDate;

    /** The type for this meal expense (ie breakfast, lunch, dinner) */
    private MealTypeEnum type;

    /** The city the expense occurred in */
    private String city;

    /** The state the expense occurred in */
    private String state;

    /** The country the expense occurred in */
    private String country;

    /**
     * Construct the MealExpense object. Sets all attributes to unset sentinels.
     */
    public MealExpense()
    {
        expenseDate = null;
        type = MealTypeEnum.UNSET;
        city = state = country = null;
    }

    /**
     * Checks if the meal expense is empty. To do this it checks if all the attributes are set to
     * the same values as they were set to in the constructor.
     * 
     * This is necessary for the FormDataConverter to determine if in the process of constructing
     * the expense we have an empty expense or a partial expense.
     * 
     * @return - true if none of the fields in this expense have been set, false otherwise.
     */
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

    /**
     * Get the date for this expense.
     * 
     * @return - The date for this expense.
     */
    public Date getExpenseDate()
    {
        return expenseDate;
    }

    /**
     * Set the date for this expense.
     * 
     * @param expenseDate - The date for this expense.
     */
    public void setExpenseDate(Date expenseDate)
    {
        this.expenseDate = expenseDate;
    }

    /**
     * Get the type for this meal expense.
     * 
     * @see MealTypeEnum
     * @return - The type for this expense.
     */
    public MealTypeEnum getType()
    {
        return type;
    }

    /**
     * Set the type for this meal expense.
     * 
     * @param type - The type to set for this expense.
     */
    public void setType(MealTypeEnum type)
    {
        this.type = type;
    }

    /**
     * Get the city which this expense occurred in
     * 
     * @return - The city for this expense.
     */
    public String getCity()
    {
        return city;
    }

    /**
     * Set the city which this expense occurred in.
     * 
     * @param city - The city to set for this expense
     */
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     * Get the state which this expense occurred in
     * 
     * @return - The state for this expense.
     */
    public String getState()
    {
        return state;
    }

    /**
     * Set the state which this expense occurred in.
     * 
     * @param state - The state to set for this expense
     */
    public void setState(String state)
    {
        this.state = state;
    }

    /**
     * Get the country which this expense occurred in
     * 
     * @return - The ccountry for this expense.
     */
    public String getCountry()
    {
        return country;
    }

    /**
     * Set the country which this expense occurred in.
     * 
     * @param country - The country to set for this expense
     */
    public void setCountry(String country)
    {
        this.country = country;
    }

    /**
     * Create a string representation of this object.
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Meal Expense:\n");
        sb.append(String.format("\tDate:%s\n", expenseDate));
        sb.append(String.format("\tCity:%s\n", city));
        sb.append(String.format("\tState:%s\n", state));
        sb.append(String.format("\tCountry:%s\n", country));
        sb.append(String.format("\tType:%s\n", type));

        return sb.toString();
    }
}
