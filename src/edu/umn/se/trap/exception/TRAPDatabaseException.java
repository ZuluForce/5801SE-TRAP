// TRAPDatabaseException.java
package edu.umn.se.trap.exception;

/**
 * @author planeman
 * 
 */
public class TRAPDatabaseException extends TRAPRuntimeException
{

    @SuppressWarnings("javadoc")
    private static final long serialVersionUID = -7551548018692821650L;

    /**
     * Construct an empty exception. This is intentionally hidden from use.
     */
    protected TRAPDatabaseException()
    {
        super();
    }

    /**
     * Construct the exception with a message and a Throwable to encapsulate.
     * 
     * @param msg - Message for the exception.
     * @param t - Throwable exception to encapsulate.
     */
    public TRAPDatabaseException(String msg, Throwable t)
    {
        super(msg, t);
    }

    /**
     * Construct the exception with a message.
     * 
     * @param msg - Message to go with the exception.
     */
    public TRAPDatabaseException(String msg)
    {
        super(msg);
    }

    /**
     * Construct the exception with another Throwable exception.
     * 
     * @param t - The Throwable exception
     */
    public TRAPDatabaseException(Throwable t)
    {
        super(t);
    }
}
