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
// TrapTestFramework.java
package edu.umn.se.test.frame;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.TravelFormMetadata;
import edu.umn.se.trap.TravelFormProcessor;
import edu.umn.se.trap.exception.InputValidationException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.OutputFieldKeys;
import edu.umn.se.trap.rules.input.DateValidator;
import edu.umn.se.trap.test.generate.LoadedSampleForm;
import edu.umn.se.trap.test.generate.TestDataGenerator;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;
import edu.umn.se.trap.util.Pair;

/**
 * This framework primarily acts as a driver to TRAP (TravelFormProcessor). It provides many methods
 * for boilerplate functionality that most tests need such as setting a user, loading a form and
 * saving it.
 * 
 * @author andrewh
 * 
 */
public class TrapTestFramework
{
    /** Logger for the TrapTestFramework */
    private static final Logger log = LoggerFactory.getLogger(TrapTestFramework.class);

    // Our versions of the databases with added information and methods to manipulate internal data
    public final TestCurrencyDB currencyDB = new TestCurrencyDB();
    public final TestGrantDB grantDB = new TestGrantDB();
    public final TestPerDiemDB perDiemDB = new TestPerDiemDB();
    public final TestUserDB userDB = new TestUserDB();
    public final TestUserGrantDB userGrantDB = new TestUserGrantDB();

    /** TRAP!! */
    private final TravelFormProcessor trapProcessor;

    // When using the #setup(SampleDataEnum) method of the framework, the resulting loaded form will
    // be placed here along with its save id assigned by TRAP.
    protected LoadedSampleForm testFormData;
    protected Integer testFormId;

    /**
     * Constructor for the test framework. It instantiates the TravelFormProcessor with our
     * databases and initializes the TestDataGenerator class which loads the paths for known sample
     * forms.
     */
    public TrapTestFramework()
    {
        trapProcessor = new TravelFormProcessor(userDB, perDiemDB, grantDB, userGrantDB, currencyDB);

        TestDataGenerator.initialize();
    }

    /**
     * This is a convenience method to remove a lot of the boilerplate code that a lot of tests will
     * need. Here is what it does:<br/>
     * - Loads the sample form of the given type (source) <br />
     * - Sets the current user in TRAP to the user on the sample form<br/>
     * - Saves the form<br/>
     * - Returns the form data and the id of the saved form <br/>
     * 
     * @param formType - The source for the sample form to load. This will correspond to a path
     *            defined in the {@link TestDataGenerator}.
     * @throws TRAPException When there is an error saving the form
     */
    public void setup(SampleDataEnum formType) throws TRAPException
    {
        Pair<Integer, LoadedSampleForm> formInfo = basicTrapSetup(formType);
        testFormData = formInfo.getRight();
        testFormId = formInfo.getLeft();
    }

    /**
     * Re-save the sample loaded form. The framework know the id of the form and has a reference to
     * its data map and it will use this to make a driver call to TRAP to save over the existing
     * form data.
     * 
     * @throws TRAPException When there is an error saving the form
     */
    public void resaveTestForm() throws TRAPException
    {
        this.saveFormData(testFormData, testFormId);
    }

    /**
     * Re-saves the sample loaded form and then submits it for processing.
     * 
     * @throws TRAPException - When there is an error saving or during processing.
     */
    public void saveAndSubmitTestForm() throws TRAPException
    {
        resaveTestForm();
        submitFormData(testFormId);
    }

    /**
     * A driver call to TRAP to clear all saved forms.
     * 
     * @throws TRAPException When there is an error clearing the forms
     */
    public void clearSavedForms() throws TRAPException
    {
        trapProcessor.clearSavedForms();
    }

    /**
     * A driver call to TRAP to get the data for the completed form with the given id.
     * 
     * @param id - id of the completed form to fetch
     * @return - The data for the completed form with formId = id
     * @throws TRAPException - When the completed from cannot be retrieved.
     */
    public Map<String, String> getCompletedForm(Integer id) throws TRAPException
    {
        return trapProcessor.getCompletedForm(id);
    }

    /**
     * A driver call to TRAP to get the data for the saved form with the given id.
     * 
     * @param id - id of the saved form to fetch
     * @return - the data for the saved form with formId = id
     * @throws TRAPException - When the saved from cannot be retrieved.
     */
    public Map<String, String> getSavedFormData(Integer id) throws TRAPException
    {
        return trapProcessor.getSavedFormData(id);
    }

