// GrantJustificationTest.java
package edu.umn.se.trap.data;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.InputValidationException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author andrewh
 * 
 */
public class GrantJustificationTest extends TrapTestFramework
{

    @Before
    public void setup() throws TRAPException
    {
        super.setup(SampleDataEnum.DOMESTIC1);
    }

    @Test
    public void sponsoredWithJust() throws TRAPException
    {
        saveAndSubmitTestForm();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void sponsoredWithNonSponsoredJust() throws TRAPException
    {
        exception.expect(InputValidationException.class);

        testFormData.remove(InputFieldKeys.JUSTIFICATION_SPONSORED);
        testFormData.put(InputFieldKeys.JUSTIFICATION_NONSPONSORED, "party");
        saveAndSubmitTestForm();
    }

    @Test
    public void sponsoredWithNoJust() throws TRAPException
    {
        exception.expect(InputValidationException.class);
        exception
                .expectMessage("Grant justfication field. Must have at least one depending on the grants used.");

        testFormData.remove(InputFieldKeys.JUSTIFICATION_SPONSORED);
        saveAndSubmitTestForm();
    }

    @Test
    public void nonSponsoredWithJust() throws TRAPException
    {
        testFormData.remove(InputFieldKeys.TRAVEL_TYPE_CSE_SPONSORED);
        testFormData.remove(InputFieldKeys.JUSTIFICATION_SPONSORED);
        testFormData.put(InputFieldKeys.TRAVEL_TYPE_NONSPONSORED, TRAPConstants.STR_YES);
        testFormData.put(InputFieldKeys.JUSTIFICATION_NONSPONSORED, "party");

        saveAndSubmitTestForm();
    }

}
