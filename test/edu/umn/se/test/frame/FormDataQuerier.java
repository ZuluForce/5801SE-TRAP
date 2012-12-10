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
// FormDataQuerying.java
package edu.umn.se.test.frame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.umn.se.trap.data.IncidentalExpense;
import edu.umn.se.trap.data.LodgingExpense;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TRAPConstants;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.data.TransportationTypeEnum;
import edu.umn.se.trap.data.TripDay;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.FormDataConverter;

/**
 * This class' purpose is to query a set of form data about certain information. This is to reduce
 * boilerplate code.
 * 
 * @author andrewh
 * 
 */
public class FormDataQuerier
{
    /**
     * Given a format string fill it with the day number for the day number of the first incidental.
     * 
     * @param formData - the form data to search for the incidental in
     * @param fieldFormat - the format string to fill with the day number
     * @return - a string that is the result of formatting the given string with the day number for
     *         the first incidental
     * @throws TRAPException - When the form data cannot be converted to an app.
     */
    public static String buildFieldStrForLastIncidental(Map<String, String> formData,
            String fieldFormat) throws TRAPException
    {
        List<Integer> incidentalDays = findIncidentalDays(formData);

        if (incidentalDays.size() == 0)
        {
            return null;
        }

        return String.format(fieldFormat, incidentalDays.get(incidentalDays.size() - 1));
    }

    /**
     * Given a format string fill it with the day number for the day number of the first incidental.
     * 
     * @param formData - the form data to search for the incidental in
     * @param fieldFormat - the format string to fill with the day number
     * @return - a string that is the result of formatting the given string with the day number for
     *         the first incidental
     * @throws TRAPException - When the form data cannot be converted to an app.
     */
    public static String buildFieldStrForFirstIncidental(Map<String, String> formData,
            String fieldFormat) throws TRAPException
    {
        List<Integer> incidentalDays = findIncidentalDays(formData);

        if (incidentalDays.size() == 0)
        {
            return null;
        }

        return String.format(fieldFormat, incidentalDays.get(0));
    }

    /**
     * Find the day number for all incidentals in the given formData.
     * 
     * @param formData - form data to search for incidentals in
     * @return - The day number for all incidentals in the given data
     * @throws TRAPException - When the form data cannot be converted to an app
     */
    public static List<Integer> findIncidentalDays(Map<String, String> formData)
            throws TRAPException
    {
        ReimbursementApp app = FormDataConverter.formToReimbursementApp(formData);

        return findIncidentalDays(app);
    }

    /**
     * Find the day number for all incidentals in app.
     * 
     * @param app - app to search for incidentals in
     * @return - The day number for all incidentals in the given app
     */
    public static List<Integer> findIncidentalDays(ReimbursementApp app)
    {
        List<Integer> incidentalDays = new ArrayList<Integer>();

        for (TripDay day : app.getAllTripDays())
        {
            IncidentalExpense expense = day.getIncidentalExpense();
            if (expense != null)
            {
                incidentalDays.add(day.getDayNumber());
            }
        }

        return incidentalDays;
    }

    /**
     * Get the transportation expense number for all rental expenses.
     * 
     * @param formData - Form data
     * @return - The expense number for all transportation rental expenses
     * @throws TRAPException - When the formData cannot be converted into an app
     */
    public static List<Integer> findRentalExpenses(Map<String, String> formData)
            throws TRAPException
    {
        return findRentalExpenses(formData, null);
    }

    /**
     * Get the transportation expense number for rental expenses.
     * 
     * @param formData - Form data
     * @param domestic - If true, return only domestic rentals. If false, return only foreign
     *            rentals, If null, return all.
     * @return - The expense number for all transportation rental expenses
     * @throws TRAPException - When the formData cannot be converted into an app
     */
    public static List<Integer> findRentalExpenses(Map<String, String> formData, Boolean domestic)
            throws TRAPException
    {
        ReimbursementApp app = FormDataConverter.formToReimbursementApp(formData);

        TransportationExpense texpense;

        List<TransportationExpense> texpenses = app.getTransportationExpenseList();
        List<Integer> rentalExpenses = new ArrayList<Integer>();
        for (int i = 0; i < texpenses.size(); ++i)
        {
            texpense = texpenses.get(i);
            if (texpense.isRentalCar())
            {
                if (domestic == null)
                {
                    rentalExpenses.add(i + 1);
                    continue;
                }
                if (texpense.getOriginalCurrency().compareToIgnoreCase(TRAPConstants.USD) == 0)
                {
                    if (domestic)
                        rentalExpenses.add(i + 1);
                }
                else
                {
                    if (!domestic)
                        rentalExpenses.add(i + 1);
                }
            }
        }

        return rentalExpenses;
    }

    /**
     * Find the index (expense number) for all transportation expenses with the given type.
     * 
     * @param formData - The formData to search for the transportation expenses
     * @param type - The type of transportation expenses to look for
     * @return - The index (expense number) of all transportation expenses with the given type.
     * @throws TRAPException - When the form data cannot be converted to an app
     */
    public static List<Integer> findTransportExpenses(Map<String, String> formData,
            TransportationTypeEnum type) throws TRAPException
    {
        ReimbursementApp app = FormDataConverter.formToReimbursementApp(formData);

        return findTransportExpenses(app, type);
    }

    /**
     * Find the index (expense number) for all transportation expenses with the given type.
     * 
     * @param app - The app to search for the transportation expenses
     * @param type - The type of transportation expenses to look for
     * @return - The index (expense number) of all transportation expenses with the given type.
     */
    public static List<Integer> findTransportExpenses(ReimbursementApp app,
            TransportationTypeEnum type)
    {
        TransportationExpense texpense;

        List<Integer> expensesOfType = new ArrayList<Integer>();
        List<TransportationExpense> texpenses = app.getTransportationExpenseList();
        for (int i = 0; i < texpenses.size(); ++i)
        {
            texpense = texpenses.get(i);

            if (texpense.getTransportationType() == type)
            {
                expensesOfType.add(i + 1);
            }
        }

        return expensesOfType;
    }

    /**
     * Find the index of all lodging expenses in the given form.
     * 
     * @param formData - the form data to search for lodging expenses
     * @return - The indexes (day numbers) of all lodging expenses
     * @throws TRAPException - when the form data cannot be converted to an app
     */
    public static List<Integer> findLodgingExpenses(Map<String, String> formData)
            throws TRAPException
    {
        ReimbursementApp app = FormDataConverter.formToReimbursementApp(formData);

        List<Integer> blah = new ArrayList<Integer>();
        List<LodgingExpense> lodgingExpenses = app.getLodgingExpenseList();
        for (int i = 0; i < lodgingExpenses.size(); ++i)
        {
            blah.add(i + 1);
        }

        return blah;

    }

}
