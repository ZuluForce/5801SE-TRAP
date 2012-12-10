// FormTests.java
package edu.umn.se.trap.form;

import java.util.Map;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author nagell2008
 * 
 */
public class FormTests extends TrapTestFramework
{

    Map<String, String> form1 = getLoadableForm(SampleDataEnum.DOMESTIC1);
    Map<String, String> form2 = getLoadableForm(SampleDataEnum.DOMESTIC1);

    public void setup() throws TRAPException
    {
        super.setup(SampleDataEnum.DOMESTIC1);

    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void saveForm() throws TRAPException
    {
        setUser("linc001");
        int id = this.saveFormData(form1, "test");
    }

    @Test
    public void getForm() throws TRAPException
    {
        setUser("linc001");
        int id = this.saveFormData(form1, "test");
        Map<String, String> tempForm = getSavedFormData(id);

        Assert.assertEquals(tempForm, form1);

    }

    @Test
    public void clearForms() throws TRAPException
    {
        exception.expect(TRAPException.class);

        setUser("linc001");
        int id = this.saveFormData(form1, "test");
        int id2 = this.saveFormData(form2, "another test");

        clearSavedForms();

        Map<String, String> tempForm = getSavedFormData(id);
    }

    @Test
    public void completedForm() throws TRAPException
    {
        setUser("linc001");
        int id = this.saveFormData(form1, "test");

        submitFormData(id);

        Map<String, String> completedForm = getCompletedForm(id);

        Map<String, String> expectedForm = this.getExpectedOutput(SampleDataEnum.DOMESTIC1);

        Assert.assertTrue(doOutputsMatch(completedForm, expectedForm));

    }
}
