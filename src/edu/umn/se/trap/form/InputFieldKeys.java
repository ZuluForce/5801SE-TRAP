// InputFieldKeys.java
package edu.umn.se.trap.form;

/**
 * String constants used to access fields of input form data. Field names with an appended _FMT mean
 * they are format strings. For example, the grant accounts are numbered so their field will have to
 * be GRANT%d_ACCOUNT where %s is the placeholder for an integer later.
 * 
 * @author planeman
 * 
 */
public class InputFieldKeys
{
    // public final static String =
    public final static String ARRIVAL_DATETIME = "ARRIVAL_DATETIME";
    public final static String DEPARTURE_DATETIME = "DEPARTURE_DATETIME";

    // Funding types
    public final static String TRAVEL_TYPE_CSE_SPONSORED = "TRAVEL_TYPE_CSE_SPONSORED";
    public final static String TRAVEL_TYPE_DTC_SPONSORED = "TRAVEL_TYPE_DTC_SPONSORED";
    public final static String TRAVEL_TYPE_NONSPONSORED = "TRAVEL_TYPE_NONSPONSORED";

    // User Information Fields
    public final static String EMERGENCY_NAME = "EMERGENCY_CONTACT_NAME";
    public final static String EMERGENCY_PHONE = "EMERGENCY_CONTACT_PHONE";

    // User Information
    public final static String USER_NAME = "USER_NAME";

    // Conference Information Fields
    public final static String JUSTIFICATION_CONFERENCE_TITLE = "JUSTIFICATION_CONFERENCE_TITLE";
    public final static String JUSTIFICATION_PRESENTED = "JUSTIFICATION_PRESENTED";
    public final static String JUSTIFICATION_PRESENTATION_TITLE = "JUSTIFICATION_PRESENTATION_TITLE";
    public final static String JUSTIFICATION_PRESENTATION_ABSTRACT = "JUSTIFICATION_PRESENTATION_ABSTRACT";
    public final static String JUSTIFICATION_PRESENTATION_ACKNOWLEDGEMENT = "JUSTIFICATION_PRESENTATION_ACKNOWLEDGEMENT";
    public final static String JUSTIFICATION_NONSPONSORED = "JUSTIFICATION_NONSPONSORED";
    public final static String JUSTIFICATION_SPONSORED = "JUSTIFICATION_SPONSORED";

    // Meal Expenses

    // Breakfast
    public final static String BREAKFAST_CITY_FMT = "DAY%d_BREAKFAST_CITY";
    public final static String BREAKFAST_STATE_FMT = "DAY%d_BREAKFAST_STATE";
    public final static String BREAKFAST_COUNTRY_FMT = "DAY%d_BREAKFAST_COUNTRY";

    // Lunch
    public final static String LUNCH_CITY_FMT = "DAY%d_LUNCH_CITY";
    public final static String LUNCH_STATE_FMT = "DAY%d_LUNCH_STATE";
    public final static String LUNCH_COUNTRY_FMT = "DAY%d_LUNCH_COUNTRY";

    // Dinner
    public final static String DINNER_CITY_FMT = "DAY%d_DINNER_CITY";
    public final static String DINNER_STATE_FMT = "DAY%d_DINNER_STATE";
    public final static String DINNER_COUNTRY_FMT = "DAY%d_DINNER_COUNTRY";

    // Incidental
    public final static String INCIDENTAL_CITY_FMT = "DAY%d_INCIDENTAL_CITY";
    public final static String INCIDENTAL_STATE_FMT = "DAY%d_INCIDENTAL_STATE";
    public final static String INCIDENTAL_COUNTRY_FMT = "DAY%d_INCIDENTAL_COUNTRY";
    public final static String INCIDENTAL_AMOUNT_FMT = "DAY%d_INCIDENTAL_AMOUNT";
    public final static String INCIDENTAL_CURRENCY_FMT = "DAY%d_INCIDENTAL_CURRENCY";
    public final static String INCIDENTAL_JUSTIFICATION_FMT = "DAY%d_INCIDENTAL_JUSTIFICATION";

    // Lodging
    public final static String LODGING_CITY_FMT = "DAY%d_LODGING_CITY";
    public final static String LODGING_STATE_FMT = "DAY%d_LODGING_STATE";
    public final static String LODGING_COUNTRY_FMT = "DAY%d_LODGING_COUNTRY";
    public final static String LODGING_AMOUNT_FMT = "DAY%d_LODGING_AMOUNT";
    public final static String LODGING_CURRENCY_FMT = "DAY%d_LODGING_CURRENCY";

    // Transportation
    public final static String NUMBER_TRANSPORTATION_EXPENSES = "NUM_TRANSPORTATION";
    public final static String TRANSPORTATION_DATE_FMT = "TRANSPORTATION%d_DATE";
    public final static String TRANSPORTATION_TYPE_FMT = "TRANSPORTATION%d_TYPE";
    public final static String TRANSPORTATION_RENTAL_FMT = "TRANSPORTATION%d_RENTAL";
    public final static String TRANSPORTATION_CARRIER_FMT = "TRANSPORTATION%d_CARRIER";
    public final static String TRANSPORTATION_MILES_FMT = "TRANSPORTATION%d_MILES_TRAVELED";
    public final static String TRANSPORTATION_AMOUNT_FMT = "TRANSPORTATION%d_AMOUNT";
    public final static String TRANSPORTATION_CURRENCY_FMT = "TRANSPORTATION%d_CURRENCY";

    // Other
    public final static String NUMBER_OTHER_EXPENSES = "NUM_OTHER_EXPENSES";
    public final static String OTHER_DATE_FMT = "OTHER%d_DATE";
    public final static String OTHER_JUSTIFICATION_FMT = "OTHER%d_JUSTIFICATION";
    public final static String OTHER_AMOUNT_FMT = "OTHER%d_AMOUNT";
    public final static String OTHER_CURRENTCY_FMT = "OTHER%d_CURRENCY";

    // Grant Fields
    public final static String NUM_GRANTS = "NUM_GRANTS";
    public final static String GRANT_ACCOUNT_FMT = "GRANT%d_ACCOUNT";
    public final static String GRANT_PERCENT_FMT = "GRANT%d_PERCENT";

    // Expense Fields
    public final static String NUM_DAYS = "NUM_DAYS";
}
