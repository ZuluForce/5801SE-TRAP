// AllUserFormsTests.java
package edu.umn.se.trap.form;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.exception.FormStorageException;

/**
 * @author nagell2008
 * 
 */
public class AllUserFormsTests
{

    private static Logger log = LoggerFactory.getLogger(AllUserFormsTests.class);

    /**
     * Test method for {@link edu.umn.se.trap.form.AllUserForms#AllUserForms()}.
     */
    @Test
    public void testAllUserForms()
    {
        AllUserForms allForms = new AllUserForms();

        Assert.assertNotNull(allForms);
    }

    /**
     * Test method for {@link edu.umn.se.trap.form.AllUserForms#addUser(java.lang.String)}.
     */
    @Test
    public void testAddUser()
    {
        AllUserForms allForms = new AllUserForms();

        allForms.addUser("test");

        int id = -1;

        try
        {
            Map<String, String> temp = new HashMap<String, String>();
            temp.put("test", "testValue");
            id = allForms.saveFormData("test", temp, "This is a test");

            Map<String, String> form = allForms.getSavedFormData("test", id);

            Assert.assertEquals(form.containsKey("test"), true);
            Assert.assertEquals(form.get("test"), "testValue");

            log.info("Map:{}", form);

        }
        catch (FormStorageException f)
        {
            f.printStackTrace();
            Assert.fail(f.getMessage());
        }

    }

    /**
     * Test method for
     * {@link edu.umn.se.trap.form.AllUserForms#saveFormData(java.lang.String, java.util.Map, int)}.
     */
    @Test
    public void testSaveFormDataStringMapOfStringStringInt()
    {
        AllUserForms allForms = new AllUserForms();

        allForms.addUser("test");

        int id = -1;
        int sameID = -1;

        Map<String, String> temp = new HashMap<String, String>();

        temp.put("test", "testValue");

        try
        {
            id = allForms.saveFormData("test", temp, "a test form");
            Assert.assertTrue(id != -1);
        }
        catch (FormStorageException f)
        {
            f.printStackTrace();
            Assert.fail(f.getMessage());
        }

        temp.put("test2", "test2Value");

        try
        {
            sameID = allForms.saveFormData("test", temp, id);
            log.info("Map: {}", temp);

            Assert.assertEquals(id, sameID);

        }
        catch (FormStorageException f)
        {
            f.printStackTrace();
            Assert.fail(f.getMessage());
        }

    }

    /**
     * Test method for
     * {@link edu.umn.se.trap.form.AllUserForms#saveFormData(java.lang.String, java.util.Map, java.lang.String)}
     * .
     */
    @Test
    public void testSaveFormDataStringMapOfStringStringString()
    {
        AllUserForms allForms = new AllUserForms();

        allForms.addUser("test");

        int id = -1;
        int sameID = -1;

        Map<String, String> temp = new HashMap<String, String>();

        temp.put("test", "testValue");

        try
        {
            id = allForms.saveFormData("test", temp, "a test form");
            Assert.assertTrue(id != -1);
        }
        catch (FormStorageException f)
        {
            f.printStackTrace();
            Assert.fail(f.getMessage());
        }

    }

    /**
     * Test method for
     * {@link edu.umn.se.trap.form.AllUserForms#getCompletedForm(java.lang.String, int)}.
     */
    @Test
    public void testGetCompletedForm()
    {
        AllUserForms allForms = new AllUserForms();

        allForms.addUser("test");

        int id = -1;

        Map<String, String> temp = new HashMap<String, String>();

        temp.put("test", "testValue");

        try
        {
            id = allForms.saveFormData("test", temp, "a test form");
            Assert.assertTrue(id != -1);

            log.info("Map: {}", allForms.getSavedFormData("test", id));
        }
        catch (FormStorageException f)
        {
            f.printStackTrace();
            Assert.fail(f.getMessage());
        }

        temp.put("test2", "testCompleted");

        try
        {
            allForms.saveCompletedForm("test", temp, id);
            Map<String, String> completedForm = allForms.getCompletedForm("user", id);

            Assert.assertEquals(completedForm.get("test2"), "testCompleted");

        }
        catch (FormStorageException f)
        {
            f.printStackTrace();
            Assert.fail(f.getMessage());
        }

    }

    /**
     * Test method for
     * {@link edu.umn.se.trap.form.AllUserForms#getSavedFormData(java.lang.String, int)}.
     */
    @Test
    public void testGetSavedFormData()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link edu.umn.se.trap.form.AllUserForms#getSavedForms(java.lang.String)}.
     */
    @Test
    public void testGetSavedForms()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link edu.umn.se.trap.form.AllUserForms#saveCompletedForm(java.lang.String, java.util.Map, int)}
     * .
     */
    @Test
    public void testSaveCompletedForm()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link edu.umn.se.trap.form.AllUserForms#clearSavedForms(java.lang.String)}.
     */
    @Test
    public void testClearSavedForms()
    {
        fail("Not yet implemented");
    }

}
