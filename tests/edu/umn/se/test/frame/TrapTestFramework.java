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
import edu.umn.se.trap.exception.TRAPRuntimeException;
import edu.umn.se.trap.form.OutputFieldKeys;
import edu.umn.se.trap.rules.input.DateValidator;
import edu.umn.se.trap.test.generate.LoadedSampleForm;
import edu.umn.se.trap.test.generate.TestDataGenerator;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;
import edu.umn.se.trap.util.Pair;

/**
 * @author andrewh
 * 
 */
public class TrapTestFramework
{
    /** Logger for the TrapTestFramework */
    private static final Logger log = LoggerFactory.getLogger(TrapTestFramework.class);

    public final TestCurrencyDB currencyDB = new TestCurrencyDB();
    public final TestGrantDB grantDB = new TestGrantDB();
    public final TestPerDiemDB perDiemDB = new TestPerDiemDB();
    public final TestUserDB userDB = new TestUserDB();
    public final TestUserGrantDB userGrantDB = new TestUserGrantDB();

    private final TravelFormProcessor trapProcessor;
    private final Map<Integer, LoadedSampleForm> savedForms;

    final String testUser1 = "linc001";

    protected LoadedSampleForm testFormData;
    protected Integer testFormId;

    public void setup(SampleDataEnum formType) throws TRAPException
    {
        Pair<Integer, LoadedSampleForm> formInfo = basicTrapSetup(formType);
        testFormData = formInfo.getRight();
        testFormId = formInfo.getLeft();
    }

    public void resaveTestForm() throws TRAPException
    {
        this.saveFormData(testFormData, testFormId);
    }

    public void saveAndSubmitTestForm() throws TRAPException
    {
        resaveTestForm();
        submitFormData(testFormId);
    }

    public TrapTestFramework()
    {
        trapProcessor = new TravelFormProcessor(userDB, perDiemDB, grantDB, userGrantDB, currencyDB);

        savedForms = new HashMap<Integer, LoadedSampleForm>();

        TestDataGenerator.initialize();
    }

    public void clearSavedForms() throws TRAPException
    {
        trapProcessor.clearSavedForms();
    }

    public Map<String, String> getCompletedForm(Integer id) throws TRAPException
    {
        return trapProcessor.getCompletedForm(id);
    }

    public Map<String, String> getSavedFormData(Integer id) throws TRAPException
    {
        return trapProcessor.getSavedFormData(id);
    }

    public Map<Integer, TravelFormMetadata> getSavedForms() throws TRAPException
    {
        return trapProcessor.getSavedForms();
    }

    public String getUser()
    {
        return trapProcessor.getUser();
    }

    public Integer saveFormData(Map<String, String> formData, String desc) throws TRAPException
    {
        return trapProcessor.saveFormData(formData, desc);
    }

    public Integer saveFormData(Map<String, String> formData, Integer id) throws TRAPException
    {
        return trapProcessor.saveFormData(formData, id);
    }

    public void setUser(String user) throws TRAPException
    {
        trapProcessor.setUser(user);
    }

    public void submitFormData(Integer id) throws TRAPException
    {
        trapProcessor.submitFormData(id);
    }

    // Helper methods for tests

    public Pair<Integer, LoadedSampleForm> basicTrapSetup(SampleDataEnum formType)
            throws TRAPException
    {
        setValidUser();
        LoadedSampleForm formData = getLoadableForm(formType);
        Integer formId = this.saveFormData(formData, "A test form of type " + formType.toString());

        return new Pair<Integer, LoadedSampleForm>(formId, formData);
    }

    public String getValidTestUser()
    {
        return testUser1;
    }

    public void setValidUser() throws TRAPException
    {
        trapProcessor.setUser(getValidTestUser());
    }

    public Integer addRandomForm() throws TRAPException
    {
        return this.addRandomForm("test");
    }

    public Integer addRandomForm(Integer id) throws TRAPException
    {
        Map<String, String> testData = TestDataGenerator.getSampleForm(SampleDataEnum.RANDOM);
        trapProcessor.saveFormData(testData, id);
        savedForms.put(id, (LoadedSampleForm) testData);

        return id;
    }

    public Integer addRandomForm(String desc) throws TRAPException
    {
        Map<String, String> testData = TestDataGenerator.getSampleForm(SampleDataEnum.RANDOM);
        int id = trapProcessor.saveFormData(testData, desc);
        savedForms.put(id, (LoadedSampleForm) testData);

        return id;
    }

    public LoadedSampleForm getLoadableForm(SampleDataEnum source)
    {
        return TestDataGenerator.getSampleForm(source);
    }

    public LoadedSampleForm getExpectedOutput(SampleDataEnum source)
    {
        return TestDataGenerator.getExpectedOutput(source);
    }

    public LoadedSampleForm getExpectedOutput(LoadedSampleForm form)
    {
        return TestDataGenerator.getExpectedOutput(form);
    }

    /**
     * Get the expected output for a previously added form given its id. This method will not work
     * if the user has been switched since the form was added. If you need to support that then
     * load/add the form yourself.
     * 
     * @param id - The id of the previously saved form.
     * @return - The expected output of the previously saved form.
     * @throws TRAPException - If there is a problem retrieving the saved form or the expected
     *             output.
     */
    public LoadedSampleForm getExpectedOutput(Integer id) throws TRAPException
    {
        LoadedSampleForm data = savedForms.get(id);
        if (data == null)
        {
            throw new TRAPRuntimeException("getExpectedOutput failed to find saved form with id "
                    + id);
        }

        return TestDataGenerator.getExpectedOutput(data);
    }

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
