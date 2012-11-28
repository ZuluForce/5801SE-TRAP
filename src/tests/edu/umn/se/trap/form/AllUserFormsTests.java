/*****************************************************************************************
 * Copyright (c) 2012 Dylan Bettermann, Andrew Helgeson, Brian Maurer, Ethan Waytas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ****************************************************************************************/
// AllUserFormsTests.java
package edu.umn.se.trap.form;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.TravelFormMetadata;
import edu.umn.se.trap.exception.FormStorageException;
import edu.umn.se.trap.test.generate.TestDataGenerator;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author nagell2008
 * 
 */
public class AllUserFormsTests
{

    private static Logger log = LoggerFactory.getLogger(AllUserFormsTests.class);

    /**
     * Test method for {@link edu.umn.se.trap.form.AllUserForms#AllUserForms()}.
     */
    @Test
    public void testAllUserForms()
    {
        AllUserForms allForms = new AllUserForms();

        Assert.assertNotNull(allForms);
    }

    /**
     * Testing the private method of getUserForms(String) via getSavedFormData
     */
    public void testgetUserForms()
    {
        AllUserForms allForms = new AllUserForms();

        allForms.addUser("test");

        try
        {
            allForms.getSavedFormData("test", 0);
        }
        catch (FormStorageException e)
        {
            Assert.assertEquals("User test has no forms available", e.getMessage());
        }

    }

    /**
     * Testing the private method of getUserForms(String) via getSavedFormData
     */
    public void testgetUserFormsAfterClearFormsCalled()
    {
        AllUserForms allForms = new AllUserForms();

        allForms.addUser("test");

        int id = -1;

        try
        {
            Map<String, String> temp = new HashMap<String, String>();
            temp.put("test", "testValue");
            id = allForms.saveFormData("test", temp, "This is a test");

            Map<String, String> form = allForms.getSavedFormData("test", id);

            Assert.assertEquals(form.containsKey("test"), true);
            Assert.assertEquals(form.get("test"), "testValue");

            log.info("Map:{}", form);

        }
        catch (FormStorageException f)
        {
            f.printStackTrace();
            Assert.fail(f.getMessage());
        }

        try
        {
            allForms.clearSavedForms("test");
        }
        catch (FormStorageException e1)
        {
            Assert.fail(e1.getMessage());
        }

        try
        {
            allForms.getSavedFormData("test", 0);
        }
        catch (FormStorageException e)
        {
            Assert.assertEquals("User test has no forms available", e.getMessage());
        }

    }

    /**
     * Test method for {@link edu.umn.se.trap.form.AllUserForms#addUser(java.lang.String)}.
     */
    @Test
    public void testAddUser()
    {
        AllUserForms allForms = new AllUserForms();

        allForms.addUser("test");

        int id = -1;

        try
        {
            Map<String, String> temp = new HashMap<String, String>();
            temp.put(InputFieldKeys.NUM_DAYS, "5");
            id = allForms.saveFormData("test", temp, "This is a test");

            Map<String, String> form = allForms.getSavedFormData("test", id);

            Assert.assertEquals(form.containsKey(InputFieldKeys.NUM_DAYS), true);
            Assert.assertEquals(form.get(InputFieldKeys.NUM_DAYS), "5");

            log.info("Map:{}", form);

        }
        catch (FormStorageException f)
        {
            f.printStackTrace();
            Assert.fail(f.getMessage());
        }

    }

