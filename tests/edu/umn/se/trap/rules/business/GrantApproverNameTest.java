// GrantApproverName.java
package edu.umn.se.trap.rules.business;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.test.frame.RuleProcessingTest;
import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.UserInfo;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author nagell2008
 * 
 */
public class GrantApproverNameTest extends TrapTestFramework
{
    private static Logger log = LoggerFactory.getLogger(RuleProcessingTest.class);

    String testUser = "";

    UserInfo user;

    ReimbursementApp testApp = new ReimbursementApp();

    GrantApproverName rule = new GrantApproverName();

    public GrantApproverNameTest()
    {
        testUser = "heimd001";
        user = new UserInfo();

        user.setUsername(testUser);
        user.setCitizenship("United States");
        user.setEmailAddress("blah@blah.com");
        user.setEmergencyContactName("Bob Newhart");
        user.setEmergencycontactPhone("123-456-7890");
        user.setFullName("Mats Heimdahl");
        user.setVisaStatus(null);
        user.setPaidByUniversity("Yes");

    }

    @Test
    public void userNotInOutput()
    {
        Map<String, String> form = getLoadableForm(SampleDataEnum.DOMESTIC1);

        // testApp.setUserInfo(user);
        form.put("USER_NAME", "heimd001");

        try
        {
            setUser("heimd001");
        }
        catch (TRAPException e1)
        {
            e1.printStackTrace();
            Assert.fail("Failed to set user: " + e1.getMessage());
        }
        int id = -1;

        try
        {
            id = this.saveFormData(form, "a test form");
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

        Map<String, String> completedForm = null;

        try
        {
            completedForm = getCompletedForm(id);
        }
        catch (TRAPException e)
        {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }

        Assert.assertEquals(null, completedForm.get("GRANT1_APPROVER_NAME"));

    }
}
