// FromContainerTests.java
package edu.umn.se.trap.form;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.TravelFormProcessorIntf;

/**
 * @author nagell2008
 * 
 */
public class FromContainerTests
{

    private static Logger log = LoggerFactory.getLogger(AllUserFormsTests.class);

    /**
     * Test method for
     * {@link edu.umn.se.trap.form.FormContainer#FormContainer(java.util.Map, java.lang.String)}.
     */
    @Test
    public void testFormContainer()
    {
        FormContainer tempFC = new FormContainer(new HashMap<String, String>(),
                "this is a test description");

        Assert.assertNotNull(tempFC);
        log.info("Form Container: {}", tempFC);
    }

    /**
     * Test method for {@link edu.umn.se.trap.form.FormContainer#getDescription()}.
     */
    @Test
    public void testGetDescription()
    {

        FormContainer tempFC = new FormContainer(new HashMap<String, String>(),
                "this is a test description");

        Assert.assertEquals("this is a test description", tempFC.getDescription());
    }

    /**
     * Test method for {@link edu.umn.se.trap.form.FormContainer#setDescription(java.lang.String)}.
     */
    @Test
    public void testSetDescription()
    {
        Map<String, String> tempForm = new HashMap<String, String>();

        tempForm.put("test", "testValue");

        FormContainer tempFC = new FormContainer(tempForm, "this is a test description");

        tempFC.setDescription("this is a new test description");

        Assert.assertNotSame(tempFC.getDescription(), "this is a test description");

        Assert.assertEquals(tempFC.getDescription(), "this is a new test description");

    }

    /**
     * Test method for {@link edu.umn.se.trap.form.FormContainer#getForm()}.
     */
    @Test
    public void testGetForm()
    {
        Map<String, String> tempForm = new HashMap<String, String>();

        tempForm.put("test", "testValue");

        FormContainer tempFC = new FormContainer(tempForm, "this is a test description");

        tempFC.saveForm(tempForm);

        Map<String, String> returnedForm = tempFC.getForm();

        Assert.assertEquals(returnedForm, tempForm);

        log.info("Map: {}", returnedForm);

    }

    /**
     * Test method for {@link edu.umn.se.trap.form.FormContainer#saveForm(java.util.Map)}.
     */
    @Test
    public void testSaveFormMapOfStringString()
    {
        Map<String, String> tempForm = new HashMap<String, String>();

        tempForm.put("test", "testValue");

        FormContainer tempFC = new FormContainer(tempForm, "this is a test description");

        tempFC.saveForm(tempForm);

        Map<String, String> grabbedForm = tempFC.getForm();

        Assert.assertEquals(grabbedForm, tempForm);
    }

    /**
     * Test method for
     * {@link edu.umn.se.trap.form.FormContainer#saveForm(java.util.Map, java.lang.String)}.
     */
    @Test
    public void testSaveFormMapOfStringStringString()
    {
        Map<String, String> tempForm = new HashMap<String, String>();

        tempForm.put("test", "testValue");

        FormContainer tempFC = new FormContainer(tempForm, "this is a test description");

        tempFC.saveForm(tempForm);

        Map<String, String> newForm = new HashMap<String, String>();

        newForm.put("test2", "test2Value");

        tempFC.saveForm(newForm, "this is a new test description");

        Assert.assertEquals(tempFC.getForm(), newForm);

        Assert.assertEquals(tempFC.getDescription(), "this is a new test description");

    }

    /**
     * Test method for {@link edu.umn.se.trap.form.FormContainer#getStatus()}.
     */
    @Test
    public void testGetStatus()
    {
        Map<String, String> tempForm = new HashMap<String, String>();

        tempForm.put("test", "testValue");

        FormContainer tempFC = new FormContainer(tempForm, "this is a test description");

        Assert.assertEquals(TravelFormProcessorIntf.FORM_STATUS.DRAFT, tempFC.getStatus());

    }

    /**
     * Test method for
     * {@link edu.umn.se.trap.form.FormContainer#setStauts(edu.umn.se.trap.TravelFormProcessorIntf.FORM_STATUS)}
     * .
     */
    @Test
    public void testSetStauts()
    {
        Map<String, String> tempForm = new HashMap<String, String>();

        tempForm.put("test", "testValue");

        FormContainer tempFC = new FormContainer(tempForm, "this is a test description");

        Assert.assertEquals(TravelFormProcessorIntf.FORM_STATUS.DRAFT, tempFC.getStatus());

        tempFC.setStauts(TravelFormProcessorIntf.FORM_STATUS.SUBMITTED);

        Assert.assertEquals(TravelFormProcessorIntf.FORM_STATUS.SUBMITTED, tempFC.getStatus());

    }

}
