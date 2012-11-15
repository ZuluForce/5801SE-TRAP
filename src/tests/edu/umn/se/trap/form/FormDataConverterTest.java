// FormDataConverterTest.java
package edu.umn.se.trap.form;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static Logger log = LoggerFactory.getLogger(FormDataConverterTest.class);

    @Test
    public void testSampleForm1()
    {
        Map<String, String> form = TestDataGenerator.getSampleForm(SampleDataEnum.SAMPLE1);
        try
        {
            ReimbursementApp app = FormDataConverter.formToReimbursementApp(form);
            log.info("ReimbursementApp:\n{}", app);
            Assert.assertNotNull(app);
        }
        catch (TRAPException te)
        {
            te.printStackTrace();
            Assert.fail("The data converter threw an exception on valid input");
        }
    }
}