    /**
     * Test method for
     * {@link edu.umn.se.trap.form.AllUserForms#saveFormData(java.lang.String, java.util.Map, int)}.
     */
    @Test
    public void testSaveFormDataStringMapOfStringStringInt()
    {
        AllUserForms allForms = new AllUserForms();

        allForms.addUser("test");

        int id = -1;
        int sameID = -1;

        Map<String, String> temp = TestDataGenerator.getSampleForm(SampleDataEnum.SAMPLE1);

        try
        {
            id = allForms.saveFormData("test", temp, "a test form");
            Assert.assertTrue(id != -1);
        }
        catch (FormStorageException f)
        {
            f.printStackTrace();
            Assert.fail(f.getMessage());
        }

        temp.put(InputFieldKeys.NUM_DAYS, "4");

        try
        {
            sameID = allForms.saveFormData("test", temp, id);
            log.info("Map: {}", temp);

            Assert.assertEquals(id, sameID);

        }
        catch (FormStorageException f)
        {
            f.printStackTrace();
            Assert.fail(f.getMessage());
        }

    }

    /**
     * Test method for
     * {@link edu.umn.se.trap.form.AllUserForms#saveFormData(java.lang.String, java.util.Map, java.lang.String)}
     * .
     */
    @Test
    public void testSaveFormDataStringMapOfStringStringString()
    {
        AllUserForms allForms = new AllUserForms();

        allForms.addUser("test");

        int id = -1;
        int sameID = -1;

        Map<String, String> temp = TestDataGenerator.getSampleForm(SampleDataEnum.SAMPLE1);

        try
        {
            id = allForms.saveFormData("test", temp, "a test form");
            Assert.assertTrue(id != -1);
        }
        catch (FormStorageException f)
        {
            f.printStackTrace();
            Assert.fail(f.getMessage());
        }

    }

    /**
     * Test method for
     * {@link edu.umn.se.trap.form.AllUserForms#getCompletedForm(java.lang.String, int)}.
     */
    @Test
    public void testGetCompletedForm()
    {
        AllUserForms allForms = new AllUserForms();

        allForms.addUser("test");

        int id = -1;

        Map<String, String> temp = TestDataGenerator.getSampleForm(SampleDataEnum.SAMPLE1);

        try
        {
            id = allForms.saveFormData("test", temp, "a test form");
            Assert.assertTrue(id != -1);

            log.info("Map: {}", allForms.getSavedFormData("test", id));
        }
        catch (FormStorageException f)
        {
            f.printStackTrace();
            Assert.fail(f.getMessage());
        }

        temp.put("test2", "testCompleted");
        Map<String, String> completed = new HashMap<String, String>();

        completed.put("test", "this is now done");

        try
        {
            allForms.saveCompletedForm("test", temp, id);
            Map<String, String> completedForm = allForms.getCompletedForm("test", id);
            log.info("Map: {}", temp);
            Assert.assertEquals(completedForm.get("test2"), "testCompleted");

        }
        catch (FormStorageException f)
        {
            f.printStackTrace();
            Assert.fail(f.getMessage());
        }

    }

    /**
     * Test method for
     * {@link edu.umn.se.trap.form.AllUserForms#getSavedFormData(java.lang.String, int)}.
     */
    @Test
    public void testGetSavedFormData()
    {
        AllUserForms allForms = new AllUserForms();

        allForms.addUser("test");

        int id = -1;

        Map<String, String> temp = TestDataGenerator.getSampleForm(SampleDataEnum.SAMPLE1);

        try
        {
            id = allForms.saveFormData("test", temp, "a test form");

            Assert.assertTrue(id != -1);

            log.info("Map: {}", allForms.getSavedFormData("test", id));

            Assert.assertEquals(allForms.getSavedFormData("test", id), temp);
        }
        catch (FormStorageException f)
        {
            f.printStackTrace();
            Assert.fail(f.getMessage());
        }

    }

