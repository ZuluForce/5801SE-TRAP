// UserFullNameTest.java
package edu.umn.se.trap.rules.output;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.form.OutputFieldKeys;
import edu.umn.se.trap.test.generate.TestDataGenerator.SampleDataEnum;

/**
 * @author planeman
 * 
 */
public class UserFullNameTest extends TrapTestFramework
{
    private static final Logger log = LoggerFactory.getLogger(UserFullNameTest.class);
    private final Pattern fullnamePattern = Pattern.compile(
            "(?:\\w+\\s+)?\\w+,\\s*\\w+(?:\\s+\\w+)?", Pattern.CASE_INSENSITIVE);

    @Before
    public void setup() throws TRAPException
    {
        this.setup(SampleDataEnum.INTERNATIONAL1);
    }

    @Test
    public void validFullName() throws TRAPException
    {
        saveAndSubmitTestForm();

        Map<String, String> output = getCompletedForm(testFormId);
        Assert.assertTrue(output.containsKey(OutputFieldKeys.FULL_NAME));

        String name = output.get(OutputFieldKeys.FULL_NAME);
        log.info("Full name: {}", name);
        Matcher nameMatcher = fullnamePattern.matcher(name);
        Assert.assertTrue(nameMatcher.matches());
    }
}
