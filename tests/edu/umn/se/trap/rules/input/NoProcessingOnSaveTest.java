// NoProcessingOnSaveTest.java
package edu.umn.se.trap.rules.input;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.TravelFormMetadata;
import edu.umn.se.trap.TravelFormProcessorIntf;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.InputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * This test case checks requirement 1.a which states that no processing of a form shall occur on
 * save.
 * 
 * @author planeman
 * 
 */
public class NoProcessingOnSaveTest extends TrapTestFramework
{

    private Integer formId;
    private Map<String, String> formData;

    /**
     * Save an initial form for test cases to use.
     * 
     * @throws TRAPException
     */
    @Before
    public void setup() throws TRAPException
    {
        setValidUser();

        formData = getLoadableForm(SampleDataEnum.DOMESTIC1);

        formId = this.saveFormData(formData, "A test form");
    }

    /**
     * Test that when a form is saved it is not processed. This is verified by checking the status
     * on the form's metadata which is supposed to be saved along with the form data.
     * 
     * This test specifically checks that the form status is not modified upon saving a new form
     * with a description.
     * 
     * @throws Exception
     */
    @Test
    public void formStillDraftAfterSaveNew() throws Exception
    {
        Map<Integer, TravelFormMetadata> formMetaData = getSavedForms();

        Assert.assertTrue("Saved form not in metadata map", formMetaData.containsKey(formId));

        TravelFormMetadata savedFormMetaData = formMetaData.get(formId);
        Assert.assertTrue("Saved form changed status",
                savedFormMetaData.status == TravelFormProcessorIntf.FORM_STATUS.DRAFT);
    }

    /**
     * Test that when a form is saved it is not processed. This is verified by checking the status
     * on the form's metadata which is supposed to be saved along with the form data.
     * 
     * This test specifically checks that the form status is not modified upon saving over an
     * existing form id.
     * 
     * @throws TRAPException
     * 
     * @throws Exception
     */
    @Test
    public void formUnmodifiedAfterSaveOld() throws TRAPException
    {
        // Modify the original form and re-save under the same id
        formData.put(InputFieldKeys.NUM_DAYS, "-1");
        this.saveFormData(formData, formId);

        Map<Integer, TravelFormMetadata> formMetaData = getSavedForms();

        Assert.assertTrue("Saved form not in metadata map", formMetaData.containsKey(formId));

        TravelFormMetadata savedFormMetaData = formMetaData.get(formId);
        Assert.assertTrue("Saved form changed status",
                savedFormMetaData.status == TravelFormProcessorIntf.FORM_STATUS.DRAFT);
    }
}
