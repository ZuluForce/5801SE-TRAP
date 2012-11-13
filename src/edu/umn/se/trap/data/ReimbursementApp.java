/**
 * 
 */
package edu.umn.se.trap.data;

import java.util.Date;
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

    private Map<String, String> outputFields;

    private List<LodgingExpense> lodgingExpenseList;
    private List<OtherExpense> otherExpenseList;
    private List<IncidentalExpense> incidentalExpenseList;
    private List<MealExpense> mealExpenseList;
    private List<TransportationExpense> trasportationExpenseList;

    private ConferenceInfo conferenceInfo;
    private UserInfo userInfo;

    private List<Grant> grantList;

    private float reimbursementTotal;

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

    public void addOutputField(String key, String value)
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
