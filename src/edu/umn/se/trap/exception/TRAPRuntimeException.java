// TRAPRuntimeException.java
package edu.umn.se.trap.exception;

/**
 * @author planeman
 * 
 */
public class TRAPRuntimeException extends RuntimeException
{

    /**
     * Construct an empty exception. This is intentionally hidden from use.
     */
    protected TRAPRuntimeException()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Construct the exception with a message and a Throwable to encapsulate.
     * 
     * @param msg - Message for the exception.
     * @param t - Throwable exception to encapsulate.
     */
    public TRAPRuntimeException(String msg, Throwable t)
    {
        super(msg, t);
        // TODO Auto-generated constructor stub
    }

    /**
     * Construct the exception with a message.
     * 
     * @param msg - Message to go with the exception.
     */
    public TRAPRuntimeException(String msg)
    {
        super(msg);
        // TODO Auto-generated constructor stub
    }

    /**
     * Construct the exception with another Throwable exception.
     * 
     * @param t - The Throwable exception
     */
    public TRAPRuntimeException(Throwable t)
    {
        super(t);
        // TODO Auto-generated constructor stub
    }

}
