// LodgingPerDiemTest.java
package edu.umn.se.trap.rules.business;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.TRAPException;
import edu.umn.se.trap.test.generate.LoadedSampleForm;

/**
 * @author Dylan
 * 
 */
public class LodgingPerDiemTest extends TrapTestFramework
{
    Integer formId;
    LoadedSampleForm formData;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    public void setup() throws TRAPException
    {
        setValidUser();
    }

}
