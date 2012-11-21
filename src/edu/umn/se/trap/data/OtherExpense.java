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

import java.util.Date;

/**
 * A data object containing all information pertaining to one meal expense.
 * 
 * @author planeman
 * 
 */
public class OtherExpense
{
    /** The date the expense occurred on */
    private Date expenseDate;

    /** The justification for this expense */
    private String expenseJustification;

    /** The currency for this expense */
    private String expenseCurrency;

    /** The claimed amount for this expense */
    private Double expenseAmount;

    /** The reimbursement amount for this expense */
    private Double reimbursementAmount;

    /**
     * Get the date for this expense
     * 
     * @return - The date for the expense
     */
    public Date getExpenseDate()
    {
        return expenseDate;
    }

    /**
     * Set the date for the expense
     * 
     * @param expenseDate - The date to set for this expense
     */
    public void setExpenseDate(Date expenseDate)
    {
        this.expenseDate = expenseDate;
    }

    /**
     * Get the justification for this expense.
     * 
     * @return - The justification for this expense
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
     * Get the claimed amount for this expense.
     * 
     * @return - The claimed amount for this expense.
     */
    public Double getExpenseAmount()
    {
        return expenseAmount;
    }

    /**
     * Set the this expense's claimed amount.
     * 
     * @param expenseAmount - The value to set for this expense's claimed amount.
     */
    public void setExpenseAmount(Double expenseAmount)
    {
        this.expenseAmount = expenseAmount;
    }

    /**
     * Get the reimbursement amount. This is the amount that is actually being reimbursed in
     * comparison to the claimed expense amount.
     * 
     * @return - The reimbursement amount.
     */
    public Double getReimbursementAmount()
    {
        return reimbursementAmount;
    }

    /**
     * Set the reimbursement amount for this expense.
     * 
     * @param amount - The value to set for this expenses reimbursement amount
     */
    public void setReimbursementAmount(Double amount)
    {
        reimbursementAmount = amount;
    }

    /**
     * Get the currency for this expense.
     * 
     * @return - The currency for this expense.
     */
    public String getExpenseCurrency()
    {
        return expenseCurrency;
    }

    /**
     * Set the currency for this expense.
     * 
     * @param expenseCurrency - The currency type to set for this expense.
     */
    public void setExpenseCurrency(String expenseCurrency)
    {
        this.expenseCurrency = expenseCurrency;
    }

    /**
     * Create a string representation of this object.
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Other Expense:\n");
        sb.append(String.format("\tDate: %s\n", expenseDate));
        sb.append(String.format("\tAmount: $%f\n", expenseAmount));
        sb.append(String.format("\tCurrency: %s\n", expenseCurrency));
        sb.append(String.format("\tJustification: %s\n", expenseJustification));

        return sb.toString();
    }
}
