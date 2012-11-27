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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.umn.se.trap.exception.InputValidationException;
import edu.umn.se.trap.exception.TRAPRuntimeException;
import edu.umn.se.trap.form.OutputFieldKeys;

/**
 * A large data object that holds the hierarchy of objects and attributes that represent an the
 * equivalent of an input form's data. The purpose for this data object is to make it simpler for
 * rules and later processing to access form data without being dependent on the format of the input
 * data.
 * 
 * @author andrewh
 * 
 */
public class ReimbursementApp
{
    /** Departure datetime for the trip */
    private Date departureDatetime;

    /** Arrival datetime for the trip */
    private Date arrivalDatetime;

    /** Number of days the trip spans */
    private Integer numDays;

    /** yes/no if the travel is using a sponsored CSE managed grant */
    private String travelTypeCSESponsored;

    /** yes/no if the travel is using a sponsored DTC managed grant */
    private String travelTypeDTCSponsored;

    /** yes/no if the travel is using a non-sponsored grant */
    private String travelTypeNonSponsored;

    /** The map to hold the output of the application processing */
    private final Map<String, String> outputFields;

    /** All lodging expenses for this application */
    private final List<LodgingExpense> lodgingExpenseList;

    /** All other expenses for this application */
    private final List<OtherExpense> otherExpenseList;

    /** All incidental expenses for this application */
    private final List<IncidentalExpense> incidentalExpenseList;

    /** All meal expenses for this application */
    private final List<MealExpense> mealExpenseList;

    /** All transportation expenses for this application */
    private final List<TransportationExpense> transportationExpenseList;

    /** The conference info for the submitted form */
    private ConferenceInfo conferenceInfo;

    /** Information on the submitting user */
    private UserInfo userInfo;

    /** All grants being used in this reimbursement */
    private final List<Grant> grantList;

    /** The total reimbursement amount for this app */
    private Double reimbursementTotal;

    /** Per day reimbursement totals. These are used for filling in the output map */
    private final List<TripDay> tripDays;

    /**
     * Construct the ReimbursementApp object. This initializes all attributes.
     */
    public ReimbursementApp()
    {
        outputFields = new HashMap<String, String>();
        lodgingExpenseList = new ArrayList<LodgingExpense>();
        otherExpenseList = new ArrayList<OtherExpense>();
        incidentalExpenseList = new ArrayList<IncidentalExpense>();
        mealExpenseList = new ArrayList<MealExpense>();
        transportationExpenseList = new ArrayList<TransportationExpense>();

        grantList = new ArrayList<Grant>();

        tripDays = new ArrayList<TripDay>();

        numDays = 0;
        reimbursementTotal = 0.0;
    }

    /**
     * Get the yes/no flag for the CSE managed sponsored travel type.
     * 
     * @return - The flag for CSE managed sponsored travel type.
     */
    public String getTravelTypeCSESponsored()
    {
        return travelTypeCSESponsored;
    }

    /**
     * Set the yes/no flag for CSE managed sponsored travel type.
     * 
     * @param travelTypeCSESponsored - The value to set for the CSE managed sponsored travel type
     *            flag.
     */
    public void setTravelTypeCSESponsored(String travelTypeCSESponsored)
    {
        this.travelTypeCSESponsored = travelTypeCSESponsored;
    }

    /**
     * Get the yes/no flag for the DTC managed sponsored travel type.
     * 
     * @return - The flag for DTC managed sponsored travel type.
     */
    public String getTravelTypeDTCSponsored()
    {
        return travelTypeDTCSponsored;
    }

    /**
     * Set the yes/no flag for DTC managed sponsored travel type.
     * 
     * @param travelTypeDTCSponsored - The value to set for the DTC managed sponsored travel type
     *            flag.
     */
    public void setTravelTypeDTCSponsored(String travelTypeDTCSponsored)
    {
        this.travelTypeDTCSponsored = travelTypeDTCSponsored;
    }

