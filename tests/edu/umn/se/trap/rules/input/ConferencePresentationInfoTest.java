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
 * TODO: Requirement
 * 
 * @author planeman
 * 
 */
public class ConferencePresentationInfoTest extends TrapTestFramework
{
    private static final Logger log = LoggerFactory.getLogger(ConferenceInfo.class);

    /**
     * Load sample international form.
     * 
     * @throws TRAPException When form saving fails.
     */
    @Before
    public void setup() throws TRAPException
    {
        this.setup(SampleDataEnum.INTERNATIONAL1);

        // Read the comment below for why we are doing this.00
        ReimbursementApp app = FormDataConverter.formToReimbursementApp(testFormData);
        log.info("Base sample conference info: {}", app.getConferenceInfo());

        // Since apparently eclemma won't let me exclude the toString method here is how I will get
        // that coverage
        log.info("Priting an incidental to get coverage on the method: {}", app
                .getIncidentalExpenseList().get(0));
    }

    /**
     * Verify that there is no error when the user did not present and no presentation data is
     * provided (as expected).
     * 
     * @throws TRAPException When for submission fails.
     */
    @Test
    public void presentedIsNo() throws TRAPException
    {
        testFormData.put(InputFieldKeys.JUSTIFICATION_PRESENTED, TRAPConstants.STR_NO);
        saveAndSubmitTestForm();
    }

    @SuppressWarnings("javadoc")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Verify that processing fails when the presented boolean field (yes/no) is omitted from the
     * form.
     * 
     * @throws TRAPException When for submission fails.
     */
    @Test
    public void presentedIsMissing() throws TRAPException
    {
        exception.expect(MissingFieldException.class);
        exception.expectMessage("Missing " + InputFieldKeys.JUSTIFICATION_PRESENTED);

        testFormData.remove(InputFieldKeys.JUSTIFICATION_PRESENTED);
        saveAndSubmitTestForm();
    }

    /**
     * Verify that processing fails when the user did present and the presentation title is missing.
     * 
     * @throws TRAPException When for submission fails.
     */
    @Test
    public void missingPresentationTitle() throws TRAPException
    {
        exception.expect(MissingFieldException.class);
        exception.expectMessage("Missing " + InputFieldKeys.JUSTIFICATION_PRESENTATION_TITLE);

        testFormData.remove(InputFieldKeys.JUSTIFICATION_PRESENTATION_TITLE);
        saveAndSubmitTestForm();
    }

    /**
     * Verify that processing fails when the user did present and the presentation abstract is
     * missing.
     * 
     * @throws TRAPException When for submission fails.
     */
    @Test
    public void missingPresentationAbstract() throws TRAPException
    {
        exception.expect(MissingFieldException.class);
        exception.expectMessage("Missing " + InputFieldKeys.JUSTIFICATION_PRESENTATION_ABSTRACT);

        testFormData.remove(InputFieldKeys.JUSTIFICATION_PRESENTATION_ABSTRACT);
        saveAndSubmitTestForm();
    }

    /**
     * Verify that processing fails when the user did present and the presentation acknowledgment is
     * missing.
     * 
     * @throws TRAPException When for submission fails.
     */
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
