// TransportationMileageExpensesTest.java
package edu.umn.se.trap.rules.business;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.FormDataQuerier;
import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.data.TRAPConstants;
import edu.umn.se.trap.data.TransportationTypeEnum;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.form.OutputFieldKeys;
import edu.umn.se.trap.test.generate.LoadedSampleForm;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author Dylan
 * 
 */
public class TransportationMileageExpensesTest extends TrapTestFramework
{
    int numCarTravel, totalTransportationExpenses;

    String newDateField;
    String newTypeField;
    String newRentalField;
    String newMilesTraveled;
    String newCurrencyField;

    String newTransportationTotalExpense;

    @SuppressWarnings("javadoc")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() throws TRAPException
    {
        super.setup(SampleDataEnum.INTERNATIONAL1);

        totalTransportationExpenses = Integer.parseInt(testFormData
                .get(InputFieldKeys.NUMBER_TRANSPORTATION_EXPENSES));

        List<Integer> carExpenses = FormDataQuerier.findTransportExpenses(testFormData,
                TransportationTypeEnum.CAR);
        numCarTravel = carExpenses.size();
        if (numCarTravel == 0)
        {
            Assert.fail("Need car expenses in the sample form for this test");
        }

        newDateField = String.format(InputFieldKeys.TRANSPORTATION_DATE_FMT, 9);
        newTypeField = String.format(InputFieldKeys.TRANSPORTATION_TYPE_FMT, 9);
        newRentalField = String.format(InputFieldKeys.TRANSPORTATION_RENTAL_FMT, 9);
        newMilesTraveled = String.format(InputFieldKeys.TRANSPORTATION_MILES_FMT, 9);
        newCurrencyField = String.format(InputFieldKeys.TRANSPORTATION_CURRENCY_FMT, 9);

        newTransportationTotalExpense = String.format(OutputFieldKeys.TRANSPORTATION_TOTAL_FMT, 9);
    }

    @Test
    public void noPersonalCarExpenses() throws TRAPException
    {
        // Sample form does not have personal car expenses
        saveAndSubmitTestForm();
    }

    @Test
    public void validPersonalCarMileageConversion() throws TRAPException
    {

        testFormData.put(newDateField, "20121126");
        testFormData.put(newTypeField, "CAR");
        testFormData.put(newRentalField, "No");
        testFormData.put(newMilesTraveled, "30");
        testFormData.put(newCurrencyField, TRAPConstants.USD);

        LoadedSampleForm expected = this.getExpectedOutput(testFormData);

        saveAndSubmitTestForm();

        Map<String, String> result = getCompletedForm(testFormId);
        Assert.assertTrue(doOutputsMatch(result, expected));
    }

    @Test
    public void invalidPersonlCarMileageConversion() throws TRAPException
    {
        getCompletedForm(testFormId);
    }
}
