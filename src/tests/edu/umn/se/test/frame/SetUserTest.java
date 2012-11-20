// SetUserTest.java
package edu.umn.se.test.frame;

import java.util.HashMap;

import junit.framework.Assert;

import org.junit.Test;

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
        
        @Test
        public void userSetWorking()
    }
}
