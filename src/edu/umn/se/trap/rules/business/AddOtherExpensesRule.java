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
// AddOtherExpensesRule.java
package edu.umn.se.trap.rules.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.OtherExpense;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * Add all other expenses to the reimbursement total.
 * 
 * @author planeman
 * 
 */
public class AddOtherExpensesRule extends BusinessLogicRule
{
    /** Log for the AddOtherExpensesRule */
    private static final Logger log = LoggerFactory.getLogger(AddOtherExpensesRule.class);

    /**
     * Add all other expenses to the reimbursement total.
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        Double otherTotal = 0.0;

        for (OtherExpense expense : app.getOtherExpenseList())
        {
            Double amount = expense.getExpenseAmount();
            if (expense.getReimbursementAmount() >= 0.0)
            {
                log.error("Other expense reimbursement amount set before expected");
                throw new FormProcessorException(
                        "Other expense had reimbursement amount set before expected");
            }

            app.addToReimbursementTotal(amount);
            expense.setReimbursementAmount(amount);

            otherTotal += amount;
        }

        log.info("Added ${} in other expenses to the total", otherTotal);
    }
}
