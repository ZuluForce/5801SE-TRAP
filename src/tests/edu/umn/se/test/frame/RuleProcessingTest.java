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
// RuleProcessingTest.java
package edu.umn.se.test.frame;

import static org.junit.Assert.fail;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.util.PrettyPrint;

/**
 * @author andrewh
 * 
 */
public class RuleProcessingTest extends TrapTestFramework
{
    /** Logger for the RuleProcessingTest */
    private static Logger log = LoggerFactory.getLogger(RuleProcessingTest.class);

    /**
     * This is a simple black box test of the form processing. A sample form is generated, it is
     * processed and its saved output is compared to the expected, nothing more.
     */
    @Test
    public void fullFormProcessTest()
    {
        try
        {
            setValidUser();

            int id = this.addRandomForm();
            submitFormData(id);

            Map<String, String> expected = getExpectedOutput(id);
            Map<String, String> output = getCompletedForm(id);
            log.info("\nOutput map: {}", PrettyPrint.prettyMap(output));

            if (expected.size() != output.size())
            {
                Assert.fail(String.format("Output is not expected size. output={}  expected={}",
                        output.size(), expected.size()));
            }

            Assert.assertTrue("Output map not equal to expected", doOutputsMatch(output, expected));
        }
        catch (TRAPException e)
        {
            e.printStackTrace();
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}
