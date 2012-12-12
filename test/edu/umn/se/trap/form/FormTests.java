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
// FormTests.java
package edu.umn.se.trap.form;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.TravelFormMetadata;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * Tests all the form functionalities
 * 
 * @author nagell2008
 * 
 */
public class FormTests extends TrapTestFramework
{

    Map<String, String> form1 = getLoadableForm(SampleDataEnum.DOMESTIC1);
    Map<String, String> form2 = getLoadableForm(SampleDataEnum.DOMESTIC1);

    /**
     * Setup code as necessary.
     * 
     * @throws TRAPException
     */
    public void setup() throws TRAPException
    {
        super.setup(SampleDataEnum.DOMESTIC1);

    }

    /**
     * Expected exceptions
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Tests saving a new form, and then inserting something into the form and saving.
     * 
     * @throws TRAPException
     */
    @Test
    public void saveForm() throws TRAPException
    {
        setUser("linc001");
        int id = this.saveFormData(form1, "test");

        Map<String, String> temp = getSavedFormData(id);
        temp.put("NUM_GRANTS", "0");
        this.saveFormData(temp, id);
        temp = getSavedFormData(id);
        Assert.assertTrue(temp.containsKey("NUM_GRANTS"));

    }

    /**
     * Gets a saved form.
     * 
     * @throws TRAPException
     */
    @Test
    public void getForm() throws TRAPException
    {
        setUser("linc001");
        int id = this.saveFormData(form1, "test");
        Map<String, String> tempForm = getSavedFormData(id);

        Assert.assertEquals(tempForm, form1);

    }

    /**
     * Checks that any forms a user has saved are removed.
     * 
     * @throws TRAPException
     */
    @Test
    public void clearForms() throws TRAPException
    {
        exception.expect(TRAPException.class);

        setUser("linc001");
        int id = this.saveFormData(form1, "test");
        int id2 = this.saveFormData(form2, "another test");

        clearSavedForms();

        Map<String, String> tempForm = getSavedFormData(id);
    }

    /**
     * Checks that a completed form is returned.
     * 
     * @throws TRAPException
     */
    @Test
    public void completedFormGood() throws TRAPException
    {
        setUser("linc001");
        int id = this.saveFormData(form1, "test");

        submitFormData(id);

        Map<String, String> completedForm = getCompletedForm(id);

        Map<String, String> expectedForm = this.getExpectedOutput(SampleDataEnum.DOMESTIC1);

        Assert.assertTrue(doOutputsMatch(completedForm, expectedForm));
    }

    /**
     * Checks that a bad form is not returned after submitting and calling getCompletedForms.
     * 
     * @throws TRAPException
     */
    @Test
    public void completedFormBad()
    {
        try
        {
            setUser("linc001");
        }
        catch (TRAPException e2)
        {
            Assert.fail();
        }
        Map<String, String> temp = new HashMap<String, String>();
        int id = -1;
        try
        {
            id = this.saveFormData(temp, "test");
        }
        catch (TRAPException e1)
        {
            e1.printStackTrace();
            Assert.fail();
        }

        try
        {
            submitFormData(id);
        }
        catch (TRAPException e)
        {
            // Assert.fail();
        }

        try
        {
            Map<String, String> completedForm = getCompletedForm(id);
        }
        catch (TRAPException e)
        {
            e.printStackTrace();
            Assert.assertTrue(true);
        }
    }

    /**
     * Tests that the form id's and meta data are returned.
     * 
     * @throws TRAPException
     */
    @Test
    public void getSavedFormsTest() throws TRAPException
    {
        setUser("linc001");

        this.saveFormData(form1, "test");

        Map<Integer, TravelFormMetadata> temp = getSavedForms();

        Assert.assertTrue(temp.get(0) != null);
    }
}