    /**
     * A driver call to retrieve information on saved forms
     * 
     * @return - A map of saved form ids to their metadata
     * @throws TRAPException - When the saved form metadata cannot be retrieved.
     */
    public Map<Integer, TravelFormMetadata> getSavedForms() throws TRAPException
    {
        return trapProcessor.getSavedForms();
    }

    /**
     * A driver call to retrieve the current TRAP user
     * 
     * @return - The current user in TRAP
     */
    public String getUser()
    {
        return trapProcessor.getUser();
    }

    /**
     * A driver call to save form data with a description which will result in a new id.
     * 
     * @param formData - form data to save
     * @param desc - The description to assign to the data
     * @return - The id for the newly saved form
     * @throws TRAPException - When there is an error saving the data
     */
    public Integer saveFormData(Map<String, String> formData, String desc) throws TRAPException
    {
        return trapProcessor.saveFormData(formData, desc);
    }

    /**
     * A driver call to save form data over the existing form saved under the given form id.
     * 
     * @param formData - the form data to save
     * @param id - the id of the form to save this data over
     * @return - The id of the newly saved form
     * @throws TRAPException - When there is an error saving the data
     */
    public Integer saveFormData(Map<String, String> formData, Integer id) throws TRAPException
    {
        return trapProcessor.saveFormData(formData, id);
    }

    /**
     * Set the current user in TRAP.
     * 
     * @param user - The user to set in TRAP.
     * @throws TRAPException - When there is an error setting the user. Usually because the username
     *             is invalid/unknown.
     */
    public void setUser(String user) throws TRAPException
    {
        trapProcessor.setUser(user);
    }

    /**
     * Submit the form saved under the provided id for processing.
     * 
     * @param id - The id of the saved form to submit for processing.
     * @throws TRAPException - When there is an error during processing.
     */
    public void submitFormData(Integer id) throws TRAPException
    {
        trapProcessor.submitFormData(id);
    }

    // ============ Helper methods for tests ============

    /**
     * This method is used by the framework's {@link #setup(SampleDataEnum)} to do most of its work.
     * Given a sample form type it loads it, saves it, sets the current user to the one in the
     * sample form and returns the (formData, formId) for the newly saved form.
     * 
     * @param formType - The source for the sample form to load. Look to {@link TestDataGenerator}
     *            for how this enum maps to files.
     * @return - The form data and form id for the newly loaded/saved form.
     * @throws TRAPException - when there is an error setting the user or saving the form.
     */
    public Pair<Integer, LoadedSampleForm> basicTrapSetup(SampleDataEnum formType)
            throws TRAPException
    {
        setValidUser(formType);
        LoadedSampleForm formData = getLoadableForm(formType);
        Integer formId = this.saveFormData(formData, "A test form of type " + formType.toString());

        return new Pair<Integer, LoadedSampleForm>(formId, formData);
    }

    /**
     * Get the appropriate user for the given sample form. When submitting the form, the name on the
     * form is checked against the current user in TRAP so it is necessary that this is set
     * correctly.
     * 
     * @param whichForm - The sample form type/source to get the username for.
     * @return - The username for the given sample form.
     */
    public String getValidTestUser(SampleDataEnum whichForm)
    {
        return TestDataGenerator.getUserForForm(whichForm);
    }

    /**
     * A boilerplate method for setting a valid user in the system. This method assumes you are
     * using the domestic1 sample form.
     * 
     * @throws TRAPException - When there is an error setting the user.
     */
    public void setValidUser() throws TRAPException
    {
        setValidUser(SampleDataEnum.DOMESTIC1);
    }

    /**
     * Set the valid user in the system for the given sample form source.
     * 
     * @param formType - The type of the sample form.
     * @throws TRAPException - When there is an error setting the user in TRAP.
     */
    public void setValidUser(SampleDataEnum formType) throws TRAPException
    {
        trapProcessor.setUser(getValidTestUser(formType));
    }

    /**
     * Load a sample form from the filesystem.
     * 
     * @param source - The type/source for the sample form to load.
     * @return - The LoadedSampleForm object which is a complete form that can be used within TRAP.
     */
    public LoadedSampleForm getLoadableForm(SampleDataEnum source)
    {
        return TestDataGenerator.getSampleForm(source);
    }

    /**
     * Get the expected output for a given sample form. On the filesystem these are the same
     * filename but with a .output appendix.
     * 
     * @param source - The source for the file whose expected output to load
     * @return - A LoadedSampleForm (ie map of strings) representing the expected output of the
     *         source sample form.
     */
    public LoadedSampleForm getExpectedOutput(SampleDataEnum source)
    {
        return TestDataGenerator.getExpectedOutput(source);
    }

