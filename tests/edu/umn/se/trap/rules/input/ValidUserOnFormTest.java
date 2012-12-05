// ValidUserOnFormTest.java
package edu.umn.se.trap.rules.input;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.InputValidationException;
import edu.umn.se.trap.exception.MissingFieldException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * Test for requirement 1.m
 * 
 * @author planeman
 * 
 */
public class ValidUserOnFormTest extends TrapTestFramework
{

    @Before
    public void setup() throws TRAPException
    {
        super.setup(SampleDataEnum.INTERNATIONAL1);
    }

    @Test
    public void validUsername() throws TRAPException
    {
        saveAndSubmitTestForm();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void invalidUserName() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid username");
        testFormData.put(InputFieldKeys.USER_NAME, "Bob Barker");
        saveAndSubmitTestForm();
    }

    @Test
    public void missingUserName() throws TRAPException
    {
        exception.expect(MissingFieldException.class);
        exception.expectMessage("Missing " + InputFieldKeys.USER_NAME);
        testFormData.remove(InputFieldKeys.USER_NAME);
        saveAndSubmitTestForm();
    }
}
