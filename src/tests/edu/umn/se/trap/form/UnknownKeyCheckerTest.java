// UnknownKeyCheckerTest.java
package edu.umn.se.trap.form;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.umn.se.trap.exception.InputValidationException;
import edu.umn.se.trap.test.generate.TestDataGenerator;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author planeman
 * 
 */
public class UnknownKeyCheckerTest
{
    UnknownKeyChecker checker;

    Map<String, String> fullValidForm;
    Map<String, String> invalidForm;

    @Before
    public void setUp()
    {
        checker = new UnknownKeyChecker();

        fullValidForm = TestDataGenerator.getSampleForm(SampleDataEnum.SAMPLE1);

        invalidForm = new HashMap<String, String>();
        invalidForm.put("bacon", "secretsauce");
    }

    @After
    public void tearDown()
    {
        checker = null;
    }

    @Test
    public void validKeyTest() throws InputValidationException
    {
        checker.areFormKeysValid(fullValidForm);
    }

    @Test
    public void invalidKeyTest()
    {
        try
        {
            checker.areFormKeysValid(invalidForm);
            Assert.fail("Invalid form keys passed check");
        }
        catch (InputValidationException ive)
        {
            ; // Good
        }
    }
}
