// OutputFieldKeys.java
package edu.umn.se.trap.form;

/**
 * String constants representing the keys used in the TRAP output map.
 * 
 * @author planeman
 * 
 */
public class OutputFieldKeys
{
    public static final String FULL_NAME = "NAME";
    public static final String USER_NAME = "USER_NAME";
    public static final String EMAIL = "EMAIL";
    public static final String CITIZENSHIP = "CITIZENSHIP";
    public static final String VISA_STATUS = "VISA_STATUS";
    public static final String FORM_SUBMISSION_DATETIME = "VISA_STATUS";
    public static final String ARRIVAL_DATETIME = "ARRIVAL_DATETIME";
    public static final String DEPARTURE_DATETIME = "DEPARTURE_DATETIME";
    public static final String PAID_BY_UNIVERSITY = "PAID_BY_UNIVERSITY";
    public static final String EMERGENCY_NAME = "EMERGENCY_CONTACT_NAME";
    public static final String EMERGENCY_PHONE = "EMERGENCY_CONTACT_PHONE";

    // Funding types
    public final static String TRAVEL_TYPE_CSE_SPONSORED = "TRAVEL_TYPE_CSE_SPONSORED";
    public final static String TRAVEL_TYPE_DTC_SPONSORED = "TRAVEL_TYPE_DTC_SPONSORED";
    public final static String TRAVEL_TYPE_NONSPONSORED = "TRAVEL_TYPE_NONSPONSORED";

    // Conference Information Fields
    public final static String JUSTIFICATION_CONFERENCE_TITLE = "JUSTIFICATION_CONFERENCE_TITLE";
    public final static String JUSTIFICATION_PRESENTED = "JUSTIFICATION_PRESENTED";
    public final static String JUSTIFICATION_PRESENTATION_TITLE = "JUSTIFICATION_PRESENTATION_TITLE";
    public final static String JUSTIFICATION_PRESENTATION_ABSTRACT = "JUSTIFICATION_PRESENTATION_ABSTRACT";
    public final static String JUSTIFICATION_PRESENTATION_ACKNOWLEDGEMENT = "JUSTIFICATION_PRESENTATION_ACKNOWLEDGEMENT";
    public final static String JUSTIFICATION_NONSPONSORED = "JUSTIFICATION_NONSPONSORED";
    public final static String JUSTIFICATION_SPONSORED = "JUSTIFICATION_SPONSORED";

    public static final String NUM_DAYS = "NUM_DAYS";

    // Transportation expenses
    public final static String NUM_TRANSPORTATION_EXPENSES = "NUM_TRANSPORTATION";
    public final static String TRANSPORTATION_DATE_FMT = "TRANSPORTATION%d_DATE";
    public final static String TRANSPORTATION_TYPE_FMT = "TRANSPORTATION%d_TYPE";
    public final static String TRANSPORTATION_TOTAL_FMT = "TRANSPORTATION%d_TOTAL";

    // Other expenses
    public final static String NUM_OTHER_EXPENSES = "NUM_OTHER_EXPENSES";
    public final static String OTHER_DATE_FMT = "OTHER%d_DATE";
    public final static String OTHER_JUSTIFICATION_FMT = "OTHER%d_JUSTIFICATION";
    public final static String OTHER_TOTAL_FMT = "OTHER%d_TOTAL";

    public final static String NUM_GRANTS = "NUM_GRANTS";
    public final static String GRANT_ACCOUNT_FMT = "GRANT%d_ACCOUNT";
    public final static String GRANT_PERCENT_FMT = "GRANT%d_PERCENT";
}
