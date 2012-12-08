// LodgingPerDiemTest.java
package edu.umn.se.trap.rules.business;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TestPerDiemDB;
import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.test.generate.LoadedSampleForm;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;
import edu.umn.se.trap.util.Pair;

/**
 * @author Dylan
 * 
 */
public class LodgingPerDiemTest extends TrapTestFramework
{
    Integer formId;
    LoadedSampleForm formData;

    String lodging1Name, lodging1Amount;
    String lodging2Name, lodging2Amount;

    TestPerDiemDB.PerDiemBuilder builder;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() throws TRAPException
    {
        Pair<Integer, LoadedSampleForm> setupData = basicTrapSetup(SampleDataEnum.INTERNATIONAL1);
        formData = setupData.getRight();
        formId = setupData.getLeft();

        // builder = perDiemDB.

        // lodging1Name = String.format(InputFieldKeys.LODGING_CURRENCY_FMT, 1);
        lodging1Amount = String.format(InputFieldKeys.LODGING_AMOUNT_FMT, 1);

        lodging2Amount = String.format(InputFieldKeys.LODGING_AMOUNT_FMT, 2);
    }

    // One day - under per diem amount
    @Test
    public void singleLodgingExpenseLessThanPerDiemLimit() throws TRAPException
    {
        // formData.put(lodging1Amount, value)
    }

    // Two days - under per diem amount
    // One day - over per diem amount
    // Two days - over per diem amount
}
