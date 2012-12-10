// OnlyOneCheckedLuggageTest.java
package edu.umn.se.trap.rules.business;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.FormDataQuerier;
import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.data.TransportationTypeEnum;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author Dylan
 * 
 */
public class OnlyOneCheckedLuggageTest extends TrapTestFramework
{
    int numCheckedLuggage, numAirTravel;
    String transportationDate, transportationType, transportationAmount, transportationCurrency;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() throws TRAPException
    {
        super.setup(SampleDataEnum.INTERNATIONAL1);

        List<Integer> luggageExpenses = FormDataQuerier.findTransportExpenses(testFormData,
                TransportationTypeEnum.BAGGAGE);
        List<Integer> airExpenses = FormDataQuerier.findTransportExpenses(testFormData,
                TransportationTypeEnum.AIR);
        numCheckedLuggage = luggageExpenses.size();
        numAirTravel = airExpenses.size();
        if (numAirTravel == 0)
        {
            Assert.fail("Need air expenses in sample form for this test");
        }

        // TODO Figure out how to remove whole object
        // test = String.format(InputFieldKeys.TRANSPORTATION_AMOUNT_FMT, luggageExpenses.get(0));
    }

    @Test
    public void validCheckedLuggage() throws TRAPException
    {
        // Sample form should have correct amount of checked luggage
        submitFormData(testFormId);
    }

    @Test
    public void overOneCheckedLuggage() throws TRAPException
    {
        // testFormData.put(key, value)
        Assert.fail();
    }

    @Test
    public void underOneCheckedLuggage() throws TRAPException
    {
        // testFormData.remove(key)
        Assert.fail();
    }

}