    /**
     * Get the yes/no flag for the non-sponsored travel type.
     * 
     * @return - The flag for non-sponsored travel type.
     */
    public String getTravelTypeNonSponsored()
    {
        return travelTypeNonSponsored;
    }

    /**
     * Set the yes/no flag for non-sponsored travel type.
     * 
     * @param travelTypeNonSponsored - The value to set for the non-sponsored travel type flag.
     */
    public void setTravelTypeNonSponsored(String travelTypeNonSponsored)
    {
        this.travelTypeNonSponsored = travelTypeNonSponsored;
    }

    /**
     * Get the departure datetime for this trip.
     * 
     * @return - The departure datetime for this trip
     */
    public Date getDepartureDatetime()
    {
        return departureDatetime;
    }

    /**
     * Set the departure datetime for this trip.
     * 
     * @param departureDatetime - The departure datetime to set for this trip
     */
    public void setDepartureDatetime(Date departureDatetime)
    {
        this.departureDatetime = departureDatetime;
    }

    /**
     * Get the arrival datetime for this trip
     * 
     * @return - The arrival datetime for this trip.
     */
    public Date getArrivalDatetime()
    {
        return arrivalDatetime;
    }

    /**
     * Set the arrival datetime for the trip.
     * 
     * @param arrivalDatetime - The arrival datetime to set for this trip.
     */
    public void setArrivalDatetime(Date arrivalDatetime)
    {
        this.arrivalDatetime = arrivalDatetime;
    }

    /**
     * Get the number of days for the trip.
     * 
     * @return - The length of the trip in days
     */
    public Integer getNumDays()
    {
        return numDays;
    }

    /**
     * Set the number of days for the trip.
     * 
     * @param numDays - Length of the trip in days
     */
    public void setNumDays(int numDays)
    {
        this.numDays = numDays;

        // Make sure we have a corresponding number of TripDay's
        while (numDays > tripDays.size())
        {
            tripDays.add(new TripDay(tripDays.size() + 1));
        }
    }

    /**
     * Get this app's conference information
     * 
     * @return - The conference information for this app
     */
    public ConferenceInfo getConferenceInfo()
    {
        return conferenceInfo;
    }

    /**
     * Set the conferenceInfo for this application
     * 
     * @param conferenceInfo - The value to set for this app's conferenceInfo
     */
    public void setConferenceInfo(ConferenceInfo conferenceInfo)
    {
        this.conferenceInfo = conferenceInfo;
    }

    /**
     * Get the user information
     * 
     * @return - The submitting user's information
     */
    public UserInfo getUserInfo()
    {
        return userInfo;
    }

    /**
     * Set the userInfo attribute
     * 
     * @param userInfo - Value to set for the userInfo attribute.
     */
    public void setUserInfo(UserInfo userInfo)
    {
        this.userInfo = userInfo;
    }

    /**
     * Add to the reimbursement total amount. If you are adding an amount pertaining to a
     * {@link MealExpense}, {@link LodgingExpense}, or {@link IncidentalExpense} you must use the
     * {@link #addtoPerDiemTotal(Double, Integer)} method. Essentially, if you are dealing with any
     * expense type that is held by a {@link TripDay} you should use the other method.
     * 
     * @see #getReimbursementTotal()
     * @param amount - The amount to add to the total
     */
    public void addToReimbursementTotal(Double amount)
    {
        reimbursementTotal += amount;
    }

    /**
     * This will add an expense amount to both the reimbursementTotal as well as the TripDay
     * corresponding to the day number provided.
     * 
     * @see TripDay
     * @param amount - The amount to add
     * @param dayNum - The day to add it under. (will also be added to grand total)
     */
    public void addtoPerDiemTotal(Double amount, Integer dayNum)
    {
        if (dayNum > tripDays.size())
        {
            throw new TRAPRuntimeException(String.format("Day %d is out of range for the trip",
                    dayNum));
        }

        reimbursementTotal += amount;

        TripDay day = tripDays.get(dayNum - 1);
        day.addToDayTotal(amount);
    }

