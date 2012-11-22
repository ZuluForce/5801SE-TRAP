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

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.TravelFormMetadata;
import edu.umn.se.trap.TravelFormProcessor;
import edu.umn.se.trap.db.CurrencyDB;
import edu.umn.se.trap.db.GrantDB;
import edu.umn.se.trap.db.PerDiemDB;
import edu.umn.se.trap.db.UserDB;
import edu.umn.se.trap.db.UserGrantDB;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.exception.TRAPRuntimeException;
import edu.umn.se.trap.test.generate.LoadedSampleForm;
import edu.umn.se.trap.test.generate.TestDataGenerator;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author andrewh
 * 
 */
public class TrapTestFramework
{
    private static final Logger log = LoggerFactory.getLogger(TrapTestFramework.class);

    private final CurrencyDB currencyDB = new CurrencyDB();
    private final GrantDB grantDB = new GrantDB();
    private final PerDiemDB perDiemDB = new PerDiemDB();
    private final UserDB userDB = new UserDB();
    private final UserGrantDB userGrantDB = new UserGrantDB();

    private final TravelFormProcessor trapProcessor;
    private final Map<Integer, LoadedSampleForm> savedForms;

    String testUser1 = "linc001";

    public TrapTestFramework()
    {
        trapProcessor = new TravelFormProcessor(userDB, perDiemDB, grantDB, userGrantDB, currencyDB);

        savedForms = new HashMap<Integer, LoadedSampleForm>();
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
    public Map<String, String> getExpectedOutput(Integer id) throws TRAPException
    {
        LoadedSampleForm data = savedForms.get(id);
        if (data == null)
        {
            throw new TRAPRuntimeException("getExpectedOutput failed to find saved form with id "
                    + id);
        }

        Map<String, String> expected = TestDataGenerator.getExpectedOutput(data);
        log.info("getExpectedOutput: {}", expected);

        return expected;
    }
}
