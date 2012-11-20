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


/**
 * A data object containing all information pertaining to one meal expense.
 * 
 * @author planeman
 * 
 */
public class MealExpense extends PerDiemExpense
{
    /** The type for this meal expense (ie breakfast, lunch, dinner) */
    private MealTypeEnum type;

    /**
     * Construct the MealExpense object. Sets all attributes to unset sentinels.
     */
    public MealExpense()
    {
        expenseDate = null;
        type = MealTypeEnum.NOT_SET;
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
    @Override
    public boolean isEmpty()
    {
        boolean empty = true;

        empty &= empty &= type == MealTypeEnum.NOT_SET;

        return empty;
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