    /**
     * Get the reimbursement total amount
     * 
     * @see #addToReimbursementTotal(Double)
     * @return - a Double representing the reimbursement total
     */
    public Double getReimbursementTotal()
    {
        return reimbursementTotal;
    }

    /**
     * Get the TripDay object for a particular day in the trip. If you request a day outside the
     * range of known trip days then a trap runtime exception will be thrown.
     * 
     * @param dayNumber - The day to get the TripDay object for.
     * @return - The TripDay object for the given dayNumber
     */
    public TripDay getTripDay(Integer dayNumber)
    {
        if (dayNumber > tripDays.size())
        {
            throw new TRAPRuntimeException("ReimbursementApp has no TripDay for day " + dayNumber);
        }

        return tripDays.get(dayNumber - 1);
    }

    /**
     * Get all TripDays.
     * 
     * @return - The list of TripDays, one for each day of the trip.
     */
    public List<TripDay> getAllTripDays()
    {
        return tripDays;
    }

    /**
     * Get the map of output fields. These are the fields resulting from the processing of an
     * application.
     * 
     * @see #setOutputField(String, String)
     * @return - A map of output fields to their values.
     */
    public Map<String, String> getOutputFields()
    {
        return outputFields;
    }

    /**
     * Set an output field. These are the fields resulting from the processing of an application.
     * 
     * @see #getOutputFields()
     * @param key - The key for the map entry
     * @param value - The value associated with the given key.
     */
    public void setOutputField(String key, String value)
    {
        outputFields.put(key, value);
    }

    /**
     * Get the list of lodging expenses.
     * 
     * @see #addLodgingExpense(LodgingExpense,Integer)
     * @return - A list of LodgingExpenses contained in this app.
     */
    public List<LodgingExpense> getLodgingExpenseList()
    {
        return lodgingExpenseList;
    }

    /**
     * Add to the list of lodging expenses.
     * 
     * @see #getLodgingExpenseList()
     * @param expense - The LodgingExpense object to add to the app's list.
     * @param dayNumber - The day of the trip to add the expense to
     * @throws InputValidationException - If this is the second loding expense to be added for a
     *             particular day
     */
    public void addLodgingExpense(LodgingExpense expense, Integer dayNumber)
            throws InputValidationException
    {
        lodgingExpenseList.add(expense);

        TripDay day = getTripDay(dayNumber);
        day.setLodgingExpense(expense);
    }

    /**
     * Get the list of other expenses.
     * 
     * @see #addOtherExpense(OtherExpense)
     * @see OtherExpense
     * @return - A list of OtherExpenses contained in this app.
     */
    public List<OtherExpense> getOtherExpenseList()
    {
        return otherExpenseList;
    }

    /**
     * Add to the list of other expenses.
     * 
     * @see #getOtherExpenseList()
     * @see OtherExpense
     * @param expense - The OtherExpense object to add to the app's list.
     */
    public void addOtherExpense(OtherExpense expense)
    {
        otherExpenseList.add(expense);
    }

    /**
     * Get the list of incidental expenses.
     * 
     * @see #addIncidentalExpense(IncidentalExpense,Integer)
     * @see IncidentalExpense
     * @return - A list of IncidentalExpenses contained in this app.
     */
    public List<IncidentalExpense> getIncidentalExpenseList()
    {
        return incidentalExpenseList;
    }

    /**
     * Add to the list of incidental expenses.
     * 
     * @see #getIncidentalExpenseList()
     * @see IncidentalExpense
     * @param expense - The LodgingExpense object to add to the app's list.
     * @param dayNumber - The day to add the incidental expense under.
     * @throws InputValidationException - If this is the second incidental expense to be added for
     *             the given day.
     */
    public void addIncidentalExpense(IncidentalExpense expense, Integer dayNumber)
            throws InputValidationException
    {
        incidentalExpenseList.add(expense);

        TripDay day = getTripDay(dayNumber);
        day.setIncidentalExpense(expense);
    }

