// TRAPRuntimeException.java
package edu.umn.se.trap.exception;

/**
 * A general runtime exception in the TRAP system.
 * 
 * @author planeman
 * 
 */
public class TRAPRuntimeException extends RuntimeException
{

    @SuppressWarnings("javadoc")
    private static final long serialVersionUID = -4603643805687460429L;

    /**
     * Construct an empty exception. This is intentionally hidden from use.
     */
    protected TRAPRuntimeException()
    {
        super();
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
    }

    /**
     * Construct the exception with a message.
     * 
     * @param msg - Message to go with the exception.
     */
    public TRAPRuntimeException(String msg)
    {
        super(msg);
    }

    /**
     * Construct the exception with another Throwable exception.
     * 
     * @param t - The Throwable exception
     */
    public TRAPRuntimeException(Throwable t)
    {
        super(t);
    }

}
