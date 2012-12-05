// VisaStatusOutputTest.java
package edu.umn.se.trap.rules.output;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import edu.umn.se.test.frame.TestUserDB;
import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.data.TRAPConstants;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.OutputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author planeman
 * 
 */
public class VisaStatusOutputTest extends TrapTestFramework
{

    TestUserDB.UserEntryBuilder userDBInfo;

    @Before
    public void setup() throws TRAPException
    {
        super.setup(SampleDataEnum.INTERNATIONAL1);

        userDBInfo = userDB.fillBuilderWithUserInfo(TestDataGenerator
                .getUserForForm(SampleDataEnum.INTERNATIONAL1));
    }

    @Test
    public void noStatusForUSCitizen() throws TRAPException
    {
        saveAndSubmitTestForm();
        Map<String, String> output = getCompletedForm(testFormId);

        Assert.assertFalse("output contains visa status for US citizen",
                output.containsKey(OutputFieldKeys.VISA_STATUS));
    }

    @Test
    public void statusForNonCitizen() throws TRAPException
    {
        userDBInfo.setCitizenship(TRAPConstants.STR_NO);
        userDBInfo.setVisaStatus("A OK");
        userDB.addUser(userDBInfo);

        saveAndSubmitTestForm();

        Map<String, String> output = getCompletedForm(testFormId);
        Assert.assertTrue("Output missing visa status for non US citizen",
                output.containsKey(OutputFieldKeys.VISA_STATUS));

        String status = output.get(OutputFieldKeys.VISA_STATUS);
        Assert.assertEquals(status, "A OK");
    }

    @Test
    public void statusAvailableButIsUSCitizen() throws TRAPException
    {
        userDBInfo.setVisaStatus("A OK");
        userDB.addUser(userDBInfo);

        saveAndSubmitTestForm();

        Map<String, String> output = getCompletedForm(testFormId);
        Assert.assertFalse("output contains visa status for US citizen",
                output.containsKey(OutputFieldKeys.VISA_STATUS));
    }
}
