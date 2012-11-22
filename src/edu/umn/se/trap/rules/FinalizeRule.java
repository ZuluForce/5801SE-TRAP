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
package edu.umn.se.trap.rules;

import java.util.Date;
import java.util.List;

import edu.umn.se.trap.data.Grant;
import edu.umn.se.trap.data.OtherExpense;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.data.TripDay;
import edu.umn.se.trap.db.GrantDBWrapper;
import edu.umn.se.trap.db.KeyNotFoundException;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.exception.InsufficientFundsException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.OutputFieldKeys;
import edu.umn.se.trap.rules.input.DateValidator;

/**
 * TODO: Write description
 * 
 * @author planeman
 * 
 */
public class FinalizeRule implements TRAPRule
{

    /**
     * Implements the checkRule functionality of the TRAPRule interface. The finalize rule
     * partitions the required fund between all grants according to the percentages and then adds
     * all output fields to complete the application processing.
     * 
     * @param app - The ReimbursementApp that has gone through all other rules and is ready to be
     *            finalized
     */
    @Override
    public void checkRule(ReimbursementApp app) throws TRAPException
    {
        double total = app.getReimbursementTotal();

        // Go through each grant and find how much we need from each
        for (Grant grant : app.getGrantList())
        {
            Double grantCharge = grant.getGrantPercentage() * total;

            // Check that the grant has the required funds
            try
            {
                double grantFunds = GrantDBWrapper.getGrantBalance(grant.getGrantAccount());
                if (grantFunds < grantCharge)
                {
                    String msg = String.format(
                            "Insufficent funds in grant %s. Required=%f, Contains=%f",
                            grant.getGrantAccount(), grantCharge, grantFunds);

                    throw new InsufficientFundsException(msg);
                }

                // Mark how much we need to charge so we can do it later
                grant.setGrantCharge(grantCharge);
                grant.setFinalBalance(grantFunds - grantCharge);
            }
            catch (KeyNotFoundException e)
            {
                throw new FormProcessorException("No grant found with account "
                        + grant.getGrantAccount(), e);
            }
        }

        // Update the grants with the charge amount deducted
        for (Grant grant : app.getGrantList())
        {
            try
            {
                // Update the balance
                GrantDBWrapper.updateAccountBalance(grant.getGrantAccount(),
                        grant.getFinalBalance());
            }
            catch (KeyNotFoundException e)
            {
                throw new FormProcessorException("No grant found with account "
                        + grant.getGrantAccount(), e);
            }
        }

        setOutputFields(app);

    }

    /**
     * Set all the fields required for the output map in TRAP.
     * 
     * @param app - The ReimbursementApp that has been fully processed and needs to have output
     *            fields set.
     * @throws FormProcessorException - On failure to look up DB values
     */
    private void setOutputFields(ReimbursementApp app) throws FormProcessorException
    {
        String formattedField;
        // Reimbursement Total
        formattedField = String.format("%1$,.2f", app.getReimbursementTotal());
        app.setOutputField(OutputFieldKeys.TOTAL_REIMBURSEMENT, formattedField);

        // Grant output fields
        List<Grant> grantList = app.getGrantList();
        formattedField = String.format("%d", grantList.size());
        app.setOutputField(OutputFieldKeys.NUM_GRANTS, formattedField);

        for (int i = 0; i < grantList.size(); ++i)
        {
            Grant grant = grantList.get(i);

            formattedField = String.format(OutputFieldKeys.GRANT_ACCOUNT_FMT, i + 1);
            app.setOutputField(formattedField, grant.getGrantAccount());

            formattedField = String.format(OutputFieldKeys.GRANT_PERCENT_FMT, i + 1);
            app.setOutputField(formattedField, grant.getGrantPercentage().toString());

            formattedField = String.format(OutputFieldKeys.GRANT_CHARGE_FMT, i + 1);
            app.setOutputField(formattedField, grant.getGrantCharge().toString());

        }

        // Add day totals
        app.setOutputField(OutputFieldKeys.NUM_DAYS, app.getNumDays().toString());
        for (TripDay day : app.getAllTripDays())
        {
            Date dayDate = DateValidator.advanceDateInDays(app.getDepartureDatetime(),
                    day.getDayNumber() - 1);

            formattedField = String.format(OutputFieldKeys.DAY_DATE_FMT, day.getDayNumber());
            app.setOutputField(formattedField, DateValidator.dateToString(dayDate));

            formattedField = String.format(OutputFieldKeys.DAY_TOTAL_FMT, day.getDayNumber());
            app.setOutputField(formattedField, day.getDayTotal().toString());
        }

        // TODO: Add any other fields not covered elsewhere

        // Add transportation expense fields
        List<TransportationExpense> transportExpenses = app.getTransportationExpenseList();
        app.setOutputField(OutputFieldKeys.NUM_TRANSPORTATION_EXPENSES,
                "" + transportExpenses.size());

        for (int i = 1; i <= transportExpenses.size(); ++i)
        {
            TransportationExpense expense = transportExpenses.get(i - 1);

            // Date
            formattedField = String.format(OutputFieldKeys.TRANSPORTATION_DATE_FMT, i);
            app.setOutputField(formattedField,
                    DateValidator.dateToString(expense.getExpenseDate()));

            // Type
            formattedField = String.format(OutputFieldKeys.TRANSPORTATION_TYPE_FMT, i);
            app.setOutputField(formattedField, expense.getTransportationType().toString());

            // Total
            formattedField = String.format(OutputFieldKeys.TRANSPORTATION_TOTAL_FMT, i);
            app.setOutputField(formattedField,
                    formatDoubleAsCurrency(expense.getReimbursementAmount()));
        }

        // Add other expense fields
        List<OtherExpense> otherExpenses = app.getOtherExpenseList();
        app.setOutputField(OutputFieldKeys.NUM_OTHER_EXPENSES, "" + otherExpenses.size());

        for (int i = 1; i <= otherExpenses.size(); ++i)
        {
            OtherExpense expense = otherExpenses.get(i - 1);

            // Date
            formattedField = String.format(OutputFieldKeys.OTHER_DATE_FMT, i);
            app.setOutputField(formattedField, DateValidator.dateToString(expense.getExpenseDate()));

            // Justification
            formattedField = String.format(OutputFieldKeys.OTHER_JUSTIFICATION_FMT, i);
            app.setOutputField(formattedField, expense.getExpenseJustification());

            // Total
            formattedField = String.format(OutputFieldKeys.OTHER_TOTAL_FMT, i);
            app.setOutputField(formattedField,
                    formatDoubleAsCurrency(expense.getReimbursementAmount()));
        }
    }

    /**
     * Given a double this will return its string representation in a format how money is usually
     * represented, with two decimal places.
     * 
     * @param money - The Double to be formatted to a string.
     * @return - A Double formatted as a string as "[0-9]+\.[0-9]{2}" to put it as a regex
     */
    public static String formatDoubleAsCurrency(Double money)
    {
        return String.format("%1$,.2f", money);
    }
}
