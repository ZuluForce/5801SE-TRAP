// DatabaseTimeoutException.java
package edu.umn.se.trap.exception;


/**
 * @author planeman
 * 
 */
public class DatabaseTimeoutException extends TRAPException
{

    /**
     * @param msg
     * @param t
     */
    public DatabaseTimeoutException(String msg, Throwable t)
    {
        super(msg, t);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param msg
     */
    public DatabaseTimeoutException(String msg)
    {
        super(msg);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param t
     */
    public DatabaseTimeoutException(Throwable t)
    {
        super(t);
        // TODO Auto-generated constructor stub
    }

}
