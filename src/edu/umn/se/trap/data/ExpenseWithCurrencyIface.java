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
// ExpenseWithCurrencyIface.java
package edu.umn.se.trap.data;

import java.util.Date;

/**
 * @author andrewh
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
