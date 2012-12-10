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

    /**
     * Load a sample form.
     * 
     * @throws TRAPException - when the form cannot be saved
     */
    @Before
    public void setup() throws TRAPException
    {
        super.setup(SampleDataEnum.INTERNATIONAL1);
    }

    /**
     * Check that the form is correctly accepted with a valid username
     * 
     * @throws TRAPException - When form submission fails
     */
    @Test
    public void validUsername() throws TRAPException
    {
        saveAndSubmitTestForm();
    }

    @SuppressWarnings("javadoc")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Check that an invalid username is caught.
     * 
     * @throws TRAPException - When form submission fails
     */
    @Test
    public void invalidUserName() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Invalid username");
        testFormData.put(InputFieldKeys.USER_NAME, "Bob Barker");
        saveAndSubmitTestForm();
    }

    /**
     * Check that a missing username is caught.
     * 
     * @throws TRAPException - When form submission fails
     */
    @Test
    public void missingUserName() throws TRAPException
    {
        exception.expect(MissingFieldException.class);
        exception.expectMessage("Missing " + InputFieldKeys.USER_NAME);
        testFormData.remove(InputFieldKeys.USER_NAME);
        saveAndSubmitTestForm();
    }
}
