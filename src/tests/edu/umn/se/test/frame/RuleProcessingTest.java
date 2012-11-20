// RuleProcessingTest.java
package edu.umn.se.test.frame;

import static org.junit.Assert.fail;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.exception.TRAPException;

/**
 * @author planeman
 * 
 */
public class RuleProcessingTest extends TrapTestFramework
{
    private static Logger log = LoggerFactory.getLogger(RuleProcessingTest.class);

    /**
     * This is a simple black box test of the form processing. A sample form is generated, it is
     * processed and its saved output is compared to the expected, nothing more.
     */
    @Test
    public void fullFormProcessTest()
    {
        try
        {
            setValidUser();

            int id = this.addRandomForm();
            submitFormData(id);

            Map<String, String> expected = getExpectedOutput(id);
            Map<String, String> output = getCompletedForm(id);
            log.info("Output map: {}", output);
            Assert.assertTrue("Output map is not equal to the expected", expected.equals(output));
        }
        catch (TRAPException e)
        {
            e.printStackTrace();
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}
