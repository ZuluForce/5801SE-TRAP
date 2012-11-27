// FormDataLoaderTest.java
package edu.umn.se.trap.util;

import java.util.Properties;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author planeman
 * 
 */
public class FormDataLoaderTest
{

    @Test
    public void testInvalidFilenameException()
    {
        try
        {
            Properties props = FormDataLoader.loadFormData("nonExistantFile.crazyext", false);
            Assert.fail("No exception thrown when property file loading should have failed");
        }
        catch (RuntimeException re)
        {
            ; // Good
        }
    }

    @Test
    public void testInvalidFilenameNoException()
    {
        Properties props = FormDataLoader.loadFormData("nonExistantFile.crazyext", true);
        Assert.assertNotNull(props);
        Assert.assertEquals(0, props.size());
    }
}
