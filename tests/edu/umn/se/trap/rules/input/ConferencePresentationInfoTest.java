// ConferencePresentationInfoTest.java
package edu.umn.se.trap.rules.input;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.data.ConferenceInfo;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.TRAPConstants;
import edu.umn.se.trap.exception.MissingFieldException;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.FormDataConverter;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author planeman
 * 
 */
public class ConferencePresentationInfoTest extends TrapTestFramework
{

    private static final Logger log = LoggerFactory.getLogger(ConferenceInfo.class);

    @Before
    public void setup() throws TRAPException
    {
        this.setup(SampleDataEnum.INTERNATIONAL1);

        ReimbursementApp app = FormDataConverter.formToReimbursementApp(testFormData);
        log.info("Base sample conference info: {}", app.getConferenceInfo());

        // Since apparently eclemma won't let me exclude the toString method here is how I will get
        // that coverage
        log.info("Priting an incidental to get coverage on the method: {}", app
                .getIncidentalExpenseList().get(0));
    }

    @Test
    public void presentedIsNo() throws TRAPException
    {
        testFormData.put(InputFieldKeys.JUSTIFICATION_PRESENTED, TRAPConstants.STR_NO);
        saveAndSubmitTestForm();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void presentedIsMissing() throws TRAPException
    {
        exception.expect(MissingFieldException.class);
        exception.expectMessage("Missing " + InputFieldKeys.JUSTIFICATION_PRESENTED);

        testFormData.remove(InputFieldKeys.JUSTIFICATION_PRESENTED);
        saveAndSubmitTestForm();
    }

    @Test
    public void missingPresentationTitle() throws TRAPException
    {
        exception.expect(MissingFieldException.class);
        exception.expectMessage("Missing " + InputFieldKeys.JUSTIFICATION_PRESENTATION_TITLE);

        testFormData.remove(InputFieldKeys.JUSTIFICATION_PRESENTATION_TITLE);
        saveAndSubmitTestForm();
    }

    @Test
    public void missingPresentationAbstract() throws TRAPException
    {
        exception.expect(MissingFieldException.class);
        exception.expectMessage("Missing " + InputFieldKeys.JUSTIFICATION_PRESENTATION_ABSTRACT);

        testFormData.remove(InputFieldKeys.JUSTIFICATION_PRESENTATION_ABSTRACT);
        saveAndSubmitTestForm();
    }

    @Test
    public void missingPresentationAck() throws TRAPException
    {
        exception.expect(MissingFieldException.class);
        exception.expectMessage("Missing "
                + InputFieldKeys.JUSTIFICATION_PRESENTATION_ACKNOWLEDGEMENT);

        testFormData.remove(InputFieldKeys.JUSTIFICATION_PRESENTATION_ACKNOWLEDGEMENT);
        saveAndSubmitTestForm();
    }
}
