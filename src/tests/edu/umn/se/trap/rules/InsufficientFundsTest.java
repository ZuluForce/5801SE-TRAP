// InsufficientFundsTest.java
package edu.umn.se.trap.rules;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.test.generate.TestDataGenerator;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author planeman
 * 
 */
public class InsufficientFundsTest extends TrapTestFramework
{

    public InsufficientFundsTest()
    {
        super();
    }

    @Test
    public void testExtremelyLargeExpense() throws TRAPException
    {
        setValidUser(); // Sets a known user as the current one in TRAP
        Map<String, String> form = TestDataGenerator.getSampleForm(SampleDataEnum.SAMPLE1);
        form.put("OTHER2_AMOUNT", new Double(Double.MAX_VALUE).toString());

        Integer id = this.saveFormData(form, "a very expensive trip");

        try
        {
            submitFormData(id);
            Assert.fail("Very expensive reimbursement should not have passed");
        }
        catch (TRAPException e)
        {
            ; // Good
        }
    }
}
