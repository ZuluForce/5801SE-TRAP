/**
 * Copyright (c) 2012, Ian De Silva All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer. - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution. - Neither the name of the University of Minnesota nor
 * the names of its contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package edu.umn.se.trap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.umn.se.trap.db.CurrencyDB;
import edu.umn.se.trap.db.GrantDB;
import edu.umn.se.trap.db.PerDiemDB;
import edu.umn.se.trap.db.UserDB;
import edu.umn.se.trap.db.UserGrantDB;

/**
 * Provides some basic tests for TRAP using the information given in the coding assignment.
 * 
 * Feel free to update/change this as you see fit.
 * 
 * This was built using JUnit 4.10 and Hamcrest 1.1.0.
 * 
 * @author desilva
 */
public class TravelFormProcessorTestValidData
{
    // --------------------------------------------------------------------------
    // DATA MEMBERS
    // --------------------------------------------------------------------------
    /**
     * The object under test.
     */
    protected TravelFormProcessorIntf testProcessor;

    /**
     * The input form to test
     */
    private Map<String, String> inputFormData = new HashMap<String, String>();

    /**
     * The expected output
     */
    private Map<String, String> outputFormData = new HashMap<String, String>();

    // The DBs to use to initialize TravelFormProcessor.
    private final CurrencyDB currencyDB = new CurrencyDB();
    private final GrantDB grantDB = new GrantDB();
    private final PerDiemDB perDiemDB = new PerDiemDB();
    private final UserDB userDB = new UserDB();
    private final UserGrantDB userGrantDB = new UserGrantDB();

    /**
     * Contains the description we will use to store the form.
     */
    private String description;

    // --------------------------------------------------------------------------
    // CONSTANTS
    // --------------------------------------------------------------------------
    /**
     * A constant representing the key FORM_SUBMISSION_DATE in the form output.
     */
    private static final String FORM_SUBMISSION_DATE = "FORM_SUBMISSION_DATETIME";

    /**
     * 
     */
    private static final String DATE_TIME_FORMAT = "yyyyMMdd HHmmss";

