/**
 * 
 */
package edu.umn.se.trap.exception;

/**
 * @author planeman
 * 
 */
public abstract class TRAPException extends Exception
{
    private static final long serialVersionUID = -1124664119379547664L;

    protected TRAPException(String msg)
    {
        super(msg);
    }

    protected TRAPException(String msg, Throwable t)
    {
        super(msg, t);
    }

    protected TRAPException(Throwable t)
    {
        super(t);
    }

}
