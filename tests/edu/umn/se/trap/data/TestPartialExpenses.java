// TestPartialExpenses.java
package edu.umn.se.trap.data;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.MissingFieldException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author planeman
 * 
 */
public class TestPartialExpenses extends TrapTestFramework
{
    // This day in the international sample has all three meals, lodging and incidental
    Integer targetDay = 4;

    @Before
    public void setup() throws TRAPException
    {
        this.setup(SampleDataEnum.INTERNATIONAL1);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void partialBreakfast() throws TRAPException
    {
        String breakfastCity = String.format(InputFieldKeys.BREAKFAST_COUNTRY_FMT, targetDay);
        exception.expect(MissingFieldException.class);
        exception.expectMessage("Missing " + breakfastCity);

        testFormData.remove(breakfastCity);

        saveAndSubmitTestForm();
    }

    @Test
    public void partialLunch() throws TRAPException
    {
        String lunchCity = String.format(InputFieldKeys.LUNCH_COUNTRY_FMT, targetDay);
        exception.expect(MissingFieldException.class);
        exception.expectMessage("Missing " + lunchCity);

        testFormData.remove(lunchCity);

        saveAndSubmitTestForm();
    }

    @Test
    public void partialDinner() throws TRAPException
    {
        String dinnerCity = String.format(InputFieldKeys.DINNER_COUNTRY_FMT, targetDay);
        exception.expect(MissingFieldException.class);
        exception.expectMessage("Missing " + dinnerCity);

        testFormData.remove(dinnerCity);

        saveAndSubmitTestForm();
    }

    @Test
    public void partialLodging() throws TRAPException
    {
        String lodgingAmount = String.format(InputFieldKeys.LODGING_AMOUNT_FMT, targetDay);
        exception.expect(MissingFieldException.class);
        exception.expectMessage("Missing " + lodgingAmount);

        testFormData.remove(lodgingAmount);

        saveAndSubmitTestForm();
    }
}
