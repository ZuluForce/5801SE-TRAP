// TravelTypeTest.java
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
 * @author andrewh
 * 
 */
public class TravelTypeTest extends TrapTestFramework
{

    @Before
    public void setup() throws TRAPException
    {
        super.setup(SampleDataEnum.DOMESTIC1);
    }

    @Test
    public void cseSponsored() throws TRAPException
    {
        saveAndSubmitTestForm();
    }

    @Test
    public void dtcSponsored() throws TRAPException
    {
        testFormData.remove(InputFieldKeys.TRAVEL_TYPE_CSE_SPONSORED);
        testFormData.put(InputFieldKeys.TRAVEL_TYPE_DTC_SPONSORED, TRAPConstants.STR_YES);
        saveAndSubmitTestForm();
    }

    @Test
    public void nonSponsored() throws TRAPException
    {
        testFormData.remove(InputFieldKeys.TRAVEL_TYPE_CSE_SPONSORED);
        testFormData.put(InputFieldKeys.TRAVEL_TYPE_NONSPONSORED, TRAPConstants.STR_YES);
        saveAndSubmitTestForm();
    }

    @Test
    public void allTypes()
    {
        testFormData.put(InputFieldKeys.TRAVEL_TYPE_CSE_SPONSORED, TRAPConstants.STR_YES);
        testFormData.put(InputFieldKeys.TRAVEL_TYPE_DTC_SPONSORED, TRAPConstants.STR_YES);
        testFormData.put(InputFieldKeys.TRAVEL_TYPE_NONSPONSORED, TRAPConstants.STR_YES);
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void noType() throws TRAPException
    {
        exception.expect(MissingFieldException.class);
        exception.expectMessage("Must have at least one Travel Type");

        testFormData.remove(InputFieldKeys.TRAVEL_TYPE_CSE_SPONSORED);
        testFormData.remove(InputFieldKeys.TRAVEL_TYPE_DTC_SPONSORED);
        testFormData.remove(InputFieldKeys.TRAVEL_TYPE_NONSPONSORED);

        saveAndSubmitTestForm();
    }
}
