// LodgingPerDiemTest.java
package edu.umn.se.trap.rules.business;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.FormDataQuerier;
import edu.umn.se.test.frame.TestPerDiemDB;
import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author Dylan
 * 
 */
public class LodgingPerDiemTest extends TrapTestFramework
{

    String lodgingCity, lodgingState, lodgingAmount;

    TestPerDiemDB.PerDiemBuilder builder;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() throws TRAPException
    {
        super.setup(SampleDataEnum.DOMESTIC1);

        List<Integer> lodgingExpenses = FormDataQuerier.findLodgingExpenses(testFormData);
        if (lodgingExpenses.size() == 0)
        {
            Assert.fail("Needed lodging expenses in sample form for this test");
        }

        lodgingCity = String.format(InputFieldKeys.LODGING_CITY_FMT, lodgingExpenses.get(0));
        lodgingState = String.format(InputFieldKeys.LODGING_STATE_FMT, lodgingExpenses.get(0));
        lodgingAmount = String.format(InputFieldKeys.LODGING_AMOUNT_FMT, lodgingExpenses.get(0));

        if (lodgingCity != null)
        {
            builder.setCity(testFormData.get(lodgingCity));
        }
        builder.setState(testFormData.get(lodgingState));
        builder.addRate(TestPerDiemDB.RATE_FIELDS.LODGING_CEILING, 500.00);

    }

    // One day - under per diem amount
    @Test
    public void oneLodgingExpenseLessThanLimit() throws TRAPException
    {
        testFormData.put(lodgingAmount, "1");
        saveAndSubmitTestForm();
    }

    // One day - equal to per diem amount
    @Test
    public void oneLodgingExpenseEqualToLimit() throws TRAPException
    {
        testFormData.put(lodgingAmount, "500");
        saveAndSubmitTestForm();
    }

    // One day - over per diem amount
    @Test
    public void oneLodgingExpenseMoreThanLimit() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("Lodging expense exceeds per diem limit.");

        testFormData.put(lodgingAmount, "1000000");
        saveAndSubmitTestForm();
    }

}
