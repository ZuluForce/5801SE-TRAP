// OnlyOneCheckedLuggageTest.java
package edu.umn.se.trap.rules.business;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.InputValidationException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.test.generate.LoadedSampleForm;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author Dylan
 * 
 */
public class OnlyOneCheckedLuggageTest extends TrapTestFramework
{
    LoadedSampleForm formData;
    Integer formId;

    // luggage1Name =

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() throws TRAPException
    {
        super.setup(SampleDataEnum.INTERNATIONAL1);
    }

    @Test
    public void negativeLuggage() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception.expectMessage("Cannot have negative luggage amount");

        // formData.put(key, value);
        Assert.fail();
    }

    @Test
    public void zeroCheckedLuggage() throws TRAPException
    {
        Assert.fail();
    }

    @Test
    public void oneCheckedLuggage() throws TRAPException
    {
        Assert.fail();
    }

    @Test
    public void twoCheckedLuggage() throws TRAPException
    {
        Assert.fail();
    }

}