    /**
     * Test method for {@link edu.umn.se.trap.form.AllUserForms#getSavedForms(java.lang.String)}.
     */
    @Test
    public void testGetSavedForms()
    {
        AllUserForms allForms = new AllUserForms();

        allForms.addUser("test");

        int idTemp = -1;
        int idTemp2 = -1;

        Map<String, String> temp = TestDataGenerator.getSampleForm(SampleDataEnum.SAMPLE1);
        Map<String, String> temp2 = TestDataGenerator.getSampleForm(SampleDataEnum.SAMPLE1);

        try
        {
            idTemp = allForms.saveFormData("test", temp, "a test form");
            idTemp2 = allForms.saveFormData("test", temp, "a test2 form");

            Assert.assertTrue(idTemp != -1);
            Assert.assertTrue(idTemp2 != -1 && idTemp2 != idTemp);

            // log.info("Map: {}", allForms.getSavedFormData("test", id));
        }
        catch (FormStorageException f)
        {
            f.printStackTrace();
            Assert.fail(f.getMessage());
        }

        try
        {
            Map<Integer, TravelFormMetadata> savedData = allForms.getSavedForms("test");
            Assert.assertNotNull(savedData);

            for (Map.Entry<Integer, TravelFormMetadata> thing : savedData.entrySet())
            {
                log.info("Id: {} Description: {} Status: {}", thing.getKey(),
                        thing.getValue().description, thing.getValue().status);
            }

        }
        catch (FormStorageException f)
        {
            f.printStackTrace();
            Assert.fail(f.getMessage());
        }

    }

    /**
     * Test method for
     * {@link edu.umn.se.trap.form.AllUserForms#saveCompletedForm(java.lang.String, java.util.Map, int)}
     * .
     */
    @Test
    public void testSaveCompletedForm()
    {
        AllUserForms allForms = new AllUserForms();

        allForms.addUser("test");

        int id = -1;

        Map<String, String> temp = TestDataGenerator.getSampleForm(SampleDataEnum.SAMPLE1);

        try
        {
            id = allForms.saveFormData("test", temp, "a test form");
            Assert.assertTrue(id != -1);

            log.info("Map: {}", allForms.getSavedFormData("test", id));
        }
        catch (FormStorageException f)
        {
            f.printStackTrace();
            Assert.fail(f.getMessage());
        }

        temp.put("test2", "testCompleted");
        Map<String, String> completed = new HashMap<String, String>();

        completed.put("test", "this is now done");

        try
        {
            allForms.saveCompletedForm("test", temp, id);
            Map<String, String> completedForm = allForms.getCompletedForm("test", id);
            log.info("Map: {}", temp);
            Assert.assertEquals(completedForm.get("test2"), "testCompleted");

        }
        catch (FormStorageException f)
        {
            f.printStackTrace();
            Assert.fail(f.getMessage());
        }

    }

    /**
     * Test method for {@link edu.umn.se.trap.form.AllUserForms#clearSavedForms(java.lang.String)}.
     */
    @Test
    public void testClearSavedForms()
    {
        AllUserForms allForms = new AllUserForms();

        allForms.addUser("test");

        int idTemp = -1;
        int idTemp2 = -1;

        Map<String, String> temp = TestDataGenerator.getSampleForm(SampleDataEnum.SAMPLE1);
        Map<String, String> temp2 = TestDataGenerator.getSampleForm(SampleDataEnum.SAMPLE1);

        try
        {
            idTemp = allForms.saveFormData("test", temp, "a test form");
            idTemp2 = allForms.saveFormData("test", temp, "a test2 form");

            Assert.assertTrue(idTemp != -1);
            Assert.assertTrue(idTemp2 != -1 && idTemp2 != idTemp);

            // log.info("Map: {}", allForms.getSavedFormData("test", id));
        }
        catch (FormStorageException f)
        {
            f.printStackTrace();
            Assert.fail(f.getMessage());
        }

        try
        {
            allForms.clearSavedForms("test");
        }
        catch (FormStorageException f)
        {
            f.printStackTrace();
            Assert.fail(f.getMessage());
        }

        try
        {
            allForms.getSavedFormData("test", idTemp);
        }
        catch (FormStorageException f)
        {
            log.info("The clearSavedForms test should fail by throwing an exception");

        }
    }
}
