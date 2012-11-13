/**
 * 
 */
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
    private float expenseAmount;
    private String expenseCurrency;

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

}
