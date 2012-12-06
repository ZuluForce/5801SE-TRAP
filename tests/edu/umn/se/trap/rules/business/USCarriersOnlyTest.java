// USCarriersOnlyTest.java
package edu.umn.se.trap.rules.business;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.se.test.frame.TrapTestFramework;
import edu.umn.se.trap.exception.TRAPException;

/**
 * @author Dylan
 * 
 */
public class USCarriersOnlyTest extends TrapTestFramework
{

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() throws TRAPException
    {

    }

    @Test
    public void validUSCarrier() throws TRAPException
    {

    }

}
