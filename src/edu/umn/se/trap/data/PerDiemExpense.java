// PerDiemExpense.java
package edu.umn.se.trap.data;

import java.util.Date;

/**
 * @author planeman
 * 
 */
public class PerDiemExpense
{
    /** The date the expense occurred */
    protected Date expenseDate;

    /** The city the expense occurred in */
    protected String city;

    /** The state the expense occurred in */
    protected String state;

    /** The country the expense occurred in */
    protected String country;

    /**
     * Construct the PerDiemExpense
     */
    public PerDiemExpense()
    {
        expenseDate = null;
        city = state = country = null;
    }

    /**
     * Checks if the per diem expense is empty. To do this it checks if all the attributes are set
     * to the same values as they were set to in the constructor.
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
        empty &= (city == null && state == null && country == null);

        return empty;
    }

    /**
     * Get the date for this expense.
     * 
     * @return - The date for this expense
     */
    public Date getExpenseDate()
    {
        return expenseDate;
    }

    /**
     * Set the date for this expense.
     * 
     * @param expenseDate - The date to set for this expense.
     */
    public void setExpenseDate(Date expenseDate)
    {
        this.expenseDate = expenseDate;
    }

    /**
     * Get the city this expense occurred in.
     * 
     * @return - The city this expense occurred in
     */
    public String getCity()
    {
        return city;
    }

    /**
     * Set the city this expense occurred in.
     * 
     * @param city - The city to set for this expense.
     */
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     * Get the state this expense occurred in.
     * 
     * @return - The state this expense occurred in
     */
    public String getState()
    {
        return state;
    }

    /**
     * Set the state this expense occurred in.
     * 
     * @param state - The state to set for this expense.
     */
    public void setState(String state)
    {
        this.state = state;
    }

    /**
     * Get the country this expense occurred in.
     * 
     * @return - The country this expense occurred in
     */
    public String getCountry()
    {
        return country;
    }

    /**
     * Set the country this expense occurred in.
     * 
     * @param country - The country to set for this expense.
     */
    public void setCountry(String country)
    {
        this.country = country;
    }
}
