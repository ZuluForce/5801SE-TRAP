// OuputAvailableAfterProcessingTest.java
package edu.umn.se.trap.rules.output;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.test.generate.LoadedSampleForm;
import edu.umn.se.trap.test.generate.TestDataGenerator;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * For requirement 5.a and 5.b
 * 
 * @author planeman
 * 
 */
public class OuputAvailableAfterProcessingTest extends TrapTestFramework
{

    @Before
    public void setup() throws TRAPException
    {
        super.setup(SampleDataEnum.DOMESTIC1);
    }

    @Test
    public void submitAndCheckOutput() throws TRAPException
    {
        saveAndSubmitTestForm();
    }

    @Test
    public void originalFormAvailableAfterError() throws TRAPException
    {
        setValidUser(SampleDataEnum.INTERNATIONAL1);
        try
        {
            submitAndCheckOutput();
        }
        catch (TRAPException error)
        {
            // What we want
            LoadedSampleForm originalForm = TestDataGenerator
                    .getSampleForm(SampleDataEnum.DOMESTIC1);

            setValidUser(SampleDataEnum.DOMESTIC1); // Have to change back so I can get the form
            Map<String, String> savedForm = getSavedFormData(testFormId);
            Assert.assertEquals(originalForm, savedForm);
        }
    }
}