    // --------------------------------------------------------------------------
    // STATIC INTIALIZERS
    // --------------------------------------------------------------------------
    /**
     * Generates a default, valid, input form.
     * 
     * @return a valid travel input form for use in testing
     */
    public static Map<String, String> getDefaultForm()
    {
        Map<String, String> formData = new HashMap<String, String>();
        formData.put("USER_NAME", "linc001");
        formData.put("ARRIVAL_DATETIME", "20121112 235900");
        formData.put("DEPARTURE_DATETIME", "20121108 100000");

        formData.put("TRAVEL_TYPE_CSE_SPONSORED", "yes");
        formData.put("EMERGENCY_CONTACT_NAME", "Greg Gay");
        formData.put("EMERGENCY_CONTACT_PHONE", "765-432-1098");
        formData.put("JUSTIFICATION_CONFERENCE_TITLE",
                "ASE2012: 26th IEEE/ACM International Conference on "
                        + "Automated Software Engineering");
        formData.put("JUSTIFICATION_PRESENTED", "no");
        formData.put("JUSTIFICATION_SPONSORED", "Learn about research in the field.");
        formData.put("NUM_GRANTS", "1");
        formData.put("GRANT1_ACCOUNT", "010101010101");
        formData.put("GRANT1_PERCENT", "100");
        formData.put("NUM_OTHER_EXPENSES", "2");
        formData.put("OTHER1_DATE", "20121003");
        formData.put("OTHER1_JUSTIFICATION", "Conference Registration");
        formData.put("OTHER1_AMOUNT", "450");
        formData.put("OTHER1_CURRENCY", "USD");
        formData.put("OTHER2_DATE", "20121003");
        formData.put("OTHER2_JUSTIFICATION", "Workshop Registration");
        formData.put("OTHER2_AMOUNT", "100");
        formData.put("OTHER2_CURRENCY", "USD");
        formData.put("NUM_DAYS", "5");
        formData.put("DAY1_LUNCH_CITY", "Des Moines");
        formData.put("DAY1_LUNCH_STATE", "IA");
        formData.put("DAY1_LUNCH_COUNTRY", "USA");
        formData.put("DAY1_DINNER_CITY", "Kansas City");
        formData.put("DAY1_DINNER_STATE", "MO");
        formData.put("DAY1_DINNER_COUNTRY", "USA");
        formData.put("DAY1_LODGING_CITY", "Lawrence");
        formData.put("DAY1_LODGING_STATE", "KS");
        formData.put("DAY1_LODGING_COUNTRY", "USA");
        formData.put("DAY1_LODGING_AMOUNT", "86.31");
        formData.put("DAY1_LODGING_CURRENCY", "USD");
        formData.put("DAY2_DINNER_CITY", "Lawrence");
        formData.put("DAY2_DINNER_STATE", "KS");
        formData.put("DAY2_DINNER_COUNTRY", "USA");
        formData.put("DAY2_LODGING_CITY", "Lawrence");
        formData.put("DAY2_LODGING_STATE", "KS");
        formData.put("DAY2_LODGING_COUNTRY", "USA");
        formData.put("DAY2_LODGING_AMOUNT", "86.31");
        formData.put("DAY2_LODGING_CURRENCY", "USD");
        formData.put("DAY3_LODGING_CITY", "Lawrence");
        formData.put("DAY3_LODGING_STATE", "KS");
        formData.put("DAY3_LODGING_COUNTRY", "USA");
        formData.put("DAY3_LODGING_AMOUNT", "86.31");
        formData.put("DAY3_LODGING_CURRENCY", "USD");
        formData.put("DAY4_DINNER_CITY", "Lawrence");
        formData.put("DAY4_DINNER_STATE", "KS");
        formData.put("DAY4_DINNER_COUNTRY", "USA");
        formData.put("DAY4_LODGING_CITY", "Lawrence");
        formData.put("DAY4_LODGING_STATE", "KS");
        formData.put("DAY4_LODGING_COUNTRY", "USA");
        formData.put("DAY4_LODGING_AMOUNT", "86.31");
        formData.put("DAY4_LODGING_CURRENCY", "USD");
        formData.put("DAY5_DINNER_CITY", "Des Moines");
        formData.put("DAY5_DINNER_STATE", "IA");
        formData.put("DAY5_DINNER_COUNTRY", "USA");
        formData.put("NUM_TRANSPORTATION", "6");
        formData.put("TRANSPORTATION1_DATE", "20121108");
        formData.put("TRANSPORTATION1_TYPE", "PARKING");
        formData.put("TRANSPORTATION1_AMOUNT", "12.00");
        formData.put("TRANSPORTATION1_CURRENCY", "USD");
        formData.put("TRANSPORTATION2_DATE", "20121109");
        formData.put("TRANSPORTATION2_TYPE", "PARKING");
        formData.put("TRANSPORTATION2_AMOUNT", "13.00");
        formData.put("TRANSPORTATION2_CURRENCY", "USD");
        formData.put("TRANSPORTATION3_DATE", "20121110");
        formData.put("TRANSPORTATION3_TYPE", "PARKING");
        formData.put("TRANSPORTATION3_AMOUNT", "12.00");
        formData.put("TRANSPORTATION3_CURRENCY", "USD");
        formData.put("TRANSPORTATION4_DATE", "20121110");
        formData.put("TRANSPORTATION4_TYPE", "PARKING");
        formData.put("TRANSPORTATION4_AMOUNT", "22");
        formData.put("TRANSPORTATION4_CURRENCY", "USD");
        formData.put("TRANSPORTATION5_DATE", "20121110");
        formData.put("TRANSPORTATION5_TYPE", "TOLL");
        formData.put("TRANSPORTATION5_AMOUNT", "1.65");
        formData.put("TRANSPORTATION5_CURRENCY", "USD");
        formData.put("TRANSPORTATION6_DATE", "20121110");
        formData.put("TRANSPORTATION6_TYPE", "TOLL");
        formData.put("TRANSPORTATION6_AMOUNT", "1.60");
        formData.put("TRANSPORTATION6_CURRENCY", "USD");

        return formData;
    }

