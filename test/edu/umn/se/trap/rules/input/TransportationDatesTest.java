// TransportationDatesTest.java
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
 * Test that transportation expenses are checked to be within the date range of the trip.
 * 
 * @author andrewh
 * 
 */
public class TransportationDatesTest extends TrapTestFramework
{

    /**
     * Load a sample form.
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
     * Test that a non air travel transportation expense occurring the day before the departure date
     * is rejected.
     * 
     * @throws TRAPException When form processing fails.
     */
    @Test
    public void beforeDepartureDay() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception
                .expectMessage("Transportation expense 1 comes before departure date and isn't air travel");

        String expenseDate = String.format(InputFieldKeys.TRANSPORTATION_DATE_FMT, 1);
        testFormData.put(expenseDate, "20121107");

        saveAndSubmitTestForm();
    }

    /**
     * Test that a transportation expense occurring the day after the arrival date for the trip is
     * rejected.
     * 
     * @throws TRAPException When form processing fails.
     */
    @Test
    public void dayAfterArrival() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("is after trip arrival time");

        String expenseDate = String.format(InputFieldKeys.TRANSPORTATION_DATE_FMT, 1);
        testFormData.put(expenseDate, "20121113");

        saveAndSubmitTestForm();
    }

    /**
     * Test that a transportation expense occurring after the arrival date for the trip is rejected.
     * 
     * @throws TRAPException When form processing fails.
     */
    @Test
    public void awhileAfterArrival() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("is after trip arrival time");

        String expenseDate = String.format(InputFieldKeys.TRANSPORTATION_DATE_FMT, 1);
        testFormData.put(expenseDate, "20121120");

        saveAndSubmitTestForm();
    }
}
