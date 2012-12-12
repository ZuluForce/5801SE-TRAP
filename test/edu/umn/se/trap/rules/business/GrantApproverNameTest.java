/*****************************************************************************************
 * Copyright (c) 2012 Dylan Bettermann, Andrew Helgeson, Brian Maurer, Ethan Waytas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 ****************************************************************************************/
// GrantApproverName.java
package edu.umn.se.trap.rules.business;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.data.ReimbursementApp;
import edu.umn.se.trap.data.UserInfo;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * This class fulfills requirement 3.c, that the user's name is not printed out if the user is the
 * grant approver.
 * 
 * @author nagell2008
 */
public class GrantApproverNameTest extends TrapTestFramework
{

    String testUser = "";

    UserInfo user;

    ReimbursementApp testApp = new ReimbursementApp();

    GrantApproverName rule = new GrantApproverName();

    /**
     * Setup code for tests
     */
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

    /**
     * Checks that the username is not in the output, as the user is the grant approver.
     */
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
