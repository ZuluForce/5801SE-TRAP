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

/**
 * @author planeman
 * 
 */
public class ReimbursementApp
{
    private Date departureDatetime;
    private Date arrivalDatetime;
    private int numDays;

    private String travelTypeCSESponsored;
    private String travelTypeDTCSponsored;
    private String travelTypeNonSponsored;

    private final Map<String, String> outputFields;

    private final List<LodgingExpense> lodgingExpenseList;
    private final List<OtherExpense> otherExpenseList;
    private final List<IncidentalExpense> incidentalExpenseList;
    private final List<MealExpense> mealExpenseList;
    private final List<TransportationExpense> trasportationExpenseList;

    private ConferenceInfo conferenceInfo;
    private UserInfo userInfo;

    private List<Grant> grantList;

    private float reimbursementTotal;

    public ReimbursementApp()
    {
        outputFields = new HashMap<String, String>();
        lodgingExpenseList = new ArrayList<LodgingExpense>();
        otherExpenseList = new ArrayList<OtherExpense>();
        incidentalExpenseList = new ArrayList<IncidentalExpense>();
        mealExpenseList = new ArrayList<MealExpense>();
        trasportationExpenseList = new ArrayList<TransportationExpense>();
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

    public int getNumDays()
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

    public float getReimbursementTotal()
    {
        return reimbursementTotal;
    }

    public void addReimbursementTotal(float amount)
    {
        reimbursementTotal = reimbursementTotal;
    }

    public Map<String, String> getOutputFields()
    {
        return outputFields;
    }

    public void setOutputField(String key, String value)
    {
        outputFields.put(key, value);
    }

    public List<LodgingExpense> getLodgingExpenseList()
    {
        return lodgingExpenseList;
    }

    public void addLodgingExpense(LodgingExpense expense)
    {
        lodgingExpenseList.add(expense);
    }

    public List<OtherExpense> getOtherExpenseList()
    {
        return otherExpenseList;
    }

    public void addOtherExpense(OtherExpense expense)
    {
        otherExpenseList.add(expense);
    }

    public List<IncidentalExpense> getIncidentalExpenseList()
    {
        return incidentalExpenseList;
    }

    public void addIncidentalExpense(IncidentalExpense expense)
    {
        incidentalExpenseList.add(expense);
    }

    public List<MealExpense> getMealExpenseList()
    {
        return mealExpenseList;
    }

    public void addMealExpense(MealExpense expense)
    {
        mealExpenseList.add(expense);
    }

    public List<TransportationExpense> getTrasportationExpenseList()
    {
        return trasportationExpenseList;
    }

    public void addTransportationExpense(TransportationExpense expense)
    {
        trasportationExpenseList.add(expense);
    }

    public List<Grant> getGrantList()
    {
        return grantList;
    }

    public void addGrant(Grant grant)
    {
        grantList.add(grant);
    }
}
