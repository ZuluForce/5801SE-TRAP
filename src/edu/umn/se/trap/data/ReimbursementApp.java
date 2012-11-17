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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.umn.se.trap.exception.TRAPRuntimeException;

/**
 * A large data object that holds the hierarchy of objects and attributes that represent an the
 * equivalent of an input form's data. The purpose for this data object is to make it simpler for
 * rules and later processing to access form data without being dependent on the format of the input
 * data.
 * 
 * @author planeman
 * 
 */
public class ReimbursementApp
{
    private Date departureDatetime;
    private Date arrivalDatetime;
    private Integer numDays;

    private String travelTypeCSESponsored;
    private String travelTypeDTCSponsored;
    private String travelTypeNonSponsored;

    private final Map<String, String> outputFields;

    private final List<LodgingExpense> lodgingExpenseList;
    private final List<OtherExpense> otherExpenseList;
    private final List<IncidentalExpense> incidentalExpenseList;
    private final List<MealExpense> mealExpenseList;
    private final List<TransportationExpense> transportationExpenseList;

    private ConferenceInfo conferenceInfo;
    private UserInfo userInfo;

    private final List<Grant> grantList;

    private Double reimbursementTotal;
    private final List<Double> perDayTotals;

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

        perDayTotals = new ArrayList<Double>();
    }

    public String getTravelTypeCSESponsored()
    {
        return travelTypeCSESponsored;
    }

    public void setTravelTypeCSESponsored(String travelTypeCSESponsored)
    {
        this.travelTypeCSESponsored = travelTypeCSESponsored;
    }

    public String getTravelTypeDTCSponsored()
    {
        return travelTypeDTCSponsored;
    }

    public void setTravelTypeDTCSponsored(String travelTypeDTCSponsored)
    {
        this.travelTypeDTCSponsored = travelTypeDTCSponsored;
    }

    public String getTravelTypeNonSponsored()
    {
        return travelTypeNonSponsored;
    }

    public void setTravelTypeNonSponsored(String travelTypeNonSponsored)
    {
        this.travelTypeNonSponsored = travelTypeNonSponsored;
    }

    public Date getDepartureDatetime()
    {
        return departureDatetime;
    }

    public void setDepartureDatetime(Date departureDatetime)
    {
        this.departureDatetime = departureDatetime;
    }

    public Date getArrivalDatetime()
    {
        return arrivalDatetime;
    }

    public void setArrivalDatetime(Date arrivalDatetime)
    {
        this.arrivalDatetime = arrivalDatetime;
    }

    public Integer getNumDays()
    {
        return numDays;
    }

    public void setNumDays(int numDays)
    {
        this.numDays = numDays;
    }

    public ConferenceInfo getConferenceInfo()
    {
        return conferenceInfo;
    }

    public void setConferenceInfo(ConferenceInfo conferenceInfo)
    {
        this.conferenceInfo = conferenceInfo;
    }

    public UserInfo getUserInfo()
    {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo)
    {
        this.userInfo = userInfo;
    }

    /**
     * Add to the reimbursement total amount
     * 
     * @see #getReimbursementTotal()
     * @param amount - The amount to add to the total
     */
    public void addToReimbursementTotal(Double amount)
    {
        reimbursementTotal += amount;
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
     * Add an expense amount under a certain day. This amount added should be the final
     * reimbursement amount for the given expense. This method will also add the amount to the
     * reimbursement total.
     * 
     * OtherExpense amounts do not go under a day total but they do need to be added to the
     * reimbursement total.
     * 
     * @see #getDayTotal(Integer)
     * @param day - The day to add the amount to
     * @param amount - The amount to add under the given day and the reimbursement total
     */
    public void addToDayTotal(Integer day, Double amount)
    {
        // This just ensures that we have the correct size to fit all days
        while (day > perDayTotals.size())
        {
            perDayTotals.add(0.0);
        }

        Double newAmount = perDayTotals.get(day);
        newAmount += amount;

        perDayTotals.set(day, newAmount);
    }

    /**
     * Get the reimbursement amount for a given day.
     * 
     * @see #addToDayTotal(Integer, Double)
     * @param day - The day to retrieve the total for
     * @return - The reimbursement amount for the given day
     */
    public Double getDayTotal(Integer day)
    {
        if (day > perDayTotals.size())
        {
            throw new TRAPRuntimeException("There is no day " + day);
        }

        return perDayTotals.get(day);
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
     * @see #addLodgingExpense(LodgingExpense)
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
     */
    public void addLodgingExpense(LodgingExpense expense)
    {
        lodgingExpenseList.add(expense);
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
     * @see #addIncidentalExpense(IncidentalExpense)
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
     */
    public void addIncidentalExpense(IncidentalExpense expense)
    {
        incidentalExpenseList.add(expense);
    }

    /**
     * Get the list of meal expenses.
     * 
     * @see #addMealExpense(MealExpense)
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
     */
    public void addMealExpense(MealExpense expense)
    {
        mealExpenseList.add(expense);
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
