// NoExportGrantsOnlyForUSCitizensTest.java
package edu.umn.se.trap.rules.business;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import edu.umn.se.test.frame.TestGrantDB;
import edu.umn.se.test.frame.TestUserDB;
import edu.umn.se.test.frame.TestUserGrantDB;
import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author nagell2008
 * 
 */
public class NoExportGrantsOnlyForUSCitizensTest extends TrapTestFramework
{

    ReimbursementApp testApp = new ReimbursementApp();
    Map<String, String> citizenForm = null;
    Map<String, String> nonCitizenForm = null;

    public NoExportGrantsOnlyForUSCitizensTest()
    {
        // Need to set a a foreign user
        TestUserDB.UserEntryBuilder builder = new TestUserDB.UserEntryBuilder();
        builder.setCitizenship("Denmark");
        builder.setEmail("blah@blah.com");
        builder.setFullname("Johnson, Swenson");
        builder.setId("123456");
        builder.setPaidByUniversity("No");
        builder.setUsername("john123");
        builder.setVisaStatus("Yes");
        userDB.addUser(builder);

        TestUserGrantDB.UserGrantBuilder ugBuilder = new TestUserGrantDB.UserGrantBuilder();
        ugBuilder.setAccount("8675309");
        ugBuilder.setAdmin("Ethan");
        userGrantDB.addUserGrantInfo(ugBuilder);

        TestGrantDB.GrantBuilder grantBuilder = new TestGrantDB.GrantBuilder();
        grantBuilder.setAccount("8675309");
        grantBuilder.setAcctype("non-sponsored");
        grantBuilder.setBalance(34000.00);
        grantBuilder.setFunder("DARPA");
        grantBuilder.setOrgType("noexport");
        grantDB.addGrant(grantBuilder);

        citizenForm = getLoadableForm(SampleDataEnum.DOMESTIC1);
        citizenForm.put("GRANT1_ACCOUNT", "8675309");

        nonCitizenForm = getLoadableForm(SampleDataEnum.DOMESTIC1);

        nonCitizenForm.put("USER_NAME", "john123");
        nonCitizenForm.put("GRANT1_ACCOUNT", "8675309");

    }

    @Test
    public void USCitizenTest()
    {
        try
        {
            setUser("linc001");
        }
        catch (TRAPException e1)
        {
            e1.printStackTrace();
            Assert.fail("Failed to set user: " + e1.getMessage());
        }
        int id = -1;

        try
        {
            id = this.saveFormData(citizenForm, "a test form");
        }
        catch (TRAPException e)
        {
            e.printStackTrace();
            Assert.fail("Failed to save form: " + e.getMessage());
        }

        try
        {
            submitFormData(id);
        }
        catch (TRAPException e)
        {
            e.printStackTrace();
            Assert.fail("Failed to submit form: " + e.getMessage());
        }

        Assert.assertEquals(true, true);

    }

    @Test
    public void foreignCitizenTest()
    {
        try
        {
            setUser("john123");
        }
        catch (TRAPException e1)
        {
            e1.printStackTrace();
            Assert.fail("Failed to set user: " + e1.getMessage());
        }
        int id = -1;

        try
        {
            id = this.saveFormData(nonCitizenForm, "a test form");
        }
        catch (TRAPException e)
        {
            e.printStackTrace();
            Assert.fail("Failed to save form: " + e.getMessage());
        }

        try
        {
            submitFormData(id);
        }
        catch (TRAPException e)
        {
            e.printStackTrace();
            Assert.assertEquals(
                    "User has citizenship: Denmark and is trying to claim a no-export grant",
                    e.getMessage());
        }

    }

}
