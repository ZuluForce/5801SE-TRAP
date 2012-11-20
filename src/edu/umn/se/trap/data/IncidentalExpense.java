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
 * A data object containing all information pertaining to one incidental expense.
 * 
 * @author planeman
 * 
 */
public class IncidentalExpense extends PerDiemExpense
{
    /** The claimed amount for this expense */
    private Double expenseAmount;

    /** The currency for this expense */
    private String expenseCurrency;

    /** The justification for this expense */
    private String expenseJustification;

    /**
     * Construct the IncidentalExpense. This initializes all internal state.
     */
    public IncidentalExpense()
    {
        expenseDate = null;
        city = state = country = expenseCurrency = expenseJustification = null;
        expenseAmount = -1.0;
    }

    /**
     * Checks if the incidental expense is empty. To do this it checks if all the attributes are set
     * to the same values as they were set to in the constructor.
     * 
     * This is necessary for the FormDataConverter to determine if in the process of constructing
     * the expense we have an empty expense or a partial expense.
     * 
     * @return - true if none of the fields in this expense have been set, false otherwise.
     */
    @Override
    public boolean isEmpty()
    {
        boolean empty = true;
        empty &= city == null;
        empty &= state == null;
        empty &= country == null;
        empty &= expenseDate == null;
        empty &= expenseCurrency == null;
        empty &= expenseJustification == null;

        empty &= expenseAmount == -1.0;

        return empty;
    }

    /**
     * Get the date for this expense
     * 
     * @return - The date for this expense
     */
    @Override
    public Date getExpenseDate()
    {
        return expenseDate;
    }

    /**
     * Set the date for this expense
     * 
     * @param expenseDate - The date of the expense
     */
    @Override
    public void setExpenseDate(Date expenseDate)
    {
        this.expenseDate = expenseDate;
    }

    /**
     * Get the city the expense occurred in.
     * 
     * @return - The city the expense occurred in.
     */
    @Override
    public String getCity()
    {
        return city;
    }

    /**
     * Set the city the expense occurred in.
     * 
     * @param city - The city the expense occurred in.
     */
    @Override
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     * Get the state the expense occurred in.
     * 
     * @return - The state the expense occurred in.
     */
    @Override
    public String getState()
    {
        return state;
    }

    /**
     * Set the state the expense occurred in.
     * 
     * @param state - The state the expense occurred in.
     */
    @Override
    public void setState(String state)
    {
        this.state = state;
    }

    /**
     * Get the country the expense occurred in.
     * 
     * @return - The country the expense occurred in.
     */
    @Override
    public String getCountry()
    {
        return country;
    }

    /**
     * Set the country the expense occurred in.
     * 
     * @param country - The country the expense occurred in.
     */
    @Override
    public void setCountry(String country)
    {
        this.country = country;
    }

    /**
     * Get the claimed amount for this expense
     * 
     * @return - The claimed amount for this expense
     */
    public Double getExpenseAmount()
    {
        return expenseAmount;
    }

    /**
     * Set the claimed expense amount.
     * 
     * @param expenseAmount - The amount to set for this expense's claim
     */
    public void setExpenseAmount(Double expenseAmount)
    {
        this.expenseAmount = expenseAmount;
    }

    /**
     * Get the currency for this expense.
     * 
     * @return - The currency for this expense
     */
    public String getExpenseCurrency()
    {
        return expenseCurrency;
    }

    /**
     * Set the currency for this expense.
     * 
     * @param expenseCurrency - The currency to set for this expense.
     */
    public void setExpenseCurrency(String expenseCurrency)
    {
        this.expenseCurrency = expenseCurrency;
    }

    /**
     * Get the justification for this expense.
     * 
     * @return - The justification for this expense.
     */
    public String getExpenseJustification()
    {
        return expenseJustification;
    }

    /**
     * Set the justification for this expense.
     * 
     * @param expenseJustification - The justification to set for this expense.
     */
    public void setExpenseJustification(String expenseJustification)
    {
        this.expenseJustification = expenseJustification;
    }

    /**
     * Create a string representation of this expense.
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Incidental Expense:");
        sb.append(String.format("\tDate: %s\n", expenseDate));
        sb.append(String.format("\tCity: %s\n", city));
        sb.append(String.format("\tState: %s\n", state));
        sb.append(String.format("\tCountry: %s\n", country));
        sb.append(String.format("\tAmount: $%f\n", expenseAmount));
        sb.append(String.format("\tCurrency: %s\n", expenseCurrency));
        sb.append(String.format("\tJustification: %s\n", expenseJustification));

        return sb.toString();
    }

}