    /**
     * Generates a default, valid, output form without the FORM_SUBMISSION_DATETIME field.
     * 
     * @return a valid travel output form for use in testing
     */
    public static Map<String, String> getOutputForm()
    {
        Map<String, String> formData = new HashMap<String, String>();

        formData.put("NAME", "Lincoln, Abraham");
        formData.put("USER_NAME", "linc001");
        formData.put("EMAIL", "linc001@umn.edu");
        formData.put("CITIZENSHIP", "United States");

        // We omit this because we have to check it separately.
        // formData.put("FORM_SUBMISSION_DATETIME", "This one depends on the test");
        formData.put("DEPARTURE_DATETIME", "20121108 100000");
        formData.put("ARRIVAL_DATETIME", "20121112 235900");
        formData.put("PAID_BY_UNIVERSITY", "yes");
        formData.put("EMERGENCY_CONTACT_NAME", "Greg Gay");
        formData.put("EMERGENCY_CONTACT_PHONE", "765-432-1098");
        formData.put("TRAVEL_TYPE_CSE_SPONSORED", "yes");
        formData.put("JUSTIFICATION_CONFERENCE_TITLE",
                "ASE2012: 26th IEEE/ACM International Conference on "
                        + "Automated Software Engineering");
        formData.put("JUSTIFICATION_PRESENTED", "no");
        formData.put("JUSTIFICATION_SPONSORED", "Learn about research in the field.");
        formData.put("NUM_DESTINATIONS", "3");
        formData.put("DESTINATION1_CITY", "Des Moines");
        formData.put("DESTINATION1_STATE", "IA");
        formData.put("DESTINATION1_COUNTRY", "USA");
        formData.put("DESTINATION2_CITY", "Kansas City");
        formData.put("DESTINATION2_STATE", "MO");
        formData.put("DESTINATION2_COUNTRY", "USA");
        formData.put("DESTINATION3_CITY", "Lawrence");
        formData.put("DESTINATION3_STATE", "KS");
        formData.put("DESTINATION3_COUNTRY", "USA");
        formData.put("NUM_DAYS", "5");
        formData.put("DAY1_DATE", "20121108");
        formData.put("DAY1_TOTAL", "111.81");
        formData.put("DAY2_DATE", "20121109");
        formData.put("DAY2_TOTAL", "109.31");
        formData.put("DAY3_DATE", "20121110");
        formData.put("DAY3_TOTAL", "86.31");
        formData.put("DAY4_DATE", "20121111");
        formData.put("DAY4_TOTAL", "109.31");
        formData.put("DAY5_DATE", "20121112");
        formData.put("DAY5_TOTAL", "17.25");
        formData.put("NUM_TRANSPORTATION", "6");
        formData.put("TRANSPORTATION1_DATE", "20121108");
        formData.put("TRANSPORTATION1_TYPE", "PARKING");
        formData.put("TRANSPORTATION1_TOTAL", "12.00");
        formData.put("TRANSPORTATION2_DATE", "20121109");
        formData.put("TRANSPORTATION2_TYPE", "PARKING");
        formData.put("TRANSPORTATION2_TOTAL", "13.00");
        formData.put("TRANSPORTATION3_DATE", "20121110");
        formData.put("TRANSPORTATION3_TYPE", "PARKING");
        formData.put("TRANSPORTATION3_TOTAL", "12.00");
        formData.put("TRANSPORTATION4_DATE", "20121110");
        formData.put("TRANSPORTATION4_TYPE", "PARKING");
        formData.put("TRANSPORTATION4_TOTAL", "22.00");
        formData.put("TRANSPORTATION5_DATE", "20121110");
        formData.put("TRANSPORTATION5_TYPE", "TOLL");
        formData.put("TRANSPORTATION5_TOTAL", "1.65");
        formData.put("TRANSPORTATION6_DATE", "20121110");
        formData.put("TRANSPORTATION6_TYPE", "TOLL");
        formData.put("TRANSPORTATION6_TOTAL", "1.60");
        formData.put("NUM_OTHER_EXPENSES", "2");
        formData.put("OTHER1_DATE", "20121003");
        formData.put("OTHER1_JUSTIFICATION", "Conference Registration");
        formData.put("OTHER1_TOTAL", "450");
        formData.put("OTHER2_DATE", "20121003");
        formData.put("OTHER2_JUSTIFICATION", "Workshop Registration");
        formData.put("OTHER2_TOTAL", "100");
        formData.put("NUM_GRANTS", "1");
        formData.put("GRANT1_ACCOUNT", "010101010101");
        formData.put("GRANT1_PERCENT", "100");
        formData.put("GRANT1_AMOUNT_TO_CHARGE", "1046.24");
        formData.put("GRANT1_APPROVER_NAME", "heimd001");
        formData.put("TOTAL_REIMBURSEMENT", "1046.24");

        return formData;
    }

