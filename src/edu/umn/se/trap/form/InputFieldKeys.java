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

    // Grant Fields
    public final static String NUM_GRANTS = "NUM_GRANTS";
    public final static String GRANT_ACCOUNT_FMT = "GRANT%d_ACCOUNT";
    public final static String GRANT_PERCENT = "GRANT%d_PERCENT";

    // Expense Fields
    public final static String NUM_DAYS = "NUM_DAYS";
}
