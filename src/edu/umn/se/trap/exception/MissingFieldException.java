/**
 * 
 */
package edu.umn.se.trap.exception;

/**
 * @author planeman
 * 
 */
public class MissingFieldException extends TRAPException
{

    /**
     * @param msg
     * @param t
     */
    public MissingFieldException(String msg, Throwable t)
    {
        super(msg, t);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     */
    public MissingFieldException(String msg)
    {
        super(msg);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param t
     */
    public MissingFieldException(Throwable t)
    {
        super(t);
        // TODO Auto-generated constructor stub
    }

}