    /**
     * Get the expected output of the given LoadedSampleForm. The filename for the sample form is
     * stored in this object so the output can be retrieved assuming it follows the standard .output
     * naming convention.
     * 
     * @param form - The input sample form whose expected output is needed.
     * @return - The expected output corresponding to the given form.
     */
    public LoadedSampleForm getExpectedOutput(LoadedSampleForm form)
    {
        return TestDataGenerator.getExpectedOutput(form);
    }

    /**
     * Check if the output of two maps are the same. This is explicitly checking maps that are the
     * result of TRAP processing.
     * 
     * @param output - The actual output of processing a form
     * @param expected - The expected output of the form processing
     * @return - True if they both match using the semantics of TRAP output or false if they don't
     * @throws InputValidationException -
     */
    public boolean doOutputsMatch(Map<String, String> output, Map<String, String> expected)
            throws InputValidationException
    {
        // First check the values that aren't necessarily the same across both outputs but don't
        // invalidate them from being equivalent results

        // Submission datetime
        String submitTimeStr = output.get(OutputFieldKeys.FORM_SUBMISSION_DATETIME);
        Date submitDatetime = DateValidator.convertToDatetime(submitTimeStr);

        Date now = new Date();
        if (submitDatetime.after(now))
        {
            log.debug("submission datetime after output check time.");
            return false;
        }

        output.remove(OutputFieldKeys.FORM_SUBMISSION_DATETIME);

        // Destinations. They can come in any order so we need to check that the *set* of
        // destinations is the same
        Set<Map<String, String>> outDests, expectedDests;
        outDests = new HashSet<Map<String, String>>();
        expectedDests = new HashSet<Map<String, String>>();

        int numDests = Integer.parseInt(output.get(OutputFieldKeys.NUM_DESTINATIONS));
        int numExpectedDests = Integer.parseInt(expected.get(OutputFieldKeys.NUM_DESTINATIONS));

        if (numDests != numExpectedDests)
        {
            log.error("Number of destinations in the output({}) does not match expected ({})",
                    numDests, numExpectedDests);
        }

        for (int i = 1; i <= numDests; ++i)
        {
            Map<String, String> newDest = new HashMap<String, String>();
            Map<String, String> newDestExpected = new HashMap<String, String>();

            // city
            String field = String.format(OutputFieldKeys.DESTINATION_CITY_FMT, i);
            String value = output.get(field);
            output.remove(field);
            newDest.put("city", value);

            // city - expected
            value = expected.get(field);
            expected.remove(field);
            newDestExpected.put("city", value);

            // state
            field = String.format(OutputFieldKeys.DESTINATION_STATE_FMT, i);
            value = output.get(field);
            output.remove(field);
            newDest.put("state", value);

            // state - expected
            value = expected.get(field);
            expected.remove(field);
            newDestExpected.put("state", value);

            // country
            field = String.format(OutputFieldKeys.DESTINATION_COUNTRY_FMT, i);
            value = output.get(field);
            output.remove(field);
            newDest.put("country", value);

            // country - expected
            value = expected.get(field);
            expected.remove(field);
            newDestExpected.put("country", value);

            // Add to the sets
            outDests.add(newDest);
            expectedDests.add(newDestExpected);
        }

        if (!outDests.equals(expectedDests))
        {
            log.error("Set of destinations not equivalent");
            log.error("Actual output destinations:\n\t{}", outDests);
            log.error("Expected destinations:\n\t{}", expectedDests);
            return false;
        }

        List<String> difference = mapDifference(output, expected);
        if (difference.size() != 0)
        {
            log.error("Difference between output and expected:\n{}", difference);
            return false;
        }

        return true;
    }

    /**
     * Find the differences in the two maps.
     * 
     * @param m1 - Map1 to check
     * @param m2 - Map2 to check
     * @return - A list of strings is returned containing field names. Field names are prepended
     *         with {@literal <missing>} if they are in m1 but not m2. Field names are prepended
     *         with {@literal <diff>} if their value is different in m1 and m2.
     */
    public static List<String> mapDifference(Map<String, String> m1, Map<String, String> m2)
    {
        List<String> difference = new ArrayList<String>();

        for (Map.Entry<String, String> entry : m1.entrySet())
        {
            String key = entry.getKey();
            if (!m2.containsKey(key))
            {
                difference.add("<missing>" + key);
                continue;
            }

            if (!m2.get(key).equalsIgnoreCase(m1.get(key)))
            {
                difference.add("<diff>" + key);
            }
        }

        return difference;
    }
}
