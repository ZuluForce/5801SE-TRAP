// AllUserFormsTests.java
package edu.umn.se.trap.form;

import static org.junit.Assert.fail;

import java.util.HashMap;

import junit.framework.Assert;

import org.junit.Test;

import edu.umn.se.trap.exception.FormStorageException;

/**
 * @author nagell2008
 * 
 */
public class AllUserFormsTests
{

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
     * Test method for {@link edu.umn.se.trap.form.AllUserForms#getUserSavedForms(java.lang.String)}
     * .
     */
    @Test
    public void testGetUserSavedForms()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link edu.umn.se.trap.form.AllUserForms#addUser(java.lang.String)}.
     */
    @Test
    public void testAddUser()
    {
        AllUserForms allForms = new AllUserForms();

        allForms.addUser("Ethan");

        int id = -1;

        try
        {
            allForms.saveFormData("Ethan", new HashMap<String, String>(), "This is a test");
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
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link edu.umn.se.trap.form.AllUserForms#saveFormData(java.lang.String, java.util.Map, java.lang.String)}
     * .
     */
    @Test
    public void testSaveFormDataStringMapOfStringStringString()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link edu.umn.se.trap.form.AllUserForms#getCompletedForm(java.lang.String, int)}.
     */
    @Test
    public void testGetCompletedForm()
    {
        fail("Not yet implemented");
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
