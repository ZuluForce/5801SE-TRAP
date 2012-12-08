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
import edu.umn.se.trap.test.generate.LoadedSampleForm;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author Dylan
 * 
 */
public class USCarriersOnlyTest extends TrapTestFramework
{
    LoadedSampleForm formData;
    Integer formId;

    String airCarrier;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() throws TRAPException
    {
        setValidUser();
        formData = getLoadableForm(SampleDataEnum.INTERNATIONAL1);
        formId = this.saveFormData(formData, "test form");

        airCarrier = String.format(InputFieldKeys.TRANSPORTATION_CARRIER_FMT, 1);

    }

    @Test
    public void validUSCarrier() throws TRAPException
    {
        formData.put(airCarrier, "American");
        this.saveFormData(formData, formId);
        submitFormData(formId);
    }

    @Test
    public void invalidCarrier() throws TRAPException
    {
        exception.expect(BusinessLogicException.class);
        exception.expectMessage("is not a valid US carrier");
        formData.put(airCarrier, "Dylan's Party Plane");
        this.saveFormData(formData, formId);
        submitFormData(formId);
    }

}
