// IncidentalsPerDiemTest.java
package edu.umn.se.trap.rules.business;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.FormDataQuerier;
import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.form.OutputFieldKeys;
import edu.umn.se.trap.rules.FinalizeRule;
import edu.umn.se.trap.test.generate.LoadedSampleForm;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * A set of test for requirement 1.n involving incidental expense per diems.
 * 
 * @author Dylan
 * 
 */
public class IncidentalsPerDiemTest extends TrapTestFramework
{

    @SuppressWarnings("javadoc")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    String incidental1Amnt;
    String incidental1Country;
    String lastIncidentalAmnt;

    String day1Total;
    String lastDayTotal;

    String grant1Amnt;
    String grant2Amnt;

    /**
     * Load a sample form.
     * 
     * @throws TRAPException When form saving fails
     */
    @Before
    public void setup() throws TRAPException
    {
        this.setup(SampleDataEnum.INTERNATIONAL1);

        // This incidental must be on the first day which is assumed true for the international form
        incidental1Amnt = String.format(InputFieldKeys.INCIDENTAL_AMOUNT_FMT, 1);
        incidental1Country = String.format(InputFieldKeys.INCIDENTAL_COUNTRY_FMT, 1);
        lastIncidentalAmnt = FormDataQuerier.buildFieldStrForLastIncidental(testFormData,
                InputFieldKeys.INCIDENTAL_AMOUNT_FMT);
        day1Total = String.format(OutputFieldKeys.DAY_TOTAL_FMT, 1);
        lastDayTotal = FormDataQuerier.buildFieldStrForLastIncidental(testFormData,
                OutputFieldKeys.DAY_TOTAL_FMT);
        grant1Amnt = String.format(OutputFieldKeys.GRANT_CHARGE_FMT, 1);
        grant2Amnt = String.format(OutputFieldKeys.GRANT_CHARGE_FMT, 2);
    }

    /**
     * Verify that only 75% of claimed incidental expense on the first day is reimbursed.
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void seventyFivePercentOnFirstDay() throws TRAPException
    {
        // Find how much the incidental is
        Double curAmount = Double.parseDouble(testFormData.get(incidental1Amnt));
        testFormData.put(incidental1Amnt, "0.0");

        curAmount *= 0.75;

        // Get the expected output and more importantly the expected total
        LoadedSampleForm expected = this.getExpectedOutput(testFormData);
        Double expectedTotal = Double
                .parseDouble(expected.get(OutputFieldKeys.TOTAL_REIMBURSEMENT));

        // Create a new total with 75% of the incidental cost subtracted
        expected.put(OutputFieldKeys.TOTAL_REIMBURSEMENT, getDiffAsString(expectedTotal, curAmount));

        // Update the day1 total
        Double d1Total = Double.parseDouble(expected.get(day1Total));
        expected.put(day1Total, getDiffAsString(d1Total, curAmount));

        // Update the grant totals. We hardcode their percentages here so don't change them in the
        // file!!
        // Double grant1Total = Double.parseDouble(expected.get(grant1Amnt));
        expected.put(grant1Amnt, "2663.50"); // We hard code it due to rounding problems

        Double grant2Total = Double.parseDouble(expected.get(grant2Amnt));
        expected.put(grant2Amnt, getDiffAsString(grant2Total, curAmount * .25));

        saveAndSubmitTestForm();
        Map<String, String> result = getCompletedForm(testFormId);

        Assert.assertTrue(doOutputsMatch(result, expected));
    }

    /**
     * Verify that only 75% of claimed incidental expense on the last day is reimbursed.
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void seventyFivePercentOnLastDay() throws TRAPException
    {
        // Find how much the incidental is
        Double curAmount = Double.parseDouble(testFormData.get(lastIncidentalAmnt));
        testFormData.put(lastIncidentalAmnt, "0.0");

        curAmount *= 0.75;

        // Get the expected output and more importantly the expected total
        LoadedSampleForm expected = this.getExpectedOutput(testFormData);
        Double expectedTotal = Double
                .parseDouble(expected.get(OutputFieldKeys.TOTAL_REIMBURSEMENT));

        // Create a new total with 75% of the incidental cost subtracted
        expected.put(OutputFieldKeys.TOTAL_REIMBURSEMENT, getDiffAsString(expectedTotal, curAmount));

        // Update the last total
        Double lastDTotal = Double.parseDouble(expected.get(lastDayTotal));
        expected.put(lastDayTotal, getDiffAsString(lastDTotal, curAmount));

        // Update the grant totals. We hardcode their percentages here so don't change them in the
        // file!!
        // Double grant1Total = Double.parseDouble(expected.get(grant1Amnt));
        expected.put(grant1Amnt, "2663.50"); // We hard code it due to rounding problems

        Double grant2Total = Double.parseDouble(expected.get(grant2Amnt));
        expected.put(grant2Amnt, getDiffAsString(grant2Total, curAmount * .25));

        saveAndSubmitTestForm();
        Map<String, String> result = getCompletedForm(testFormId);
        Assert.assertTrue(doOutputsMatch(result, expected));
    }

    /**
     * Verify that an incidental expense above the per diem amount is rejected.
     * 
     * @throws TRAPException When form processing fails.
     */
    @Test
    public void amountAbovePerDiem() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("greater than per diem");

        testFormData.put(incidental1Amnt, "200");
        saveAndSubmitTestForm();
    }

    /**
     * Verify that a per diem with an unknown location (to the db) is rejected.
     * 
     * @throws TRAPException When form processing fails
     */
    @Test
    public void unknownPerDiemLocation() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("Could not find per diem for incidental on day");

        testFormData.put(incidental1Country, "Andrew's new Country");

        saveAndSubmitTestForm();
    }

    private String getDiffAsString(Double amount1, Double amount2)
    {
        Double diff = amount1 - amount2;

        return FinalizeRule.formatDoubleAsCurrencyNoComma(diff);
    }
}