    /**
     * Get the list of meal expenses.
     * 
     * @see #addMealExpense(MealExpense,Integer)
     * @see MealExpense
     * @return - A list of MealExpenses contained in this app.
     */
    public List<MealExpense> getMealExpenseList()
    {
        return mealExpenseList;
    }

    /**
     * Add to the list of meal expenses.
     * 
     * @see #getMealExpenseList()
     * @see MealExpense
     * @param expense - The MealExpense object to add to the app's list.
     * @param dayNumber - The day to add the expense under.
     * @throws InputValidationException - If this expense is 4th meal expense to be added under this
     *             day.
     */
    public void addMealExpense(MealExpense expense, Integer dayNumber)
            throws InputValidationException
    {
        mealExpenseList.add(expense);

        TripDay day = getTripDay(dayNumber);
        day.addMealExpenses(expense);
    }

    /**
     * Get the list of transportation expenses.
     * 
     * @see #addTransportationExpense(TransportationExpense)
     * @see TransportationExpense
     * @return - A list of TransportationExpenses contained in this app.
     */
    public List<TransportationExpense> getTransportationExpenseList()
    {
        return transportationExpenseList;
    }

    /**
     * Add to the list of transportation expenses.
     * 
     * @see #getTransportationExpenseList()
     * @see TransportationExpense
     * @param expense - The TransportationExpense object to add to the app's list.
     */
    public void addTransportationExpense(TransportationExpense expense)
    {
        transportationExpenseList.add(expense);
    }

    /**
     * Get the list of grants.
     * 
     * @see #addGrant(Grant)
     * @see Grant
     * @return - A list of Grants contained in this app.
     */
    public List<Grant> getGrantList()
    {
        return grantList;
    }

    /**
     * Add to the list of grants.
     * 
     * @see #getGrantList()
     * @see Grant
     * @param grant - The grant object to add to the app's list.
     */
    public void addGrant(Grant grant)
    {
        grantList.add(grant);
    }

    /**
     * Get the time the form was submitted. This is extracted from the output map so if it hasn't
     * been set yet it will return null.
     * 
     * @return - The string representation of the datetime when the form was submitted. If not set
     *         this will return null.
     */
    public String getSubmissionTime()
    {
        return outputFields.get(OutputFieldKeys.FORM_SUBMISSION_DATETIME);
    }

    /**
     * Create a string representation of the ReimbursementApp
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--------------- Reimbursement Application ---------------\n");
        sb.append(String.format("Departure Datetime: %s\n", departureDatetime));
        sb.append(String.format("Arrival Datetime: %s\n", arrivalDatetime));
        sb.append(String.format("CSE sponsored: %s\n", travelTypeCSESponsored));
        sb.append(String.format("DTC sponsored: %s\n", travelTypeDTCSponsored));
        sb.append(String.format("NonSponsored: %s\n", travelTypeNonSponsored));
        sb.append(String.format("Reimbursement Total: $%f\n", reimbursementTotal));

        sb.append("\n\n-- Conference Info --\n");
        sb.append(conferenceInfo);

        sb.append("\n\n-- User Info --\n");
        sb.append(userInfo);

        sb.append("\n\n-- Grant Info --\n");
        appendListItems(sb, grantList);

        sb.append(String.format("\nNumber of Days: %s\n", numDays));
        sb.append("\n-- Expenses --\n");
        appendListItems(sb, mealExpenseList);
        appendListItems(sb, transportationExpenseList);
        appendListItems(sb, lodgingExpenseList);
        appendListItems(sb, otherExpenseList);
        appendListItems(sb, incidentalExpenseList);

        return sb.toString();
    }

    /**
     * Helps in adding list items to the string builder.
     * 
     * @param sb - string builder
     * @param items - The items to convert to string and append onto the string builder
     */
    private void appendListItems(StringBuilder sb, List<? extends Object> items)
    {
        for (Object item : items)
        {
            sb.append(item);
        }
    }
}
