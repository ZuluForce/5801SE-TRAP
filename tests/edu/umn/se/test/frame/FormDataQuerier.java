// FormDataQuerying.java
package edu.umn.se.test.frame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.umn.se.trap.data.IncidentalExpense;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TRAPConstants;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.data.TransportationTypeEnum;
import edu.umn.se.trap.data.TripDay;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.FormDataConverter;

/**
 * @author planeman
 * 
 */
public class FormDataQuerier
{
    public static String buildFieldStrForAnIncidental(Map<String, String> formData,
            String fieldFormat) throws TRAPException
    {
        List<Integer> incidentalDays = findIncidentalDays(formData);

        if (incidentalDays.size() == 0)
        {
            return null;
        }

        return String.format(fieldFormat, incidentalDays.get(0));
    }

    public static List<Integer> findIncidentalDays(Map<String, String> formData)
            throws TRAPException
    {
        ReimbursementApp app = FormDataConverter.formToReimbursementApp(formData);

        return findIncidentalDays(app);
    }

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
     * @throws TRAPException
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
     * @throws TRAPException
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

    public static List<Integer> findTransportExpenses(Map<String, String> formData,
            TransportationTypeEnum type) throws TRAPException
    {
        ReimbursementApp app = FormDataConverter.formToReimbursementApp(formData);

        return findTransportExpenses(app, type);
    }

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
}
