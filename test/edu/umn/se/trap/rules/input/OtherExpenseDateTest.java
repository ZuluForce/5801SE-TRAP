// OtherExpenseDateTest.java
package edu.umn.se.trap.rules.input;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.InputValidationException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author planeman
 * 
 */
public class OtherExpenseDateTest extends TrapTestFramework
{

    private final String other1DateField = String.format(InputFieldKeys.OTHER_DATE_FMT, 1);

    /**
     * Load the sample domestic form.
     * 
     * @throws TRAPException When form saving fails
     */
    @Before
    public void setup() throws TRAPException
    {
        this.setup(SampleDataEnum.DOMESTIC1);
    }

    @SuppressWarnings("javadoc")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Verify that other expenses on the day after the arrival date are rejected.
     * 
     * @throws TRAPException When form processing fails.
     */
    @Test
    public void dayAfterArrival() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("is after trip arrival time");

        testFormData.put(other1DateField, "20121113");
        saveAndSubmitTestForm();
    }

    /**
     * Verify that other expenses several days after the arrival date are rejected.
     * 
     * @throws TRAPException When form processing fails.
     */
    @Test
    public void awhileAfterArrival() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("is after trip arrival time");

        testFormData.put(other1DateField, "20121120");
        saveAndSubmitTestForm();
    }
}
