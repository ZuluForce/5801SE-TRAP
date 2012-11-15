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
