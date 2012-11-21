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
// OutputFieldKeys.java
package edu.umn.se.trap.form;

/**
 * String constants representing the keys used in the TRAP output map.
 * 
 * @author andrewh
 * 
 */
@SuppressWarnings("javadoc")
public class OutputFieldKeys
{
    // User information
    public static final String FULL_NAME = "NAME";
    public static final String USER_NAME = "USER_NAME";
    public static final String EMAIL = "EMAIL";
    public static final String CITIZENSHIP = "CITIZENSHIP";
    public static final String VISA_STATUS = "VISA_STATUS";
    public static final String PAID_BY_UNIVERSITY = "PAID_BY_UNIVERSITY";
    public static final String EMERGENCY_NAME = "EMERGENCY_CONTACT_NAME";
    public static final String EMERGENCY_PHONE = "EMERGENCY_CONTACT_PHONE";

    // TRAP processing fields
    public static final String FORM_SUBMISSION_DATETIME = "FORM_SUBMISSION_DATETIME";

    // Trip time information
    public static final String ARRIVAL_DATETIME = "ARRIVAL_DATETIME";
    public static final String DEPARTURE_DATETIME = "DEPARTURE_DATETIME";

    // Destinations
    public static final String NUM_DESTINATIONS = "NUM_DESTINATIONS";
    public static final String DESTINATION_CITY_FMT = "DESTINATION%d_CITY";
    public static final String DESTINATION_STATE_FMT = "DESTINATION%d_STATE";
    public static final String DESTINATION_COUNTRY_FMT = "DESTINATION%d_COUNTRY";

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

    // Per Day Fields
    public static final String NUM_DAYS = "NUM_DAYS";
    public static final String DAY_DATE_FMT = "DAY%d_DATE";
    public static final String DAY_TOTAL_FMT = "DAY%d_TOTAL";

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

    // Grants
    public final static String NUM_GRANTS = "NUM_GRANTS";
    public final static String GRANT_ACCOUNT_FMT = "GRANT%d_ACCOUNT";
    public final static String GRANT_PERCENT_FMT = "GRANT%d_PERCENT";
    public final static String GRANT_CHARGE_FMT = "GRANT%d_AMOUNT_TO_CHARGE";
    public final static String GRANT_APPROVER_FMT = "GRANT%d_APPROVER_NAME";

    // Total reimbursement
    public final static String TOTAL_REIMBURSEMENT = "TOTAL_REIMBURSEMENT";
}
