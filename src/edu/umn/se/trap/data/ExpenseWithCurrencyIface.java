// ExpenseWithCurrencyIface.java
package edu.umn.se.trap.data;

import java.util.Date;

/**
 * @author planeman
 * 
 */
public interface ExpenseWithCurrencyIface
{

    /**
     * Get the amount for the expense
     * 
     * @return - The amount for the expense as a Double
     */
    public Double getExpenseAmount();

    /**
     * Set the amount for the expense
     * 
     * @param amnt - The amount to set for the expense as a Double
     */
    public void setExpenseAmount(Double amnt);

    /**
     * Get the currency for the expense
     * 
     * @return - Currency represented as a string
     */
    public String getExpenseCurrency();

    /**
     * Set the currency for the expense
     * 
     * @param currency - The currency to set for the expense as a String
     */
    public void setExpenseCurrency(String currency);

    /**
     * Get the date the expense occurred on.
     * 
     * @return - The date of the expense
     */
    public Date getExpenseDate();

    /**
     * Set the original, pre-conversion, currency for this expense.
     * 
     * @param currency - The original currency to set for the expense
     */
    public void setOriginalCurrency(String currency);

    /**
     * Get the original, pre-conversion, currency for this expense.
     * 
     * @return - The original currency for this expense
     */
    public String getOriginalCurrency();
}
