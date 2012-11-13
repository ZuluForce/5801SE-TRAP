/**
 * 
 */
package edu.umn.se.trap.data;

import java.util.Date;

/**
 * @author planeman
 * 
 */
public class OtherExpense
{
    private Date expenseDate;
    private String expenseJustification;
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

    public String getExpenseJustification()
    {
        return expenseJustification;
    }

    public void setExpenseJustification(String expenseJustification)
    {
        this.expenseJustification = expenseJustification;
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