    // --------------------------------------------------------------------------
    // SET-UP AND TEAR-DOWN FOR ALL TESTS IN THIS SUITE (FILE).
    // --------------------------------------------------------------------------
    /**
     * Sets up the data needed for the tests in this suite.
     * 
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {

        testProcessor = new TravelFormProcessor(userDB, perDiemDB, grantDB, userGrantDB, currencyDB);
        testProcessor.setUser("linc001");
        testProcessor.clearSavedForms();

        inputFormData = getDefaultForm();
        outputFormData = getOutputForm();
        description = "my form description";
    }

    /**
     * Tears down after the tests have completed.
     * 
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
        testProcessor = null;

        inputFormData = null;
        outputFormData = null;
        description = null;
    }

    /**
     * Test method for {@link TravelFormProcessorIntf#getUser()} and
     * {@link TravelFormProcessorIntf#setUser(String)}.
     * 
     * This checks to make sure the set user correctly set the user worked.
     */
    @Test
    public void testGetUser()
    {
        String currentUser = testProcessor.getUser();

        if (currentUser == null || !"linc001".equals(currentUser))
            fail("Bad user name.  Expected linc001, but got " + currentUser);
    }

    /**
     * Test method for {@link TravelFormProcessorIntf#saveFormData(Map, String)},
     * {@link TravelFormProcessorIntf#getSavedForms()}, and
     * {@link TravelFormProcessorIntf#submitFormData(Integer)}.
     * <p />
     * This will save a new form, ensure that it exists in the list and then submit the form
     * checking that the output is what we expect.
     * 
     * @throws Exception
     */
    @Test
    public void testSubmitFormData_new() throws Exception
    {
        // We need this later.
        Calendar then = Calendar.getInstance();

        // Save the form data, expecting no exception.
        testProcessor.saveFormData(inputFormData, description);

        Map<Integer, TravelFormMetadata> savedForms = testProcessor.getSavedForms();

        Set<Integer> keys = savedForms.keySet();
        Integer formId = null;

        // Find the form id we need.
        for (Integer key : keys)
        {
            TravelFormMetadata metadata = savedForms.get(key);
            if (metadata != null && description.equals(metadata.description))
            {
                // The form id was found, store it and break.
                formId = key;
                break;
            }
        }

        // If the form id was not found.
        if (formId == null)
        {
            fail("The saved form was not found with description '" + description + "'.");
        }
        // Otherwise, we have a valid form id.

        // Now, we need to submit the form -- The test will fail if an exception
        // is thrown by submit.
        testProcessor.submitFormData(formId);

        // If we get here, the submission should have succeeded. Get the
        // completed form.
        Map<String, String> actualOut = testProcessor.getCompletedForm(formId);

        checkOutput(actualOut, then);
    }

    /**
     * Check the output to see if it matches what we expect.
     * 
     * @param actualOut the form that was emitted by TRAP
     * @param earliestFormSubmissionTime the earliest possible submission time to use in checking
     *            our times.
     * @throws ParseException
     */
    private void checkOutput(Map<String, String> actualOut, Calendar earliestFormSubmissionTime)
            throws ParseException
    {
        assertNotNull(actualOut);

        // Check that they have the same size (assuming we are missing 1 field in
        // our expected output, the form submission date)
        assertEquals(outputFormData.size() + 1, actualOut.size());

        // Now, check each key.
        for (String key : outputFormData.keySet())
        {
            String expectedValue = outputFormData.get(key);
            String actualValue = actualOut.get(key);

            assertNotNull(actualValue);

            assertEquals(expectedValue, actualValue);
        }

        // Check the form submission date
        String formSubmissionDate = actualOut.get(FORM_SUBMISSION_DATE);

        assertNotNull(formSubmissionDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        Date submissionDate = dateFormat.parse(formSubmissionDate);

        Calendar submissionDateCal = Calendar.getInstance();
        submissionDateCal.setTime(submissionDate);

        // Fail if submissionDate occurs before the earliest possible submission
        // time
        if (earliestFormSubmissionTime != null
                && submissionDateCal.before(earliestFormSubmissionTime))
        {
            fail("The form submission time is incorrect.  It is shown "
                    + "as submitted before we started the submission.");
        }

        Calendar now = Calendar.getInstance();

        // Fail if the submission date occurs after the current time.
        if (submissionDateCal.after(now))
        {
            fail("The form submission time is incorrect.  It is shown "
                    + "as submitted after the current time.");
        }

        // We passed the output check.
    }

}
