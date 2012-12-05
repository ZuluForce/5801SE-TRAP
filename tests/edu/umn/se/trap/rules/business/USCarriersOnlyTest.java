// USCarriersOnlyTest.java
package edu.umn.se.trap.rules.business;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.BusinessLogicException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author Dylan
 * 
 */
public class USCarriersOnlyTest extends TrapTestFramework
{
    String airCarrier;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() throws TRAPException
    {
        super.setup(SampleDataEnum.INTERNATIONAL1);

        airCarrier = String.format(InputFieldKeys.TRANSPORTATION_CARRIER_FMT, 1);

    }

    @Test
    public void validUSCarrier() throws TRAPException
    {
        testFormData.put(airCarrier, "American");
        this.saveFormData(testFormData, testFormId);
        submitFormData(testFormId);
    }

    @Test
    public void invalidCarrier() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("is not a valid US carrier");
        testFormData.put(airCarrier, "Dylan's Party Plane");
        this.saveFormData(testFormData, testFormId);
        submitFormData(testFormId);
    }

}
