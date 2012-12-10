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
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author Dylan
 * 
 */
public class LodgingPerDiemTest extends TrapTestFramework
{

    String lodgingName, lodgingAmount;

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

        // builder.getRates()

        lodgingAmount = String.format(InputFieldKeys.LODGING_AMOUNT_FMT, lodgingExpenses.get(0));
    }

    // TODO Figure out per diem amount

    // One day - under per diem amount
    @Test
    public void oneLodgingExpenseLessThanLimit() throws TRAPException
    {
        testFormData.put(lodgingAmount, "1");
        this.saveFormData(testFormData, testFormId);
        submitFormData(testFormId);
    }

    // One day - equal to per diem amount
    @Test
    public void oneLodgingExpenseEqualToLimit() throws TRAPException
    {
        testFormData.put(lodgingAmount, "200");
        this.saveFormData(testFormData, testFormId);
        submitFormData(testFormId);
    }

    // One day - over per diem amount
    @Test
    public void oneLodgingExpenseMoreThanLimit() throws TRAPException
    {
        testFormData.put(lodgingAmount, "9000");
        this.saveFormData(testFormData, testFormId);
        submitFormData(testFormId);
    }

}
