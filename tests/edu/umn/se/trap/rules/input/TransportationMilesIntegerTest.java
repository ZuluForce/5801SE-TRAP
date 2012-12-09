// TransportationMilesIntegerTest.java
package edu.umn.se.trap.rules.input;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TRAPConstants;
import edu.umn.se.trap.data.TransportationExpense;
import edu.umn.se.trap.exception.InputValidationException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.FormDataConverter;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * A test for requirement 1.j. This test checks that only whole numbers are accepted for the
 * transportation mileage field.
 * 
 * @author planeman
 * 
 */
public class TransportationMilesIntegerTest extends TrapTestFramework
{

    ReimbursementApp app;
    Integer newTExpenseNum;
    String newTExpenseMileField;

    @Before
    public void setup() throws TRAPException
    {
        super.setup(SampleDataEnum.INTERNATIONAL1);

        app = FormDataConverter.formToReimbursementApp(testFormData);

        // Add a new expense onto the end that is for a personal car
        List<TransportationExpense> texpenses = app.getTransportationExpenseList();
        newTExpenseNum = texpenses.size();

        // Build the fields for the new expense
        String newTExpenseType = String.format(InputFieldKeys.TRANSPORTATION_TYPE_FMT,
                newTExpenseNum);
        String newTExpenseRental = String.format(InputFieldKeys.TRANSPORTATION_RENTAL_FMT,
                newTExpenseNum);
        String newTExpenseDate = String.format(InputFieldKeys.TRANSPORTATION_DATE_FMT,
                newTExpenseNum);
        // String newTExpenseRental = String.format(InputFieldKeys.TRANSPORTATION_CURRENCY_FMT,
        // "USD");
        newTExpenseMileField = String.format(InputFieldKeys.TRANSPORTATION_MILES_FMT,
                newTExpenseNum);

        // Add to the form
        testFormData.put(newTExpenseType, "car");
        testFormData.put(newTExpenseRental, TRAPConstants.STR_NO);
        testFormData.put(newTExpenseMileField, "1");
        testFormData.put(newTExpenseDate, "20121128");
    }

    /**
     * Test that an integer is accepted.
     * 
     * @throws TRAPException
     */
    @Test
    public void milesTraveledInteger() throws TRAPException
    {
        testFormData.put(newTExpenseMileField, "1");
        saveAndSubmitTestForm();
    }

    /**
     * Test that the 0 integer is accepted.
     * 
     * @throws TRAPException
     */
    @Test
    public void milesTraveledIntegerZero() throws TRAPException
    {
        testFormData.put(newTExpenseMileField, "0");
        saveAndSubmitTestForm();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Test that a negative integer is rejected.
     * 
     * @throws TRAPException
     */
    @Test
    public void milesTraveledIntegerNegative() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Miles traveled must be positive");

        testFormData.put(newTExpenseMileField, "-1");
        saveAndSubmitTestForm();
    }

    /**
     * Test that a real number is not accepted. In this case the real number could be truncated to
     * an integer without lose of precision but it should still be rejected.
     * 
     * @throws TRAPException
     */
    @Test
    public void milesTraveledIntegerRealNumber() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Transportation miles traveled field is not an integer");

        testFormData.put(newTExpenseMileField, "0.0");
        saveAndSubmitTestForm();
    }

    /**
     * Test that a real number is not accepted. Now this number would lose precision if truncated to
     * an int and most definitely should produce an error.
     * 
     * @throws TRAPException
     */
    @Test
    public void milesTraveledIntegerRealNumber2() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Transportation miles traveled field is not an integer");

        testFormData.put(newTExpenseMileField, "1.25");
        saveAndSubmitTestForm();
    }
}
