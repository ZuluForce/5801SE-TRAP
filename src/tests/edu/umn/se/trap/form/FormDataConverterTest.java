// FormDataConverterTest.java
package edu.umn.se.trap.form;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.test.generate.TestDataGenerator;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author planeman
 * 
 */
public class FormDataConverterTest
{

    @Test
    public void testSampleForm1()
    {
        Map<String, String> form = TestDataGenerator.getSampleForm(SampleDataEnum.SAMPLE1);
        try
        {
            ReimbursementApp app = FormDataConverter.formToReimbursementApp(form);
            Assert.assertNotNull(app);
        }
        catch (TRAPException te)
        {
            te.printStackTrace();
            Assert.fail("The data converter threw an exception on valid input");
        }
    }
}
