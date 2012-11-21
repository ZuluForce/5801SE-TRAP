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
// SetUserTest.java
package edu.umn.se.trap.form;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.FormProcessorException;
import edu.umn.se.trap.exception.TRAPException;

public class SetUserTest extends TrapTestFramework
{
    public SetUserTest()
    {
        super();
    }

    @Test
    public void noSetUserException()
    {
        try
        {
            Assert.assertNull("Expected user to be null", getUser());

            try
            {
                clearSavedForms();
                Assert.fail("No user set but call succeeded");
            }
            catch (FormProcessorException e)
            {
                // Expected
            }

            try
            {
                getCompletedForm(0);
                Assert.fail("No user set but call succeeded");
            }
            catch (FormProcessorException e)
            {
                // Expected
            }

            try
            {
                getSavedFormData(0);
                Assert.fail("No user set but call succeeded");
            }
            catch (FormProcessorException e)
            {
                // Expected
            }

            try
            {
                getSavedForms();
                Assert.fail("No user set but call succeeded");
            }
            catch (FormProcessorException e)
            {
                // Expected
            }

            try
            {
                this.saveFormData(new HashMap<String, String>(), 0);
                Assert.fail("No user set but call succeeded");
            }
            catch (FormProcessorException e)
            {
                // Expected
            }

            try
            {
                this.saveFormData(new HashMap<String, String>(), "test");
                Assert.fail("No user set but call succeeded");
            }
            catch (FormProcessorException e)
            {
                // Expected
            }

            try
            {
                submitFormData(0);
                Assert.fail("No user set but call succeeded");
            }
            catch (FormProcessorException e)
            {
                // Expected
            }

            try
            {
                setUser("----Invalid User-----");
                Assert.fail("Allowed to set invalid user");
            }
            catch (TRAPException e)
            {
                // Expected
            }
        }
        catch (TRAPException e)
        {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void userSetWorking()
    {
        try
        {
            Assert.assertNull("getUser gave non-null name before anything was set", getUser());
            setValidUser();
            Assert.assertNotNull("getUser gave null after setting user", getUser());

            // Save methods
            Map<String, String> emptyMap = new HashMap<String, String>();
            int id = this.saveFormData(emptyMap, "test");
            this.saveFormData(emptyMap, id);

            // Load methods
            getSavedFormData(id);
            getSavedForms();

            // The following require forms that can be fully processed
            // submitFormData(id);
            // getCompletedForm(id);

            // Clear the forms
            clearSavedForms();
        }
        catch (TRAPException e)
        {
            e.printStackTrace();
            Assert.fail("Unexpected Exception: " + e.getMessage());
        }
    }
}
