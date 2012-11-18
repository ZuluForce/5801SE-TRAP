// DatabaseTimeoutException.java
package edu.umn.se.trap.exception;

/**
 * An exception representing a timed out request from the database.
 * 
 * @author planeman
 * 
 */
public class DatabaseTimeoutException extends TRAPException
{

    @SuppressWarnings("javadoc")
    private static final long serialVersionUID = 2001842609870053812L;

    /**
     * Construct the exception with a message and a Throwable to encapsulate.
     * 
     * @param msg - Message for the exception.
     * @param t - Throwable exception to encapsulate.
     */
    public DatabaseTimeoutException(String msg, Throwable t)
    {
        super(msg, t);
    }

    /**
     * Construct the exception with a message.
     * 
     * @param msg - Message to go with the exception.
     */
    public DatabaseTimeoutException(String msg)
    {
        super(msg);
    }

    /**
     * Construct the exception with another Throwable exception.
     * 
     * @param t - The Throwable exception
     */
    public DatabaseTimeoutException(Throwable t)
    {
        super(t);
    }

}
