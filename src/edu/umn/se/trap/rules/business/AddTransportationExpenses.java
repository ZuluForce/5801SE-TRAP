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
// AddTransportationExpenses.java
package edu.umn.se.trap.rules.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TRAPConstants;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.data.TransportationTypeEnum;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.exception.TRAPException;

/**
 * This rule adds all transportation expenses not covered elsewhere. The ones this rule does not
 * handler are as follows: mileage and baggage.
 * 
 * @author planeman
 * 
 */
public class AddTransportationExpenses extends BusinessLogicRule
{
    /** Logger for the AddTransportationExpenses class */
    private static Logger log = LoggerFactory.getLogger(AddTransportationExpenses.class);

    /**
     * This rule adds all transportation expenses not covered elsewhere. The ones this rule does not
     * handler are as follows: mileage and baggage.
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        Double transportTotal = 0.0;

        for (TransportationExpense expense : app.getTransportationExpenseList())
        {
            TransportationTypeEnum type = expense.getTransportationType();
            Double amount = expense.getExpenseAmount();
            String rental = expense.getTransportationRental();

            // Baggage is already handled by another rule
            if (type == TransportationTypeEnum.BAGGAGE)
                continue;

            // Skip any personal car expenses since those are handled by the mileage rule
            if (type == TransportationTypeEnum.CAR
                    && rental.compareToIgnoreCase(TRAPConstants.STR_NO) == 0)
            {
                continue;
            }

            if (expense.getReimbursementAmount() >= 0.0)
            {
                log.error("Transportation expense reimbursement amount set before expected");
                throw new FormProcessorException(
                        "Transportation expense reimbursement amount set before expected");
            }

            expense.setReimbursementAmount(amount);
            app.addToReimbursementTotal(amount);

            transportTotal += amount;
        }

        log.info("Added ${} in transport expenses to the total", transportTotal);
    }
}
