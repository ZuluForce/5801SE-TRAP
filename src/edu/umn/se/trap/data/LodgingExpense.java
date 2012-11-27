/*****************************************************************************************
 * Copyright (c) 2012 Dylan Bettermann, Andrew Helgeson, Brian Maurer, Ethan Waytas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ****************************************************************************************/
package edu.umn.se.trap.data;

/**
 * A data object containing all information pertaining to one lodging expense.
 * 
 * @author andrewh
 * 
 */
public class LodgingExpense extends PerDiemExpense implements ExpenseWithCurrencyIface
{
    /** The claimed amount of the expense */
    private Double expenseAmount;

    /** The currency for this expense */
    private String expenseCurrency;

    /** The original, pre-conversion currency for this expense */
    private String originalCurrency;

    /**
     * Construct the LodgingExpense object. This initializes all attributes to unset sentinel
     * values.
     */
    public LodgingExpense()
    {
        super();
        expenseCurrency = null;
        expenseAmount = -1.0;
    }

    /**
     * Checks if the lodging expense is empty. To do this it checks if all the attributes are set to
     * the same values as they were set to in the constructor.
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
        empty &= super.isEmpty();
        empty &= expenseCurrency == null;
        empty &= expenseAmount == -1.0;

        return empty;
    }

    /**
     * Get the claimed expense amount.
     * 
     * @return - The claimed expense amount
     */
    @Override
    public Double getExpenseAmount()
    {
        return expenseAmount;
    }

    /**
     * Set the claimed expense amount.
     * 
     * @param expenseAmount - The amount to set as the claimed expense amount.
     */
    @Override
    public void setExpenseAmount(Double expenseAmount)
    {
        this.expenseAmount = expenseAmount;
    }

    /**
     * Get the currency for this expense.
     * 
     * @return - the currency for this expense
     */
    @Override
    public String getExpenseCurrency()
    {
        return expenseCurrency;
    }

    /**
     * Set the currency for this expense
     * 
     * @param expenseCurrency - The currency to set for this expense
     */
    @Override
    public void setExpenseCurrency(String expenseCurrency)
    {
        this.expenseCurrency = expenseCurrency;
    }

    /**
     * Set the original, pre-conversion, currency for this expense.
     * 
     * @param currency - The currency to set
     */
    @Override
    public void setOriginalCurrency(String currency)
    {
        originalCurrency = currency;
    }

    /**
     * Get the original, pre-conversion, currency for this expense.
     * 
     * @return - The original currency for this expense
     */
    @Override
    public String getOriginalCurrency()
    {
        return originalCurrency;
    }

    /**
     * Create a string representation of this expense.
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Lodging Expense:\n");
        sb.append(String.format("\tDate: %s\n", expenseDate));
        sb.append(String.format("\tCountry: %s\n", city));
        sb.append(String.format("\tState: %s\n", state));
        sb.append(String.format("\tCountry: %s\n", country));
        sb.append(String.format("\tAmount: $%f\n", expenseAmount));
        sb.append(String.format("\tCurrency: %s\n", expenseCurrency));

        return sb.toString();
    }

}
